package tr.edu.mu.ceng.mad.reminderapp;

public class Plan {
    int id;
    String date,time,name,note;

    public Plan(int id, String date, String time, String name, String note) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.note = note;
    }

    public Plan(String date, String time, String name, String note) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setName(String name) {
        this.name = name;
    }

}