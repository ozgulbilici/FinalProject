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

public class OtherRemindCategory extends AppCompatActivity {
    private TextView textViewOtherReminders;
    private RecyclerView rv;
    private Button AddOtherReminders;
    private ArrayList<Reminders> remindersArrayList;
    private OtherReminderAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_remind_category);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("OtherReminder");

        textViewOtherReminders = findViewById(R.id.textViewOtherReminders);
        rv = findViewById(R.id.rv);
        AddOtherReminders = findViewById(R.id.AddOtherReminders);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        remindersArrayList = new ArrayList<>();

        adapter = new OtherReminderAdapter(this, remindersArrayList);

        rv.setAdapter(adapter);

        everyOtherReminders();


        AddOtherReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtherRemindCategory.this,AddReminderActivity.class));
            }
        });



    }

    private void everyOtherReminders() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                remindersArrayList.clear();

                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Reminders reminder = d.getValue(Reminders.class);
                    reminder.setReminder_id(d.getKey());

                    remindersArrayList.add(reminder);


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