package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    private ImageButton btnClick3;
    private EditText editTextReminderName;
    private EditText editTextReminderNote;

    //add spinner
    private Spinner spinner;

    private ArrayList<String> category = new ArrayList<>();
    private ArrayAdapter<String> categorychoseeadapter;

    //add datepicker
    private TextView txtdate;
    private EditText datePickerdate;

    DatePickerDialog.OnDateSetListener setListener;

    //add timePicker
    private TextView txtTime;
    int Hour;
    int Minute;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    ImageView imgBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        boolean alarm = (PendingIntent.getBroadcast(this, 0, new Intent("ALARM"), PendingIntent.FLAG_NO_CREATE) == null);
        if(alarm){
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,itAlarm,0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000, pendingIntent);
        }



        imgBackward = findViewById(R.id.imgbackward);

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddReminderActivity.this, HomePage.class);
                startActivity(intent);

            }
        });


        editTextReminderName = findViewById(R.id.editTextReminderName);
        editTextReminderNote = findViewById(R.id.editTextReminderNote);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminder");

        //spinner
        spinner = findViewById(R.id.spinnerSelectCategory);

        category.add("Health");
        category.add("Education");
        category.add("Social Activity");
        category.add("Other Activity");

        categorychoseeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,category);

        spinner.setAdapter(categorychoseeadapter);



        //datepicker
        txtdate = findViewById(R.id.txtdate);
        datePickerdate = findViewById(R.id.datePickerdate);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        txtdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReminderActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txtdate.setText(date);

            }
        };

        datePickerdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        datePickerdate.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });




        //timePicker

        txtTime = findViewById(R.id.txtTime);


        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddReminderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                Hour = hour;
                                Minute = minute;

                                Calendar cal = Calendar.getInstance();
                                cal.set(0,0,0,Hour,Minute);
                                txtTime.setText(DateFormat.format("hh:mm aa", cal));


                            }
                        },12,0,true
                );
                timePickerDialog.updateTime(Hour,Minute);
                timePickerDialog.show();
            }
        });

        //click button give a message
        btnClick3 = (ImageButton) findViewById(R.id.btnClick3);

        btnClick3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String select_category = spinner.getSelectedItem().toString();
                String reminder_name = editTextReminderName.getText().toString().trim();
                //String  date = txtdate.getText().toString();
                String  date = datePickerdate.getText().toString();
                String  time = txtTime.getText().toString();
                String  reminder_note = editTextReminderNote.getText().toString().trim();

                // Current User uuid
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String uuid = currentFirebaseUser.getUid();

                ReminderEducation remindersEducation = new ReminderEducation(uuid,"",select_category,reminder_name,date, time, reminder_note);

                myRef.push().setValue(remindersEducation);

                Toast.makeText(AddReminderActivity.this, "Reminder is created.", Toast.LENGTH_SHORT).show();
                category.add("");
                category.add("");
                category.add("");
                category.add("");
                Intent serviceIntent = new Intent(AddReminderActivity.this, ReminderService.class);
                serviceIntent.putExtra("bisiler", String.valueOf("10000"));
                stopService(serviceIntent);
                startService(serviceIntent);

                if (select_category.equals("Health")){
                    startActivity(new Intent(AddReminderActivity.this,HealthCategory.class));
                    finish();
                } else if (select_category.equals("Education")){
                    startActivity(new Intent(AddReminderActivity.this,EducationCategory.class));
                    finish();
                } else if (select_category.equals("Social Activity")){
                    startActivity(new Intent(AddReminderActivity.this,SocialActCategory.class));
                    finish();
                } else if (select_category.equals("Other Activity")){
                    startActivity(new Intent(AddReminderActivity.this,OtherRemindCategory.class));
                    finish();
                }
            }
        });

    }


}

