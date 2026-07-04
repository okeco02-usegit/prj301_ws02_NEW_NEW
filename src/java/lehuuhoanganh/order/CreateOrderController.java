package lehuuhoanganh.order;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.Order;
import lehuuhoanganh.utils.OrderDAO;
import lehuuhoanganh.utils.User;

/**
 * POST from CreateOrder.html → validate → insert order → redirect to ListOrderController.
 * @author LeHuuHoangAnh
 */
@WebServlet(name = "CreateOrderController", urlPatterns = {"/CreateOrderController"})
public class CreateOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkLogin(request, response)) return;
        response.sendRedirect("CreateOrder.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkLogin(request, response)) return;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String itemId   = request.getParameter("txtItemId");
        String itemName = request.getParameter("txtItemName");
        String qtyStr   = request.getParameter("txtQuantity");
        String priceStr = request.getParameter("txtUnitPrice");

        // --- Validation ---
        String errorMsg = null;
        int    quantity  = 0;
        double unitPrice = 0;

        if (itemId == null || itemId.trim().isEmpty()) {
            errorMsg = "Item ID is required.";
        } else if (itemName == null || itemName.trim().isEmpty()) {
            errorMsg = "Item Name is required.";
        } else {
            try {
                quantity = Integer.parseInt(qtyStr.trim());
                if (quantity < 1) errorMsg = "Quantity must be at least 1.";
            } catch (Exception e) {
                errorMsg = "Quantity must be a valid integer.";
            }
        }

        if (errorMsg == null) {
            try {
                unitPrice = Double.parseDouble(priceStr.trim());
                if (unitPrice < 0) errorMsg = "Unit price cannot be negative.";
            } catch (Exception e) {
                errorMsg = "Unit price must be a valid number.";
            }
        }

        if (errorMsg != null) {
            out.println("<!DOCTYPE html><html><body>");
            out.println("<h2>Create Order - Error</h2>");
            out.println("<p style='color:red'>" + errorMsg + "</p>");
            out.println("<a href='CreateOrder.html'>Back</a>");
            out.println("</body></html>");
            return;
        }

        try {
            OrderDAO dao = new OrderDAO();
            Order order = new Order(itemId.trim(), itemName.trim(), quantity, unitPrice);
            if (dao.addOrder(order)) {
                response.sendRedirect("ListOrderController?message=Order+added+successfully.");
            } else {
                response.sendRedirect("ListOrderController?message=Something+went+wrong.");
            }
        } catch (Exception ex) {
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red'>Error: " + ex.getMessage() + "</p>");
            out.println("<a href='CreateOrder.html'>Back</a>");
            out.println("</body></html>");
        }
        out.close();
    }

    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("Login.html");
            return false;
        }
        return true;
    }
}