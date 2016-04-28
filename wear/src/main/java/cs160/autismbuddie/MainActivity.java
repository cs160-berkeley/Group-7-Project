package cs160.autismbuddie;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("PREF_FILE", 0);
        boolean mode = settings.getBoolean("freeMode", false);
        if (mode) {
            //If mode is unrestricted, show list of activities
            String pack = settings.getString("Package", "");

            ActivityGridPageAdapter adapter = new ActivityGridPageAdapter(this, getFragmentManager(), pack);
            GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);

            DotsPageIndicator dotsIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
            dotsIndicator.setPager(pager);
        } else {
            setContentView(R.layout.activity_main_restricted);
        }
    }

}
