package lehuuhoanganh.order;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.Order;
import lehuuhoanganh.utils.OrderDAO;

/**
 * POST from ListOrderController update form → update order quantity → redirect back.
 * @author LeHuuHoangAnh
 */
@WebServlet(name = "UpdateOrderController", urlPatterns = {"/UpdateOrderController"})
public class UpdateOrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkLogin(request, response)) return;

        String orderIdStr  = request.getParameter("OrderId");
        String quantityStr = request.getParameter("quantity");

        // --- Validation ---
        int orderId  = 0;
        int quantity = 0;
        String errorMsg = null;

        try {
            orderId = Integer.parseInt(orderIdStr.trim());
        } catch (Exception e) {
            errorMsg = "Invalid Order ID.";
        }

        if (errorMsg == null) {
            try {
                quantity = Integer.parseInt(quantityStr.trim());
                if (quantity < 1) errorMsg = "Quantity must be at least 1.";
            } catch (Exception e) {
                errorMsg = "Quantity must be a valid integer.";
            }
        }

        if (errorMsg != null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red'>" + errorMsg + "</p>");
            out.println("<a href='ListOrderController'>Back to Orders</a>");
            out.println("</body></html>");
            out.close();
            return;
        }

        try {
            OrderDAO dao = new OrderDAO();
            // Fetch existing order to keep other fields unchanged
            Order existing = dao.getOrderById(orderId);
            if (existing == null) {
                response.sendRedirect("ListOrderController?message=Order+not+found.");
                return;
            }
            existing.setQuantity(quantity);
            if (dao.updateOrder(existing)) {
                response.sendRedirect("ListOrderController?message=Order+updated+successfully.");
            } else {
                response.sendRedirect("ListOrderController?message=Something+went+wrong.");
            }
        } catch (Exception ex) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red'>Error: " + ex.getMessage() + "</p>");
            out.println("<a href='ListOrderController'>Back to Orders</a>");
            out.println("</body></html>");
            out.close();
        }
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