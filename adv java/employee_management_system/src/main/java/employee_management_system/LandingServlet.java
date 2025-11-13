package employee_management_system;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class LandingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Option A: forward to static index.html (if you created index.html in webapp root)
        RequestDispatcher rd = req.getRequestDispatcher("/login.html");
        rd.forward(req, resp);

        // Option B: or directly render a small HTML here via PrintWriter (not necessary if you have index.html)
    }
}
