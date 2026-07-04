package lehuuhoanganh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO {

    // ----------------------------------------------------------------
    // Get Connection (same as UserDAO)
    // ----------------------------------------------------------------
    public static Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionString = "jdbc:sqlserver://localhost:1433;database=LeHuuHoangAnh_SU26";
            //SQL Server Authentication
            Connection cnn = DriverManager.getConnection(connectionString, "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException ex) {
            throw ex;
        }
    }

    // ----------------------------------------------------------------
    // GET ALL ORDERS
    // ----------------------------------------------------------------
    public List<Order> getAllOrders() throws Exception {
        List<Order> list = new ArrayList<>();
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cnn = getConnection();
            String sql = "SELECT OrderId, ItemId, ItemName, Quantity, UnitPrice "
                       + "FROM Orders ORDER BY OrderId";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("OrderId"),
                        rs.getString("ItemId"),
                        rs.getString("ItemName"),
                        rs.getInt("Quantity"),
                        rs.getDouble("UnitPrice")
                ));
            }
        } finally {
            close(rs, ps, cnn);
        }
        return list;
    }

    // ----------------------------------------------------------------
    // GET SINGLE ORDER BY ORDERID
    // ----------------------------------------------------------------
    public Order getOrderById(int orderId) throws Exception {
        Order order = null;
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cnn = getConnection();
            String sql = "SELECT OrderId, ItemId, ItemName, Quantity, UnitPrice "
                       + "FROM Orders WHERE OrderId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order(
                        rs.getInt("OrderId"),
                        rs.getString("ItemId"),
                        rs.getString("ItemName"),
                        rs.getInt("Quantity"),
                        rs.getDouble("UnitPrice")
                );
            }
        } finally {
            close(rs, ps, cnn);
        }
        return order;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------
    public boolean addOrder(Order order) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "INSERT INTO Orders (ItemId, ItemName, Quantity, UnitPrice) "
                       + "VALUES (?, ?, ?, ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, order.getItemId());
            ps.setString(2, order.getItemName());
            ps.setInt(3, order.getQuantity());
            ps.setDouble(4, order.getUnitPrice());
            return ps.executeUpdate() > 0;
        } finally {
            close(null, ps, cnn);
        }
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------
    public boolean updateOrder(Order order) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "UPDATE Orders "
                       + "SET ItemId = ?, ItemName = ?, Quantity = ?, UnitPrice = ? "
                       + "WHERE OrderId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, order.getItemId());
            ps.setString(2, order.getItemName());
            ps.setInt(3, order.getQuantity());
            ps.setDouble(4, order.getUnitPrice());
            ps.setInt(5, order.getOrderId());
            return ps.executeUpdate() > 0;
        } finally {
            close(null, ps, cnn);
        }
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------
    public boolean deleteOrder(int orderId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "DELETE FROM Orders WHERE OrderId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } finally {
            close(null, ps, cnn);
        }
    }

    // ----------------------------------------------------------------
    // Helper: close resources
    // ----------------------------------------------------------------
    private void close(ResultSet rs, PreparedStatement ps, Connection cnn) {
        try { if (rs  != null) rs.close();  } catch (SQLException ignored) {}
        try { if (ps  != null) ps.close();  } catch (SQLException ignored) {}
        try { if (cnn != null) cnn.close(); } catch (SQLException ignored) {}
    }
}