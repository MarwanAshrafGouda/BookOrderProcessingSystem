package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML


    public void logIn(ActionEvent event) throws IOException {
        /// TODO: change the scene to the first view of the app

        defaultView(event);
    }
    public void signUp(ActionEvent event) throws IOException {
        /// TODO: change the scene to the first view of the app

        defaultView(event);
    }
    private void defaultView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ManagerView.fxml"));
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
