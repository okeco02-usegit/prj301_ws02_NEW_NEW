package lehuuhoanganh.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.User;
import lehuuhoanganh.utils.UserDAO;

@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        String lastName = request.getParameter("txtLastName");

        // --- Kiểm tra định dạng (Validation giống Lab06) ---
        String errorMsg = null;
        if (userName == null || !userName.trim().matches("U\\d{3}")) {
            errorMsg = "The UserName must be formatted Uxxx, x is digits.";
        } else if (password == null || !password.matches(".{3,15}")) {
            errorMsg = "The Password must be 3 to 15 characters.";
        } else if (lastName == null || !lastName.trim().matches(".{5,50}")) {
            errorMsg = "The LastName must be 5 to 50 characters.";
        }

        if (errorMsg != null) {
            response.sendRedirect("Login.html?error=" + URLEncoder.encode(errorMsg, "UTF-8"));
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            if (dao.checkUserNameExists(userName.trim())) {
                response.sendRedirect("Login.html?error=" + URLEncoder.encode("The UserName already exists.", "UTF-8"));
                return;
            }

            // Đăng ký vãng lai luôn thiết lập isAdmin = false
            User user = new User(userName.trim(), password.trim(), lastName.trim(), false);

            if (dao.addUser(user)) {
                response.sendRedirect("Login.html?error=" + URLEncoder.encode("Sign up successful! Please login.", "UTF-8"));
            } else {
                response.sendRedirect("Login.html?error=" + URLEncoder.encode("Something went wrong!", "UTF-8"));
            }
        } catch (Exception ex) {
            response.sendRedirect("Login.html?error=" + URLEncoder.encode("System error: " + ex.getMessage(), "UTF-8"));
        }
    }
}