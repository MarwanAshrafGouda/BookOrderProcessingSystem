package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    //SearchView Attributes
    @FXML
    private TextField search_txt;
    @FXML
    private Label search_label;
    @FXML
    private RadioButton ISBN_rbtn, Title_rbtn, Author_rbtn, Publisher_rbtn, Category_rbtn;
    @FXML
    ProgressIndicator progressInd;

    private String searchBy;

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

        if (searchBy.equals("ISBN")) {
            resultTable = dbConn.ISBNSearch(Integer.parseInt(txt));
        } else if (searchBy.equals("Title")) {
            resultTable = dbConn.titleSearch(txt);
        } else if (searchBy.equals("Author")) {
            resultTable = dbConn.authorSearch(txt);
        } else if (searchBy.equals("Publisher")) {
            resultTable = dbConn.publisherSearch(txt);
        } else if (searchBy.equals("Category")) {
            resultTable = dbConn.categorySearch(txt);
        }

        System.out.println("IN : SearchViewController ");/// TODO: to be deleted
        System.out.println("tring to print table from here "); /// TODO: to be deleted
        System.out.println("rows = "+(((Integer)resultTable.size()).toString()) +"Cols = "+(((Integer)resultTable.get(0).size()).toString()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUserController.fxml"));
        root = loader.load();
        loader.setController(new ShowTableViewController());
        ShowTableViewController userController = loader.getController();
        userController.initializeView(resultTable, "Search Using " + txt, "SearchView.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
