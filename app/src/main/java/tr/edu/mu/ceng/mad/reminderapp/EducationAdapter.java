package tr.edu.mu.ceng.mad.reminderapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.CardDesign> {
    private Context context;
    private List<RemindersEducation> educationreminderslist;

    public EducationAdapter(Context context, List<RemindersEducation> educationreminderslist) {
        this.context = context;
        this.educationreminderslist = educationreminderslist;
    }

    @NonNull
    @Override
    public CardDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesigneducation,parent,false);
        return new CardDesign(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDesign holder, int position) {
        final RemindersEducation remindersEducation = educationreminderslist.get(position);
        holder.remindName.setText(remindersEducation.getReminder_name());
        holder.remindNote.setText(remindersEducation.getReminder_note());

        holder.reminderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("nesne", remindersEducation);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return educationreminderslist.size();
    }

    public class CardDesign extends RecyclerView.ViewHolder{
        private TextView remindName;
        private TextView remindNote;
        private CardView reminderCard;
        private ImageView alert;

        public CardDesign(@NonNull View itemView) {
            super(itemView);
            remindName = itemView.findViewById(R.id.remindName);
            remindNote = itemView.findViewById(R.id.remindNote);
            reminderCard = itemView.findViewById(R.id.reminderCard);
            alert = itemView.findViewById(R.id.alert);

        }
    }
}
