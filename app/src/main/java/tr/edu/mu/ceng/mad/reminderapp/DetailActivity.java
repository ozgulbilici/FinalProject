package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinner1;

    private ArrayList<String> category = new ArrayList<>();
    private ArrayAdapter<String> categorychoseeadapter;

    private ArrayList<String> repeat = new ArrayList<>();
    private ArrayAdapter<String> repeatchoseeadapter;
    //add datepicker
    private TextView txtdate;
    private EditText datePickerdate;

    //add timePicker
    private TextView txtTime;
    int Hour;
    int Minute;

    private Button DeleteButton;
    private Button EditButton;

    private RemindersEducation remindersEducation;


    private FirebaseDatabase database;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminders");


        remindersEducation = (RemindersEducation) getIntent().getSerializableExtra("nesne");

        spinner = findViewById(R.id.spinnerSelectCategory);
        spinner1 = findViewById(R.id.spinnerRepeat);
        //datepicker
        txtdate = findViewById(R.id.txtdate);
        datePickerdate = findViewById(R.id.datePickerdate);
        txtTime = findViewById(R.id.txtTime);
        DeleteButton = findViewById(R.id.Delete);
        EditButton = findViewById(R.id.editReminder);


    }
}