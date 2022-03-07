package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.ProductDAO;
import dto.CartDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.ProductDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.EmailSenderUtils;
import utils.ServletUtils;

public class CheckoutController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CheckoutController.class.getName());

    private static final String VIEW = "/WEB-INF/view/checkout.jsp";
    private static final String ERROR = "/WEB-INF/view/checkout.jsp";
    private static final String LOGIN_ERROR = "LoginController";
    private static final String NOT_USER_ERROR = "HomeController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        boolean isRedirect = false;

        try {
            ImmutableTriple<Boolean, Boolean, String> checkUserResult = doCheckUser(request, response);
            if (checkUserResult.getLeft()) {
                String action = request.getParameter("action");
                if (action == null) {
                    action = "View";
                }
                doView(request, response);

                switch (action) {
                    case "Cancel":
                        doCancel(request, response);
                        break;
                    case "Order":
                        doOrder(request, response);
                        break;
                }

                doView(request, response);
                url = VIEW;
            } else {
                isRedirect = checkUserResult.getMiddle();
                url = checkUserResult.getRight();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        } finally {
            ServletUtils.doRedirectOrForward(isRedirect, url, request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private ImmutableTriple<Boolean, Boolean, String> doCheckUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");

        if (user == null) {
            return ImmutableTriple.of(false, true, LOGIN_ERROR);
        }

        if (user.getRole() != 0) {
            return ImmutableTriple.of(false, true, NOT_USER_ERROR);
        }

        return ImmutableTriple.of(true, false, VIEW);
    }

    private void doView(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> products = productDAO.getAll();

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");
        if (cart == null) {
            return;
        }

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();
        for (Map.Entry<ProductDTO, Integer> p : productsInCart.entrySet()) {
            ProductDTO key = p.getKey();
            Integer value = p.getValue();

            int index = products.indexOf(key);
            if (index == -1) {
                productsInCart.remove(key);
                continue;
            }

            ProductDTO product = products.get(index);
            if (value > product.getQuantity()) {
                request.setAttribute("PRODUCT_ERROR", "Some products that have exceeded the quantity in stock, will be set to the residual value");
                productsInCart.put(key, product.getQuantity());
            }
        }
    }

    private void doCancel(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("CART", new CartDTO());
    }

    private void doOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");

        if (cart == null) {
            return;
        }

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();
        if (productsInCart.isEmpty()) {
            return;
        }

        long now = System.currentTimeMillis();
        String orderID = Long.toString(now);
        String userID = ((UserDTO) session.getAttribute("CURRENT_USER")).getUserID();
        Timestamp date = new Timestamp(now);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        OrderDAO orderDAO = new OrderDAO();
        OrderDTO order = new OrderDTO(orderID, userID, date, email, phone, address, cart.getTotal());
        if (!orderDAO.create(order)) {
            return;
        }

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ProductDAO productDAO = new ProductDAO();
        for (Map.Entry<ProductDTO, Integer> p : productsInCart.entrySet()) {
            ProductDTO key = p.getKey();
            Integer value = p.getValue();

            orderDetailDAO.create(new OrderDetailDTO(orderID, key.getProductID(), value, key.getPrice()));
            key.setQuantity(key.getQuantity() - value);
            productDAO.update(key);
        }

        doCancel(request, response);
        request.setAttribute("SUCCESS", "Your order has been successfully");

        new Thread(new Runnable() {
            @Override
            public void run() {
                EmailSenderUtils.send(email, order);
            }
        }).start();
    }
}
