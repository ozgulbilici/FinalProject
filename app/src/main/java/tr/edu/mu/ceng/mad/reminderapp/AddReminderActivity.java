package tr.edu.mu.ceng.mad.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddReminderActivity extends AppCompatActivity {
    /*add spinner */
    private Spinner spinner;
    private Spinner spinner1;

    private ArrayList<String> category = new ArrayList<>();
    private ArrayAdapter<String> categorychoseeadapter;

    private ArrayList<String> repeat = new ArrayList<>();
    private ArrayAdapter<String> repeatchoseeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        spinner = findViewById(R.id.spinner);

        category.add("Health");
        category.add("Education");
        category.add("Social Activity");
        category.add("Other Activity");

        categorychoseeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,category);

        spinner.setAdapter(categorychoseeadapter);



        spinner1 = findViewById(R.id.spinner3);

        repeat.add("daily");
        repeat.add("weekly");
        repeat.add("fornightly");
        repeat.add("monthly");
        repeat.add("every 3 mounth");
        repeat.add("every 6 mounth");
        repeat.add("yearly");

        repeatchoseeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,repeat);

        spinner1.setAdapter(repeatchoseeadapter);
    }
}