package cs160.autismbuddie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class SendActivity extends AppCompatActivity {

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Set up Packages Grid
        RecyclerView packsRecycler = (RecyclerView)findViewById(R.id.sendRecycler);
        packsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        PackagesCardGridAdapter adt = new PackagesCardGridAdapter();
        packsRecycler.setAdapter(adt);
        packsRecycler.setNestedScrollingEnabled(false);
        packsRecycler.setHasFixedSize(false);

        // Set up send buttons
        Button triviaSend = (Button)findViewById(R.id.triviaSend);
        Button facesSend = (Button)findViewById(R.id.facesSend);
        if(triviaSend != null) {
            triviaSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {sendTrivia();}});
        }
        if(facesSend != null) {
            facesSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {sendFaces();}});
        }

    }

    private void sendTrivia()
    {
        int theme_object = 331; // TODO: Use real identifier for theme

        try {
            String currPackJSON = mSharedPrefs.getString(Utils.KEY_PACKAGE, null);
            JSONObject jsonObject;
            if(currPackJSON != null)
            {
                JSONObject packJSON = new JSONObject(currPackJSON);
                jsonObject = packJSON.getJSONObject("Trivia");
            }
            else
                jsonObject = MainActivity.ptwUtil.getTriviaGame("someId");

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
            String currPackJSON = mSharedPrefs.getString(Utils.KEY_PACKAGE, null);
            JSONObject jsonObject;
            if(currPackJSON != null)
            {
                JSONObject packJSON = new JSONObject(currPackJSON);
                jsonObject = packJSON.getJSONObject("Faces");
            }
            else
                jsonObject = MainActivity.ptwUtil.getFaceGame("someId");

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
