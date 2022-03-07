package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import dto.CategoryDTO;
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

public class AdminController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    private static final String VIEW = "/WEB-INF/view/admin.jsp";
    private static final String ERROR = "/WEB-INF/view/admin.jsp";
    private static final String LOGIN_ERROR = "LoginController";
    private static final String NOT_ADMIN_ERROR = "HomeController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        boolean isRedirect = false;
        String url = ERROR;

        try {
            ImmutableTriple<Boolean, Boolean, String> checkAdminResult = doCheckAdmin(request, response);

            if (checkAdminResult.getLeft()) {
                String action = request.getParameter("action");
                if (action == null) {
                    action = "View";
                }
                doView(request, response);

                switch (action) {
                    case "AddCategory":
                        doAddCategory(request, response);
                        break;
                    case "UpdateCategory":
                        doUpdateCategory(request, response);
                        break;
                    case "AddProduct":
                        doAddProduct(request, response);
                        break;
                    case "UpdateProduct":
                        doUpdateProduct(request, response);
                        break;
                    case "RemoveProduct":
                        doRemoveProduct(request, response);
                        break;
                }

                doView(request, response);
                url = VIEW;
            } else {
                isRedirect = checkAdminResult.getMiddle();
                url = checkAdminResult.getRight();
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

    private ImmutableTriple<Boolean, Boolean, String> doCheckAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");

        if (user == null) {
            return ImmutableTriple.of(false, true, LOGIN_ERROR);
        }

        if (user.getRole() != 1) {
            return ImmutableTriple.of(false, true, NOT_ADMIN_ERROR);
        }

        return ImmutableTriple.of(true, false, VIEW);
    }

    private void doView(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> categories = categoryDAO.getAll();
        request.setAttribute("CATEGORIES", categories);

        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> products = productDAO.getAllSearch("");
        request.setAttribute("PRODUCTS", products);
    }

    private void doAddCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("categoryName");

        CategoryDAO dao = new CategoryDAO();
        if (!dao.create(categoryID, categoryName)) {
            request.setAttribute("CATEGORY_ERROR", "Add failed");
            return;
        }
    }

    private void doUpdateCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("categoryName");

        CategoryDAO dao = new CategoryDAO();
        if (!dao.update(categoryID, categoryName)) {
            request.setAttribute("CATEGORY_ERROR", "Update failed");
            return;
        }
    }

    private void doAddProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String image = request.getParameter("image");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer categoryID = Integer.parseInt(request.getParameter("categoryID"));

        ProductDAO dao = new ProductDAO();
        if (!dao.create(new ProductDTO(productID, productName, image, quantity, price, categoryID, ""))) {
            request.setAttribute("PRODUCT_ERROR", "Add failed");
        }
    }

    private void doUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String image = request.getParameter("image");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer categoryID = Integer.parseInt(request.getParameter("categoryID"));

        ProductDAO dao = new ProductDAO();
        if (!dao.update(new ProductDTO(productID, productName, image, quantity, price, categoryID, ""))) {
            request.setAttribute("PRODUCT_ERROR", "Update failed");
        }
    }

    private void doRemoveProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer productID = Integer.parseInt(request.getParameter("productID"));

        ProductDAO dao = new ProductDAO();
        if (!dao.remove(productID)) {
            request.setAttribute("PRODUCT_ERROR", "Remove failed");
        }
    }

}
