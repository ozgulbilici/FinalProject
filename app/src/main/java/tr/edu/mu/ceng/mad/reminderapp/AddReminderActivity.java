package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {
    //add spinner
    private Spinner spinner;
    private Spinner spinner1;

    private ArrayList<String> category = new ArrayList<>();
    private ArrayAdapter<String> categorychoseeadapter;

    private ArrayList<String> repeat = new ArrayList<>();
    private ArrayAdapter<String> repeatchoseeadapter;
    //add datepicker
    TextView txtdate;
    EditText datePickerdate;
    DatePickerDialog.OnDateSetListener setListener;

    //add timePicker
    TextView txtTime;
    int Hour;
    int Minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);


        //spinner
        spinner = findViewById(R.id.spinner);

        category.add("Health");
        category.add("Education");
        category.add("Social Activity");
        category.add("Other Activity");

        categorychoseeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,category);

        spinner.setAdapter(categorychoseeadapter);



        spinner1 = findViewById(R.id.spinner3);

        repeat.add("daily");
        repeat.add("weekly");
        repeat.add("fornightly");
        repeat.add("monthly");
        repeat.add("every 3 mounth");
        repeat.add("every 6 mounth");
        repeat.add("yearly");

        repeatchoseeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,repeat);

        spinner1.setAdapter(repeatchoseeadapter);


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
                        },12,0,false
                );
                timePickerDialog.updateTime(Hour,Minute);
                timePickerDialog.show();
            }
        });

     }



}
