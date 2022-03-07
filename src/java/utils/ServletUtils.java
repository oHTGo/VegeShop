package utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {

    public static void doRedirectOrForward(boolean isRedirect, String url, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isRedirect) {
            response.sendRedirect(url);
        } else {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
