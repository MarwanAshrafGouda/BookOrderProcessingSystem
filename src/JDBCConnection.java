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
            String dbURL = "jdbc:mysql://localhost:3306/Bookstore?autoReconnect=true&useSSL=false";
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
            statement.setString(2, lastName);
            statement.setString(1, emailAddress);
            statement.setString(2, phoneNumber);
            statement.setString(2, shippingAddress);
            statement.execute();
            this.username = username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int signIn(String username, String password) {
        // returns 0 if a customer and 1 if a manager
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call signIn(?, ?)}")
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            this.username = username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call addBook(?, ?, ?, ?, ?, ?)}");
                CallableStatement statement2 = conn.prepareCall("{call addAuthor(?, ?)}")
        ) {
            modify(ISBN, title, publisher, publicationYear, sellingPrice, category, arrOfAuthors, statement1, statement2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category) {
        String[] arrOfAuthors = authors.split(","), databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement1 = conn.prepareCall("{call modifyBook(?, ?, ?, ?, ?, ?)}");
                CallableStatement statement2 = conn.prepareCall("{call modifyAuthor(?, ?)}")
        ) {
            modify(ISBN, title, publisher, publicationYear, sellingPrice, category, arrOfAuthors, statement1, statement2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modify(int ISBN, String title, String publisher, int publicationYear, int sellingPrice, String category, String[] arrOfAuthors, CallableStatement statement1, CallableStatement statement2) throws SQLException {
        statement1.setInt(1, ISBN);
        statement1.setString(2, title);
        statement1.setString(3, publisher);
        statement1.setInt(4, publicationYear);
        statement1.setInt(5, sellingPrice);
        statement1.setString(6, category);
        statement1.execute();
        for (String arrOfAuthor : arrOfAuthors) {
            statement2.setInt(1, ISBN);
            statement2.setString(2, arrOfAuthor);
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
                CallableStatement statement = conn.prepareCall("{call ISBNSearch(?)}")
        ) {
            statement.setInt(1, ISBN);
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
                CallableStatement statement = conn.prepareCall("{call titleSearch(?)}")
        ) {
            statement.setString(1, title);
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
                CallableStatement statement = conn.prepareCall("{call authorSearch(?)}")
        ) {
            statement.setString(1, author);
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
                CallableStatement statement = conn.prepareCall("{call publisherSearch(?)}")
        ) {
            statement.setString(1, publisher);
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
                CallableStatement statement = conn.prepareCall("{call categorySearch(?)}")
        ) {
            statement.setString(1, category);
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editUserInfo(String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call categorySearch(?, ?, ?, ?, ?, ?, ?)}")
        ) {
            statement.setString(1, this.username);
            statement.setString(2, password);
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
                CallableStatement statement = conn.prepareCall("{call removeFromCart(?, ?, ?)}")
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
