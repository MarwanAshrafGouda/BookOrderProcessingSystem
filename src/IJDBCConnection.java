import java.sql.*;

public interface IJDBCConnection {
    //ATTRIBUTES:
    String getUsername();
    Boolean getIsManager();

    // LOGIN:
    // has button -> logIn view
    void signUp(String username, String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);
    // has button -> logIn view
    Boolean signIn(String username, String password);

    // SEARCH:
    // has button -> Search view
    ResultSet ISBNSearch(int ISBN);
    // has button -> Search view
    ResultSet titleSearch(String title);
    // has button -> Search view
    ResultSet authorSearch(String author);
    // has button -> Search view
    ResultSet publisherSearch(String publisher);
    // has button -> Search view
    ResultSet categorySearch(String category);

    // has button -> default view
    void editUserInfo(String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    public void editUserPassword(String oldPassword, String newPassword);

    //CART:
    // has button -> default view
    void addToCart(int ISBN, int copiesNo);
    // has button -> cart view
    ResultSet viewCart();
    // has button -> cart view
    void removeFromCart(int ISBN, int copiesNo);

    void checkOut();

    void logOut();

    // manager
    // has button -> default view
    void confirmOrder(int orderNo);
    //BOOKS: Manager
    void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold);
    // manager
    void modifyBook(int ISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold);
    // manager
    void promoteUser(String username);
    // manager
    ResultSet totalSalesReport();
    // manager
    ResultSet top5CustomersReport();
    // manager
    ResultSet top10SellingBooksReport();
}
