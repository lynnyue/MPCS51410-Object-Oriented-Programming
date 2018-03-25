// User class represent the actors involved with the system
// super class of Student and Instructor class
public class User {

    public int ID;
    public String firstName;
    public String lastName;
    public String email;
    public String passWord;

    public User(int ID, String firstName, String lastName, String email, String passWord) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
    }

    // change password
    public void changePassword(String newPassWord) {
        System.out.println("Old password:" + this.passWord);
        this.passWord = newPassWord;
        System.out.println("New password:" + this.passWord);
    }
}

