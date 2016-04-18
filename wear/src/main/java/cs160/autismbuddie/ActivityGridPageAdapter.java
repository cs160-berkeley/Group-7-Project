package cs160.autismbuddie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

/**
 * Created by robinhu on 4/16/16.
 */
public class ActivityGridPageAdapter extends FragmentGridPagerAdapter{

    private final Context mContext;

    public ActivityGridPageAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    @Override
    public Fragment getFragment(int i, int i1) {
        ActivityFragment act = new ActivityFragment();
        Bundle b = new Bundle();
        b.putInt("activity", i1);
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
