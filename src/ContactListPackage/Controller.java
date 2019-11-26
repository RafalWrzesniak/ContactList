package ContactListPackage;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.robot.Robot;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {

    @FXML private Button clearAllButton = new Button();
    @FXML private Button ncApplyButton = new Button();
    @FXML private MenuButton ncMenuButton = new MenuButton();
    @FXML private AnchorPane anchor = new AnchorPane();
    @FXML private Label labelka = new Label();
    @FXML private BorderPane mainBorderPane = new BorderPane();
    @FXML private TableView<Contact> contactTable = new TableView<>();
    @FXML private TableColumn<Contact, String> tableName = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableSurname = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tablePhone = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableNote = new TableColumn<>();
    @FXML public TextField ncPopName;
    @FXML public TextField ncPopSurName;
    @FXML public TextField ncPopPhone;
    @FXML public TextField ncPopNote;

    @FXML
    public void exitProgram() {
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

    private void showCorrectionInfo(String infoText, boolean showHide){
        final TranslateTransition translateLabel = new TranslateTransition(Duration.millis(750), anchor);
        if (showHide) {
            if(!labelka.isVisible() && !translateLabel.getStatus().equals(Animation.Status.RUNNING)){
                System.out.println("show executing");
                labelka.setText(infoText);
                labelka.setVisible(true);
                translateLabel.setFromY(44);
                translateLabel.setToY(0);
                translateLabel.play();
            }
        } else if(labelka.isVisible() && !translateLabel.getStatus().equals(Animation.Status.RUNNING)){
            System.out.println("hide executing");
            translateLabel.setFromY(0);
            translateLabel.setToY(44);
            translateLabel.setOnFinished(actionEvent -> labelka.setVisible(false));
            translateLabel.play();
        }
    }
    private boolean checkTextFieldsOptions(Contact newContact) {
        if (isAnyFieldFilled()) {
            try {
                newContact.setName(ncPopName.getText());
                newContact.setSurname(ncPopSurName.getText());
                newContact.setPhone(ncPopPhone.getText());
                newContact.setNote(ncPopNote.getText());
                return true;
            } catch (IllegalArgumentException e) {
//                System.out.println(e.getMessage());
                showCorrectionInfo(e.getMessage(), true);
            }
        }
        return false;
    }

    private boolean isAnyFieldFilled() {
        return (!ncPopName.getText().trim().isEmpty() || !ncPopSurName.getText().trim().isEmpty() ||
                !ncPopPhone.getText().trim().isEmpty() || !ncPopNote.getText().trim().isEmpty());
    }

    @FXML
    private void ncApply() {
        Contact newContact = new Contact();
        if (checkTextFieldsOptions(newContact)){
            lista.add(newContact);
            contactTable.setItems(lista);
            contactTable.refresh();
            clearNcFields();
            tabPresser.keyPress(KeyCode.ESCAPE);
        }
    }


    private Robot tabPresser = new Robot();
    @FXML
    private void handleKeyEvents(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            ncApply();
        } else if(keyEvent.getCode() == KeyCode.TAB) {
            tabPresser.keyPress(KeyCode.TAB);
        }
    }

    @FXML
    private void backgroundErrorChecking() {
        PauseTransition checkForErrorFixed = new PauseTransition(Duration.seconds(1));
        checkForErrorFixed.setOnFinished((e) -> {
            if(!isAnyFieldFilled() || checkTextFieldsOptions(new Contact())) {
                showCorrectionInfo("", false);
            }
            if(ncMenuButton.isShowing()){
                checkForErrorFixed.playFromStart();
            } else {
                checkForErrorFixed.stop();
            }
        });
        checkForErrorFixed.play();
    }

    @FXML
    public void clearNcFields() {
        ncPopName.clear();
        ncPopSurName.clear();
        ncPopPhone.clear();
        ncPopNote.clear();
    }

    private void showMenuAfterError() {
        if (labelka.isVisible()) {
            System.out.println("menu error should be shown");
            ncMenuButton.show();
        }
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

        clearAllButton.setOnMouseReleased(actionEvent -> ncMenuButton.show());
        ncApplyButton.setOnMouseReleased(actionEvent -> showMenuAfterError());
        anchor.setPrefHeight(44);
        anchor.setPrefWidth(0);
        labelka.setVisible(false);


    }


}
