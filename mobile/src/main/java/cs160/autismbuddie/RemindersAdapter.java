package cs160.autismbuddie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GusSilva on 4/13/16.
 */
public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {

    private ArrayList<Reminder> reminders = new ArrayList<>();
    public RemindersAdapter(ArrayList<Reminder> reminderList)
    {
        reminders = reminderList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.reminderText.setText(reminders.get(position).text);
        holder.checkBox.setChecked(reminders.get(position).checked);
        if(reminders.get(position).checked)
            holder.reminderText.setAlpha((float)0.4);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView reminderText;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            reminderText = (TextView) itemView.findViewById(R.id.reminderText);
            checkBox = (CheckBox) itemView.findViewById(R.id.reminderCheckbox);
        }
    }
}
