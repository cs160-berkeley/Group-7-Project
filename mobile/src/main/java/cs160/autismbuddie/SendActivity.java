package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ImageButton imageButton = (ImageButton)findViewById(R.id.sendImageButton);
        if(imageButton != null)
        {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Utils.TAG, "Send clicked, sending trivia");
                    try {
                        JSONObject jsonObject = new JSONObject();
                        // Sample code for sending trivia
                        int theme_id = 331;
                        jsonObject.put("theme", theme_id);
                        JSONArray facts = new JSONArray();
                        facts.put("This is a sample fact");
                        facts.put("This is another sample fact");
                        facts.put("This is a third sample fact");
                        jsonObject.put("facts", facts);
                        MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_TRIVIA, jsonObject.toString());
                    }
                    catch (JSONException e)
                    {
                        Log.d(Utils.TAG, "Unable to construct JSONObject from trivia");
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
