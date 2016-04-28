package cs160.autismbuddie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FacesActivity extends Activity {

    private int picker;
    private int screen;
    private int face_num;
    private Context _that = this;
    private int mShortAnimationDuration;
    private int curr_high_score;
    private int curr_score;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faces);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        //Get Intent arguments and setup variables
        String pa = getPackageName();
        Intent intent = getIntent();
        picker = intent.getIntExtra(pa + ".picker", 0);
        screen = 0;
        face_num = 1;

        //Get package data
        settings = getSharedPreferences("PREF_FILE", 0);
        String pack = settings.getString("Package", "");
        curr_high_score = settings.getInt("Faces_Score", 0);
        curr_score = 0;


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                //Get View Variables
                final ImageView home_screen = (ImageView) stub.findViewById(R.id.faces_home);
                final ImageView tut_screen = (ImageView) stub.findViewById(R.id.faces_tut);
                final ImageView back_screen = (ImageView) stub.findViewById(R.id.faces_back);
                final TextView hiscore = (TextView) stub.findViewById(R.id.faces_hiscore);
                final TextView score = (TextView) stub.findViewById(R.id.faces_score);
                final ImageView image = (ImageView) stub.findViewById(R.id.faces_image);
                final ImageView right = (ImageView) stub.findViewById(R.id.faces_right);
                final ImageView wrong = (ImageView) stub.findViewById(R.id.faces_wrong);
                Typeface face = Typeface.createFromAsset(getAssets(),"fonts/pocket_monk.otf");
                hiscore.setTypeface(face);
                hiscore.setText("HISCORE:" + curr_high_score);
                score.setTypeface(face);
                score.setText("SCORE:" + curr_score);

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
                            View[] v1 = {tut_screen};
                            View[] v2 = {back_screen, hiscore, score, image};
                            crossFade(v1, v2);
                            screen = 2;
                        }
                    }

                    public void onSwipeRight() {
                        Log.v("RIGHT", "RIGHT");
                        if (screen == 2) {
                            screen = 3;
                            if (face_num == 1 || face_num == 3 || face_num == 4) {
                                curr_score += 1;
                                if (curr_score > curr_high_score) {
                                    curr_high_score = curr_score;
                                    setHighScore(hiscore, curr_score);
                                }
                                score.setText("SCORE:" + curr_score);
                                final View[] v1 = {image};
                                final View[] v2 = {right};
                                crossFade(v1, v2);
                                new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        //set the new Content of your activity
                                        if (face_num == 1) {
                                            image.setImageResource(R.drawable.faces_2);
                                            face_num = 2;
                                            screen = 2;
                                        } else if (face_num == 2) {
                                            image.setImageResource(R.drawable.faces_3);
                                            face_num = 3;
                                            screen = 2;
                                        } else if (face_num == 3) {
                                            image.setImageResource(R.drawable.faces_4);
                                            face_num = 4;
                                            screen = 2;
                                        } else if (face_num == 4) {
                                            image.setImageResource(R.drawable.faces_1);
                                            face_num = 1;
                                            screen = 2;
                                        }
                                        crossFade(v2, v1);
                                    }
                                }.start();
                            } else {
                                curr_score = 0;
                                score.setText("SCORE:" + curr_score);
                                final View[] v1 = {image};
                                final View[] v2 = {wrong};
                                crossFade(v1, v2);
                                new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        //set the new Content of your activity
                                        if (face_num == 1) {
                                            image.setImageResource(R.drawable.faces_2);
                                            face_num = 2;
                                            screen = 2;
                                        } else if (face_num == 2) {
                                            image.setImageResource(R.drawable.faces_3);
                                            face_num = 3;
                                            screen = 2;
                                        } else if (face_num == 3) {
                                            image.setImageResource(R.drawable.faces_4);
                                            face_num = 4;
                                            screen = 2;
                                        } else if (face_num == 4) {
                                            image.setImageResource(R.drawable.faces_1);
                                            face_num = 1;
                                            screen = 2;
                                        }
                                        crossFade(v2, v1);
                                    }
                                }.start();
                            }
                        }
                    }

                    public void onSwipeLeft() {
                        Log.v("LEFT", "LEFT");
                        if (screen == 2) {
                            screen = 3;
                            if (face_num == 1 || face_num == 3 || face_num == 4) {
                                curr_score = 0;
                                score.setText("SCORE:" + curr_score);
                                final View[] v1 = {image};
                                final View[] v2 = {wrong};
                                crossFade(v1, v2);
                                new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        //set the new Content of your activity
                                        if (face_num == 1) {
                                            image.setImageResource(R.drawable.faces_2);
                                            face_num = 2;
                                            screen = 2;
                                        } else if (face_num == 2) {
                                            image.setImageResource(R.drawable.faces_3);
                                            face_num = 3;
                                            screen = 2;
                                        } else if (face_num == 3) {
                                            image.setImageResource(R.drawable.faces_4);
                                            face_num = 4;
                                            screen = 2;
                                        } else if (face_num == 4) {
                                            image.setImageResource(R.drawable.faces_1);
                                            face_num = 1;
                                            screen = 2;
                                        }
                                        crossFade(v2, v1);
                                    }
                                }.start();
                            } else {
                                curr_score += 1;
                                if (curr_score > curr_high_score) {
                                    curr_high_score = curr_score;
                                    setHighScore(hiscore, curr_score);
                                }
                                score.setText("SCORE:" + curr_score);
                                final View[] v1 = {image};
                                final View[] v2 = {right};
                                crossFade(v1, v2);
                                new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        //set the new Content of your activity
                                        if (face_num == 1) {
                                            image.setImageResource(R.drawable.faces_2);
                                            face_num = 2;
                                            screen = 2;
                                        } else if (face_num == 2) {
                                            image.setImageResource(R.drawable.faces_3);
                                            face_num = 3;
                                            screen = 2;
                                        } else if (face_num == 3) {
                                            image.setImageResource(R.drawable.faces_4);
                                            face_num = 4;
                                            screen = 2;
                                        } else if (face_num == 4) {
                                            image.setImageResource(R.drawable.faces_1);
                                            face_num = 1;
                                            screen = 2;
                                        }
                                        crossFade(v2, v1);
                                    }
                                }.start();
                            }
                        }
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

    //Cross fade views v1 into views v2
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

    private void setHighScore(TextView v, int h) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Faces_Score", h);
        editor.commit();
        v.setText("HISCORE:" + h);
    }
}
