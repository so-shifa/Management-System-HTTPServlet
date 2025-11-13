package employee_management_system;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String loginId = req.getParameter("login_id");
        String password = req.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db", "root", "tiger"
            );

            PreparedStatement ps = con.prepareStatement(
                "SELECT eid, ename FROM employees WHERE email = ? AND password_hash = ?"
            );

            ps.setString(1, loginId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("eid", rs.getString("eid"));
                session.setAttribute("ename", rs.getString("ename"));
                session.setAttribute("email", loginId);
                resp.sendRedirect("profile");
            } else {
                resp.getWriter().println("Invalid email or password");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Login failed: " + e.toString());
        }
    }
}
