package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{ //implements NavigationView.OnNavigationItemSelectedListener{

    public NavigationView navigationView;
    public DrawerLayout drawer;
    public Toolbar toolbar;
    public Fragment fragment;
    Context context=this;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String uuid;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Current User uuid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uuid = currentFirebaseUser.getUid();


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminder");
        dbHelper=new DatabaseHelper(getApplicationContext());



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ReminderEducation reminderEducation = d.getValue(ReminderEducation.class);
                    reminderEducation.setReminder_id(d.getKey());

                    if (reminderEducation.getWhouuid().equals(uuid)){

                        Plan plan=new Plan(reminderEducation.getDate(),reminderEducation.getClock()
                                ,reminderEducation.getReminder_name(),reminderEducation.getReminder_note());

                        dbHelper.addPlan(plan);


                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent serviceIntent = new Intent(HomePage.this, ReminderService.class);
        serviceIntent.putExtra("bisiler", String.valueOf("10000"));
        stopService(serviceIntent);
        startService(serviceIntent);


        navigationView = findViewById(R.id.navigationView);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        fragment = new FragmentHomePage();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_keep,fragment).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar
                ,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this); ////Triggers the NavigationView.OnNavigationItemSelectedListener interface.
    }

  /*  public void openEditProfile(){
        Intent intent = new Intent(this, EditProfileActivity.class );
        startActivity(intent);
    }
    public void openAddReminder(){
        Intent intent = new Intent (this,AddReminderActivity.class) ;
        startActivity(intent);
    }
    public void allReminders(){
        Intent intent = new Intent (this,HomePage.class) ;
        startActivity(intent);
    }
    public void LogOut(){
        Intent intent = new Intent (this,MainActivity.class) ;
        startActivity(intent);
    } */




    //This method tells which item was clicked.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_profile:
                showToast("Profile");
                openActivity (EditProfileActivity.class);
                break;
            case R.id.nav_reminders:
                showToast("All Reminders");
                openActivity(HomePage.class);
                break;
            case R.id.nav_add_new_reminder:
                showToast("Add New Reminders");
                openActivity(AddReminderActivity.class);
                break;
            case R.id.nav_log_out:
                showToast("log out");
                openActivity(MainActivity.class);
            default:
                return true;
        }
        return false;
    }

    private void openActivity(Class c) {

        startActivity(new Intent(context,c));
    }

    private void showToast(String msg) {

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed(){

        if(drawer.isDrawerOpen(GravityCompat.START)){ //If the Drawer is open, pressing the back button will first close the navigation drawer.
            drawer.closeDrawer(GravityCompat.START);
        }

        else{ //If the drawer is closed, the application exits.
            Intent myIntent = new Intent(Intent.ACTION_MAIN);
            myIntent.addCategory(Intent.CATEGORY_HOME);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(HomePage.this,AddReminderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

