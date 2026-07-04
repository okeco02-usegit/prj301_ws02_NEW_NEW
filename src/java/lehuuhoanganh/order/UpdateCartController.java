package lehuuhoanganh.order;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.CartItem;

@WebServlet(name = "UpdateCartController", urlPatterns = {"/UpdateCartController"})
public class UpdateCartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemId = request.getParameter("Itemid");
        String quantityStr = request.getParameter("quantity");
        
        if (itemId != null && quantityStr != null) {
            try {
                int newQuantity = Integer.parseInt(quantityStr.trim());
                HttpSession session = request.getSession();
                HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
                
                if (cart != null && cart.containsKey(itemId)) {
                    if (newQuantity > 0) {
                        cart.get(itemId).setQuantity(newQuantity);
                        request.setAttribute("Message", "Your cart has been updated successfully.");
                    } else {
                        cart.remove(itemId);
                        request.setAttribute("Message", "Item removed successfully.");
                    }
                }
            } catch (Exception e) {
                log("Error in UpdateCartController: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("CartController?action=View+Cart").forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}