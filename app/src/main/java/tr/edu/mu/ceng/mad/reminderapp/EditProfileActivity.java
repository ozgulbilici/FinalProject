package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    ImageButton btnClick;
    ImageButton btnClick2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnClick2 = (ImageButton) findViewById(R.id.btnClick2);

        btnClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Password is changed.", Toast.LENGTH_SHORT).show();
            }
        });

        btnClick = (ImageButton) findViewById(R.id.btnClick);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Profile is updated.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}