package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;


public class SearchViewController {
    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    Vector<Vector<String>> resultTable;


    @FXML
    private Group searchView_group,resultView_group;
    //SearchView Attributes
    @FXML
    private TextField search_txt;
    @FXML
    private Label search_label;
    @FXML
    private RadioButton ISBN_rbtn, Title_rbtn, Author_rbtn, Publisher_rbtn, Category_rbtn;
    @FXML
    ProgressIndicator progressInd;
    //resultView Attributes

    @FXML
    private TableView resultTableview;

    private String searchBy;


    @FXML
    public void initialize(){
        searchView_group.setVisible(true);
        resultView_group.setVisible(false);
    }

    //SearchView Methods
    public void searchMethod() {
        if (ISBN_rbtn.isSelected()) {
            searchBy = ISBN_rbtn.getText();
        } else if (Title_rbtn.isSelected()) {
            searchBy = Title_rbtn.getText();
        } else if (Author_rbtn.isSelected()) {
            searchBy = Author_rbtn.getText();
        } else if (Publisher_rbtn.isSelected()) {
            searchBy = Publisher_rbtn.getText();
        } else if (Category_rbtn.isSelected()) {
            searchBy = Category_rbtn.getText();
        }
        search_label.setText("Enter " + searchBy);
        search_label.setAlignment(Pos.CENTER);
    }

    public void searchDB(ActionEvent event) throws IOException {
        progressInd.setVisible(true);

        String txt = search_txt.getText();
        boolean use_author = false;

        if (searchBy.equals("ISBN")) {
            resultTable = dbConn.ISBNSearch(Integer.parseInt(txt));
        } else if (searchBy.equals("Title")) {
            resultTable = dbConn.titleSearch(txt);
        } else if (searchBy.equals("Author")) {
            resultTable = dbConn.authorSearch(txt);
            use_author = true;
        } else if (searchBy.equals("Publisher")) {
            resultTable = dbConn.publisherSearch(txt);
        } else if (searchBy.equals("Category")) {
            resultTable = dbConn.categorySearch(txt);
        }
        showResult(use_author);
        searchView_group.setVisible(false);
        resultView_group.setVisible(true);

    }

    private ObservableList<Book> getBooks(boolean use_author){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for(Vector<String> v: resultTable){
            books.add(new Book(v,use_author));
        }
        return books;
    }


    private void showResult(boolean use_author){
        resultTableview.getColumns().clear();
        resultTableview.setItems(getBooks(use_author));
        for(String s: Book.attributesNames(use_author)){
            TableColumn<Book,String> col =new TableColumn<>(s);
            s = s.replaceAll("\\s","");
            col.setMinWidth(200);
            col.setCellValueFactory(new PropertyValueFactory<>(s));
            resultTableview.getColumns().add(col);
        }
    }

    public void backToSearch(){
        searchView_group.setVisible(true);
        resultView_group.setVisible(false);
        progressInd.setVisible(false);
    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DefaultView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
