package lehuuhoanganh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.UserDAO;
import lehuuhoanganh.utils.User;

        

@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkAdmin(request, response)) {
            return;
        }
        response.sendRedirect("CreateUser.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkAdmin(request, response)) {
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        String lastName = request.getParameter("txtLastName");
        String isAdminStr = request.getParameter("chkIsAdmin");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Users Management</title></head><body>");
        out.println("<h2>Users Management - Create user</h2>");

        // --- Validation (same rules as Lab06) ---
        String errorMsg = null;
        if (userName == null || !userName.trim().matches("U\\d{3}")) {
            errorMsg = "The UserName must be formatted Uxxx, x is digits.";
        } else if (password == null || !password.matches(".{3,15}")) {
            errorMsg = "The Password must be 3 to 15 characters.";
        } else if (lastName == null || !lastName.trim().matches(".{5,50}")) {
            errorMsg = "The LastName must be 5 to 50 characters.";
        }

        if (errorMsg != null) {
            out.println("<p style='color:red'>" + errorMsg + "</p>");
            out.println("<a href='CreateUser.html'>Back</a>");
            out.println("</body></html>");
            return;
        }

        try {
            UserDAO dao = new UserDAO();

            // Check duplicate UserName
            if (dao.checkUserNameExists(userName.trim())) {
                out.println("<p style='color:red'>The UserName already exists.</p>");
                out.println("<a href='CreateUser.html'>Back</a>");
                out.println("</body></html>");
                return;
            }

            boolean isAdmin = (isAdminStr != null);
            User user = new User(userName.trim(), password.trim(), lastName.trim(), isAdmin);

            if (dao.addUser(user)) {
                out.println("<p>The user has been created successfully</p>");
            } else {
                out.println("<h3>Something went wrong!</h3>");
            }
        } catch (Exception ex) {
            out.println("<p style='color:red'>Error: " + ex.getMessage() + "</p>");
        }

        out.println("<a href='CreateUser.html'>Create another</a><br/>");
        out.println("<a href='ListController'>View user list</a><br/>");
        out.println("<a href='LoginController'>Back to Menu</a>");
        out.println("</body></html>");
        out.close();
    }

    private boolean checkAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("Login.html");
            return false;
        }
        User u = (User) session.getAttribute("loggedUser");
        if (!u.isAdmin()) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red'>Access denied. Admin role required.</p>");
            out.println("<a href='LoginController'>Back to Menu</a>");
            out.println("</body></html>");
            return false;
        }
        return true;
    }
}