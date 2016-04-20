package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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

                    // TODO: Pick faces or trivia based on button
                    if(System.currentTimeMillis() % 2 == 0)
                    {
                        Log.d(Utils.TAG, "Sending trivia");
                        sendTrivia();
                    }
                    else
                    {
                        Log.d(Utils.TAG, "Sending faces");
                        sendFaces();
                    }
                }
            });
        }

    }

    private void sendTrivia()
    {
        int theme_object = 331;
        // TODO: Use real identifier for theme
        try {
            JSONObject jsonObject = MainActivity.ptwUtil.getTriviaGame("someId");
            if (jsonObject != null) {
                jsonObject.put("theme", theme_object);
                MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_TRIVIA, jsonObject.toString());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void sendFaces()
    {
        int theme_object = 331;

        // TODO: Use real theme identifier
        try
        {
            JSONObject jsonObject = MainActivity.ptwUtil.getFaceGame("someId");
            if(jsonObject != null)
            {
                jsonObject.put("theme", theme_object);
                MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_FACES, jsonObject.toString());
            }
        }
        catch (JSONException e)
        {
            Log.d(Utils.TAG, "Unable to construct JSONObject from faces");
            e.printStackTrace();
        }
    }
}
