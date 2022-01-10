package tr.edu.mu.ceng.mad.reminderapp;

public class User {
    String username;
    String email;
    String password;
    String reminder_id;
    String birthday;
    String age;

    public User(String username, String email, String password, String birthday, String age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.reminder_id = reminder_id;
        this.birthday = birthday;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
