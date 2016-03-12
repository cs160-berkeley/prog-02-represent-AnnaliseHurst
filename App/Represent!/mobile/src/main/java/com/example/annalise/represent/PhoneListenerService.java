package com.example.annalise.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String LOC = "/location";
    private static final String REP = "/rep";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(LOC) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Intent intent = new Intent(this, CongressionalView.class);
            intent.putExtra("LOCATION", "location");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            startActivity(intent);
            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions
        } else if( messageEvent.getPath().equalsIgnoreCase(REP) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Intent intent = new Intent(this, MoreInfo.class);
            intent.putExtra("REP", "rep");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}





//package com.example.annalise.represent;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.wearable.MessageEvent;
//import com.google.android.gms.wearable.WearableListenerService;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * Created by Annalise on 3/1/16.
// */
//public class PhoneListenerService extends WearableListenerService {
//
//    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
//    private static final String LOC = "/location";
//    private static final String REP = "/rep";
//
//    @Override
//    public void onMessageReceived(MessageEvent messageEvent) {
//        Log.d("flip", "in PhoneListenerService, got: " + messageEvent.getPath());
//        if( messageEvent.getPath().equalsIgnoreCase( LOC ) ) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, CongressionalView.class );
//            intent.putExtra("LOCATION", "location");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( REP )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MoreInfo.class );
//            intent.putExtra("REP", "rep");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            startActivity(intent);
//        } else {
//            super.onMessageReceived( messageEvent );
//        }
//
//    }
//}
