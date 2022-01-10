package tr.edu.mu.ceng.mad.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHomePage extends Fragment {
    public FragmentHomePage(){}
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page,container,false);

        Button goToHealth = view.findViewById(R.id.goToHealth);
        Button goToEducation = view.findViewById(R.id.goToEducation);
        Button goToSocialAct = view.findViewById(R.id.goToSocialAct);
        Button goToOther = view.findViewById(R.id.goToOther);

        goToHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HealthCategory.class);
                startActivity(intent);
            }
        });

        goToEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EducationCategory.class);
                startActivity(intent);
            }
        });

        goToSocialAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SocialActCategory.class);
                startActivity(intent);
            }
        });

        goToOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OtherRemindCategory.class);
                startActivity(intent);
            }
        });


        ////// NOTIFY SERVICE

        Intent serviceIntent = new Intent(getContext(), ReminderService.class);
        serviceIntent.putExtra("bisiler", String.valueOf("10000"));
        getActivity().stopService(serviceIntent);
        getActivity().startService(serviceIntent);



        return view;
    }

}
