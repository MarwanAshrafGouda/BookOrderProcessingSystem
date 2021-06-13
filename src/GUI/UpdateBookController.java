package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateBookController{
    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label updateType_label;
    @FXML
    private TextField ISBN_txt , newISBN_txt , title_txt , authors_txt, publisher_txt, publicationYear_txt, sellingPrice_txt, category_txt, threshold_txt;
    @FXML
    ProgressIndicator progressInd_update;
    @FXML
    private Group newISBN_group;
    boolean updateType = true;


    public void setUpdateType(boolean type) {
        updateType = type;
        if(type){
            updateType_label.setText("ADD BOOK");
            newISBN_group.setVisible(false);
        }else{
            updateType_label.setText("Modify BOOK");
            newISBN_group.setVisible(true);
        }
    }

    public void update() {
        progressInd_update.setVisible(true);
        if (updateType) {
            dbConn.addBook(Integer.parseInt(ISBN_txt.getText()), title_txt.getText(), authors_txt.getText(), publisher_txt.getText(), Integer.parseInt(publicationYear_txt.getText()), Integer.parseInt(sellingPrice_txt.getText()), category_txt.getText(), Integer.parseInt(threshold_txt.getText()));
        }else {
            dbConn.modifyBook(Integer.parseInt(ISBN_txt.getText()),Integer.parseInt(newISBN_txt.getText()), title_txt.getText(), authors_txt.getText(), publisher_txt.getText(), Integer.parseInt(publicationYear_txt.getText()), Integer.parseInt(sellingPrice_txt.getText()), category_txt.getText(), Integer.parseInt(threshold_txt.getText()));
        }
        progressInd_update.setVisible(false);
    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ManagerView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
