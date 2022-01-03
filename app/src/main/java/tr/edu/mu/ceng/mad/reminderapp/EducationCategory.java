package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EducationCategory extends AppCompatActivity {
    private TextView textViewEducation;
    private RecyclerView rv;
    private Button AddEducation;
    private ArrayList<RemindersEducation> remindersEducationArrayList;
    private EducationAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_remind_category);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminderEducation");

        textViewEducation = findViewById(R.id.textViewEducation);
        rv = findViewById(R.id.rv);
        AddEducation = findViewById(R.id.AddEducation);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        remindersEducationArrayList = new ArrayList<>();

        adapter = new EducationAdapter(this, remindersEducationArrayList);

        rv.setAdapter(adapter);

        everyEducationReminders();


        AddEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EducationCategory.this,AddReminderActivity.class));
            }
        });



    }

    private void everyEducationReminders() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                remindersEducationArrayList.clear();

                for(DataSnapshot d:dataSnapshot.getChildren()){
                    RemindersEducation reminderEducation = d.getValue(RemindersEducation.class);
                    reminderEducation.setReminder_id(d.getKey());

                    remindersEducationArrayList.add(reminderEducation);


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }*/

}