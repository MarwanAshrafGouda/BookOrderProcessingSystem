package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TextField username_txt , password_txt ,userName_txt, Password_txt, firstName_txt,lastName_txt, emailAddress_txt, phone_txt,  shippingAddress_txt;


    public void logIn(ActionEvent event) throws IOException {
        dbConn.signIn(username_txt.getText(), password_txt.getText());
        defaultView(event);
    }
    public void signUp(ActionEvent event) throws IOException {
        dbConn.signUp(userName_txt.getText(), Password_txt.getText(), firstName_txt.getText(),lastName_txt.getText(), emailAddress_txt.getText(), phone_txt.getText(),  shippingAddress_txt.getText());
        defaultView(event);
    }

    private void defaultView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DefaultView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
