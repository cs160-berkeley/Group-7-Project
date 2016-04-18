package cs160.autismbuddie;

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

        //Read mode from storage, decide which layout to render

        //If mode is unrestricted, show list of activities
        ActivityGridPageAdapter adapter = new ActivityGridPageAdapter(this, getFragmentManager());
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        DotsPageIndicator dotsIndicator = (DotsPageIndicator)findViewById(R.id.page_indicator);
        dotsIndicator.setPager(pager);
    }

}
