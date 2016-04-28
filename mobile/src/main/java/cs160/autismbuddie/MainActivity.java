package cs160.autismbuddie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static PhoneToWatchUtil ptwUtil;
    private String currentMode = PhoneToWatchUtil.MODE_FREE;
    public static SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView reminderText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ptwUtil == null)
            ptwUtil = new PhoneToWatchUtil(getApplicationContext());
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        currentMode = mSharedPreferences.getString(Utils.KEY_MODE, PhoneToWatchUtil.MODE_FREE);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ABuddie");
        else
            cacheDir=getApplicationContext().getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .build();


        ImageLoader.getInstance().init(config);



        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if( ConnectionResult.SUCCESS != result ){
            Log.d(Utils.TAG, "Showing update dialog");
            // Show appropriate dialog
            Dialog d = GooglePlayServicesUtil.getErrorDialog(result, this, 0);
            d.show();
        }

        // Bind frame views
        FrameLayout sendFrame = (FrameLayout)findViewById(R.id.sendFrame);
        FrameLayout reminderFrame = (FrameLayout)findViewById(R.id.reminderFrame);
        FrameLayout packagesFrame = (FrameLayout)findViewById(R.id.packagesFrame);
        FrameLayout modesFrame = (FrameLayout)findViewById(R.id.modesFrame);

        // This will be useful later to cancel message send
        reminderText = (TextView)findViewById(R.id.reminderText);
        ImageButton sendReminderButton = (ImageButton) findViewById(R.id.sendReminderButton);

        final ImageView modeImg = (ImageView)findViewById(R.id.modeImg);
        if(currentMode.equalsIgnoreCase(PhoneToWatchUtil.MODE_FREE) && modeImg != null)
            modeImg.setImageResource(R.drawable.modes_free);
        else if (modeImg != null)
            modeImg.setImageResource(R.drawable.modes_restricted);

        // Set on click listeners
        if(sendFrame != null)
        {
            sendFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SendActivity.class);
                    startActivity(intent);
                }
            });
        }
        if(reminderFrame != null)
            reminderFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Animate message sending
                    CardView cardView = (CardView)findViewById(R.id.reminderMsgCard);
                    if(cardView.getVisibility() == View.VISIBLE)
                    {
                        hideReminderCard(cardView);
                    }
                    else
                    {
                        showReminderCard(cardView);
                    }
                }
            });
        if(packagesFrame != null)
            packagesFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GetPackagesActivity.class);
                    startActivity(intent);
                }
            });
        if(modesFrame != null)
            modesFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentMode.equalsIgnoreCase(PhoneToWatchUtil.MODE_FREE))
                    {
                        //switch to restricted
                        modeImg.setImageResource(R.drawable.modes_restricted);
                        currentMode = PhoneToWatchUtil.MODE_RESTRICTED;
                        mEditor.putString(Utils.KEY_MODE, PhoneToWatchUtil.MODE_RESTRICTED);
                        mEditor.apply();
                        ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_MODE, PhoneToWatchUtil.MODE_RESTRICTED);
                    }
                    else
                    {
                        // switch to free
                        modeImg.setImageResource(R.drawable.modes_free);
                        currentMode = PhoneToWatchUtil.MODE_FREE;
                        mEditor.putString(Utils.KEY_MODE, PhoneToWatchUtil.MODE_FREE);
                        mEditor.apply();
                        ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_MODE, PhoneToWatchUtil.MODE_FREE);
                    }
                }
            });
        if(sendReminderButton != null)
        {
            sendReminderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText)findViewById(R.id.reminderMsg);
                    String message = editText.getText().toString();
                    ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_REMINDER, message);
                    Toast.makeText(getApplicationContext(), "Reminder sent!", Toast.LENGTH_SHORT).show();
                    hideReminderCard((CardView)findViewById(R.id.reminderMsgCard));
                    editText.setText("");
                }
            });
        }


        // Send current package on startup
        // TODO: Do we want to store in sharedPrefs ... or nah?
        // String currentPackageJSON = mSharedPreferences.getString(Utils.KEY_PACKAGE, null);
        //if(currentPackageJSON == null)
        String currentPackageJSON = ptwUtil.queryForPackageJSON("Pokemon").toString(); //TODO: Use a default identifier
        Log.d(Utils.TAG, "Sending: " + currentPackageJSON);
        ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_PACKAGE, currentPackageJSON);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ptwUtil.disconnect();
    }

    private void showReminderCard(CardView cardView)
    {
        // previously invisible view
        // get the center for the clipping circle
        int cx = cardView.getWidth() / 2;
        int cy = cardView.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(cardView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        cardView.setVisibility(View.VISIBLE);
        anim.start();
        reminderText.setText("CANCEL");
    }

    private void hideReminderCard(final CardView cardView)
    {
        // get the center for the clipping circle
        int cx = cardView.getWidth() / 2;
        int cy = cardView.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(cardView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                cardView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
        reminderText.setText("Reminders");

    }
}
