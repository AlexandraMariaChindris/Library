package repository.order;

import model.Order;
import model.builder.OrderBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryMySQL implements OrderRepository {

    private final Connection connection;

    public OrderRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM book_orders;";

        List<Order> orders = new ArrayList<>();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setUserId(resultSet.getLong("user_id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPrice(resultSet.getFloat("price"))
                .setQuantity(resultSet.getInt("quantity"))
                .setTime(resultSet.getTimestamp("time").toLocalDateTime())
                .build();
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM book_order WHERE id = ?";
        Optional<Order> order = Optional.empty();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                order = Optional.of(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    @Override
    public boolean save(Order order) {
        String newSql = "INSERT INTO book_orders (user_id, author, title, quantity, price) VALUES (?, ?, ?, 1, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setInt(1, Math.toIntExact(order.getUser_id()));
            preparedStatement.setString(2, order.getAuthor());
            preparedStatement.setString(3, order.getTitle());
            //preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setFloat(4, order.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Order order) {
        String deleteSql = "DELETE FROM book_order WHERE author = ? AND title = ? AND quantity = ? AND price = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setString(1, order.getAuthor());
            preparedStatement.setString(2, order.getTitle());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setFloat(4, order.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book_order WHERE id >= 0;";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
