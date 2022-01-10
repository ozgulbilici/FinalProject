package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView txtname, txtcate, txtdate, txttime, txtnote;
    String reminderid,  rawnamei, rawdate, rawcate,rawtime,rawnote;
    Button editReminder, Delete;
    ImageView imgBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBackward = findViewById(R.id.imgbackward);

        imgBackward.setColorFilter(R.color.black);

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailActivity.this, HomePage.class);
                startActivity(intent);

            }
        });

        //Connect Firebase Realtime DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();

        txtname = findViewById(R.id.txtname);
        txtcate = findViewById(R.id.txtcate);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        txtnote = findViewById(R.id.txtnote);

        editReminder = findViewById(R.id.editReminder);
        Delete = findViewById(R.id.Delete);

        reminderid = intent.getStringExtra("remiid");
        rawnamei = intent.getStringExtra("reminame");
        rawcate = intent.getStringExtra("remicate");
        rawdate = intent.getStringExtra("remidate");
        rawtime = intent.getStringExtra("remitime");
        rawnote = intent.getStringExtra("reminote");

        txtname.setText(rawnamei);
        txtcate.setText(rawcate);
        txtdate.setText(rawdate);
        txttime.setText(rawtime);
        txtnote.setText(rawnote);

        editReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailActivity.this,EditReminderActivity.class);
                intent.putExtra("remiid",reminderid);
                intent.putExtra("reminame", rawnamei);
                intent.putExtra("remicate", rawcate);
                intent.putExtra("remidate", rawdate);
                intent.putExtra("remitime", rawtime);
                intent.putExtra("reminote",rawnote);
                startActivity(intent);

            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRefReminder = database.getReference("reminder/"+reminderid);
                myRefReminder.removeValue();
                Toast.makeText(getApplicationContext(),"Reminder Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}