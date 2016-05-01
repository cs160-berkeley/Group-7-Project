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
import android.speech.tts.TextToSpeech;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class TriviaActivity extends Activity {

    private int picker;
    private int screen;
    private Context _that = this;
    private int mShortAnimationDuration;
    private SharedPreferences settings;
    private String[] trivia_facts;
    private int fact_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        String pa = getPackageName();
        Intent intent = getIntent();
        picker = intent.getIntExtra(pa + ".picker", 0);
        screen = 0;
        fact_counter = 0;
        trivia_facts = null;

        //Get package data
        settings = getSharedPreferences("PREF_FILE", 0);
        final String pack_string = settings.getString("Package", "");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final ImageView home_screen = (ImageView) stub.findViewById(R.id.trivia_home);
                final ImageView tut_screen = (ImageView) stub.findViewById(R.id.trivia_tut);
                final ImageView back_screen = (ImageView) stub.findViewById(R.id.trivia_back);
                final TextView fact1 = (TextView) stub.findViewById(R.id.trivia_fact_1);
                final TextView fact2 = (TextView) stub.findViewById(R.id.trivia_fact_2);
                Typeface face = Typeface.createFromAsset(getAssets(),"fonts/pokemon_gb.ttf");
                fact1.setTypeface(face);
                fact2.setTypeface(face);

                try {
                    JSONObject pack = new JSONObject(pack_string);
                    JSONObject trivia_pack = pack.getJSONObject("Trivia");
                    Bitmap home_b = MainActivity.getBitmapFromString(trivia_pack.getString("home"));
                    Bitmap tut_b = MainActivity.getBitmapFromString(trivia_pack.getString("tutorial"));
                    Bitmap back_b = MainActivity.getBitmapFromString(trivia_pack.getString("back"));
                    String font = trivia_pack.getString("font");
                    String font_color = trivia_pack.getString("font-color");
                    JSONArray facts = trivia_pack.getJSONArray("facts");
                    trivia_facts = new String[facts.length()];
                    for (int i = 0; i < facts.length(); i++) {
                        trivia_facts[i] = facts.getString(i);
                    }

                    home_screen.setImageBitmap(home_b);
                    tut_screen.setImageBitmap(tut_b);
                    back_screen.setImageBitmap(back_b);
                    Typeface face2 = Typeface.createFromAsset(getAssets(),"fonts/" + font);
                    fact1.setTypeface(face2);
                    fact1.setTextColor(Color.parseColor(font_color));
                    fact2.setTypeface(face2);
                    fact2.setTextColor(Color.parseColor(font_color));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (MainActivity.PACK) {
                    home_screen.setImageResource(R.drawable.trivia_home2);
                    tut_screen.setImageResource(R.drawable.trivia_tut2);
                    back_screen.setImageResource(R.drawable.trivia_back2);
                    Typeface face3 = Typeface.createFromAsset(getAssets(),"fonts/minecraft.ttf");
                    fact1.setTypeface(face3);
                    fact2.setTypeface(face3);
                    fact1.setTextColor(Color.parseColor("#c5c5c5"));
                    fact2.setTextColor(Color.parseColor("#c5c5c5"));
                }

                if (picker == 1) {
                    home_screen.setVisibility(View.GONE);
                    tut_screen.setVisibility(View.VISIBLE);
                    screen = 1;
                }
                stub.setOnTouchListener(new OnSwipeTouchListener(_that) {
                    @Override
                    public void onTap() {
                        Log.v("TAP", "TAP");
                        if (screen == 0) {
                            View[] v1 = {home_screen};
                            View[] v2 = {tut_screen};
                            crossFade(v1, v2);
                            screen = 1;
                        } else if (screen == 1) {
                            updateFact(fact1);
                            View[] v1 = {tut_screen};
                            View[] v2 = {back_screen, fact1};
                            crossFade(v1, v2);
                            screen = 2;
                        } else if (screen == 2) {
                            updateFact(fact2);
                            View[] v1 = {fact1};
                            View[] v2 = {fact2};
                            crossFade(v1, v2);
                            screen = 3;
                        } else if (screen == 3) {
                            updateFact(fact1);
                            View[] v1 = {fact2};
                            View[] v2 = {fact1};
                            crossFade(v1, v2);
                            screen = 2;
                        }
                    }

                    public void onSwipeRight() {
                        Log.v("RIGHT", "RIGHT");
                    }

                    public void onSwipeLeft() {
                        Log.v("LEFT", "LEFT");
                    }

                    public void onSwipeTop() {
                        Log.v("TOP", "TOP");
                    }

                    public void onSwipeBottom() {
                        Log.v("BOT", "BOT");
                        Intent intent = new Intent(_that, MainActivity.class);
                        startActivity(intent);
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

    private void updateFact(TextView v) {
        if (trivia_facts == null) {
            return;
        }
        if (fact_counter == trivia_facts.length) {
            fact_counter = 0;
        }
        v.setText(trivia_facts[fact_counter]);
        fact_counter += 1;
    }
}
