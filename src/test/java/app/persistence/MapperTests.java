package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.*;
import app.entities.Order;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MapperTests {

    private static final String USER = System.getenv("dbuser");
    private static final String PASSWORD = System.getenv("password");
    private static final String URL = "jdbc:postgresql://" + System.getenv("ip") + ":5432/%s?currentSchema=test";
    private static final String DB = "carport";

    public static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    @BeforeAll
    static void setUpClass() throws SQLException {
        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS test.orders CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.order_status CASCADE");


            stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq");


            stmt.execute("CREATE TABLE test.users (LIKE public.users INCLUDING ALL)");
            stmt.execute("CREATE TABLE test.orders (LIKE public.orders INCLUDING ALL)");
            stmt.execute("CREATE TABLE test.order_status (LIKE public.order_status INCLUDING ALL)");

            //sørger for at id starter ved 0 igen
            stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
            stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq')");
            stmt.execute("CREATE SEQUENCE test.orders_order_id_seq");
            stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq')");



        }
    }

    @BeforeEach
    void clearTestTables() throws SQLException {
        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM test.orders");
            stmt.execute("DELETE FROM test.users");
            stmt.execute("ALTER SEQUENCE test.users_user_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE test.orders_order_id_seq RESTART WITH 1");
        }
    }


    @Test
    void CreateUserTEST() throws SQLException, DatabaseException {
        int expectedUserId = 1;
        int actualUserId = UserMapper.createUser("bob", "jensen", 20304050, "bob@gmail.com", "vej20", 2000);

        assertEquals(expectedUserId, actualUserId, "The returned user ID should match the expected ID");

    }

    @Test
    void CreateOrderTEST() throws SQLException, DatabaseException {
        int expectedOrderId = 1;
        int userId = UserMapper.createUser("bob", "jensen", 20304050, "bob@gmail.com", "vej20", 2000);
        int actualOrderId = OrderMapper.createOrder(userId,600,780);

        assertEquals(expectedOrderId, actualOrderId, "The returned order ID should match the expected ID");
    }

    @Test
    void getAllOrdersTEST() throws SQLException, DatabaseException {

        int expectedOrderId = 1;
        int expectedWidth = 600;
        int expectedLength = 780;
        int userId = UserMapper.createUser("bob", "jensen", 20304050, "bob@gmail.com", "vej20", 2000);
        int actualOrderId = OrderMapper.createOrder(userId, expectedWidth, expectedLength);

        // Act
        List<Order> orders = OrderMapper.getAllOrders();

        // Assert
        assertEquals(1, orders.size(), "Should return exactly 1 order");
        Order order = orders.get(0);
        assertEquals(expectedOrderId, order.getOrderId());
        assertEquals(expectedWidth, order.getWidth());
        assertEquals(expectedLength, order.getLength());
    }

    @Test
    void getAllUsersTEST() throws SQLException, DatabaseException {
        // Arrange
        int expectedUserId = 1;
        String expectedFirstName = "bob";
        String expectedLastName = "jensen";
        int expectedPhone = 20304050;
        String expectedEmail = "bob@gmail.com";
        String expectedAddress = "vej20";
        int expectedZip = 2000;

        // Create user
        int actualUserId = UserMapper.createUser(expectedFirstName, expectedLastName, expectedPhone, expectedEmail, expectedAddress, expectedZip);

        // Act
        List<User> users = UserMapper.getAllUsers();

        // Assert
        assertEquals(1, users.size(), "Should return exactly 1 user");

        User user = users.get(0);
        assertEquals(expectedUserId, user.getUserId());
        assertEquals(expectedFirstName, user.getFirstName());
        assertEquals(expectedLastName, user.getLastName());
        assertEquals(expectedPhone, user.getPhoneNumber());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedAddress, user.getAddress());
        assertEquals(expectedZip, user.getZip());
        assertFalse(user.isAdmin()); // assuming createUser defaults to non-admin
    }

}
