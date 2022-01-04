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

public class SocialActCategory extends AppCompatActivity {
    private TextView textViewSocialAct;
    private RecyclerView rv;
    private Button AddSocialAct;
    private ArrayList<Reminders> remindersArrayList;
    private SocialActAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_act_reminder_category);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SocialActReminder");

        textViewSocialAct = findViewById(R.id.textViewSocialAct);
        rv = findViewById(R.id.rv);
        AddSocialAct = findViewById(R.id.AddSocialAct);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        remindersArrayList = new ArrayList<>();

        adapter = new SocialActAdapter(this, remindersArrayList);

        rv.setAdapter(adapter);

        everySocialActReminders();


        AddSocialAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SocialActCategory.this,AddReminderActivity.class));
            }
        });



    }

    private void everySocialActReminders() {
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