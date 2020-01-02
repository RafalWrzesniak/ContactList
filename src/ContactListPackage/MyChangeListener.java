package ContactListPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MyChangeListener implements ChangeListener<String> {

    private TextArea textArea = null;
    private TextField textField = null;
    private int length;

    MyChangeListener(TextField textField, int length) {
        this.length = length;
        this.textField = textField;
    }

    MyChangeListener(TextArea textArea, int length) {
        this.length = length;
        this.textArea = textArea;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (textField.getId().equals("ncPopPhone") || textField.getId().equals("dialogPhone"))
            digitListener(newValue, textField);
        if (textArea != null) lengthListener(oldValue, newValue, textArea, length);
        if (textField != null) lengthListener(oldValue, newValue, textField, length);

    }

    void lengthListener(String oldValue, String newValue, TextField textField, int length) {
        if (newValue.length() > length) {
            textField.setText(oldValue);
        }
    }

    void lengthListener(String oldValue, String newValue, TextArea textArea, int length) {
        if (newValue.length() > length) {
            textArea.setText(oldValue);
        }
    }

    void digitListener(String newValue, TextField textField) {
        if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
}


