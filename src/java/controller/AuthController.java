package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.ServletUtils;

public class AuthController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    private static final String SUCCESS = "HomeController";
    private static final String ERROR = "LoginController?OAUTH_ERROR";
    private static final String NOT_EXIST_ERROR = "RegisterController?email=";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        try {
            ImmutablePair<Boolean, String> result = doAuth(request, response);
            url = result.right;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        } finally {
            ServletUtils.doRedirectOrForward(true, url, request, response);
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

    private ImmutablePair<Boolean, String> doAuth(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String e = request.getParameter("e");
        Pattern pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        if (!pattern.matcher(e).matches()) {
            return ImmutablePair.of(false, ERROR);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(e);
        String decodedString = new String(decodedBytes);
        String email = decodedString;

        UserDAO dao = new UserDAO();
        UserDTO user = dao.checkLoginByEmail(email);

        if (user == null) {
            return ImmutablePair.of(false, NOT_EXIST_ERROR + email);
        }

        LOGGER.log(Level.TRACE, user);
        request.getSession().setAttribute("CURRENT_USER", user);
        return ImmutablePair.of(true, SUCCESS);
    }
}
