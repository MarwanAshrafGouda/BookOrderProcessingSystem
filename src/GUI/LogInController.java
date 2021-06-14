package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username_txt  ,userName_txt, firstName_txt,lastName_txt, emailAddress_txt, phone_txt,  shippingAddress_txt;
    @FXML
    private PasswordField signUp_Password_txt, logIn_password_txt;

    public void logIn(ActionEvent event) throws IOException {
        dbConn.signIn(username_txt.getText(), logIn_password_txt.getText());
        changeScene(event, "DefaultView.fxml");
    }
    public void signUp(ActionEvent event) throws IOException {
        dbConn.signUp(userName_txt.getText(), signUp_Password_txt.getText(), firstName_txt.getText(),lastName_txt.getText(), emailAddress_txt.getText(), phone_txt.getText(),  shippingAddress_txt.getText());
        /// to move to the defaultView there is an extra logical work must be done ,so for simplicity after singing up move to loginView
        changeScene(event, "LogIn.fxml");
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        changeScene(event, "SignUp.fxml");
    }

    public void switchToLogIn(ActionEvent event) throws IOException {
        changeScene(event, "LogIn.fxml");
    }

    private void changeScene(ActionEvent event, String strScene) throws IOException {
        root = FXMLLoader.load(getClass().getResource(strScene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void popMSG(Stage stage, String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Are you sure?");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}
