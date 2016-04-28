package cs160.autismbuddie;

/**
 * Created by GusSilva on 4/26/16.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class PackagesCardGridAdapter extends RecyclerView.Adapter<PackagesCardGridAdapter.ViewHolder> {

    public List<ParseObject> packages = new ArrayList<>();
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private View.OnClickListener listener;
    private Context context;

    private static final int ANIMATED_ITEMS_COUNT = 8;
    private int lastAnimatedPosition = -1;

    public PackagesCardGridAdapter(List<ParseObject> packs, View.OnClickListener lstnr, Context ctxt)
    {
        packages = packs;
        imageLoader = ImageLoader.getInstance();
        listener = lstnr;
        context = ctxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.packages_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        runEnterAnimation(holder.cardView, position);
        holder.packName.setText(packages.get(position).getString("name"));
        String imgUrl = PhoneToWatchUtil.getImageUrl(packages.get(position), "cardImage");
        imageLoader.displayImage(imgUrl, holder.cardImg);
        holder.cardView.setTag(position);
        if(listener != null)
            holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public ParseObject removeItem(int position)
    {
        return packages.remove(position);
    }

    public void addItem(ParseObject parseObject)
    {
        packages.add(parseObject);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            //view.setTranslationY(getScreenHeight());
            view.setScaleX(0f);
            view.setScaleY(0f);
            view.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setStartDelay(150 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(600)
                    .start();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView packName;
        public ImageView cardImg;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            packName = (TextView) itemView.findViewById(R.id.packName);
            cardImg = (ImageView) itemView.findViewById(R.id.cardImg);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}

