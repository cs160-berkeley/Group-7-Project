package cs160.autismbuddie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ReminderActivity extends Activity {

    private Context _that = this;
    private int screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        screen = 0;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final ImageView v = (ImageView) stub.findViewById(R.id.reminder_img);
                v.setOnTouchListener(new OnSwipeTouchListener(_that){
                    @Override
                    public void onTap() {
                        Log.v("TAP", "TAP");
                    }
                    public void onSwipeRight() {
                        Log.v("RIGHT", "RIGHT");
                        if (screen == 0) {
                            screen = 1;
                            v.setImageResource(R.drawable.reminder2);
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
                            v.setImageResource(R.drawable.reminder3);
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
}
