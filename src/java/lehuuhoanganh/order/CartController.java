package lehuuhoanganh.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "ViewCartController";

        if ("Add".equals(action)) {
            url = "AddCartController";
        } else if ("View Cart".equals(action)) {
            url = "ViewCartController";
        } else if ("Remove".equals(action)) {
            url = "RemoveCartController";
        } else if ("Update".equals(action)) {
            url = "UpdateCartController";
        } else if ("Save".equals(action)) {
            url = "SaveCartController";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}