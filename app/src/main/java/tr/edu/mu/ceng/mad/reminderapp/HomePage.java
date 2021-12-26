package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity{ //implements NavigationView.OnNavigationItemSelectedListener{

    public NavigationView navigationView;
    public DrawerLayout drawer;
    public Toolbar toolbar;
    public Fragment fragment;

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

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawer,toolbar
                ,0,0);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

       // navigationView.setNavigationItemSelectedListener(this); ////Triggers the NavigationView.OnNavigationItemSelectedListener interface.
    }

    /*
    //This method tells which item was clicked.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_profile){
            Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.nav_reminders){
            Toast.makeText(getApplicationContext(),"Reminders",Toast.LENGTH_SHORT).show();

        }
        if(item.getItemId() == R.id.nav_add_new_reminder){
            Toast.makeText(getApplicationContext(),"Add New Reminder",Toast.LENGTH_SHORT).show();

        }
        if(item.getItemId() == R.id.nav_log_out){
            Toast.makeText(getApplicationContext(),"Log out",Toast.LENGTH_SHORT).show();
            fragment = new LogOutFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_keep,fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;

    } */

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


}