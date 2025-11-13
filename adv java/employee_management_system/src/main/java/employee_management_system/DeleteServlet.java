package employee_management_system;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String eid = req.getParameter("eid");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db", "root", "tiger"
            );

            PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE eid = ?");
            ps.setString(1, eid);
            ps.executeUpdate();
            con.close();

            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();

            resp.sendRedirect("login.html");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Delete failed: " + e.toString());
        }
    }
}