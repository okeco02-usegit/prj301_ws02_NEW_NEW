package lehuuhoanganh.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.BookDAO;

@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "index.html";
        if ("ViewBookList".equals(action)) {
            BookDAO bookDAO = new BookDAO();
            request.setAttribute("BookList", bookDAO.getBookList());
            url = "ViewBooks.jsp";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
    @Override protected void doGet(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
    @Override protected void doPost(HttpServletRequest r, HttpServletResponse s) throws ServletException, IOException { processRequest(r, s); }
}