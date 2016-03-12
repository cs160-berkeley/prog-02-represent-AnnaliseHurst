package com.example.annalise.represent;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.AccountService;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;

public class CongressionalView extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    private String loc = "mobile";
    private static String jsonCurr = "";
//    private String zipcode = "Zip Code";
    private String name1 = "Name";
    private String name2 = "Name";
    private String name = "Name";
    private static List<String> snl = new ArrayList<String>();
    private static List<String> spl = new ArrayList<String>();
    private static List<String> sel = new ArrayList<String>();
    private static List<String> swl = new ArrayList<String>();
    private static List<String> stl = new ArrayList<String>();
    private static List<String> rtl = new ArrayList<String>();
    private static List<String> stwl = new ArrayList<String>();
    private static List<String> rtwl = new ArrayList<String>();
    private static List<String> sidl = new ArrayList<String>();
    private static List<String> ridl = new ArrayList<String>();
    private static List<String> rnl = new ArrayList<String>();
    private static List<String> sdl = new ArrayList<String>();
    private static List<String> rdl = new ArrayList<String>();
    private static List<String> rpl = new ArrayList<String>();
    private static List<String> rel = new ArrayList<String>();
    private static List<String> rwl = new ArrayList<String>();
    private TwitterApiClient myTwitterApiClient;
    String tweetf = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String locName = extras.getString("LOCATION");
            jsonCurr = extras.getString("ZIP");
            loc = locName;
        }

        new RestOperation().execute();
//        try {
//            JSONArray mainObject = new JSONArray(jsonCurr);
//            Log.d("T", "made");
//            final List<String> stli = new ArrayList<String>();
//            final List<String> rtli = new ArrayList<String>();
//            for (int i = 0; i < mainObject.length(); i++) {
//                JSONObject jo_inside = mainObject.getJSONObject(i);
//                String first_value = jo_inside.getString("first_name");
//                String last_value = jo_inside.getString("last_name");
//                final String twith_value = jo_inside.getString("twitter_id");
//                String party_value = jo_inside.getString("party");
//                String website_value = jo_inside.getString("website");
//                String id_value = jo_inside.getString("bioguide_id");
//                String conatact_value = jo_inside.getString("contact_form");
//                String date_value = jo_inside.getString("term_end");
//                final String chamber_value = jo_inside.getString("chamber");
//                if (chamber_value.equals("senate")) {
//                    this.name1 = first_value;
//                    this.snl.add(first_value+" "+last_value);
//                    this.spl.add(party_value);
//                    this.sel.add(conatact_value);
//                    this.swl.add(website_value);
//                    this.sidl.add(id_value);
//                    this.sdl.add("Term ends "+date_value);
//                    this.stwl.add("@"+twith_value.toLowerCase());
//                }
//                if (chamber_value.equals("house")) {
//                    this.name2 = first_value;
//                    this.rnl.add(first_value+" "+last_value);
//                    this.rpl.add(party_value);
//                    this.rel.add(conatact_value);
//                    this.rwl.add(website_value);
//                    this.ridl.add(id_value);
//                    this.rdl.add("Term ends "+date_value);
//                    this.rtwl.add("@"+twith_value.toLowerCase());
//                }
//                TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//                Fabric.with(this, new Twitter(authConfig));
//                final CongressionalView _this = this;
//                TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
//                    @Override
//                    public void success(Result<AppSession> appSessionResult) {
//                        AppSession session = appSessionResult.data;
//                        myTwitterApiClient = TwitterCore.getInstance().getApiClient(session);
//                        myTwitterApiClient.getStatusesService().userTimeline(null, twith_value, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
//                            @Override
//                            public void success(Result<List<Tweet>> result) {
//                                if (chamber_value.equals("senate")) {
//                                    String tweet = result.data.get(0).text;
//                                    stli.add(tweet);
//                                    Log.d("T", "hi2" + stli.toString());
//                                }
//                                if (chamber_value.equals("house")) {
//                                    String tweet = result.data.get(0).text;
//                                    rtli.add(tweet);
//                                }
//                            }
//
//                            @Override
//                            public void failure(TwitterException e) {
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        e.printStackTrace();
//                    }
//                });
//                Log.d("T", (this.snl).toString());
//
//            }
//            this.stl = stli;
//            Log.d("T", "hi2" + stli.toString());
//            this.rtl = rtli;
//
//        } catch (JSONException e) {
//            Toast.makeText(getApplicationContext(),
//                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }


