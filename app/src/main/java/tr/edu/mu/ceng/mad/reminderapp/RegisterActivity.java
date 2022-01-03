package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button goToSignUp2;
    EditText userName,userPassword,userEmail;
    TextView logInHere;
    FirebaseAuth fAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        fAuth = FirebaseAuth.getInstance(); //We used the getInstance method to use the objects referenced by the FirebaseAuth class.
        database = FirebaseDatabase.getInstance(); //The object was created to access the database.

        goToSignUp2 = (Button) findViewById(R.id.goToSignUp2);

        userName = findViewById(R.id.edtTxtUserName);
        userPassword = findViewById(R.id.edtTxtPass2);
        userEmail = findViewById(R.id.edtTxtEmail2);
        logInHere = findViewById(R.id.logInHere);


        logInHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        goToSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
            }
        });
    }

            private void createUser() {
                String email = userEmail.getText().toString();
                String uName = userName.getText().toString();
                String password1 = userPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this,"Email is empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(uName)){
                    Toast.makeText(this,"Username is empty!",Toast.LENGTH_SHORT).show();
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

                //created user
                fAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user1 = new User(uName,email,password1);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user1); //The created users are written under the users name in the firebase console.
                            Toast.makeText(RegisterActivity.this,"Registration Succesful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }

                        else{
                            Toast.makeText(RegisterActivity.this,"The e-mail address belongs to another account.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }
