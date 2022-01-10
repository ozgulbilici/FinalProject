package tr.edu.mu.ceng.mad.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class EditProfileActivity extends AppCompatActivity {

    ImageView imgBackward;
    EditText editTextTextPersonName5, editTextTextPersonName6, editTextTextPersonName7, editTextTextPersonName8,
            editTextTextPassword3, editTextTextPassword4;
    ImageButton btnClick,btnClick2;

    String uuid;
    String rawpassword;
    String rawmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Current User uuid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uuid = currentFirebaseUser.getUid();

        rawmail = currentFirebaseUser.getEmail();

        //Connect Firebase Realtime DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Datas Locations
        DatabaseReference myRefAge = database.getReference("Users/"+uuid+"/age");
        DatabaseReference myRefBirth = database.getReference("Users/"+uuid+"/birthday");
        DatabaseReference myRefMail = database.getReference("Users/"+uuid+"/email");
        DatabaseReference myRefUser = database.getReference("Users/"+uuid+"/username");
        DatabaseReference myRefPassword = database.getReference("Users/"+uuid+"/password");

        imgBackward = findViewById(R.id.imgbackward);

        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName7 = findViewById(R.id.editTextTextPersonName7);
        editTextTextPersonName8 = findViewById(R.id.editTextTextPersonName8);

        editTextTextPassword3 = findViewById(R.id.editTextTextPassword3);
        editTextTextPassword4 = findViewById(R.id.editTextTextPassword4);

        btnClick = findViewById(R.id.btnClick);
        btnClick2 = findViewById(R.id.btnClick2);


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextTextPersonName5.getText().toString();
                String email = editTextTextPersonName6.getText().toString();
                String birthday = editTextTextPersonName7.getText().toString();
                String age = editTextTextPersonName8.getText().toString();

                if(!TextUtils.isEmpty(username)){

                    myRefUser.setValue(username);
                }
                if(!TextUtils.isEmpty(email)){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    // Get auth credentials from the user for re-authentication
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(rawmail, rawpassword); // Current Login Credentials \\
                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                    //Now change your email address \\
                                    //----------------Code for Changing Email Address----------\\
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.updateEmail(email)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User email address updated.");
                                                    }
                                                }
                                            });
                                    //----------------------------------------------------------\\

                                }
                            });
                    myRefMail.setValue(email);

                }
                if(!TextUtils.isEmpty(birthday)){
                    myRefBirth.setValue(birthday);
                }
                if(!TextUtils.isEmpty(age)){
                    myRefAge.setValue(age);
                }


                // Set Datas to new values





                Toast.makeText(getApplicationContext(),"Update is succesfull",Toast.LENGTH_SHORT).show();

            }
        });

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditProfileActivity.this, HomePage.class);
                startActivity(intent);

            }
        });

        btnClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentpass = editTextTextPassword3.getText().toString();
                String newpass = editTextTextPassword4.getText().toString();


                if(TextUtils.isEmpty(currentpass)){
                    Toast.makeText(getApplicationContext(),"Password is empty!",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(newpass)){
                    Toast.makeText(getApplicationContext(),"Password is empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!rawpassword.equals(currentpass)){
                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // Get auth credentials from the user for re-authentication
                AuthCredential credential = EmailAuthProvider
                        .getCredential(rawmail, rawpassword); // Current Login Credentials \\
                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User re-authenticated.");
                                //Now change your email address \\
                                //----------------Code for Changing Email Address----------\\
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(newpass)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User email address updated.");
                                                }
                                            }
                                        });
                                //----------------------------------------------------------\\

                            }
                        });


                // Set Datas to new values
                myRefPassword.setValue(newpass);
                Toast.makeText(getApplicationContext(),"Update is succesfull",Toast.LENGTH_SHORT).show();


            }
        });


        // Read from the database
        myRefUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                editTextTextPersonName5.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Read from the database
        myRefMail.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                editTextTextPersonName6.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Read from the database
        myRefBirth.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                editTextTextPersonName7.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Read from the database
        myRefAge.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                editTextTextPersonName8.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Read from the database
        myRefPassword.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                rawpassword = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

}