package tr.edu.mu.ceng.mad.reminderapp;

import java.io.Serializable;

public class RemindersEducation implements Serializable {

    private String reminder_id;
    private String select_category;
    private String reminder_name;
    private String date;
    private String clock;
    private String repeat;
    private String reminder_note;

    public RemindersEducation() {

    }

    public RemindersEducation(String reminder_id, String select_category, String reminder_name, String date, String clock, String repeat, String reminder_note) {
        this.reminder_id = reminder_id;
        this.select_category = select_category;
        this.reminder_name = reminder_name;
        this.date = date;
        this.clock = clock;
        this.repeat = repeat;
        this.reminder_note = reminder_note;
    }

    public String getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(String reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getSelect_category() {
        return select_category;
    }

    public void setSelect_category(String select_category) {
        this.select_category = select_category;
    }

    public String getReminder_name() {
        return reminder_name;
    }

    public void setReminder_name(String reminder_name) {
        this.reminder_name = reminder_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getReminder_note() {
        return reminder_note;
    }

    public void setReminder_note(String reminder_note) {
        this.reminder_note = reminder_note;
    }
}
