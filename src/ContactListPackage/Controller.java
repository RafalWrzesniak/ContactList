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

        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Run away from here", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.show();

    }

    @FXML
    private TableView<Contact> contactTable = new TableView<>();
    @FXML
    private TableColumn<Contact, String> tableName = new TableColumn<>();
    @FXML
    private TableColumn<Contact, String> tableSurname = new TableColumn<>();
    @FXML
    private TableColumn<Contact, String> tablePhone = new TableColumn<>();
    @FXML
    private TableColumn<Contact, String> tableNote = new TableColumn<>();



    Contact kl = new Contact("Klaudia", "Johns", 123123123, "girl");
    Contact rf = new Contact("Rafał", "Wick", 456456456, "boy");
    ObservableList<Contact> lista = FXCollections.observableArrayList(kl, rf);
    Contact th = new Contact("Someone", "Else", 789789789, "Here");

    public void initialize() {

        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        lista.add(th);
        contactTable.setItems(lista);
}

}
