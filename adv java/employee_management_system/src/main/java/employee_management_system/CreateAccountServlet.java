package employee_management_system;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String eid = req.getParameter("eid");
        String ename = req.getParameter("ename");
        String esal = req.getParameter("esal");
        String designation = req.getParameter("designation");
        String location = req.getParameter("location");
        String contact = req.getParameter("contact");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm_password");

        PrintWriter out = resp.getWriter();

        // simple validation: passwords match
        if (password == null || !password.equals(confirm)) {
            // include the create_account HTML so user sees the form again
            RequestDispatcher rd = req.getRequestDispatcher("/create_account.html");
            rd.include(req, resp);

            // now inject an error message into the existing div (#server_message)
            // we use client-side script to place HTML inside that div (this works because we included the page)
            System.out.println("<script>");
            System.out.println("(function(){");
            System.out.println("var el = document.getElementById('server_message');");
            System.out.println("if(el) {");
            // escaping single quotes in string
            System.out.println("el.innerHTML = '<p class=\"error\">Passwords do not match. Please re-enter.</p>';");
            System.out.println("} else {");
            System.out.println("document.body.insertAdjacentHTML('beforeend', '<p class=\"error\">Passwords do not match. Please re-enter.</p>');");
            System.out.println("}");
            System.out.println("})();");
            System.out.println("</script>");

            // close writer (container usually handles it but good practice)
            out.close();
            return;
        }

        // If passwords match: proceed to store user in DB (pseudo)
        // validate other fields, check primary key uniqueness, insert into DB...
        // For now, redirect to login page or show success message:
        resp.sendRedirect("login.html"); // or forward to a success JSP
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show the create form if someone GETs /create_acc
        RequestDispatcher rd = req.getRequestDispatcher("/create_account.html");
        rd.forward(req, resp);
    }
}
