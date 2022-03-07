package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.ServletUtils;

public class RegisterController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    private static final String VIEW = "/WEB-INF/view/register.jsp";
    private static final String SUCCESS = "HomeController";
    private static final String ERROR = "/WEB-INF/view/register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        boolean isRedirect = false;

        try {
            String action = request.getParameter("action");

            if (action == null) {
                url = VIEW;
            } else if (action.equals("Register")) {
                ImmutablePair<Boolean, String> result = doRegister(request, response);
                isRedirect = result.getLeft();
                url = result.getRight();
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
    }// </editor-fold>

    private ImmutablePair<Boolean, String> doRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("REGISTER_ERROR", "Password and confirm password do not match");
            return ImmutablePair.of(false, ERROR);
        }

        UserDAO dao = new UserDAO();
        UserDTO user = new UserDTO(userID, password, email, fullName, phone, address, 0);

        if (dao.checkLoginByEmail(email) != null) {
            request.setAttribute("REGISTER_ERROR", "Email already exists");
            return ImmutablePair.of(false, ERROR);
        }

        if (!dao.create(user)) {
            request.setAttribute("REGISTER_ERROR", "Register user failed");
            return ImmutablePair.of(false, ERROR);
        }

        user.setPassword("*");
        request.getSession().setAttribute("CURRENT_USER", user);

        return ImmutablePair.of(true, SUCCESS);
    }
}
