package cs160.autismbuddie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robinhu on 4/16/16.
 */
public class ActivityGridPageAdapter extends FragmentGridPagerAdapter{

    private final Context mContext;
    private JSONObject pack;

    public ActivityGridPageAdapter(Context ctx, FragmentManager fm, String p) {
        super(fm);
        mContext = ctx;
        try {
            pack = new JSONObject(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Fragment getFragment(int i, int i1) {
        ActivityFragment act = new ActivityFragment();
        Bundle b = new Bundle();
        b.putInt("activity", i1);
        if (i1 == 0) {
            try {
                b.putString("home_url", pack.getJSONObject("Faces").getString("homeImgUrl"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i1 == 1) {
            try {
                b.putString("home_url", pack.getJSONObject("Trivia").getString("homeImgUrl"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
