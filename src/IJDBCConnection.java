import java.sql.*;
import java.util.Vector;

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
    Vector<Vector<String>> ISBNSearch(int ISBN);
    // has button -> Search view
    Vector<Vector<String>> titleSearch(String title);
    // has button -> Search view
    Vector<Vector<String>> authorSearch(String author);
    // has button -> Search view
    Vector<Vector<String>> publisherSearch(String publisher);
    // has button -> Search view
    Vector<Vector<String>> categorySearch(String category);

    // has button -> default view
    void editUserInfo(String password, String firstName, String lastName, String emailAddress, String phoneNumber, String shippingAddress);

    void editUserPassword(String oldPassword, String newPassword);

    //CART:
    // has button -> default view
    void addToCart(int ISBN, int copiesNo);
    // has button -> cart view
    Vector<Vector<String>> viewCart();
    // has button -> cart view
    void removeFromCart(int ISBN);

    void checkOut();

    void logOut();

    // manager
    // has button -> default view
    void confirmOrder(int orderNo);
    //BOOKS: Manager
    void addBook(int ISBN, String title, String authors, String publisher, int publicationYear, double sellingPrice, String category, int threshold);
    // manager
    void addPublisher(String name, String address, String phoneNumber);
    // manager
    void modifyBook(int ISBN, int newISBN, String title, String authors, String publisher, int publicationYear, int sellingPrice, String category, int threshold);
    // manager
    void promoteUser(String username);
    // manager
    void totalSalesReport();
    // manager
    void top5CustomersReport();
    // manager
    void top10SellingBooksReport();
}
