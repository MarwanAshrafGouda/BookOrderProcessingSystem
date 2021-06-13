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

public class EditUserController {
    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField editPass_oldPassword_txt, editPass_newPassword_txt, editInfo_password_txt, editInfo_firstName_txt, editInfo_lastName_txt , editInfo_emailAddress_txt, editInfo_phoneNumber_txt, editInfo_shippingAddress_txt ;
    String back_view;

    public void setView(String v){
        back_view = v;
    }

    public void EditUserInfo(){
        dbConn.editUserInfo( editInfo_password_txt.getText(),  editInfo_firstName_txt.getText(),  editInfo_lastName_txt.getText(),  editInfo_emailAddress_txt.getText(),  editInfo_phoneNumber_txt.getText(),  editInfo_shippingAddress_txt.getText());
    }
    public void EditUserPassword() {
        dbConn.editUserPassword(editPass_oldPassword_txt.getText(), editPass_newPassword_txt.getText());
    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(back_view));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
