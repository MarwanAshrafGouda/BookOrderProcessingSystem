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


public class SearchViewController {
    IJDBCConnection dbConn = new JDBCConnection();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    //SearchView Attributes
    @FXML
    private TextField search_txt;
    @FXML
    private Button search_btn;
    @FXML
    private Label search_label;
    @FXML
    private RadioButton ISBN_rbtn, Title_rbtn, Author_rbtn, Publisher_rbtn, Category_rbtn;
    @FXML
    ProgressIndicator progressInd;

    private String searchBy;
    //SearchView Methods
    public void searchMethod() {
        if(ISBN_rbtn.isSelected()) {
            searchBy = ISBN_rbtn.getText();
        }
        else if(Title_rbtn.isSelected()) {
            searchBy =Title_rbtn.getText();
        }
        else if(Author_rbtn.isSelected()) {
            searchBy =Author_rbtn.getText();
        }
        else if(Publisher_rbtn.isSelected()) {
            searchBy =Publisher_rbtn.getText();
        }
        else if(Category_rbtn.isSelected()) {
            searchBy = Category_rbtn.getText();
        }
        search_label.setText("Enter "+searchBy);
        search_label.setAlignment(Pos.CENTER);
    }

    public void searchDB() {
        progressInd.setVisible(true);

        String txt = search_txt.getText();

        if(searchBy.equals("ISBN")) {
            dbConn.ISBNSearch(Integer.parseInt(txt));
        }
        else if(searchBy.equals("Title")) {
            dbConn.titleSearch(txt);
        }
        else if(searchBy.equals("Author")) {
            dbConn.authorSearch(txt);
        }else if(searchBy.equals("Publisher")){
            dbConn.publisherSearch(txt);
        }else if(searchBy.equals("Category")){
            dbConn.categorySearch(txt);
        }

        progressInd.setVisible(false);
    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DefaultView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
