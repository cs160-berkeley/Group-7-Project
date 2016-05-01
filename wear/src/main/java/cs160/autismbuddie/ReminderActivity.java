package cs160.autismbuddie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class ReminderActivity extends Activity {

    private Context _that = this;
    private int screen;
    private int mShortAnimationDuration;
    private SharedPreferences settings;
    private String r_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        //Get package data
        settings = getSharedPreferences("PREF_FILE", 0);
        final String pack_string = settings.getString("Package", "");

        //Get Intent Data
        String pa = getPackageName();
        Intent intent = getIntent();
        r_text = intent.getStringExtra(pa + ".reminder");

        screen = 0;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final ImageView reminder_back = (ImageView) stub.findViewById(R.id.reminder_back);
                final TextView reminder_text = (TextView) stub.findViewById(R.id.reminder_text);
                final ImageView reminder_complete = (ImageView) stub.findViewById(R.id.reminder_complete);
                final ImageView reminder_incomplete = (ImageView) stub.findViewById(R.id.reminder_incomplete);
                Typeface face = Typeface.createFromAsset(getAssets(),"fonts/pokemon_gb.ttf");
                reminder_text.setTypeface(face);
                reminder_text.setText(r_text);

                try {
                    JSONObject pack = new JSONObject(pack_string);
                    JSONObject reminder_pack = pack.getJSONObject("Reminder");
                    Bitmap back_b = MainActivity.getBitmapFromString(reminder_pack.getString("back"));
                    Bitmap complete_b = MainActivity.getBitmapFromString(reminder_pack.getString("complete"));
                    Bitmap incomplete_b = MainActivity.getBitmapFromString(reminder_pack.getString("incomplete"));
                    String font = reminder_pack.getString("font");
                    String font_color = reminder_pack.getString("font-color");

                    reminder_back.setImageBitmap(back_b);
                    reminder_incomplete.setImageBitmap(incomplete_b);
                    reminder_complete.setImageBitmap(complete_b);

                    Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/" + font);
                    reminder_text.setTypeface(face2);
                    reminder_text.setTextColor(Color.parseColor(font_color));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (MainActivity.PACK) {
                    reminder_back.setImageResource(R.drawable.reminder_back2);
                    reminder_complete.setImageResource(R.drawable.reminder_complete2);
                    reminder_incomplete.setImageResource(R.drawable.reminder_incomplete2);
                    Typeface face3 = Typeface.createFromAsset(getAssets(),"fonts/minecraft.ttf");
                    reminder_text.setTypeface(face3);
                    reminder_text.setTextColor(Color.parseColor("#c5c5c5"));
                }


                stub.setOnTouchListener(new OnSwipeTouchListener(_that){
                    @Override
                    public void onTap() {
                        Log.v("TAP", "TAP");
                    }

                    public void onSwipeRight() {
                        Log.v("RIGHT", "RIGHT");
                        if (screen == 0) {
                            screen = 1;
                            View[] v1 = {reminder_back, reminder_text};
                            View[] v2 = {reminder_complete};
                            crossFade(v1, v2);
                            new CountDownTimer(2000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    //set the new Content of your activity
                                    Intent intent = new Intent(_that, MainActivity.class);
                                    startActivity(intent);
                                }
                            }.start();
                        }
                    }

                    public void onSwipeLeft() {
                        Log.v("LEFT", "LEFT");
                        if (screen == 0) {
                            screen = 1;
                            View[] v1 = {reminder_back, reminder_text};
                            View[] v2 = {reminder_incomplete};
                            crossFade(v1, v2);
                            new CountDownTimer(2000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    //set the new Content of your activity
                                    Intent intent = new Intent(_that, MainActivity.class);
                                    startActivity(intent);
                                }
                            }.start();
                        }
                    }

                    public void onSwipeTop() {
                        Log.v("TOP", "TOP");
                    }

                    public void onSwipeBottom() {
                        Log.v("BOT", "BOT");
                    }
                });
            }
        });
    }

    private void crossFade(View[] v1, View[] v2) {
        for (View v : v2) {
            v.setAlpha(0f);
            v.setVisibility(View.VISIBLE);
            v.animate()
                    .alpha(1f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(null);
        }

        for (final View v : v1) {
            v.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            v.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
