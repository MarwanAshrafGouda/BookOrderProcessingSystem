package Connection;

import java.sql.*;
import java.util.Vector;

public class JDBCConnection implements IJDBCConnection {

    private JDBCConnection() {
    }

    private static JDBCConnection JDBCConnector = null;
    private String username;
    private boolean isManager;

    public static JDBCConnection getInstance() {
        if (JDBCConnector == null) {
            return new JDBCConnection();
        }
        return JDBCConnector;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Boolean getIsManager() {
        return this.isManager;
    }

    private String[] JDBCConnect() {
        {
            String dbURL = "jdbc:mysql://197.48.45.243:3306/Bookstore?autoReconnect=true&useSSL=false";
            String user = "wzattout";
            String password = "wzattout_pass";
            return new String[]{dbURL, user, password};
        }
    }

    @Override
    public void signUp(String username, String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call signUp(?, ?, ?, ?, ?, ?, ?)}")
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, emailAddress);
            statement.setString(6, phoneNumber);
            statement.setString(7, shippingAddress);
            statement.execute();
            this.username = username;
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public Boolean signIn(String username, String password) {
        // returns 0 if a customer and 1 if a manager
        String[] databaseInformation = JDBCConnect();
        boolean manager = false;
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call signIn(?, ?, ?)}")
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            manager = statement.getBoolean(3);
            this.username = username;
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
        this.isManager = manager;
        return manager;
    }

    @Override
    public void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, double sellingPrice, String category, int threshold) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call addBook(?, ?, ?, ?, ?, ?, ?)}");
                CallableStatement statement2 = conn.prepareCall("{call addAuthor(?, ?)}")
        ) {
            statement1.setInt(1, ISBN);
            statement1.setString(2, title);
            statement1.setString(3, publisher);
            statement1.setString(4, category);
            statement1.setDouble(5, sellingPrice);
            statement1.setInt(6, publicationYear);
            statement1.setInt(7, threshold);
            statement1.execute();
            for (String author : arrOfAuthors) {
                statement2.setInt(1, ISBN);
                statement2.setString(2, author);
                statement2.execute();
            }
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public void addPublisher(String name, String address, String phoneNumber) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call addPublisher(?, ?, ?)}")
        ) {
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phoneNumber);
            statement.execute();
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public void modifyBook(int ISBN, int newISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call modifyBook(?, ?, ?, ?, ?, ?, ?, ?)}");
                CallableStatement statement2 = conn.prepareCall("{call removeAuthors(?)}");
                CallableStatement statement3 = conn.prepareCall("{call addAuthor(?, ?)}")
        ) {
            statement1.setInt(1, ISBN);
            statement1.setInt(2, newISBN);
            statement1.setString(3, title);
            statement1.setString(4, publisher);
            statement1.setString(5, category);
            statement1.setDouble(6, sellingPrice);
            statement1.setInt(7, publicationYear);
            statement1.setInt(8, threshold);
            statement1.execute();
            statement2.setInt(1, newISBN);
            statement2.execute();
            for (String author : arrOfAuthors) {
                statement3.setInt(1, newISBN);
                statement3.setString(2, author);
                statement3.execute();
            }
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public void confirmOrder(int orderNo) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call confirmOrder(?)}")
        ) {
            statement.setInt(1, orderNo);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector<Vector<String>> ISBNSearch(int ISBN) {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByISBN(?)}")
        ) {
            statement.setInt(1, ISBN);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 8; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<Vector<String>> titleSearch(String title) {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByTitle(?)}")
        ) {
            statement.setString(1, title);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 8; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<Vector<String>> authorSearch(String author) {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByAuthor(?)}")
        ) {
            statement.setString(1, author);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 8; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<Vector<String>> publisherSearch(String publisher) {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByPublisher(?)}")
        ) {
            statement.setString(1, publisher);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 8; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<Vector<String>> categorySearch(String category) {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByCategory(?)}")
        ) {
            statement.setString(1, category);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 8; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void editUserInfo(String username, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call editUserInfo(?, ?, ?, ?, ?, ?, ?)}")
        ) {
            statement.setString(1, this.username);
            statement.setString(2, username);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, emailAddress);
            statement.setString(6, phoneNumber);
            statement.setString(7, shippingAddress);
            statement.execute();
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public void editUserPassword(String oldPassword, String newPassword) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call editUserPassword(?, ?, ?)}")
        ) {
            statement.setString(1, this.username);
            statement.setString(2, oldPassword);
            statement.setString(3, newPassword);
            statement.execute();
        } catch (SQLException e) {
            // call mostafa 01274 e.getMessage()
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(int ISBN, int copiesNo) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call addToCart(?, ?, ?)}")
        ) {
            statement.setString(1, this.username);
            statement.setInt(2, ISBN);
            statement.setInt(3, copiesNo);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector<Vector<String>> viewCart() {
        String[] databaseInformation = JDBCConnect();
        Vector<Vector<String>> result = new Vector<>();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call viewCart(?)}")
        ) {
            statement.setString(1, this.username);
            statement.execute();
            ResultSet r = statement.getResultSet();
            int i = 0;
            while (r.next()) {
                result.add(new Vector<>());
                for (int j = 1; j < 9; ++j) {
                    result.get(i).add(r.getString(j));
                }
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void removeFromCart(int ISBN) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call removeFromCart(?, ?)}")
        ) {
            statement.setString(1, this.username);
            statement.setInt(2, ISBN);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkOut() {
        String[] databaseInformation = JDBCConnect();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
            CallableStatement statement = conn.prepareCall("{call checkOut(?)}");
            statement.setString(1, this.username);
            conn.setAutoCommit(false);
            statement.execute();
            conn.commit();
        } catch (SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logOut() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call logOut(?)}")
        ) {
            statement.setString(1, this.username);
            statement.execute();
            this.username = "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void promoteUser(String username) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call promote(?)}")
        ) {
            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void totalSalesReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call totalSalesReport()}")
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void top5CustomersReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call top5CustomersReport()}")
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void top10SellingBooksReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call top10SellingBooksReport()}")
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
