package tr.edu.mu.ceng.mad.reminderapp;

public class TimeRemaining {
    String diff, satirid;

    public TimeRemaining(){

    }

    public TimeRemaining(String diff, String satirid){

        this.diff = diff;
        this.satirid = satirid;

    }

    public String getDiff() {
        return diff;
    }

    public String getSatirid() {
        return satirid;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public void setSatirid(String satirid) {
        this.satirid = satirid;
    }


}