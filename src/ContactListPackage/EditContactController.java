package ContactListPackage;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EditContactController {

    @FXML public Button dialogCancel;
    @FXML public Button dialogApply = new Button();
    @FXML private TextField dialogName = new TextField();
    @FXML private TextField dialogSurname = new TextField();
    @FXML private TextField dialogPhone = new TextField();
    @FXML private TextArea dialogNote = new TextArea();

    Contact editContact;


    EditContactController(Contact editContact) {
        this.editContact = editContact;
    }

    public void initialize(){
        dialogApply.setDisable(true);
        dialogName.setText(editContact.getName());
        dialogSurname.setText(editContact.getSurname());
        dialogPhone.setText(editContact.getPhone());
        dialogNote.setText(editContact.getNote());
        dialogPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                dialogPhone.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 9) {
                dialogPhone.setText(oldValue);
            }
        });

        dialogName.textProperty().addListener(new MyChangeListener(dialogName, 15));
        dialogSurname.textProperty().addListener(new MyChangeListener(dialogSurname, 25));
        dialogNote.textProperty().addListener(new MyChangeListener(dialogNote, 50));


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
                if(dialogName.getText().trim().isEmpty() && dialogSurname.getText().trim().isEmpty()
                        && dialogPhone.getText().trim().isEmpty() && dialogNote.getText().trim().isEmpty()) {
                    dialogApply.setDisable(true);
                } else dialogApply.setDisable(false);
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

