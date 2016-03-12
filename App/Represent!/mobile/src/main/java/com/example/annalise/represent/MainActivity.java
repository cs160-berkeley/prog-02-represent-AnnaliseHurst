package com.example.annalise.represent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TWITTER_KEY = "U1qT6EgY4kR9cgFeMUPXHFtOa";
    private static final String TWITTER_SECRET = "6N7OlWO3gIeXAc3AGRXrKAKsaHsQ0kdZ27FIS1YjWart2BQkzr";
    private GoogleApiClient mGoogleApiClient;
    private String zipcode;
    private String state;
    private String county;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create an instance of GoogleAPIClient.

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getBaseContext(), new Twitter(authConfig));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        Typeface robm = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        Typeface robr = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        TextView welcometorep = (TextView) findViewById(R.id.welcometorep);
        welcometorep.setTypeface(robm);
        TextView repinfo = (TextView) findViewById(R.id.repinfo);
        repinfo.setTypeface(robr);
        TextView zipcode = (TextView) findViewById(R.id.zipcode);
        zipcode.setTypeface(robr);
        Button curlocation = (Button) findViewById(R.id.curlocation);
        curlocation.setTypeface(robr);

        final EditText ezipcode = (EditText) findViewById(R.id.zipcode);

        ezipcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    zip_send((View) ezipcode);
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        final Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            final MainActivity _this = this;
            String urlg = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+String.valueOf(mLastLocation.getLatitude())+","+String.valueOf(mLastLocation.getLongitude())+"&key=AIzaSyDE2kpcBXFmcoKAuDstJVbrori046QaNHg";
            JsonObjectRequest jsObjRequestg = new JsonObjectRequest
                    (Request.Method.GET, urlg, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray mainObject = response.getJSONArray("results");
                                String statein = "";
                                String countyin = "";
                                JSONObject insid = mainObject.getJSONObject(0);
                                JSONArray adder = insid.getJSONArray("address_components");
                                for (int x = 0; x < adder.length(); x++) {
                                    JSONObject each =adder.getJSONObject(x);
                                    String name = each.getString("long_name");
                                    JSONArray type = each.getJSONArray("types");
//                                List<String> ty = new ArrayList<String>();
                                    for (int j = 0; j < type.length(); j++) {
//                                    ty.add(type.getString(j));
                                        String type2 = type.getString(j);
                                        if (type2.equals("locality")) {
                                            countyin = name;
                                        } else if (type2.equals("administrative_area_level_1")) {
                                            statein = name;
                                        }
                                    }
                                }
                                final String count = countyin;
                                final String stat = statein;




                                String url = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude="+String.valueOf(mLastLocation.getLatitude())+"&longitude="+String.valueOf(mLastLocation.getLongitude())+"&apikey=e016926691ab45d59aae8f34f88cf86e";
                                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONArray mainObject = response.getJSONArray("results");
                                                    Log.d("T", "made");
                                                    List<String> n = new ArrayList<String>();
                                                    List<String> p = new ArrayList<String>();
                                                    for (int i = 0; i < mainObject.length(); i++) {
                                                        JSONObject jo_inside = mainObject.getJSONObject(i);
                                                        String first_value = jo_inside.getString("first_name");
                                                        String last_value = jo_inside.getString("last_name");
                                                        String party_value = jo_inside.getString("party");
                                                        String chamber_value = jo_inside.getString("chamber");
                                                        if (chamber_value.equals("senate")) {
                                                            chamber_value = "Senator";
                                                        } else if (chamber_value.equals("house")) {
                                                            chamber_value = "Representative";
                                                        }
                                                        n.add(chamber_value + " " + first_value + " " + last_value);
                                                        p.add(party_value);
                                                    }
                                                    Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                                                    Intent intent = new Intent(getBaseContext(), CongressionalView.class);
                                                    intent.putExtra("ZIP", mainObject.toString());
                                                    startActivity(intent);
                                                    sendIntent.putExtra("NAME", n.toString().replace("[", "").replace("]", ""));
                                                    sendIntent.putExtra("PARTY", p.toString().replace("[", "").replace("]", ""));
                                                    sendIntent.putExtra("LOCATION", "current");
                                                    sendIntent.putExtra("STATE", stat);
                                                    sendIntent.putExtra("COUNTY", count);
                                                    sendIntent.putExtra("DATA", mainObject.toString());
                                                    startService(sendIntent);
                                                } catch (JSONException e) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO Auto-generated method stub

                                            }
                                        });

                                // Access the RequestQueue through your singleton class.
                                MySingleton.getInstance(_this).addToRequestQueue(jsObjRequest);


