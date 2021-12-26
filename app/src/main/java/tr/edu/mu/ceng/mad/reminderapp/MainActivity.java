package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button goToLogin;
    public Button goToSignUp;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create buttons to transfer login and register page.
        Button goToLogin = (Button) findViewById(R.id.goToLogin);
        Button goToSignUp = (Button) findViewById(R.id.goToSignUp);


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
    }
}