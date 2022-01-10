package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    Button goToHomePage;
    EditText userEmail, userPassword;
    TextView txtViewSignUp;
    FirebaseAuth fAuth;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.edtTxtEmail);
        userPassword = findViewById(R.id.edtTxtPass);
        goToHomePage = (Button) findViewById(R.id.goToHomePage);


        txtViewSignUp = findViewById(R.id.txtViewSignUp);

        dbHelper=new DatabaseHelper(getApplicationContext());
        Plan plan=new Plan("7.01.2022","00:00","testgiris","testgirisnote");

        dbHelper.addPlanEarly(plan);

        //If there is no user account, it goes to the registration activity.
        txtViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });



        goToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = userEmail.getText().toString();
        String password1 = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email is empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"Password is empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(this,"Password length must be greater than 6 letter",Toast.LENGTH_SHORT).show();
            return;
        }


        //login user
        fAuth.signInWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){  //If the email and password are entered correctly, it will be directed to the main page of the application.
                    Toast.makeText(LoginActivity.this,"Login Succesfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Email Address or Password is Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}