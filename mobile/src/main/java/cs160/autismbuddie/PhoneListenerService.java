package cs160.autismbuddie;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class PhoneListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        Log.d(Utils.TAG, "in PhoneListenerService, got: " + messageEvent.getPath());
        String message = messageEvent.getPath();
        Toast.makeText(getApplicationContext(), "MessageReceived: " + message, Toast.LENGTH_SHORT).show();
        //TODO: Handle received messages
    }
}
