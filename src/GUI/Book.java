package GUI;

import javafx.collections.ObservableList;

import java.util.Vector;

public class Book {

    private String ISBN;
    private String Title;
    private String PublisherName;
    private String Category;
    private String Price;
    private String PublicationYear;
    private String NumberOfCopiesInStock;
    private String Author;

    public Book(){
        this.ISBN = "NULL";
        this.Title = "NULL";
        this.PublisherName = "NULL";
        this.Category = "NULL";
        this.Price = "NULL";
        this.PublicationYear = "NULL";
        this.NumberOfCopiesInStock = "NULL";
        this.Author = "NULL";
    }
    public Book(Vector<String> tableHeader, boolean author){
        this.ISBN = tableHeader.get(0);
        this.Title = tableHeader.get(1);
        this.PublisherName = tableHeader.get(2);
        this.Category = tableHeader.get(3);
        this.Price = tableHeader.get(4);
        this.PublicationYear = tableHeader.get(5);
        this.NumberOfCopiesInStock = tableHeader.get(6);

        if(author)
            this.Author = tableHeader.get(7);
        else
            this.Author = "NULL";
    }

    public static String[] attributesNames(boolean author){
        if(author)
            return new String[]{"ISBN", "Title", "Publisher Name", "Category", "Price", "Publication Year", "Number Of Copies In Stock", "Author"};
        else
            return new String[]{"ISBN", "Title", "Publisher Name", "Category", "Price", "Publication Year", "Number Of Copies In Stock"};
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

    public String getPublisherName() {
        return PublisherName;
    }

    public void setPublisherName(String publisherName) {
        PublisherName = publisherName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPrice() {
        return Price;
    }

    public String getNumberOfCopiesInStock() {
        return NumberOfCopiesInStock;
    }

    public void setNumberOfCopiesInStock(String numberOfCopiesInStock) {
        NumberOfCopiesInStock = numberOfCopiesInStock;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPublicationYear() {
        return PublicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        PublicationYear = publicationYear;
    }



    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
