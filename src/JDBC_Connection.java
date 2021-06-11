import java.sql.*;

public class JDBC_Connection {

    private String username;

    String[] JDBCConnect() {
        {
            // change "SchemaName" with the name of your schema
            String dbURL = "jdbc:mysql://localhost:3306/SchemaName?autoReconnect=true&useSSL=false";
            String user = "root";
            // change "Secret" with your password
            String password = "Secret";
            return new String[]{dbURL, user, password};
        }
    }

    public void signUp(String username, String password) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call signUp(?, ?)}")
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            this.username = username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signIn(String username, String password) {
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
    }

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

    public void ISBNSearch(int ISBN) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call ISBNSearch(?)}")
        ) {
            statement.setInt(1, ISBN);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void titleSearch(String title) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call titleSearch(?)}")
        ) {
            statement.setString(1, title);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void authorSearch(String author) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call authorSearch(?)}")
        ) {
            statement.setString(1, author);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void publisherSearch(String publisher) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call publisherSearch(?)}")
        ) {
            statement.setString(1, publisher);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void categorySearch(String category) {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call categorySearch(?)}")
        ) {
            statement.setString(1, category);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void viewCart() {
        String[] databaseInformation = JDBCConnect();
        try (
                Connection conn = DriverManager.getConnection(databaseInformation[0], databaseInformation[1], databaseInformation[2]);
                CallableStatement statement = conn.prepareCall("{call viewCart(?)}")
        ) {
            statement.setString(1, this.username);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void logOut() {
        this.username = "";
    }

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

    public void totalSalesReport () {
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

    public void top5CustomersReport () {
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

    public void top10SellingBooksReport () {
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
