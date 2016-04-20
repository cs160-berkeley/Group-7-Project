package cs160.autismbuddie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhoneToWatchUtil implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

    private static GoogleApiClient mApiClient;
    private List<Node> nodes = new ArrayList<>();
    private String currentPath, currentMessage;
    public static String PATH_SEND_FACES = "/faces", PATH_SEND_TRIVIA = "/trivia"
            , PATH_SEND_REMINDER = "/reminder", PATH_SEND_MODE = "/mode"
            , MODE_FREE = "free", MODE_RESTRICTED ="restricted";

    public PhoneToWatchUtil(Context context)
    {
        if(mApiClient == null || !mApiClient.isConnected())
        {
            mApiClient = new GoogleApiClient.Builder(context)
                    .addApi( Wearable.API )
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId("myAppId")
                .clientKey("myClientKey")
                .server("https://abuddie.herokuapp.com/parse/")
                .enableLocalDataStore()
                .build());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(Utils.TAG, "Sending to watch with path: " + currentPath + " and message: " + currentMessage);
        Wearable.NodeApi.getConnectedNodes(mApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        if(nodes == null || nodes.size() < 1)
                        {
                            Log.d(Utils.TAG, "Watch is not connected!");
                            return;
                        }
                        for (Node node : nodes)
                        {
                            Wearable.MessageApi.sendMessage(
                                    mApiClient, node.getId(), currentPath, currentMessage.getBytes());
                            Log.d(Utils.TAG, "Sent from MainActivity: " + currentPath + " " + currentMessage);
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Utils.TAG, "mobile/MainActivity, could not connect: " + connectionResult.getErrorMessage());
    }

    public void disconnect()
    {
        if(mApiClient != null)
            mApiClient.disconnect();
    }

    public void sendMessage(String path, String message)
    {
        currentPath = path;
        currentMessage = message;
        if(mApiClient != null)
        {
            mApiClient.disconnect();
            mApiClient.connect();
        }
    }

    public JSONObject getTriviaGame(String id)
    {
        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("TriviaGame");
        List<ParseObject> triviaGames;
        try {
            triviaGames = q.find();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }

        for(ParseObject parseObject: triviaGames)
        {   // TODO: Pick the right game based off of id
            id = parseObject.getObjectId();
            if(parseObject.getObjectId().equalsIgnoreCase(id))
            {
                List<String> sFacts = parseObject.getList("facts");
                String homeImgUrl = getImageUrl(parseObject, "homeImg");
                String backgroundUrl = getImageUrl(parseObject, "backgroundImg");
                JSONObject result = new JSONObject();
                JSONArray jFacts = new JSONArray(sFacts);
                String[] tutorialUrls =  {getImageUrl(parseObject, "tutorial1")
                        , getImageUrl(parseObject, "tutorial2")
                        ,getImageUrl(parseObject, "tutorial3")};
                try {
                    result.put("facts", jFacts);
                    result.put("homeImgUrl", homeImgUrl);
                    result.put("backgroundUrl", backgroundUrl);
                    result.put("tutorialUrls", new JSONArray(tutorialUrls));
                    Log.d(Utils.TAG, "Trivia JSON: " + result.toString());
                    return result;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public JSONObject getFaceGame(String id)
    {
        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("FaceGame");
        List<ParseObject> faceGames;
        try {
            faceGames = q.find();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }

        for(ParseObject parseObject: faceGames)
        {   // TODO: Pick the right game based off of id
            id = parseObject.getObjectId();
            if(parseObject.getObjectId().equalsIgnoreCase(id))
            {
                List<String> sAnswers = parseObject.getList("answers");
                String[] faceUrls =  {getImageUrl(parseObject, "face1")
                , getImageUrl(parseObject, "face2")
                ,getImageUrl(parseObject, "face3")
                ,getImageUrl(parseObject, "face4")
                ,getImageUrl(parseObject, "face5")};

                try {
                    JSONObject result = new JSONObject();
                    JSONArray jFaces = new JSONArray(faceUrls);
                    JSONArray jAnswers = new JSONArray(sAnswers);
                    result.put("answers", jAnswers);
                    result.put("faceUrls", jFaces);
                    Log.d(Utils.TAG, "Face JSON: " + result.toString());
                    return result;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public static String getImageUrl(ParseObject image, String key) {
        if(image != null) {
            try {
                image.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(image != null && image.getParseFile(key) != null)
            return image.getParseFile(key).getUrl();
        return "http://dealon.herokuapp.com/default.png";
    }
}
