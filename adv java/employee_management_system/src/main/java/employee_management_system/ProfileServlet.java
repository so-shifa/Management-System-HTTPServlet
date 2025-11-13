package employee_management_system;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        if (email == null) {
            resp.sendRedirect("login.html");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db", "root", "tiger"
            );

            PreparedStatement ps = con.prepareStatement(
                "SELECT eid, ename, esal, designation, location, contact, email, password_hash FROM employees WHERE email = ? LIMIT 1"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                resp.getWriter().println("User not found");
                con.close();
                return;
            }

            String eid = rs.getString("eid");
            String ename = rs.getString("ename");
            String esal = rs.getString("esal");
            String designation = rs.getString("designation");
            String location = rs.getString("location");
            String contact = rs.getString("contact");
            String mail = rs.getString("email");
            // password hidden
            rs.close();
            con.close();

            resp.setContentType("text/html");
            StringBuilder out = new StringBuilder();
            out.append("<!doctype html><html><head><meta charset='utf-8'><title>profile</title></head><body>");
            out.append("<h1>employee details</h1>");
            out.append("<table border='1' cellpadding='8' cellspacing='0'>");

            out.append(row("eid", eid));
            out.append(row("ename", ename));
            out.append(row("esal", esal));
            out.append(row("designation", designation));
            out.append(row("location", location));
            out.append(row("contact", contact));
            out.append(row("email", mail));
            out.append(row("password", "****"));

            out.append("</table><br/>");

            // update button (goes to update form; GET with eid)
            out.append("<form action='update' method='get' style='display:inline;'>");
            out.append("<input type='hidden' name='eid' value='" + escape(eid) + "'/>");
            out.append("<input type='submit' value='Update'/>");
            out.append("</form>");

            // delete button (POST to delete)
            out.append("<form action='delete' method='post' style='display:inline;margin-left:10px;'>");
            out.append("<input type='hidden' name='eid' value='" + escape(eid) + "'/>");
            out.append("<input type='submit' value='Delete'/>");
            out.append("</form>");

            out.append("</body></html>");
            resp.getWriter().println(out.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error loading profile: " + e.toString());
        }
    }

    private String row(String label, String value) {
        return "<tr><td style='font-weight:bold;'>" + escape(label) + "</td><td>" + escape(value) + "</td></tr>";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}