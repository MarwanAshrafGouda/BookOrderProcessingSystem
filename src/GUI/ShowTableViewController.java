package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class ShowTableViewController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label Title_label;
    private TableView tableView;

    private String parentView;
    private Vector<Vector<String>> table;

    public void initializeView(Vector<Vector<String>> t, String title, String v){
        Title_label.setText(title);
        parentView = v ;
        table = t;
    }

    public void initialize(){
        /// TODO: draw table
    }


    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(parentView));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
