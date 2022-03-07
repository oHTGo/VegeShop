package controller;

import dao.ProductDAO;
import dto.CartDTO;
import dto.ProductDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.ServletUtils;

public class HomeController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

    private static final String VIEW = "/WEB-INF/view/home.jsp";
    private static final String ERROR = "/WEB-INF/view/home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        boolean isRedirect = false;

        try {
            String action = request.getParameter("action");

            if (action == null) {
                action = "View";
            }
            doView(request, response);

            switch (action) {
                case "Add":
                    doAdd(request, response);
                    break;
                case "Update":
                    doUpdate(request, response);
                    break;
                case "Remove":
                    doRemove(request, response);
                    break;

            }

            doView(request, response);
            url = VIEW;
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

    private void doView(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ProductDAO dao = new ProductDAO();
        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        }
        List<ProductDTO> products = dao.getAllSearch(search);
        request.setAttribute("PRODUCTS", products);

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");
        if (cart == null) {
            cart = new CartDTO();
        }
        session.setAttribute("CART", cart);

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();
        for (Map.Entry<ProductDTO, Integer> p : productsInCart.entrySet()) {
            ProductDTO key = p.getKey();
            if (!products.contains(key)) {
                productsInCart.remove(key);
            }
        }
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String productID = request.getParameter("productID");
        String quantity = request.getParameter("quantity");

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");

        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.get(Integer.parseInt(productID));

        if (product == null) {
            return;
        }

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();

        if (!productsInCart.containsKey(product)) {
            productsInCart.put(product, Integer.parseInt(quantity));
        } else {
            Integer productQuantity = productsInCart.get(product);
            productsInCart.put(product, Integer.parseInt(quantity) + productQuantity);
        }

        session.setAttribute("CART", cart);
    }

    private void doUpdate(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String productID = request.getParameter("productID");
        String quantity = request.getParameter("quantity");

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");

        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.get(Integer.parseInt(productID));

        if (product == null) {
            return;
        }

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();
        productsInCart.put(product, Integer.parseInt(quantity));

        session.setAttribute("CART", cart);
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String productID = request.getParameter("productID");

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");

        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.get(Integer.parseInt(productID));

        if (product == null) {
            return;
        }

        HashMap<ProductDTO, Integer> productsInCart = cart.getProducts();
        productsInCart.remove(product);

        session.setAttribute("CART", cart);
    }
}
