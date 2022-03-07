package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.ServletUtils;

public class LoginController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    private static final String VIEW = "/WEB-INF/view/login.jsp";
    private static final String SUCCESS = "HomeController";
    private static final String ERROR = "/WEB-INF/view/login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        boolean isRedirect = false;

        try {
            String action = request.getParameter("action");

            if (action == null) {
                url = VIEW;
            } else if (action.equals("Login")) {
                String userID = request.getParameter("userID");
                String password = request.getParameter("password");

                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(userID, password);

                if (user != null) {
                    request.getSession().setAttribute("CURRENT_USER", user);
                    isRedirect = true;
                    url = SUCCESS;
                } else {
                    request.setAttribute("LOGIN_ERROR", "Invalid User ID or Password");
                }
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
}
