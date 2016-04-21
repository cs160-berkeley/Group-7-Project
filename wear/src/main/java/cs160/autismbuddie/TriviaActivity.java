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

public class TriviaActivity extends Activity {

    private int picker;
    private int screen;
    private Context _that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        String pa = getPackageName();
        Intent intent = getIntent();
        picker = intent.getIntExtra(pa + ".picker", 0);
        screen = 0;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final ImageView v = (ImageView) stub.findViewById(R.id.trivia_img);
                if (picker == 1) {
                    v.setImageResource(R.drawable.trivia1);
                    screen = 1;
                }
                v.setOnTouchListener(new OnSwipeTouchListener(_that){
                    @Override
                    public void onTap() {
                        Log.v("TAP", "TAP");
                        if (screen == 0) {
                            v.setImageResource(R.drawable.trivia1);
                            screen = 1;
                        } else if (screen == 1) {
                            v.setImageResource(R.drawable.trivia2);
                            screen = 2;
                        } else if (screen == 2) {
                            v.setImageResource(R.drawable.trivia3);
                            screen = 3;
                        } else if (screen == 3) {
                            v.setImageResource(R.drawable.trivia4);
                            screen = 4;
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
}
