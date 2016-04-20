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
                    if(System.currentTimeMillis() % 2 == 0) {
                        Log.d(Utils.TAG, "Sending trivia");
                        sendTrivia();
                    }
                    else {
                        Log.d(Utils.TAG, "Sending faces");
                        sendFaces();
                    }
                }
            });
        }

    }

    private void sendTrivia()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            // Sample code for sending trivia
            int theme_object = 331;
            jsonObject.put("theme", theme_object);
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

    private void sendFaces()
    {
        try{
            int theme_object = 331;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("theme", theme_object);
            JSONArray faces = new JSONArray();
            faces.put("@assets/face1");
            faces.put("@assets/face2");
            faces.put("@assets/face3");
            jsonObject.put("faces", faces);
            MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_TRIVIA, jsonObject.toString());

        }
        catch (JSONException e)
        {
            Log.d(Utils.TAG, "Unable to construct JSONObject from faces");
            e.printStackTrace();
        }
    }
}
