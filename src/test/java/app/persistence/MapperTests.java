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
        // Arrange
        int expectedUserId = 1;

        String firstName = "Bob";
        String lastName = "Jensen";
        int phone = 20304050;
        String email = "bob@gmail.com";
        String address = "Vej 20";
        int zip = 2000;


        // Act
        int actualUserId = UserMapper.createUser(firstName, lastName, phone, email, address, zip);

        // Assert
        assertEquals(expectedUserId, actualUserId, "The returned user ID should match the expected ID");

    }

    @Test
    void CreateOrderTEST() throws SQLException, DatabaseException {
        // Arrange
        int expectedOrderId = 1;
        int userId = UserMapper.createUser("bob", "jensen", 20304050, "bob@gmail.com", "vej20", 2000);

        // Act
        int actualOrderId = OrderMapper.createOrder(userId,600,780);

        // Assert
        assertEquals(expectedOrderId, actualOrderId, "The returned order ID should match the expected ID");
    }

    @Test
    void getAllOrdersTEST() throws SQLException, DatabaseException {
        // Arrange
        OrderMapper.createOrder(1, 600, 780);
        OrderMapper.createOrder(2, 600, 600);
        OrderMapper.createOrder(3, 600, 780);

        // Act
        List<Order> orders = OrderMapper.getAllOrders();

        // Assert
        assertEquals(3, orders.size(), "Should return exactly 3 order");
    }

    @Test
    void getAllUsersTEST() throws SQLException, DatabaseException {
        // Arrange
        UserMapper.createUser("Bob", "Jensen", 20304050, "bob@gmail.com", "vej20", 2000);
        UserMapper.createUser("Bobby", "Love", 12345678, "bobby@love.com", "Elskovstien 12", 1234);
        UserMapper.createUser("Else", "Scarlet", 13685487, "scarletstar@outlook.com", "Boulevarden 66", 1300);
        UserMapper.createUser("Marianne", "Pouliner", 20604352, "mpliner@gmail.com", "Langvej 99", 2000);
        UserMapper.createUser("Joan", "Yang", 78809520, "Kongfu@gmail.com", "Kortvej", 4500);

        // Act
        List<User> users = UserMapper.getAllUsers();

        // Assert
        assertEquals(5, users.size(), "Should return exactly 5 users");

    }

    @Test
    void testCreateUserThrowsException() {
        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            UserMapper.createUser(null, null, 0, null, null, 0);
        });
        assertTrue(exception.getMessage().contains("Error inserting user"));
    }

}
