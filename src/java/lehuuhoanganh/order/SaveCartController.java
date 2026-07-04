package lehuuhoanganh.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.*;

@WebServlet(name = "SaveCartController", urlPatterns = {"/SaveCartController"})
public class SaveCartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
        String message = "Your cart is empty.";
        
        if (cart != null && !cart.isEmpty()) {
            CartUtil cartUtils = new CartUtil();
            String strItemsInCart = cartUtils.convertCartToString(new ArrayList<>(cart.values()));
            cartUtils.saveCartToCookie(request, response, strItemsInCart);
            message = "Your cart has been saved to Cookie successfully.";
        }
        request.setAttribute("Message", message);
        request.getRequestDispatcher("CartController?action=View+Cart").forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}