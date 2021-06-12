import java.sql.*;

public class JDBCConnection implements IJDBCConnection {

    private String username;
    private boolean isManager;

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
            e.printStackTrace();
        }
        // with this value change attribute is_manager from false to true
        this.isManager = manager;
        return manager;
    }

    @Override
    public void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call addBook(?, ?, ?, ?, ?, ?, ?)}");
                CallableStatement statement2 = conn.prepareCall("{call addAuthor(?, ?)}")
        ) {
            modify(ISBN, title, publisher, publicationYear, sellingPrice, category, arrOfAuthors, threshold, statement1, statement2, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call modifyBook(?, ?, ?, ?, ?, ?, ?)}");
                CallableStatement statement3 = conn.prepareCall("{call removeAuthors(?)}");
                CallableStatement statement2 = conn.prepareCall("{call addAuthor(?, ?)}")
        ) {
            modify(ISBN, title, publisher, publicationYear, sellingPrice, category, arrOfAuthors, threshold, statement1, statement2, statement3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modify(int ISBN, String title, String publisher, int publicationYear, int sellingPrice, String category, String[] arrOfAuthors, int threshold, CallableStatement statement1, CallableStatement statement2, CallableStatement statement3) throws SQLException {
        statement1.setInt(1, ISBN);
        statement1.setString(2, title);
        statement1.setString(3, publisher);
        statement1.setInt(6, publicationYear);
        statement1.setInt(5, sellingPrice);
        statement1.setString(4, category);
        statement1.setInt(7, threshold);
        statement1.execute();
        if (statement3 != null) {
            statement3.setInt(1, ISBN);
        }
        for (String author : arrOfAuthors) {
            statement2.setInt(1, ISBN);
            statement2.setString(2, author);
            statement2.execute();
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
    public ResultSet ISBNSearch(int ISBN) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByISBN(?)}")
        ) {
            statement.setInt(1, ISBN);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet titleSearch(String title) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByTitle(?)}")
        ) {
            statement.setString(1, title);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet authorSearch(String author) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByAuthor(?)}")
        ) {
            statement.setString(1, author);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet publisherSearch(String publisher) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByPublisher(?)}")
        ) {
            statement.setString(1, publisher);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet categorySearch(String category) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call searchByCategory(?)}")
        ) {
            statement.setString(1, category);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public ResultSet viewCart() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call viewCart(?)}")
        ) {
            statement.setString(1, this.username);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeFromCart(int ISBN, int copiesNo) {
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
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call checkOut(?)}")
        ) {
            statement.setString(1, this.username);
            statement.execute();
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
                CallableStatement statement = conn.prepareCall("{call promoteUser(?)}")
        ) {
            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet totalSalesReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call totalSalesReport()}")
        ) {
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet top5CustomersReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call top5CustomersReport()}")
        ) {
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet top10SellingBooksReport() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call top10SellingBooksReport()}")
        ) {
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
