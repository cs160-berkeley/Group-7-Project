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
    private int SIZE;
    public PackagesCardGridAdapter()
    {
        //TODO: Take in list of packages and draw accordingly
        SIZE = 8;
    }

    //TODO: v v v Delete this v v v
    public PackagesCardGridAdapter(int sz)
    {
        SIZE = sz;
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
        return SIZE;
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

