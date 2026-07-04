package lehuuhoanganh.order;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.*;

@WebServlet(name = "AddCartController", urlPatterns = {"/AddCartController"})
public class AddCartController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BookDAO bookDAO = new BookDAO();
            HttpSession session = request.getSession();
            
            HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
            if (cart == null) {
                cart = new HashMap<>();
                session.setAttribute("Cart", cart);
            }
            
            String bookId = request.getParameter("BookId");
            Book selectedBook = bookDAO.getBookById(bookId);
            
            if (selectedBook != null) {
                CartItem item = cart.get(selectedBook.getId());
                if (item == null) {
                    item = new CartItem(selectedBook.getId(), selectedBook.getTitle(), 1, selectedBook.getUnitPrice());
                    cart.put(item.getItemId(), item);
                } else {
                    item.setQuantity(item.getQuantity() + 1);
                }
                request.setAttribute("Message", "The book '" + item.getItemName() + "' has been added to cart successfully.");
            }
        } catch (Exception e) {
            log("Error in AddCartController: " + e.getMessage());
        }
        request.getRequestDispatcher("BookController?action=ViewBookList").forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}