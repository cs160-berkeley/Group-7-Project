package cs160.autismbuddie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faces);

        String pa = getPackageName();
        Intent intent = getIntent();
        picker = intent.getIntExtra(pa + ".picker", 0);
        screen = 0;
        face_num = 2;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final ImageView v = (ImageView) stub.findViewById(R.id.faces_image);
                if (picker == 1) {
                    v.setImageResource(R.drawable.faces1);
                    screen = 1;
                }
                v.setOnTouchListener(new OnSwipeTouchListener(_that){
                    @Override
                    public void onTap() {
                        Log.v("TAP", "TAP");
                        if (screen == 0) {
                            v.setImageResource(R.drawable.faces1);
                            screen = 1;
                        } else if (screen == 1) {
                            v.setImageResource(R.drawable.faces2);
                            screen = 2;
                        }
                    }
                    public void onSwipeRight() {
                        Log.v("RIGHT", "RIGHT");
                        if (screen == 2) {
                            screen = 3;
                            if (face_num == 2 || face_num == 4 || face_num == 5) {
                                v.setImageResource(R.drawable.faces6);
                            } else {
                                v.setImageResource(R.drawable.faces7);
                            }
                            new CountDownTimer(3000,1000){
                                @Override
                                public void onTick(long millisUntilFinished){}

                                @Override
                                public void onFinish(){
                                    //set the new Content of your activity
                                    if (face_num == 2) {
                                        v.setImageResource(R.drawable.faces3);
                                        face_num = 3;
                                        screen = 2;
                                    } else if (face_num == 3) {
                                            v.setImageResource(R.drawable.faces4);
                                            face_num = 4;
                                            screen = 2;
                                    } else if (face_num == 4) {
                                        v.setImageResource(R.drawable.faces5);
                                        face_num = 5;
                                        screen = 2;
                                    } else if (face_num == 5) {
                                        v.setImageResource(R.drawable.faces2);
                                        face_num = 2;
                                        screen = 2;
                                    }
                                }
                            }.start();
                        }
                    }

                    public void onSwipeLeft() {
                        Log.v("LEFT", "LEFT");
                        if (screen == 2) {
                            screen = 3;
                            if (face_num == 2 || face_num == 4 || face_num == 5) {
                                v.setImageResource(R.drawable.faces7);
                            } else {
                                v.setImageResource(R.drawable.faces6);
                            }
                            new CountDownTimer(5000,1000){
                                @Override
                                public void onTick(long millisUntilFinished){}

                                @Override
                                public void onFinish(){
                                    //set the new Content of your activity
                                    if (face_num == 2) {
                                        v.setImageResource(R.drawable.faces3);
                                        face_num = 3;
                                        screen = 2;
                                    } else if (face_num == 3) {
                                        v.setImageResource(R.drawable.faces4);
                                        face_num = 4;
                                        screen = 2;
                                    } else if (face_num == 4) {
                                        v.setImageResource(R.drawable.faces5);
                                        face_num = 5;
                                        screen = 2;
                                    } else if (face_num == 5) {
                                        v.setImageResource(R.drawable.faces2);
                                        face_num = 2;
                                        screen = 2;
                                    }
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
}
