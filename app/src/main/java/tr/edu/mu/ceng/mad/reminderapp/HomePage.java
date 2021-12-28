package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button healthBtn,addBtn,educationBtn,otherRemindBtn,socialActBtn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button healthBtn = (Button) findViewById(R.id.healthBtn);
        Button addBtn = (Button) findViewById(R.id.btnAdd);
        Button educationBtn = (Button) findViewById(R.id.educationBtn);
        Button otherRemindBtn = (Button) findViewById(R.id.otherRemindBtn);
        Button socialActBtn= (Button) findViewById(R.id.socialActBtn);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0 );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomePage.this, AddReminderActivity.class);
                startActivity(myIntent);
            }
        });

        healthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomePage.this, HealthRemindCategory.class);
                startActivity(myIntent);
            }
        });

        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomePage.this,EducationRemindCategory.class);
                startActivity(myIntent);
            }
        });

        socialActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomePage.this, SocialActReminderCategory.class);
                startActivity(myIntent);
            }
        });

        otherRemindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomePage.this, OtherRemindCategory.class);
                startActivity(myIntent);
            }
        });

    }
    public void editProfile(){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);

    }

     @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.nav_profile) {
            Intent intent = new Intent(HomePage.this, EditProfileActivity.class);
            startActivity(intent);
        }
        return onNavigationItemSelected(item);
    }
        /*int id = item.getItemId();
        switch(id){
            case R.id.nav_profile:
                Toast.makeText(HomePage.this,"Profile",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_reminders:
                Toast.makeText(HomePage.this,"Profile",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_add_new_reminder:
                Toast.makeText(HomePage.this,"Profile",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_log_out:
                Toast.makeText(HomePage.this,"Profile",Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }
        return false;
    }*/

}

