package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GetPackagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_packages);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up installed grid
        RecyclerView installedRecycler = (RecyclerView)findViewById(R.id.getPacksInstalledRecycler);
        installedRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        installedRecycler.setAdapter(new PackagesCardGridAdapter(2));
        installedRecycler.setNestedScrollingEnabled(false);

        RecyclerView availableRecycler = (RecyclerView)findViewById(R.id.getPacksAvailableRecycler);
        availableRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        availableRecycler.setAdapter(new PackagesCardGridAdapter());
        availableRecycler.setNestedScrollingEnabled(false);

    }
}
