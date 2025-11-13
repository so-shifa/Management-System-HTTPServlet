package employee_management_system;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NavigateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String page = req.getParameter("page");

        RequestDispatcher rd;
        if ("create".equals(page)) {
            rd = req.getRequestDispatcher("createaccount.html");
        } else if ("login".equals(page)) {
            rd = req.getRequestDispatcher("login.html");
        } else {
            rd = req.getRequestDispatcher("landing.html");
        }

        rd.forward(req, resp);
    }
}
