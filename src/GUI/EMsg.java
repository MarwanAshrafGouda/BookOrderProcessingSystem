package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EMsg {


    public static void error( String title, String header, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        if (alert.showAndWait().get() == ButtonType.OK){

        }
    }
}
