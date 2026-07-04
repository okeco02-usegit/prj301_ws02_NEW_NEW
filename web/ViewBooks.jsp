<%@page import="java.util.List"%>
<%@page import="lehuuhoanganh.utils.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>View Books</title>
</head>
<body>
    <h1>View All Available Books</h1>
    <%
        List<Book> bookList = (List<Book>) request.getAttribute("BookList");
        if (bookList == null) {
            response.sendRedirect("BookController?action=ViewBookList");
            return;
        }
    %>
    <form action="CartController">
        <p>Please select the book you want to purchase:</p>
        <table border="1" style="width:450px">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 0;
                    for (Book book : bookList) {
                %>
                <tr>
                    <td><%= (++count) %></td>
                    <td><%= book.getTitle() %></td>
                    <td>$<%= book.getUnitPrice() %></td>
                    <td style="text-align: center">
                        <a href="CartController?action=Add&BookId=<%= book.getId() %>">Add To Cart</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <br/>
        <% if (request.getAttribute("Message") != null) { %>
            <h4 style="color:green;"><%= request.getAttribute("Message") %></h4>
        <% } %>
        <input type="submit" value="View Cart" name="action" />
        <br/><br/>
        <a href="ShoppingCart.html">Back to Menu</a>
    </form>
</body>
</html>