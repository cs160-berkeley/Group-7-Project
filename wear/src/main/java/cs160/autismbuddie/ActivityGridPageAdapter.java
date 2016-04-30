package cs160.autismbuddie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robinhu on 4/16/16.
 */
public class ActivityGridPageAdapter extends FragmentGridPagerAdapter{

    private final Context mContext;
    private final String[] home_screen_strings;

    public ActivityGridPageAdapter(Context ctx, FragmentManager fm, String[] h) {
        super(fm);
        mContext = ctx;
        home_screen_strings = h;
    }

    @Override
    public Fragment getFragment(int i, int i1) {
        ActivityFragment act = new ActivityFragment();
        Bundle b = new Bundle();
        b.putInt("activity", i1);
        b.putString("home_screen", home_screen_strings[i1]);
        act.setArguments(b);
        return act;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }
}
