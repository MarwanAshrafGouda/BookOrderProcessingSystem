package GUI;

import java.util.Vector;

public class Cart {
    private String ISBN;
    private String Title;
    private String Category;
    private String PublisherName;
    private String PublicationYear;
    private String Quantity;
    private String UnitPrice;
    private String Price;

    public Cart(){
        this.ISBN= "NULL";
        this.Title= "NULL";
        this.Category= "NULL";
        this.PublisherName= "NULL";
        this.PublicationYear= "NULL";
        this.Quantity= "NULL";
        this.UnitPrice= "NULL";
        this.Price= "NULL";
    }

    public Cart(Vector<String> tableHeader){
        this.ISBN= tableHeader.get(0);
        this.Title= tableHeader.get(1);
        this.Category= tableHeader.get(2);
        this.PublisherName= tableHeader.get(3);
        this.PublicationYear= tableHeader.get(4);
        this.Quantity= tableHeader.get(5);
        this.UnitPrice= tableHeader.get(6);
        this.Price= tableHeader.get(7);
    }

    public static String[] attributesNames(){
        return new String[] {"ISBN", "Title", "Category", "Publisher Name",  "Publication Year", "Quantity", "UnitPrice", "Price"};
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPublisherName() {
        return PublisherName;
    }

    public void setPublisherName(String publisherName) {
        PublisherName = publisherName;
    }

    public String getPublicationYear() {
        return PublicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        PublicationYear = publicationYear;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


}
