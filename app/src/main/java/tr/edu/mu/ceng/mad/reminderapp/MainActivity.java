package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button goToLogin,goToSignUp;
    TextView txtViewSıgnUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //create buttons to transfer login and register page.
        goToLogin = (Button) findViewById(R.id.goToLogin);
        goToSignUp = (Button) findViewById(R.id.goToSignUp);
        txtViewSıgnUp = findViewById(R.id.txtViewSıgnUp);



        //If there is no user account, it goes to the registration activity.
        txtViewSıgnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(myIntent);
            }
        });

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        /*goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this,AddReminderActivity.class);
                startActivity(myIntent);
            }
        });*/

        /*goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this,EditProfileActivity.class);
                startActivity(myIntent);
            }
        });*/
    }
}