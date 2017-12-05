package cs240.fammapclient.JsonHandling;


import cs240.fammapclient.JsonHandling.Request;

public class RegisterRequest extends Request {

    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;
    public RegisterRequest(String username, String password, String email, String firstname, String lastname, String gender) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
    }
}
