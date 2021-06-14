package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DefaultViewController {

    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField addToCart_ISBN_txt,addToCart_copies_txt, removeFromCart_ISBN_txt;
    @FXML
    private Button managerOptions;
    @FXML
    public void initialize(){
        if(dbConn.getIsManager()){
            managerOptions.setDisable(false);
        }else{
            managerOptions.setDisable(true);
        }
    }

    public void AddToCart(){
        dbConn.addToCart(Integer.parseInt(addToCart_ISBN_txt.getText()), Integer.parseInt(addToCart_copies_txt.getText()));
    }

    public void RemoveFromCart(){
        dbConn.removeFromCart(Integer.parseInt(removeFromCart_ISBN_txt.getText()));
    }

    public void ViewCart(ActionEvent event) throws IOException{

    }

    public void Checkout(){
        dbConn.checkOut();
    }

    public void EditUserPassword(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUserPassword.fxml"));
        root = loader.load();
        EditUserController userController = loader.getController();
        userController.setView("DefaultView.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void EditUserInfo(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditUserInfo.fxml"));
        root = loader.load();
        EditUserController userController = loader.getController();
        userController.setView("DefaultView.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LogOut(ActionEvent event) throws IOException {
        dbConn.logOut();
        changeScene(event, "LogIn.fxml");
    }

    public void searchV(ActionEvent event) throws IOException {
        changeScene(event, "SearchView.fxml");
    }

    public void managerV(ActionEvent event) throws IOException {
        changeScene(event, "ManagerView.fxml");
    }

    public void changeScene(ActionEvent event, String strScene)throws IOException {
        root = FXMLLoader.load(getClass().getResource(strScene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
