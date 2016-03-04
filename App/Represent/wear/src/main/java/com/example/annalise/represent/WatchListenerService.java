package com.example.annalise.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by Annalise on 3/1/16.
 */
public class WatchListenerService extends WearableListenerService {

    private static final String CURR = "/current";
    private static final String ZIP = "/zip";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( CURR ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Reps.class );
            intent.putExtra("LOCATION", "Alameda,California");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase( ZIP )) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Reps.class );
            intent.putExtra("LOCATION", "ZipCounty,ZipState");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
