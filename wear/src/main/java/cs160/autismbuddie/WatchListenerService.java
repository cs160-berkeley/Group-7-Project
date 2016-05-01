package cs160.autismbuddie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * Created by robinhu on 4/16/16.
 */
public class WatchListenerService extends WearableListenerService {
    private String TAG = "ABuddie";

    //Most Simple Version: Listen for messages and launch activity based off path (eg Faces, Reminder, etc)
    //When receiving a mode change message, make update to storate
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "in WatchListenerService, Path: " + messageEvent.getPath());
        Log.d(TAG, "in WatchListenerService, Data: " + messageEvent.getData());

        String path = messageEvent.getPath();
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        Log.d(TAG, value);

        if (path.equals("/faces")) {
            Intent intent = new Intent(this, FacesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(getPackageName() + ".picker", 0);
            startActivity(intent);
        } else if (path.equals("/trivia")) {
            Intent intent = new Intent(this, TriviaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(getPackageName() + ".picker", 0);
            startActivity(intent);
        } else if (path.equals("/reminder")) {
            Intent intent = new Intent(this, ReminderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(getPackageName() + ".reminder", value);
            startActivity(intent);
        } else if (path.equals("/mode")) {
            boolean freeMode = value.equals("free");
            SharedPreferences settings = getSharedPreferences("PREF_FILE", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("freeMode", freeMode);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (path.equals("/package")) {
            SharedPreferences settings = getSharedPreferences("PREF_FILE", 0);
            SharedPreferences.Editor editor = settings.edit();

            try {
                JSONObject p = new JSONObject(value);
                String pack = p.getString("ID");
                editor.putString("Package", pack);
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Harder: Listen for data items that have proper themes/images as assets, then launch activity using those
}
