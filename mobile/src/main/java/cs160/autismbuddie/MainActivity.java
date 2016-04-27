package cs160.autismbuddie;

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
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static PhoneToWatchUtil ptwUtil;
    private String currentMode = PhoneToWatchUtil.MODE_FREE;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ptwUtil == null)
            ptwUtil = new PhoneToWatchUtil(getApplicationContext());
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        currentMode = mSharedPreferences.getString(Utils.KEY_MODE, PhoneToWatchUtil.MODE_FREE);


        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if( ConnectionResult.SUCCESS != result ){
            Log.d(Utils.TAG, "Showing update dialog");
            // Show appropriate dialog
            Dialog d = GooglePlayServicesUtil.getErrorDialog(result, this, 0);
            d.show();
        }

        // Bind card views
        CardView sendCard = (CardView)findViewById(R.id.sendCard);
        CardView reminderCard = (CardView)findViewById(R.id.reminderCard);
        CardView packagesCard = (CardView)findViewById(R.id.packagesCard);
        CardView modesCard = (CardView)findViewById(R.id.modesCard);

        final ImageView modeImg = (ImageView)findViewById(R.id.modeImg);
        if(currentMode.equalsIgnoreCase(PhoneToWatchUtil.MODE_FREE) && modeImg != null)
            modeImg.setImageResource(R.drawable.modes_free);
        else if (modeImg != null)
            modeImg.setImageResource(R.drawable.modes_restricted);

        // Set on click listeners
        if(sendCard != null)
        {
            sendCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SendActivity.class);
                    startActivity(intent);
                }
            });
        }
        if(reminderCard != null)
            reminderCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
                    startActivity(intent);
                }
            });
        if(packagesCard != null)
            packagesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GetPackagesActivity.class);
                    startActivity(intent);
                }
            });
        if(modesCard != null)
            modesCard.setOnClickListener(new View.OnClickListener() {
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


        String currentPackageJSON = mSharedPreferences.getString(Utils.KEY_PACKAGE, null);
        if(currentPackageJSON == null)
        {
            JSONObject pack = new JSONObject();
            try {
                pack.put("ID", "blahblah"); //TODO: Use a more realistic identifier
                JSONObject trivia = ptwUtil.getTriviaGame("pokemon");
                JSONObject faces = ptwUtil.getFaceGame("pokemon");
                pack.put("Faces", faces);
                pack.put("Trivia", trivia);
                mEditor.putString(Utils.KEY_PACKAGE, pack.toString());
                Log.d(Utils.TAG, "Sending Created: " + pack.toString());
                ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_PACKAGE, pack.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            Log.d(Utils.TAG, "Sending: " + currentPackageJSON);
            ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_PACKAGE, currentPackageJSON);
        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ptwUtil.disconnect();
    }
}