//        final ListView repList = (ListView) findViewById(R.id.replistview1);
////        String[] repNames = {"Barbara Boxer", name1};
//        String[] repNames = snl.toArray(new String[0]);
//
////        String[] catFullness = {"D", "R"};
//        String[] catFullness = spl.toArray(new String[0]);
//
//        String[] email = sel.toArray(new String[0]);
//
//        String[] web = swl.toArray(new String[0]);
//
//        String[] id = sidl.toArray(new String[0]);
//
//        String[] date = sdl.toArray(new String[0]);
//
////        String[] tweethandle = {"@SENATORBOXER", "@"};
//        String[] tweethandle = stwl.toArray(new String[0]);
//
//        Log.d("T", "hi"+this.tweetf);
//        String[] tweet = {this.tweetf, "Lorem ipsum dolor sit amet, adipiscing \n" +
//                "#dolore #magna #aliqua #Ut"};
//
//        int[] catPhotos = {R.drawable.b, R.drawable.b};
//
//        //Create the Adapter
//
//        final RepAdapter adapter = new RepAdapter(this, repNames, catFullness, email, web, id, date, tweethandle, tweet,
//
//                catPhotos);
//
//        //Set the Adapter
//
//        repList.setAdapter(adapter);
//
//        final ListView repList2 = (ListView) findViewById(R.id.replistview2);
////        String[] repNames2 = (String[]) rnl.toArray();
//        String[] repNames2 = {"Barbara Boxer", name2};
//
//        String[] catFullness2 = {"I", "R"};
//
//        String[] email2 = {"",""};
//
//        String[] web2 = {"",""};
//
//        String[] id2 = sidl.toArray(new String[0]);
//
//        String[] date2 = sdl.toArray(new String[0]);
//
//        String[] tweethandle2 = {"@TweetHandle", "@TweetHandle"};
//
//        String[] tweet2 = {"Lorem ipsum dolor sit amet, adipiscing \n" +
//                "#dolore #magna #aliqua #Ut", "Lorem ipsum dolor sit amet, adipiscing \n" +
//                "#dolore #magna #aliqua #Ut"};
//
//        int[] catPhotos2 = {R.drawable.b, R.drawable.b};
//
//        //Create the Adapter
//
//        final RepAdapter adapter2 = new RepAdapter(this, repNames2, catFullness2, email2, web2, id2, date2, tweethandle2, tweet2,
//
//                catPhotos2);
//
//        //Set the Adapter
//
//        repList2.setAdapter(adapter2);
//
//        TextView uss = (TextView) findViewById(R.id.ussenators);
//        if (catFullness[0].equals("D")){
//            uss.setBackgroundColor(getResources().getColor(R.color.democrat));
//        }
//        if (catFullness[0].equals("R")){
//            uss.setBackgroundColor(getResources().getColor(R.color.republican));
//        }
//        if (catFullness[0].equals("I")){
//            uss.setBackgroundColor(getResources().getColor(R.color.independent));
//        }
//
//        TextView hr = (TextView) findViewById(R.id.housereps);
//        if (catFullness2[0].equals("D")){
//            hr.setBackgroundColor(getResources().getColor(R.color.democrat));
//        }
//        if (catFullness2[0].equals("R")){
//            hr.setBackgroundColor(getResources().getColor(R.color.republican));
//        }
//        if (catFullness2[0].equals("I")){
//            hr.setBackgroundColor(getResources().getColor(R.color.independent));
//        }


    }



    private class RestOperation extends AsyncTask<String, Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(CongressionalView.this);
        String data = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Please wait ..");
            progressDialog.show();
            try {
                data += "&" + URLEncoder.encode("data", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                JSONArray mainObject = new JSONArray(jsonCurr);
                Log.d("T", "made");
                final List<String> stli = new ArrayList<String>();
                final List<String> rtli = new ArrayList<String>();
                for (int i = 0; i < mainObject.length(); i++) {
                    JSONObject jo_inside = mainObject.getJSONObject(i);
                    String first_value = jo_inside.getString("first_name");
                    String last_value = jo_inside.getString("last_name");
                    final String twith_value = jo_inside.getString("twitter_id");
                    String party_value = jo_inside.getString("party");
                    String website_value = jo_inside.getString("website");
                    String id_value = jo_inside.getString("bioguide_id");
                    String conatact_value = jo_inside.getString("contact_form");
                    String date_value = jo_inside.getString("term_end");
                    final String chamber_value = jo_inside.getString("chamber");
                    if (chamber_value.equals("senate")) {
                        name1 = first_value;
                        snl.add(first_value+" "+last_value);
                        spl.add(party_value);
                        sel.add(conatact_value);
                        swl.add(website_value);
                        sidl.add(id_value);
                        sdl.add("Term ends "+date_value);
                        stwl.add("@"+twith_value.toLowerCase());
                    }
                    if (chamber_value.equals("house")) {
                        name2 = first_value;
                        rnl.add(first_value+" "+last_value);
                        rpl.add(party_value);
                        rel.add(conatact_value);
                        rwl.add(website_value);
                        ridl.add(id_value);
                        rdl.add("Term ends "+date_value);
                        rtwl.add("@"+twith_value.toLowerCase());
                    }
//                    final CongressionalView _this = getBaseContext();
                    TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
                        @Override
                        public void success(Result<AppSession> appSessionResult) {
                            AppSession session = appSessionResult.data;
                            myTwitterApiClient = TwitterCore.getInstance().getApiClient(session);
                            myTwitterApiClient.getStatusesService().userTimeline(null, twith_value, 1, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                                @Override
                                public void success(Result<List<Tweet>> result) {
                                    if (chamber_value.equals("senate")) {
                                        String tweet = result.data.get(0).text;
                                        stli.add(tweet);
                                        Log.d("T", "hi2" + stli.toString());
                                    }
                                    if (chamber_value.equals("house")) {
                                        String tweet = result.data.get(0).text;
                                        rtli.add(tweet);
                                    }
                                }

                                @Override
                                public void failure(TwitterException e) {
                                }
                            });
                        }

                        @Override
                        public void failure(TwitterException e) {
                            e.printStackTrace();
                        }
                    });
                }
                stl = stli;
                Log.d("T", "hi2" + stli.toString());
                rtl = rtli;

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),
                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            final ListView repList = (ListView) findViewById(R.id.replistview1);
