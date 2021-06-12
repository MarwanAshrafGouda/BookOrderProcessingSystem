import java.sql.*;

public interface IJDBCConnection {
    String getUsername();

    String[] JDBCConnect();

    void signUp(String username, String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    int signIn(String username, String password);

    void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category);

    void modifyBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category);

    void confirmOrder(int orderNo);

    ResultSet ISBNSearch(int ISBN);

    ResultSet titleSearch(String title);

    ResultSet authorSearch(String author);

    ResultSet publisherSearch(String publisher);

    ResultSet categorySearch(String category);

    void editUserInfo(String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    void addToCart(int ISBN, int copiesNo);

    ResultSet viewCart();

    void removeFromCart(int ISBN, int copiesNo);

    void checkOut();

    void logOut();

    void promoteUser(String username);

    void totalSalesReport();

    void top5CustomersReport();

    void top10SellingBooksReport();
}
