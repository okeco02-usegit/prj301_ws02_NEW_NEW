package lehuuhoanganh.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import lehuuhoanganh.utils.OrderDAO;
import lehuuhoanganh.utils.Order;


@WebServlet(name = "ListOrderController", urlPatterns = {"/ListOrderController"})
public class ListOrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkLogin(request, response)) {
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Order Management</title></head><body>");
        out.println("<h2>Your Orders</h2>");

        // Message coming from Create/Update/Delete (redirect with query param)
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            out.println("<h4>" + message + "</h4>");
        }

        try {
            OrderDAO orderDao = new OrderDAO();
            List<Order> orderList = orderDao.getAllOrders();

            if (orderList == null || orderList.isEmpty()) {
                out.println("<h3>Order list is empty!!!</h3>");
            } else {
                double totalAmount = 0;
                int totalQuantity = 0;

                out.println("<table border='1' style='width:650px'>");
                out.println("<thead><tr>");
                out.println("<th>No.</th><th>ItemId</th><th>ItemName</th><th>UnitPrice</th>"
                        + "<th>Quantity</th><th>SubTotal</th><th colspan='2'>Action</th>");
                out.println("</tr></thead><tbody>");

                int count = 0;
                for (Order order : orderList) {
                    totalAmount += order.getSubTotal();
                    totalQuantity += order.getQuantity();

                    out.println("<form action='UpdateOrderController' method='post'>");
                    out.println("<tr>");
                    out.println("<input type='hidden' name='OrderId' value='" + order.getOrderId() + "'/>");
                    out.println("<td>" + (++count) + "</td>");
                    out.println("<td>" + order.getItemId() + "</td>");
                    out.println("<td>" + order.getItemName() + "</td>");
                    out.println("<td>" + String.format("%.2f", order.getUnitPrice()) + "</td>");
                    out.println("<td><input type='number' min='1' name='quantity' value='"
                            + order.getQuantity() + "' style='width:50px'/></td>");
                    out.println("<td>" + String.format("%.2f", order.getSubTotal()) + "</td>");
                    out.println("<td><input type='submit' value='Update'/></td>");
                    out.println("</tr>");
                    out.println("</form>");

                    out.println("<form action='DeleteOrderController' method='post'>");
                    out.println("<tr style='display:none' id='spacer-" + order.getOrderId() + "'></tr>");
                    out.println("</form>");
                }

                out.println("</tbody>");
                out.println("<tr><td colspan='5' style='text-align:right'><b>Total Amount</b></td>"
                        + "<td colspan='2'>" + String.format("%.2f", totalAmount) + "</td></tr>");
                out.println("</table>");
                out.println("<h3>Number of items in orders: " + totalQuantity + "</h3>");

                // Remove section (separate, simple list with delete buttons)
                out.println("<h3>Remove an order</h3>");
                out.println("<table border='1' style='width:300px'>");
                for (Order order : orderList) {
                    out.println("<form action='DeleteOrderController' method='post'>");
                    out.println("<tr>");
                    out.println("<td>" + order.getItemName() + " (Id: " + order.getOrderId() + ")</td>");
                    out.println("<input type='hidden' name='OrderId' value='" + order.getOrderId() + "'/>");
                    out.println("<td><input type='submit' value='Remove'/></td>");
                    out.println("</tr>");
                    out.println("</form>");
                }
                out.println("</table>");
            }
        } catch (Exception ex) {
            out.println("<p style='color:red'>Error: " + ex.getMessage() + "</p>");
        }

        out.println("<br/><a href='CreateOrder.html'>Create new order</a><br/>");
        out.println("<a href='LoginController'>Back to Menu</a>");
        out.println("</body></html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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