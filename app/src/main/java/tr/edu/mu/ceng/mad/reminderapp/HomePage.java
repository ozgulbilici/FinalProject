package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{ //implements NavigationView.OnNavigationItemSelectedListener{

    public NavigationView navigationView;
    public DrawerLayout drawer;
    public Toolbar toolbar;
    public Fragment fragment;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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
    }}