//                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                            sendIntent.putExtra("LOCATION", "current");
//                            sendIntent.putExtra("STATE", statein);
//                            sendIntent.putExtra("COUNTY", countyin);
//                            sendIntent.putExtra("DATA", mainObject.toString());
//                            startService(sendIntent);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsObjRequestg);



//            String url = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude="+String.valueOf(mLastLocation.getLatitude())+"&longitude="+String.valueOf(mLastLocation.getLongitude())+"&apikey=e016926691ab45d59aae8f34f88cf86e";
//            JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                JSONArray mainObject = response.getJSONArray("results");
//                                Log.d("T", "made");
//                                List<String> n = new ArrayList<String>();
//                                List<String> p = new ArrayList<String>();
//                                for (int i = 0; i < mainObject.length(); i++) {
//                                    JSONObject jo_inside = mainObject.getJSONObject(i);
//                                    String first_value = jo_inside.getString("first_name");
//                                    String last_value = jo_inside.getString("last_name");
//                                    String party_value = jo_inside.getString("party");
//                                    String chamber_value = jo_inside.getString("chamber");
//                                    if (chamber_value.equals("senate")) {
//                                        chamber_value = "Senator";
//                                    } else if (chamber_value.equals("house")) {
//                                        chamber_value = "Representative";
//                                    }
//                                    n.add(chamber_value + " " + first_value + " " + last_value);
//                                    p.add(party_value);
//                                }
//                                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                                Intent intent = new Intent(getBaseContext(), CongressionalView.class);
//                                intent.putExtra("ZIP", mainObject.toString());
//                                startActivity(intent);
//                                sendIntent.putExtra("NAME", n.toString().replace("[", "").replace("]", ""));
//                                sendIntent.putExtra("PARTY", p.toString().replace("[", "").replace("]", ""));
//                                sendIntent.putExtra("LOCATION", "current");
//                                sendIntent.putExtra("STATE", stat);
//                                sendIntent.putExtra("COUNTY", count);
//                                sendIntent.putExtra("DATA", mainObject.toString());
//                                startService(sendIntent);
//                            } catch (JSONException e) {
//                                Toast.makeText(getApplicationContext(),
//                                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // TODO Auto-generated method stub
//                        }
//                    });
//
//            // Access the RequestQueue through your singleton class.
//            MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
//
//
//            String urlg = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+String.valueOf(mLastLocation.getLatitude())+","+String.valueOf(mLastLocation.getLongitude())+"&key=AIzaSyDE2kpcBXFmcoKAuDstJVbrori046QaNHg";
//            JsonObjectRequest jsObjRequestg = new JsonObjectRequest
//                    (Request.Method.GET, urlg, null, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                JSONArray mainObject = response.getJSONArray("results");
//                                String statein = "";
//                                String countyin = "";
//                                JSONObject insid = mainObject.getJSONObject(0);
//                                JSONArray adder = insid.getJSONArray("address_components");
//                                for (int x = 0; x < adder.length(); x++) {
//                                    JSONObject each =adder.getJSONObject(x);
//                                    String name = each.getString("long_name");
//                                    JSONArray type = each.getJSONArray("types");
////                                List<String> ty = new ArrayList<String>();
//                                    for (int j = 0; j < type.length(); j++) {
////                                    ty.add(type.getString(j));
//                                        String type2 = type.getString(j);
//                                        if (type2.equals("locality")) {
//                                            countyin = name;
//                                        } else if (type2.equals("administrative_area_level_1")) {
//                                            statein = name;
//                                        }
//                                    }
//                                }
//                                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                                sendIntent.putExtra("LOCATION", "current");
//                                sendIntent.putExtra("STATE", statein);
//                                sendIntent.putExtra("COUNTY", countyin);
////                                sendIntent.putExtra("DATA", mainObject.toString());
//                                startService(sendIntent);
//                            } catch (JSONException e) {
//                                Toast.makeText(getApplicationContext(),
//                                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // TODO Auto-generated method stub
//
//                        }
//                    });
//
//            // Access the RequestQueue through your singleton class.
//            MySingleton.getInstance(this).addToRequestQueue(jsObjRequestg);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showreps(View view) {
        mGoogleApiClient.connect();
    }

    public void zip_send(View view) {
        EditText et = (EditText) findViewById(R.id.zipcode);
        final String s = et.getText().toString();

        final MainActivity _this = this;
        String urlg = "https://maps.googleapis.com/maps/api/geocode/json?address="+s+"&key=AIzaSyDE2kpcBXFmcoKAuDstJVbrori046QaNHg";
        JsonObjectRequest jsObjRequestg = new JsonObjectRequest
                (Request.Method.GET, urlg, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray mainObject = response.getJSONArray("results");
                            String statein = "";
                            String countyin = "";
                            JSONObject insid = mainObject.getJSONObject(0);
                            JSONArray adder = insid.getJSONArray("address_components");
                            for (int x = 0; x < adder.length(); x++) {
                                JSONObject each =adder.getJSONObject(x);
                                String name = each.getString("long_name");
                                JSONArray type = each.getJSONArray("types");
//                                List<String> ty = new ArrayList<String>();
                                for (int j = 0; j < type.length(); j++) {
//                                    ty.add(type.getString(j));
                                    String type2 = type.getString(j);
                                    if (type2.equals("locality")) {
                                        countyin = name;
                                    } else if (type2.equals("administrative_area_level_1")) {
                                        statein = name;
                                    }
                                }
                            }
                            final String count = countyin;
                            final String stat = statein;




                            String url = "http://congress.api.sunlightfoundation.com/legislators/locate?zip="+s+"&apikey=e016926691ab45d59aae8f34f88cf86e";
                            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                JSONArray mainObject = response.getJSONArray("results");
                                                Log.d("T", "made");
                                                List<String> n = new ArrayList<String>();
                                                List<String> p = new ArrayList<String>();
                                                for (int i = 0; i < mainObject.length(); i++) {
                                                    JSONObject jo_inside = mainObject.getJSONObject(i);
                                                    String first_value = jo_inside.getString("first_name");
                                                    String last_value = jo_inside.getString("last_name");
                                                    String party_value = jo_inside.getString("party");
                                                    String chamber_value = jo_inside.getString("chamber");
                                                    if (chamber_value.equals("senate")) {
                                                        chamber_value = "Senator";
                                                    } else if (chamber_value.equals("house")) {
                                                        chamber_value = "Representative";
                                                    }
                                                    n.add(chamber_value + " " + first_value + " " + last_value);
                                                    p.add(party_value);
                                                }
                                                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                                                Intent intent = new Intent(getBaseContext(), CongressionalView.class);
                                                intent.putExtra("ZIP", mainObject.toString());
                                                startActivity(intent);
                                                sendIntent.putExtra("NAME", n.toString().replace("[", "").replace("]", ""));
                                                sendIntent.putExtra("PARTY", p.toString().replace("[", "").replace("]", ""));
                                                sendIntent.putExtra("LOCATION", "current");
                                                sendIntent.putExtra("STATE", stat);
                                                sendIntent.putExtra("COUNTY", count);
                                                sendIntent.putExtra("DATA", mainObject.toString());
                                                startService(sendIntent);
                                            } catch (JSONException e) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO Auto-generated method stub

                                        }
                                    });

                            // Access the RequestQueue through your singleton class.
                            MySingleton.getInstance(_this).addToRequestQueue(jsObjRequest);


//                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                            sendIntent.putExtra("LOCATION", "current");
//                            sendIntent.putExtra("STATE", statein);
//                            sendIntent.putExtra("COUNTY", countyin);
//                            sendIntent.putExtra("DATA", mainObject.toString());
//                            startService(sendIntent);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequestg);

    }

}
