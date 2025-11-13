package employee_management_system;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String eid = req.getParameter("eid");
        String ename = req.getParameter("ename");
        String esal = req.getParameter("esal");
        String designation = req.getParameter("designation");
        String location = req.getParameter("location");
        String contact = req.getParameter("contact");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm_password");

        if (!password.equals(confirm)) {
            resp.getWriter().println("Passwords do not match");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db", "root", "tiger"
            );

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employees (eid, ename, esal, designation, location, contact, email, password_hash, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())"
            );

            ps.setString(1, eid);
            ps.setString(2, ename);
            ps.setString(3, esal);
            ps.setString(4, designation);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, email);
            ps.setString(8, password);

            ps.executeUpdate();

            con.close();

            resp.sendRedirect("login.html");

        } catch (Exception e) {
            e.printStackTrace(); 
            resp.setContentType("text/plain");
            resp.getWriter().println("Error creating account: " + e.toString());
            for (StackTraceElement s : e.getStackTrace()) resp.getWriter().println(s.toString());
        }
}
    
    
    
}
