package ContactListPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

public class EditContactController {

    @FXML private TextField dialogName = new TextField();;
    @FXML private TextField dialogSurname = new TextField();
    @FXML private TextField dialogPhone = new TextField();
    @FXML private TextArea dialogNote = new TextArea();

    private String name;
    private String surname;
    private String phone;
    private String note;
    private ButtonType OKbutton;

    public EditContactController(ButtonType OKbutton, String name, String surname, String phone, String note){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.note = note;
        this.OKbutton = OKbutton;
    }

    public void initialize(){
        dialogName.setText(name);
        dialogSurname.setText(surname);
        dialogPhone.setText(phone);
        dialogNote.setText(note);

        System.out.println(OKbutton);


        dialogPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    dialogPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });


    }

    public Contact passNewValues(){

        System.out.println(dialogNote.getParent());
//        dialogNote.getScene().getWindow();
        return new Contact(dialogName.getText(), dialogSurname.getText(),
                                             dialogPhone.getText(), dialogNote.getText());

    }



}



