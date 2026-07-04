package lehuuhoanganh.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.*;

@WebServlet(name = "ViewCartController", urlPatterns = {"/ViewCartController"})
public class ViewCartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
        
        if (cart == null) {
            CartUtil cartUtils = new CartUtil();
            Cookie cookieCart = cartUtils.getCookieByName(request, "Cart");
            cart = cartUtils.getCartFromCookie(cookieCart);
            if (cart != null && !cart.isEmpty()) {
                session.setAttribute("Cart", cart);
            }
        }
        
        ArrayList<CartItem> itemsInCart = (ArrayList<CartItem>) ((cart != null) ? new ArrayList<>(cart.values()) : new ArrayList<>());
        request.setAttribute("Cart", itemsInCart);
        request.getRequestDispatcher("ViewCart.jsp").forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}