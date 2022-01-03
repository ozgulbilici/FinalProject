package tr.edu.mu.ceng.mad.reminderapp;

public class User {
    String username;
    String email;
    String password;
    String reminder_id;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.reminder_id = reminder_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(String reminder_id) {
        this.reminder_id = reminder_id;
    }
}