//        String[] repNames = {"Barbara Boxer", name1};
            String[] repNames = snl.toArray(new String[0]);

//        String[] catFullness = {"D", "R"};
            String[] catFullness = spl.toArray(new String[0]);

            String[] email = sel.toArray(new String[0]);

            String[] web = swl.toArray(new String[0]);

            String[] id = sidl.toArray(new String[0]);

            String[] date = sdl.toArray(new String[0]);

//        String[] tweethandle = {"@SENATORBOXER", "@"};
            String[] tweethandle = stwl.toArray(new String[0]);

            Log.d("T", "hi"+tweetf);
            String[] tweet = {tweetf, "Lorem ipsum dolor sit amet, adipiscing \n" +
                    "#dolore #magna #aliqua #Ut"};

            int[] catPhotos = {R.drawable.b, R.drawable.b};

            //Create the Adapter

            final RepAdapter adapter = new RepAdapter(getBaseContext(), repNames, catFullness, email, web, id, date, tweethandle, tweet,

                    catPhotos);

            //Set the Adapter

            repList.setAdapter(adapter);

            final ListView repList2 = (ListView) findViewById(R.id.replistview2);
//        String[] repNames2 = (String[]) rnl.toArray();
            String[] repNames2 = {"Barbara Boxer", name2};

            String[] catFullness2 = {"I", "R"};

            String[] email2 = {"",""};

            String[] web2 = {"",""};

            String[] id2 = sidl.toArray(new String[0]);

            String[] date2 = sdl.toArray(new String[0]);

            String[] tweethandle2 = {"@TweetHandle", "@TweetHandle"};

            String[] tweet2 = {"Lorem ipsum dolor sit amet, adipiscing \n" +
                    "#dolore #magna #aliqua #Ut", "Lorem ipsum dolor sit amet, adipiscing \n" +
                    "#dolore #magna #aliqua #Ut"};

            int[] catPhotos2 = {R.drawable.b, R.drawable.b};

            //Create the Adapter

            final RepAdapter adapter2 = new RepAdapter(getBaseContext(), repNames2, catFullness2, email2, web2, id2, date2, tweethandle2, tweet2,

                    catPhotos2);

            //Set the Adapter

            repList2.setAdapter(adapter2);

            TextView uss = (TextView) findViewById(R.id.ussenators);
            if (catFullness[0].equals("D")){
                uss.setBackgroundColor(getResources().getColor(R.color.democrat));
            }
            if (catFullness[0].equals("R")){
                uss.setBackgroundColor(getResources().getColor(R.color.republican));
            }
            if (catFullness[0].equals("I")){
                uss.setBackgroundColor(getResources().getColor(R.color.independent));
            }

            TextView hr = (TextView) findViewById(R.id.housereps);
            if (catFullness2[0].equals("D")){
                hr.setBackgroundColor(getResources().getColor(R.color.democrat));
            }
            if (catFullness2[0].equals("R")){
                hr.setBackgroundColor(getResources().getColor(R.color.republican));
            }
            if (catFullness2[0].equals("I")){
                hr.setBackgroundColor(getResources().getColor(R.color.independent));
            }

        }
    }

}
