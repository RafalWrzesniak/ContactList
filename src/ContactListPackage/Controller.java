package ContactListPackage;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {

    @FXML private AnchorPane anchor;
    @FXML private Label labelka = new Label();
    @FXML private BorderPane mainBorderPane = new BorderPane();
    @FXML private TableView<Contact> contactTable = new TableView<>();
    @FXML private TableColumn<Contact, String> tableName = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableSurname = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tablePhone = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableNote = new TableColumn<>();
    @FXML private MenuButton ncMenuButton;
    @FXML public TextField ncPopName;
    @FXML public TextField ncPopSurName;
    @FXML public TextField ncPopPhone;
    @FXML public TextField ncPopNote;

    @FXML
    public void exitProgram() {
        Platform.exit();
    }

    private void showCorrectionInfo(String infoText, boolean showHide){
        int animationTime = 750;
        if (!infoText.isEmpty()) {
            labelka.setText(infoText);
            labelka.setVisible(true);
        }

        final TranslateTransition translateLabel =
                new TranslateTransition(Duration.millis(animationTime), anchor);
        if (showHide) {
            translateLabel.setFromY(44);
            translateLabel.setToY(0);
        } else {
            translateLabel.setFromY(0);
            translateLabel.setToY(44);
        }
        translateLabel.play();

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
    private void ncApply() {

        ncPopName.getTooltip().activatedProperty();
        if (!ncPopName.getText().trim().isEmpty() || !ncPopSurName.getText().trim().isEmpty() ||
                !ncPopPhone.getText().trim().isEmpty() || !ncPopNote.getText().trim().isEmpty()) {
            Contact newContact = new Contact();
            try {
                newContact.setName(ncPopName.getText());
                newContact.setSurname(ncPopSurName.getText());
                newContact.setPhone(ncPopPhone.getText());
                newContact.setNote(ncPopNote.getText());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                showCorrectionInfo(e.getMessage(), true);
                return;
            }


            lista.add(newContact);
            contactTable.setItems(lista);
            contactTable.refresh();
            clearNcFields();
            showCorrectionInfo("", false);
        }
    }

    @FXML
    private void handleEnter(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER) {
            ncApply();
        }

    }

    @FXML
    public void clearNcFields() {
        ncPopName.clear();
        ncPopSurName.clear();
        ncPopPhone.clear();
        ncPopNote.clear();
//        ncMenuButton.show();
    }

    private Contact kl = new Contact("Klaudia", "Johns", "123123123", "girl");
    private Contact rf = new Contact("Rafał", "Wick", "456456456", "boy");
    private ObservableList<Contact> lista = FXCollections.observableArrayList(kl, rf);
    private Contact th = new Contact("Someone", "Else", "789789789", "Here");

    public void initialize() {
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        lista.add(th);
        contactTable.setItems(lista);


        anchor.setPrefHeight(44);
        anchor.setPrefWidth(0);
        labelka.setVisible(false);

    }


}
