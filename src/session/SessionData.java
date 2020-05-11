package session;

import com.corundumstudio.socketio.SocketIOClient;

public class SessionData {
    private String firstname, lastname;
    private String email;
    private int age;
    private int career ,income;
    private String bank_id ;
    private int bank_name;
    private SocketIOClient client;

    public SocketIOClient getClient() {
        return client;
    }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public int getCareer() { return career; }

    public void setCareer(int career) { this.career = career; }

    public int getIncome() { return income; }

    public void setIncome(int income) { this.income = income; }

    public String getBank_id() { return bank_id; }

    public void setBank_id(String bank_id) { this.bank_id = bank_id; }

    public int getBank_name() { return bank_name; }

    public void setBank_name(int bank_name) { this.bank_name = bank_name; }

    public SessionData(String firstname, String lastname, String email, String password, int age, int career, int income, String bank_id, int bank_name, SocketIOClient client) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.career = career;
        this.income = income;
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.client = client;
    }

    public SessionData(SocketIOClient client) {
        this.client = client;
    }
}
