package com.example.annalise.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class WatchToPhoneService extends Service {
    final Service _this = this;
    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
        //and actually connect it
        mWatchApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();
        final String location = extras.getString("WEAR");

        // Send the message with the cat name
        new Thread(new Runnable() {
            @Override
            public void run() {
                //first, connect to the apiclient
                mWatchApiClient.connect();
                //now that you're connected, send a massage with the cat name
                Log.d("T", "sending");
                sendMessage("/" + location, location);
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage(final String path, final String text ) {
        Log.d("T", "inside");
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        for (Node node : nodes) {
                            Log.d("T", "sent");
                            Log.d("T", text);
                            Wearable.MessageApi.sendMessage(
                                    mWatchApiClient, node.getId(), path, text.getBytes());
                        }
                        _this.stopSelf();
                    }
                });
//        for (Node node : nodes) {
//            Log.d("T", "sent");
//            Wearable.MessageApi.sendMessage(
//                    mWatchApiClient, node.getId(), path, text.getBytes());
//        }
    }

}




//package com.example.annalise.represent;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.wearable.Node;
//import com.google.android.gms.wearable.NodeApi;
//import com.google.android.gms.wearable.Wearable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {
//    final Service _this = this;
//    private GoogleApiClient mWatchApiClient;
//    private List<Node> nodes = new ArrayList<>();
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //initialize the googleAPIClient for message passing
//        mWatchApiClient = new GoogleApiClient.Builder( this )
//                .addApi( Wearable.API )
//                .addConnectionCallbacks(this)
//                .build();
//        //and actually connect it
//        mWatchApiClient.connect();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mWatchApiClient.disconnect();
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
//    public void onConnected(Bundle bundle) {
//        Log.d("T", "in onconnected");
//        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
//                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//                    @Override
//                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
//                        nodes = getConnectedNodesResult.getNodes();
//                        Log.d("T", "found nodes");
//                        //when we find a connected node, we populate the list declared above
//                        //finally, we can send a message
//                        sendMessage("/send_toast", "Good job!");
//                        Log.d("T", "sent");
//                        _this.stopSelf();
//                    }
//                });
//    }
//
//    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
//    public void onConnectionSuspended(int i) {}
//
//    private void sendMessage(final String path, final String text ) {
//        for (Node node : nodes) {
//            Wearable.MessageApi.sendMessage(
//                    mWatchApiClient, node.getId(), path, text.getBytes());
//        }
//    }
//
//}











//package com.example.annalise.represent;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.wearable.Node;
//import com.google.android.gms.wearable.NodeApi;
//import com.google.android.gms.wearable.Wearable;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Annalise on 3/1/16.
// */
//public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {
//    final Service _this = this;
//    private GoogleApiClient mWatchApiClient;
//    private List<Node> nodes = new ArrayList<>();
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //initialize the googleAPIClient for message passing
//        mWatchApiClient = new GoogleApiClient.Builder( this )
//                .addApi( Wearable.API )
//                .addConnectionCallbacks(this)
//                .build();
//        //and actually connect it
//        mWatchApiClient.connect();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Which cat do we want to feed? Grab this info from INTENT
//        // which was passed over when we called startService
//        Bundle extras = intent.getExtras();
//        final String location = extras.getString("WEAR");
//
//        // Send the message with the cat name
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //first, connect to the apiclient
//                mWatchApiClient.connect();
//                //now that you're connected, send a massage with the cat name
//                sendMessage("/" + location, location);
//                _this.stopSelf();
//            }
//        }).start();
//
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mWatchApiClient.disconnect();
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
//    public void onConnected(Bundle bundle) {
//        _this.stopSelf();
//    }
//
//    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
//    public void onConnectionSuspended(int i) {}
//
//    private void sendMessage(final String path, final String text ) {
//        for (Node node : nodes) {
//            Wearable.MessageApi.sendMessage(
//                    mWatchApiClient, node.getId(), path, text.getBytes());
//        }
//    }
//
//}
