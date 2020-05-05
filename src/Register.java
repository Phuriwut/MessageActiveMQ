import javax.jms.Connection;

public class Register {
    private String firstname , lastname;
//    private int age , career ,income;

    //getter setter
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //constructor
    public Register(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Register() {
    }

    @Override
    public String toString() {
        return "Register{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
