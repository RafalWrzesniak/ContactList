package ContactListPackage;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML private Button closeDialogButton;

    @FXML private BorderPane mainBorderPane;

    @FXML public void exitProgram() {
        Platform.exit();
    }

    @FXML
    public void showHelpDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("HelpWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.show();
    }

    public void closeDialog() {
        Stage stage = (Stage) closeDialogButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, String> tableName;
    @FXML
    private TableColumn<Contact, String> tableSurname;
    @FXML
    private TableColumn<Contact, String> tablePhone;
    @FXML
    private TableColumn<Contact, String> tableNote;

    private ArrayList<Contact> contacts;

    Contact kl = new Contact("Klaudia", "Johns", 123123123, "girl");
    Contact rf = new Contact("Rafał", "Wick", 456456456, "boy");

    ObservableList<Contact> lista = FXCollections.observableArrayList(kl, rf);


    public void initialize() {

        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        contactTable.setItems(lista);
}


}

//
//    ObservableList<Person> data = ...
//        TableView<Person> tableView = new TableView<Person>(data);
//
//        TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
//        firstNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
//public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
//        // p.getValue() returns the Person instance for a particular TableView row
//        return p.getValue().firstNameProperty();
//        }
//        });
//
//        tableView.getColumns().add(firstNameCol);}