package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GetPackagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_packages);

        if(getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
