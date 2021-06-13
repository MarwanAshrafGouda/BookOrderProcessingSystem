package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Connection.IJDBCConnection;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            primaryStage.setTitle("شبب بيت زعطوط BookStore");
            Image icon = new Image("GUI/logo.jpg");
            primaryStage.getIcons().add(icon);
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                logout(primaryStage);
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Are you sure?");
        if (alert.showAndWait().get() == ButtonType.OK){
            System.out.println("You successfully closed the application");
            stage.close();
        }
    }

}
