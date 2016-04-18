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
}
