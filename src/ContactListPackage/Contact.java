package ContactListPackage;

public class Contact {

    private String name;
    private String surname;
    private int phone;
    private String note;

//
    public Contact(String name) {
        setName(name);
    }

    public Contact(String name, String surname, int phone, String note) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.note = note;
    }

    // setters
    public void setName(String name) {
        if(name.length() > 3 && name.length() < 15) {
            this.name = name;
        }
    }
    public void setSurname(String surname) {
        if(surname != null && surname.length() > 3 && surname.length() < 25) {
            this.surname = surname;
        } else {
            this.surname = "";
        }
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public void setNote(String note) {
        if(note != null && note.length() > 3 && note.length() < 50) {
        this.note = note;
    } else {
        this.note = "";
    }
    }


    // getters
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPhone() {
        return phone != 0 ? String.valueOf(phone) : "";
    }
    public String getNote() {
        return note;
    }

}
