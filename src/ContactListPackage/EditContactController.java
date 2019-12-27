package ContactListPackage;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;

public class EditContactController {

    @FXML public Button dialogCancel;
    @FXML public Button dialogApply = new Button();
    @FXML private TextField dialogName = new TextField();
    @FXML private TextField dialogSurname = new TextField();
    @FXML private TextField dialogPhone = new TextField();
    @FXML private TextArea dialogNote = new TextArea();

    public Contact editContact;


    public EditContactController(Contact editContact) {
        this.editContact = new Contact(editContact.getName(), editContact.getSurname(), editContact.getPhone(), editContact.getNote());
    }

    public void initialize(){
        dialogName.setText(editContact.getName());
        dialogSurname.setText(editContact.getSurname());
        dialogPhone.setText(editContact.getPhone());
        dialogNote.setText(editContact.getNote());

        dialogPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    dialogPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });

        dialogNote.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length() > 50) {
                    dialogNote.setText(oldValue);
                }
            }
        });
        backgroundButtonEnabling();

    }

    private void backgroundButtonEnabling() {
        Tooltip tooltip = new Tooltip();
        tooltip.setFont(Font.font(13));
        dialogApply.setTooltip(tooltip);
        PauseTransition disablingApplyButton = new PauseTransition(Duration.seconds(1));
        disablingApplyButton.setOnFinished((f) -> {
            try {
                editContact.setName(dialogName.getText());
                editContact.setSurname(dialogSurname.getText());
                editContact.setPhone(dialogPhone.getText());
                editContact.setNote(dialogNote.getText());
                dialogApply.setDisable(false);
                dialogApply.getTooltip().hide();
            } catch (IllegalArgumentException e) {
                Stage dial = (Stage) dialogApply.getParent().getScene().getWindow();
                dialogApply.getTooltip().setText(e.getMessage());
                dialogApply.getTooltip().show(dial, dial.getX() + 20, dial.getY() + 350);
                dialogApply.setDisable(true);
            }
            disablingApplyButton.playFromStart();
            dialogApply.setOnMouseReleased(stop -> disablingApplyButton.stop());

        });
        disablingApplyButton.play();
    }
}



