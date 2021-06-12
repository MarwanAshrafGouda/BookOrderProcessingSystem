import java.sql.*;

public interface IJDBCConnection {
    String getUsername();

    Boolean getIsManager();

    void signUp(String username, String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    int signIn(String username, String password);

    void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category);

    void modifyBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category);

    ResultSet ISBNSearch(int ISBN);

    ResultSet titleSearch(String title);

    ResultSet authorSearch(String author);

    ResultSet publisherSearch(String publisher);

    ResultSet categorySearch(String category);

    void addToCart(int ISBN, int copiesNo);

    ResultSet viewCart();

    void removeFromCart(int ISBN, int copiesNo);

    void checkOut();

    void logOut();

    // manager
    void confirmOrder(int orderNo);

    // manager
    void editUserInfo(String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    // manager
    void promoteUser(String username);

    // manager
    void totalSalesReport();

    // manager
    void top5CustomersReport();

    // manager
    void top10SellingBooksReport();
}
