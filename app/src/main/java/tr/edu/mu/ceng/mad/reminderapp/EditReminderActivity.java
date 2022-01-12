package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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

public class EditReminderActivity extends AppCompatActivity {

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

    ImageView imgBackward;

    String rawid, rawnamei, rawdate, rawcate,rawtime,rawnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);


        Intent intent = getIntent();
        Log.d("yazdirsana",intent.getStringExtra("remiid"));
        rawid = intent.getStringExtra("remiid");
        rawnamei = intent.getStringExtra("reminame");
        rawcate = intent.getStringExtra("remicate");
        rawdate = intent.getStringExtra("remidate");
        rawtime = intent.getStringExtra("remitime");
        rawnote = intent.getStringExtra("reminote");



        imgBackward = findViewById(R.id.imgbackward);

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextReminderName = findViewById(R.id.editTextReminderName);
        editTextReminderNote = findViewById(R.id.editTextReminderNote);

        editTextReminderNote.setText(rawnote);

        editTextReminderName.setText(rawnamei);

        database = FirebaseDatabase.getInstance();

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

        datePickerdate.setText(rawdate);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        txtdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditReminderActivity.this,
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
                        EditReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        txtTime.setText(rawtime);

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditReminderActivity.this,
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

                DatabaseReference myRefCategory = database.getReference("reminder/"+ rawid + "/category");
                DatabaseReference myRefName = database.getReference("reminder/"+ rawid + "/reminder_name");
                DatabaseReference myRefDate = database.getReference("reminder/"+ rawid + "/date");
                DatabaseReference myRefClock = database.getReference("reminder/"+ rawid + "/clock");
                DatabaseReference myRefNote = database.getReference("reminder/"+ rawid + "/reminder_note");

                myRefCategory.setValue(select_category);
                myRefName.setValue(reminder_name);
                myRefDate.setValue(date);
                myRefClock.setValue(time);
                myRefNote.setValue(reminder_note);

                Toast.makeText(EditReminderActivity.this, "Reminder updated", Toast.LENGTH_SHORT).show();

                if (select_category.equals("Health")){
                    startActivity(new Intent(EditReminderActivity.this,HealthCategory.class));
                    finish();
                } else if (select_category.equals("Social Activity")){
                    startActivity(new Intent(EditReminderActivity.this,SocialActCategory.class));
                    finish();
                } else if (select_category.equals("Other Activity")){
                    startActivity(new Intent(EditReminderActivity.this,OtherRemindCategory.class));
                    finish();
                }



            }
        });

        if (rawcate.equals("Health")){
            spinner.setSelection(0);
        } else if (rawcate.equals("Education")){
            spinner.setSelection(1);
        }  else if (rawcate.equals("Social Activity")){
            spinner.setSelection(2);
        } else if (rawcate.equals("Other Activity")){
            spinner.setSelection(3);
        }



    }


}