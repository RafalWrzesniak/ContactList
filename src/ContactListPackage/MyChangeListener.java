package ContactListPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MyChangeListener implements ChangeListener<String> {

    private TextArea textArea = null;
    private TextField textField = null;
    private int length;
    private ObservableList<Contact> lista;
    private TableView<Contact> contactTable;

    MyChangeListener(TextField textField, int length) {
        this.length = length;
        this.textField = textField;
    }

    MyChangeListener(TextArea textArea, int length) {
        this.length = length;
        this.textArea = textArea;
    }

    MyChangeListener(TextField textField, ObservableList<Contact> lista, TableView<Contact> contactTable) {
        this.textField = textField;
        this.lista = lista;
        this.contactTable = contactTable;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (textField != null) {
            if (textField.getId().equals("ncPopPhone") || textField.getId().equals("dialogPhone")){
                digitListener(newValue, textField);
            } else if(textField.getId().equals("searchContact")) {
                if (!newValue.isEmpty()){
                    System.out.println("Searching for: " + newValue);
                    SortedList<Contact> sortedData = new SortedList<>(lista.filtered(contact -> contact.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            contact.getSurname().toLowerCase().contains(newValue.toLowerCase()) ||
                            contact.getPhone().toLowerCase().contains(newValue.toLowerCase()) ||
                            contact.getNote().toLowerCase().contains(newValue.toLowerCase())));
                    contactTable.setItems(sortedData);
                    contactTable.refresh();
                } else {
                    contactTable.setItems(lista);
                    contactTable.refresh();
                }
            } else {
                lengthListener(oldValue, newValue, textField, length);
            }
        }
        if (textArea != null) lengthListener(oldValue, newValue, textArea, length);

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


