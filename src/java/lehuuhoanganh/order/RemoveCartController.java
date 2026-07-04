package lehuuhoanganh.order;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.CartItem;

@WebServlet(name = "RemoveCartController", urlPatterns = {"/RemoveCartController"})
public class RemoveCartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemId = request.getParameter("Itemid");
        if (itemId != null) {
            HttpSession session = request.getSession();
            HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
            if (cart != null) {
                cart.remove(itemId);
                request.setAttribute("Message", "The book has been removed from cart successfully.");
            }
        }
        request.getRequestDispatcher("CartController?action=View+Cart").forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}