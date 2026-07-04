package lehuuhoanganh.order;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.OrderDAO;

/**
 * POST from ListOrderController remove form → delete order by OrderId → redirect back.
 * @author LeHuuHoangAnh
 */
@WebServlet(name = "DeleteOrderController", urlPatterns = {"/DeleteOrderController"})
public class DeleteOrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkLogin(request, response)) return;

        String orderIdStr = request.getParameter("OrderId");

        int orderId = 0;
        try {
            orderId = Integer.parseInt(orderIdStr.trim());
        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><body>");
            out.println("<p style='color:red'>Invalid Order ID.</p>");
            out.println("<a href='ListOrderController'>Back to Orders</a>");
            out.println("</body></html>");
            out.close();
            return;
        }

        try {
            OrderDAO dao = new OrderDAO();
            if (dao.deleteOrder(orderId)) {
                response.sendRedirect("ListOrderController?message=Order+removed+successfully.");
            } else {
                response.sendRedirect("ListOrderController?message=Order+not+found+or+already+deleted.");
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