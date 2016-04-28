package cs160.autismbuddie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.ArrayList;

public class GetPackagesActivity extends AppCompatActivity {

    private ArrayList<ParseObject> availablePackages,installedPackages;
    private PackagesCardGridAdapter installedAdapter, availableAdapter;
    private RecyclerView availableRecycler, installedRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_packages);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Animator
        RecyclerView.ItemAnimator itemAnimator = new RecyclerView.ItemAnimator() {
            @Override
            public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
                View view = viewHolder.itemView;
                view.setScaleX(1f);
                view.setScaleY(1f);
                view.animate()
                        .scaleX(0)
                        .scaleY(0)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(1200)
                        .start();
                return true;            }

            @Override
            public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                View view = viewHolder.itemView;
                view.setScaleX(0f);
                view.setScaleY(0f);
                view.animate()
                        .scaleX(1)
                        .scaleY(1)
                        .setStartDelay(500)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(1200)
                        .start();
                return true;
            }

            @Override
            public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                return false;
            }

            @Override
            public void runPendingAnimations() {

            }

            @Override
            public void endAnimation(RecyclerView.ViewHolder item) {

            }

            @Override
            public void endAnimations() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
        // Set up installed grid
        availablePackages = new ArrayList<>(MainActivity.ptwUtil.getPackages());
        installedPackages = new ArrayList<>();
        installedPackages.add(availablePackages.remove(0));
        installedPackages.add(availablePackages.remove(0));

        if(availablePackages == null)
            Log.d(Utils.TAG, "Packages is null!");

        installedRecycler = (RecyclerView)findViewById(R.id.getPacksInstalledRecycler);
        installedRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        installedRecycler.setItemAnimator(itemAnimator);
        installedAdapter = new PackagesCardGridAdapter(installedPackages
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplicationContext(), " Already installed!", Toast.LENGTH_SHORT).show();
                    }
                }
                , getApplicationContext());
        installedRecycler.setAdapter(installedAdapter);
        installedRecycler.setNestedScrollingEnabled(false);

        availableRecycler = (RecyclerView)findViewById(R.id.getPacksAvailableRecycler);
        availableRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        availableRecycler.setItemAnimator(itemAnimator);
        availableAdapter = new PackagesCardGridAdapter(availablePackages
                , new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Integer pos = (Integer)v.getTag();
                        Log.d(Utils.TAG, " Tag result: " + pos);
                        //ParseObject parseObject = availableAdapter.removeItem(pos);
                        ParseObject parseObject = availablePackages.remove(pos.intValue());
                        availableAdapter.notifyItemRemoved(pos);
                        availableAdapter = new PackagesCardGridAdapter(availablePackages, this, getApplicationContext());
                        availableRecycler.setAdapter(availableAdapter);
                        installedPackages.add(parseObject);
                        installedAdapter.notifyItemInserted(installedAdapter.getItemCount());
                    }
                }
                , getApplicationContext());
        availableRecycler.setAdapter(availableAdapter);
        availableRecycler.setNestedScrollingEnabled(false);
    }

}
