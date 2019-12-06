package ContactListPackage;

public class Contact {

    private String name;
    private String surname;
    private String phone;
    private String note;


    public Contact() {

    }

    public Contact(String name, String surname, String phone, String note) {
        setName(name);
        setSurname(surname);
        setPhone(phone);
        setNote(note);
    }

    // setters
    public void setName(String name) throws IllegalArgumentException {
        if(name.trim().isEmpty() || name.length() > 2 && name.length() < 15) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name must have more then 2 and less then 15 characters!");
        }
    }
    public void setSurname(String surname) throws IllegalArgumentException {
        if(surname.trim().isEmpty() || surname.length() > 3 && surname.length() < 25) {
            this.surname = surname;
        } else {
            throw new IllegalArgumentException("Surname must have more then 3 and less then 25 characters!");
        }
    }
    public void setPhone(String phone) {
        if(phone.length() == 9) {
            try {
                Double.parseDouble(phone);
            } catch(NumberFormatException e){
                throw new IllegalArgumentException("Phone must have digits only!");
            }
            this.phone = phone;
        }
        else if(phone.trim().isEmpty()){
            this.phone = phone;
        }
        else {
        throw new IllegalArgumentException("Phone must have 9 digits!");
        }
    }


    public void setNote(String note) {
        if(note.trim().isEmpty() || note.length() < 50) {
            this.note = note;
        } else {
            throw new IllegalArgumentException("Note must have less then 50 characters!");
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
