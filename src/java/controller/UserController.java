package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.OrderDTO;
import dto.ProductDTO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.ServletUtils;

public class UserController extends HttpServlet {
    
    private final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private static final String VIEW = "/WEB-INF/view/user.jsp";
    private static final String ERROR = "/WEB-INF/view/admin.jsp";
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
        String orderID = request.getParameter("orderID");

        OrderDAO orderDAO = new OrderDAO();
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");

        if (orderID != null) {
            OrderDTO order = orderDAO.get(orderID);

            if (order == null) {
                return;
            }

            if (!order.getUserID().equals(user.getUserID())) {
                return;
            }

            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<ProductDTO> orderProducts = orderDetailDAO.getOrderProducts(orderID);
            request.setAttribute("ORDER_PRODUCTS", orderProducts);
        } else {
            List<OrderDTO> orders = orderDAO.getAll(user.getUserID());
            request.setAttribute("ORDERS", orders);
        }
    }
}
