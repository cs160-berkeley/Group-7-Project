package cs160.autismbuddie;

/**
 * Created by GusSilva on 4/26/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PackagesCardGridAdapter extends RecyclerView.Adapter<PackagesCardGridAdapter.ViewHolder> {

    private ArrayList<Reminder> reminders = new ArrayList<>();
    public PackagesCardGridAdapter()
    {
        //TODO: Take in list of packages and draw accordingly
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.packages_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.packName.setText("Minecraft " + position);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView packName;

        public ViewHolder(View itemView) {
            super(itemView);
            packName = (TextView) itemView.findViewById(R.id.packName);
        }
    }
}

