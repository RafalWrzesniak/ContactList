package ContactListPackage;

public class Contact {

    private String name;
    private String surname;
    private String phone;
    private String note;


    public Contact() {

    }

    public Contact(String name, String surname, String phone, String note) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.note = note;
    }

    // setters
    public void setName(String name) {
        if(name.length() > 2 && name.length() < 15) {
            this.name = name;
        } else {
            System.out.println("Name is too short!");
        }
    }
    public void setSurname(String surname) {
        if(surname != null && surname.length() > 3 && surname.length() < 25) {
            this.surname = surname;
        } else {
            this.surname = "";
        }
    }
    public void setPhone(String phone) {
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
//        return phone != 0 ? String.valueOf(phone) : "";
        return phone;
    }

    public String getNote() {
        return note;
    }

}
