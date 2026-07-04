<%@page import="java.util.List"%>
<%@page import="lehuuhoanganh.utils.CartItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
</head>
<body>
    <h1>Your Shopping Cart Details</h1>
    <%
        List<CartItem> itemsInCart = (List<CartItem>) request.getAttribute("Cart");
        if (itemsInCart == null || itemsInCart.isEmpty()) {
    %>
        <h3>Cart is empty!!!</h3>
        <a href="BookController?action=ViewBookList">Go back and choose books</a>
    <%
        } else {
            double totalAmount = 0;
            int totalQty = 0;
    %>
    <% if (request.getAttribute("Message") != null) { %>
        <h4 style="color:blue;"><%= request.getAttribute("Message") %></h4>
    <% } %>
    <table border="1" style="width:550px">
        <thead>
            <tr>
                <th>No.</th>
                <th>Id</th>
                <th>Title</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>SubTotal</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                int count = 0;
                for (CartItem item : itemsInCart) {
                    totalAmount += item.getSubTotal();
                    totalQty += item.getQuantity();
            %>
            <tr>
                <td><%= (++count) %></td>
                <td><%= item.getItemId() %></td>
                <td><%= item.getItemName() %></td>
                <td>$<%= String.format("%.2f", item.getUnitPrice()) %></td>
                <td>
                    <form action="CartController" method="post" style="display:inline;">
                        <input type="hidden" name="Itemid" value="<%= item.getItemId() %>"/>
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" style="width:50px;"/>
                        <input type="submit" name="action" value="Update"/>
                    </form>
                </td>
                <td>$<%= String.format("%.2f", item.getSubTotal()) %></td>
                <td>
                    <form action="CartController" method="post" style="display:inline;">
                        <input type="hidden" name="Itemid" value="<%= item.getItemId() %>"/>
                        <input type="submit" name="action" value="Remove"/>
                    </form>
                </td>
            </tr>
            <% } %>
            <tr>
                <td colspan="5" style="text-align:right;"><b>Total Amount:</b></td>
                <td colspan="2"><b>$<%= String.format("%.2f", totalAmount) %></b></td>
            </tr>
        </tbody>
    </table>
    <h3>Number of books in cart: <%= totalQty %></h3>
    
    <form action="CartController" method="post">
        <input type="submit" name="action" value="Save" />
    </form>
    <br/>
    <a href="BookController?action=ViewBookList">Continue Shopping</a>
    <% } %>
    <br/><br/>
    <a href="ShoppingCart.html">Back to Home</a>
</body>
</html>