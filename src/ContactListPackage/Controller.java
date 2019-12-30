package ContactListPackage;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.robot.Robot;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML private Button createNewContact = new Button();
    @FXML private Button clearAllButton = new Button();
    @FXML private Button ncApplyButton = new Button();
    @FXML private MenuButton ncMenuButton = new MenuButton();
    @FXML private AnchorPane anchor = new AnchorPane();
    @FXML private Label labelka = new Label();
    @FXML public BorderPane mainBorderPane = new BorderPane();
    @FXML public TableView<Contact> contactTable = new TableView<>();
    @FXML private TableColumn<Contact, String> tableName = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableSurname = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tablePhone = new TableColumn<>();
    @FXML private TableColumn<Contact, String> tableNote = new TableColumn<>();
    @FXML private TextField ncPopName;
    @FXML private TextField ncPopSurName;
    @FXML private TextField ncPopPhone;
    @FXML private TextField ncPopNote;


    private Robot tabPresser = new Robot();
    private boolean shouldBeRunning = true;
    public ObservableList<Contact> lista = FXCollections.observableArrayList();
    public String[] columnNames = {"name", "surname", "phone", "note"};


    public void initialize() {
        tableName.setCellValueFactory(new PropertyValueFactory<>(columnNames[0]));
        tableSurname.setCellValueFactory(new PropertyValueFactory<>(columnNames[1]));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>(columnNames[2]));
        tableNote.setCellValueFactory(new PropertyValueFactory<>(columnNames[3]));

        lista = IOoperations.readData(columnNames);
        contactTable.setItems(lista);

        clearAllButton.setOnMouseReleased(actionEvent -> ncMenuButton.show());
        ncApplyButton.setOnMouseReleased(actionEvent -> showMenuAfterError());
        ncMenuButton.setOnMouseReleased(actionEvent -> backgroundErrorChecking());
        anchor.setPrefHeight(44);
        anchor.setPrefWidth(0);
        labelka.setVisible(false);

        final TranslateTransition initTransLabel = new TranslateTransition(Duration.millis(10), anchor);
        initTransLabel.setFromY(0);
        initTransLabel.setToY(44);
        initTransLabel.play();

        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePhone.setCellFactory(TextFieldTableCell.forTableColumn());
        tableNote.setCellFactory(TextFieldTableCell.forTableColumn());

        contactTable.setEditable(true);
        tableName.setEditable(false);

        contactTable.setRowFactory(contactTableView -> {
            final TableRow<Contact> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem editContact = new MenuItem("Edit contact");
            MenuItem deleteContact = new MenuItem("Delete contact");
            MenuItem copyContact = new MenuItem("Copy contact");
            deleteContact.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete contact");
                alert.setHeaderText("Delete conact: " + row.getItem().getName() + " " + row.getItem().getSurname());
                alert.setContentText("Are you sure to delete?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    lista.remove(contactTable.getSelectionModel().getFocusedIndex());
                    contactTable.getItems().remove(row.getItem());
                    infoBanner("Contact " + contactTable.getSelectionModel().getSelectedItem().getName()
                            + " " + contactTable.getSelectionModel().getSelectedItem().getSurname() + " removed", "orange");
                }
            });
            editContact.setOnAction(actionEvent -> showEditContactDialog("Edit"));
            copyContact.setOnAction(actionEvent -> showEditContactDialog("Copy"));
            createNewContact.setOnAction(actionEvent -> showEditContactDialog("Create"));

            rowMenu.getItems().addAll(editContact, copyContact, deleteContact);
            // only display context menu for non-null items:
            row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));
            return row;
        });

    }

    @FXML
    private void ncApply() {
        shouldBeRunning = false;
        if(isAnyFieldFilled()){
            Contact newContact = new Contact(ncPopName.getText(), ncPopSurName.getText(),
                    ncPopPhone.getText(), ncPopNote.getText());
            clearNcFields();
            tabPresser.keyPress(KeyCode.ESCAPE);
            extendColumnWidth();
            addContact(newContact);
        }
    }

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
        shouldBeRunning = true;
        PauseTransition checkForErrorFixed = new PauseTransition(Duration.millis(500));
        checkForErrorFixed.setOnFinished((e) -> {
            if((!isAnyFieldFilled() || checkTextFieldsOptions(new Contact())) && labelka.isVisible() && shouldBeRunning && ncApplyButton.isDisabled()) {
                showCorrectionInfo("", false, "red");
                ncApplyButton.setDisable(false);
            }

            if(shouldBeRunning){
                checkForErrorFixed.playFromStart();
            } else {
                checkForErrorFixed.stop();
            }
        });
        checkForErrorFixed.play();
    }

    private void showCorrectionInfo(String infoText, boolean showHide, String color){
        final TranslateTransition translateLabel = new TranslateTransition(Duration.millis(750), anchor);
        if (showHide) {
            if(!labelka.isVisible() || !labelka.getText().equals(infoText)){
                labelka.setText(infoText);
                labelka.setVisible(true);
                labelka.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-background-color: " + color);
                translateLabel.setFromY(44);
                translateLabel.setToY(0);
                translateLabel.play();
            }
        } else if(labelka.isVisible()){
            translateLabel.setFromY(0);
            translateLabel.setToY(44);
            translateLabel.setOnFinished(actionEvent -> labelka.setVisible(false));
            translateLabel.play();
        }
    }


    @FXML
    private void clearNcFields() {
        ncPopName.clear();
        ncPopSurName.clear();
        ncPopPhone.clear();
        ncPopNote.clear();
    }

    @FXML
    private void exitProgram() {
        Platform.exit();
    }


    @FXML
    private void showHelpDialog() {
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
    private void showEditContactDialog(String title) {
        shouldBeRunning = false;
        Contact tempContact;
        if (!title.equals("Create")) {
            String name = contactTable.getSelectionModel().getSelectedItem().getName();
            String surname = contactTable.getSelectionModel().getSelectedItem().getSurname();
            String phone = contactTable.getSelectionModel().getSelectedItem().getPhone();
            String note = contactTable.getSelectionModel().getSelectedItem().getNote();
            tempContact = new Contact(name, surname, phone, note);
        } else {
            tempContact = new Contact("", "", "", "");
        }

        Dialog dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle(title + " contact");
        dialog.setHeaderText("Use this dialog to " + title.toLowerCase() + " contact " + tempContact.getName() + " " + tempContact.getSurname());
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(c -> new EditContactController(tempContact));
        fxmlLoader.setLocation(getClass().getResource("editContactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        EditContactController editController = fxmlLoader.getController();
        editController.dialogApply.setOnAction(apply -> processApplyDialog(title, editController, window));
        editController.dialogCancel.setOnAction(hide -> window.hide());

        dialog.showAndWait();

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
                showCorrectionInfo(e.getMessage(), true, "red");
                ncApplyButton.setDisable(true);
            }
        }
        return false;
    }

    private boolean isAnyFieldFilled() {
        return (!ncPopName.getText().trim().isEmpty() || !ncPopSurName.getText().trim().isEmpty() ||
                !ncPopPhone.getText().trim().isEmpty() || !ncPopNote.getText().trim().isEmpty());
    }


    private void showMenuAfterError() {
        if (labelka.isVisible()) {
            ncMenuButton.show();
        }
    }

    private void extendColumnWidth() {
        String testingCellValue;
        int longestValue = 0;
        for (int column = 0; column < 4; column++) {
            for (int row = 0; row < lista.size(); row++) {
                testingCellValue = contactTable.getColumns().get(column).getCellObservableValue(row).getValue().toString();
                if(testingCellValue.length() > longestValue) longestValue = testingCellValue.length();
            }
            if(longestValue > 17) contactTable.getColumns().get(column).setPrefWidth(130);
            if(longestValue > 30) contactTable.getColumns().get(column).setPrefWidth(160);
            longestValue = 0;
        }
    }

        private void processApplyDialog(String title, EditContactController controller, Window window) {
            if (title.equals("Edit")) {
                editContact(controller.editContact);
            } else if(title.equals("Create") || title.equals("Copy")) {
                addContact(controller.editContact);
            }
            window.hide();
        }

        private void addContact(Contact newContact) {
            lista.add(newContact);
            contactTable.refresh();
            IOoperations.writeData(newContact);
            infoBanner("New contact " + newContact.getName() + " " + newContact.getSurname() + " created", "green");

        }

        private void editContact(Contact editContact) {
            lista.get(contactTable.getSelectionModel().getFocusedIndex()).setName(editContact.getName());
            lista.get(contactTable.getSelectionModel().getFocusedIndex()).setSurname(editContact.getSurname());
            lista.get(contactTable.getSelectionModel().getFocusedIndex()).setPhone(editContact.getPhone());
            lista.get(contactTable.getSelectionModel().getFocusedIndex()).setNote(editContact.getNote());
            contactTable.refresh();
            infoBanner("Contact " + editContact.getName() + " " + editContact.getSurname() + " edited", "orange");
        }


        private void infoBanner(String text, String color){
            showCorrectionInfo(text, true, color);
            PauseTransition showInfo = new PauseTransition(Duration.seconds(4));
            showInfo.setOnFinished((e) -> showCorrectionInfo(text, false, color));
            showInfo.play();
        }

}
