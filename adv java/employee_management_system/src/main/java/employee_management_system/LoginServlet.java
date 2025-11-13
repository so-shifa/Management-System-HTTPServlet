package employee_management_system;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginId = req.getParameter("login_id");
        String password = req.getParameter("password");

        // Dummy check - replace with DB check
        boolean valid = "test@example.com".equals(loginId) && "secret".equals(password);

        if (!valid) {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            // include the login page then add a red error message
            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
            rd.include(req, resp);

            out.println("<script>");
            out.println("  (function(){");
            out.println("    var wrap = document.querySelector('.container');");
            out.println("    var node = document.createElement('p');");
            out.println("    node.className = 'error';");
            out.println("    node.textContent = 'Invalid credentials. Try again.';");
            out.println("    if(wrap) wrap.insertBefore(node, wrap.firstChild);");
            out.println("  })();");
            out.println("</script>");
            out.close();
            return;
        }

        // On successful login, forward to home page (could be JSP)
        req.setAttribute("user", loginId);
        RequestDispatcher rd = req.getRequestDispatcher("/home.html"); // or /home.jsp
        rd.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show login form on GET
        RequestDispatcher rd = req.getRequestDispatcher("/login.html");
        rd.forward(req, resp);
    }
}
