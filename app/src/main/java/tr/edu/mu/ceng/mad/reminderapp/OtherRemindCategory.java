package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OtherRemindCategory extends AppCompatActivity {

    private TextView textViewEducation;
    private RecyclerView rv;
    private Button AddEducation;
    private ArrayList<ReminderEducation> remindersEducationArrayList;
    private EducationAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String uuid;
    ImageView imgBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_remind_category);

        imgBackward = findViewById(R.id.imgbackward);

        imgBackward.setColorFilter(R.color.black);

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OtherRemindCategory.this, HomePage.class);
                startActivity(intent);

            }
        });

        // Current User uuid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uuid = currentFirebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminder");

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
                startActivity(new Intent(OtherRemindCategory.this,AddReminderActivity.class));
            }
        });


    }

    private void everyEducationReminders() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                remindersEducationArrayList.clear();

                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ReminderEducation reminderEducation = d.getValue(ReminderEducation.class);
                    reminderEducation.setReminder_id(d.getKey());

                    if (reminderEducation.getWhouuid().equals(uuid)){
                        if (reminderEducation.getSelect_category().equals("Other Activity"))
                            remindersEducationArrayList.add(reminderEducation);
                    }





                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}