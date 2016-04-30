package cs160.autismbuddie;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("PREF_FILE", 0);
        String pack_string = settings.getString("Package", "");
        JSONObject pack = null;
        try {
            pack = new JSONObject(pack_string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean mode = settings.getBoolean("freeMode", false);
        if (mode) {
            String[] home_screen_strings = {"", ""};
            if (pack != null) {
                try {
                    String faces_home_string = pack.getJSONObject("Faces").getString("home");
                    String trivia_home_string = pack.getJSONObject("Trivia").getString("home");
                    home_screen_strings[0] = faces_home_string;
                    home_screen_strings[1] = trivia_home_string;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ActivityGridPageAdapter adapter = new ActivityGridPageAdapter(this, getFragmentManager(), home_screen_strings);
            GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);

            DotsPageIndicator dotsIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
            dotsIndicator.setPager(pager);
        } else {
            setContentView(R.layout.activity_main_restricted);
            if (pack != null) {
                try {
                    String watch_face_string = pack.getString("WatchFace");
                    Bitmap watch_face_bitmap = MainActivity.getBitmapFromString(watch_face_string);
                    ImageView watch_face_view = (ImageView) findViewById(R.id.watch_face);
                    watch_face_view.setImageBitmap(watch_face_bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * This Function converts the String back to Bitmap
    * */
    public static Bitmap getBitmapFromString(String jsonString) {
        if (jsonString.equals("null")) {
            return null;
        }
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
