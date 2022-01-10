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
    private List<ReminderEducation> educationreminderslist;

    public EducationAdapter(Context context, List<ReminderEducation> educationreminderslist) {
        this.context = context;
        this.educationreminderslist = educationreminderslist;
    }

    @NonNull
    @Override
    public CardDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign,parent,false);
        return new CardDesign(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDesign holder, int position) {
        final ReminderEducation remindersEducation = educationreminderslist.get(position);
        holder.remindName.setText(remindersEducation.getReminder_name());
        holder.remindNote.setText(remindersEducation.getReminder_note());

        holder.reminderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("remiid", remindersEducation.getReminder_id());
                intent.putExtra("reminame", remindersEducation.getReminder_name());
                intent.putExtra("remicate", remindersEducation.getSelect_category());
                intent.putExtra("remidate", remindersEducation.getDate());
                intent.putExtra("remitime", remindersEducation.getClock());
                intent.putExtra("reminote", remindersEducation.getReminder_note());
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
