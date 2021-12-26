package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

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
    }
}