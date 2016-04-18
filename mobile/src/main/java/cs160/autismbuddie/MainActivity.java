package cs160.autismbuddie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static PhoneToWatchUtil ptwUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ptwUtil = new PhoneToWatchUtil(getApplicationContext());

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
                    Toast.makeText(getApplicationContext(), "Mode switched!", Toast.LENGTH_SHORT).show();
                    ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_MODE, PhoneToWatchUtil.MODE_FREE);
                    //TODO: Dynamically choose mode
                }
            });



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ptwUtil.disconnect();
    }
}
