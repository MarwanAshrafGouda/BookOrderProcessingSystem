package GUI;

import Connection.IJDBCConnection;
import Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerViewController {



    IJDBCConnection dbConn = JDBCConnection.getInstance();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Group orders_group, users_group;
    @FXML
    private TextField orderNumber_txt, username_txt;
    @FXML
    private Button search_btn;

    @FXML
    ProgressIndicator progressInd_reports;

    public void initialize(){

    }

// REPORTS:
    public void Top5Customers() {
        orders_group.setVisible(false);
        users_group.setVisible(false);

        progressInd_reports.setVisible(true);
        dbConn.top5CustomersReport();
        progressInd_reports.setVisible(false);
    }
    public void Top10SellingBooks() {
        orders_group.setVisible(false);
        users_group.setVisible(false);

        progressInd_reports.setVisible(true);
        dbConn.top10SellingBooksReport();
        progressInd_reports.setVisible(false);
    }
    public void TotalSales() {
        orders_group.setVisible(false);
        users_group.setVisible(false);

        progressInd_reports.setVisible(true);
        dbConn.totalSalesReport();
        progressInd_reports.setVisible(false);
    }
// UPDATE BOOKS
    public void addBook(ActionEvent event) throws IOException {
        updateBook(event , true);
    }
    public void modifyBook(ActionEvent event) throws IOException {
        updateBook(event , false);
    }
    private void updateBook(ActionEvent event , boolean addBook) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateBook.fxml"));
        root = loader.load();
        UpdateBookController updateBookController = loader.getController();
        updateBookController.setUpdateType(addBook);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//ORDERS:
        public void showOrderSettings(){
            orders_group.setVisible(true);
            users_group.setVisible(false);
        }

        public void confirmOrder(){
            dbConn.confirmOrder(Integer.parseInt(username_txt.getText()));
        }

// USERS:
        public void showUserSettings(){
            orders_group.setVisible(false);
            users_group.setVisible(true);
        }

        public void promoteUser(){
            dbConn.promoteUser(username_txt.getText());
        }

//back
    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DefaultView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
