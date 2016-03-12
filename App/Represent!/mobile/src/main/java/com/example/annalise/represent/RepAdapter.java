package com.example.annalise.represent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.service.media.MediaBrowserService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.*;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

/**
 * Created by Annalise on 2/29/16.
 */
public class RepAdapter extends BaseAdapter {

    private Context context = null;
    private String[] catNames = null;
    private String[] catFullness = null;
    private String[] email = null;
    private String[] web = null;
    private String[] tweethandle = null;
    private String[] tweet = null;
    private String[] id = null;
    private String[] date = null;
    private int[] catPhotos = null;

    public RepAdapter(Context context, String[] catNames, String[] catFullness, String[] email, String[] web, String[] id, String[] date, String[] tweethandle, String[] tweet, int[]

            catPhotos) {
        this.context = context;

        this.catNames = catNames;

        this.catFullness = catFullness;

        this.email = email;

        this.web = web;

        this.id = id;

        this.date = date;

        this.tweethandle = tweethandle;

        this.tweet = tweet;

        this.catPhotos = catPhotos;

    }

    @Override
    public int getCount() {
        return catNames.length;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View catRow = inflater.inflate(R.layout.replayout, parent, false);

        TextView nameView = (TextView) catRow.findViewById(R.id.name);

        TextView twithandView = (TextView) catRow.findViewById(R.id.twithand);

        TextView tweetView = (TextView) catRow.findViewById(R.id.tweet);

        ImageView pictureView = (ImageView) catRow.findViewById(R.id.repimage);

        ImageView frameView = (ImageView) catRow.findViewById(R.id.repframe);

        nameView.setText(catNames[position]);

        twithandView.setText(tweethandle[position]);
        tweetView.setText(tweet[position]);


//        String url1 = "congress.api.sunlightfoundation.com/committees?member_ids=" + id[position] + "&apikey=e016926691ab45d59aae8f34f88cf86e";
//        JsonObjectRequest jsObjRequest1 = new JsonObjectRequest
//                (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray mainObject = response.getJSONArray("results");
//                            Intent intent = new Intent(context, MoreInfo.class);
//                            intent.putExtra("COM", mainObject.toString());
//                        } catch (JSONException e) {
//                            Toast.makeText(context,
//                                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest1);


        catRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button eb = (Button) catRow.findViewById(R.id.email);
        eb.setTag(position);
        eb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(email[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        Button wb = (Button) catRow.findViewById(R.id.website);
        wb.setTag(position);
        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(web[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        Button mb = (Button) catRow.findViewById(R.id.moreinfo);
        mb.setTag(position);
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url2 = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=" + id[position] + "&apikey=e016926691ab45d59aae8f34f88cf86e";
                JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
                        (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    final JSONArray mainObject1 = response.getJSONArray("results");
//                                    Intent intent = new Intent(context, MoreInfo.class);
//                                    intent.putExtra("BILL", mainObject.toString());
////                                    Intent intent = new Intent(context, MoreInfo.class);
//                                    intent.putExtra("NAME", catNames[position]);
//                                    intent.putExtra("PARTY", catFullness[position]);
//                                    intent.putExtra("DATE", date[position]);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(intent);

                                    String url1 = "http://congress.api.sunlightfoundation.com/committees?member_ids=" + id[position] + "&apikey=e016926691ab45d59aae8f34f88cf86e";
                                    JsonObjectRequest jsObjRequest1 = new JsonObjectRequest
                                            (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        JSONArray mainObject = response.getJSONArray("results");
                                                        Intent intent = new Intent(context, MoreInfo.class);
                                                        intent.putExtra("COM", mainObject.toString());
//                                                        Intent intent = new Intent(context, MoreInfo.class);
                                                        intent.putExtra("BILL", mainObject1.toString());
//                                    Intent intent = new Intent(context, MoreInfo.class);
                                                        intent.putExtra("ID", id[position]);
                                                        intent.putExtra("NAME", catNames[position]);
                                                        intent.putExtra("PARTY", catFullness[position]);
                                                        intent.putExtra("DATE", date[position]);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        context.startActivity(intent);
                                                    } catch (JSONException e) {
                                                        Toast.makeText(context,
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
                                    MySingleton.getInstance(context).addToRequestQueue(jsObjRequest1);
                                } catch (JSONException e) {
                                    Toast.makeText(context,
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
                MySingleton.getInstance(context).addToRequestQueue(jsObjRequest2);
            }
        });

//        // TODO: Use a more specific parent
//        final ViewGroup parentView = (ViewGroup) catRow;
//        // TODO: Base this Tweet ID on some data from elsewhere in your app
//        long tweetId = 631879971628183552L;
//        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//                TweetView tweetView = new TweetView(RepAdapter.this, result.data);
//                parentView.addView(tweetView);
//            }
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d("TwitterKit", "Load Tweet failure", exception);
//            }
//        });


//        fullnessView.setText("Fullness: " + catFullness[position].toString());

//        pictureView.setImageResource(catPhotos[position]);
        Picasso.with(context).load("https://theunitedstates.io/images/congress/225x275/"+id[position]+".jpg").resize(275,275).into(pictureView);


        if (catFullness[position].equals("D")) {
            frameView.setImageResource(R.drawable.dframe);
        }
        if (catFullness[position].equals("R")) {
            frameView.setImageResource(R.drawable.rframe);
        }
        if (catFullness[position].equals("I")) {
            frameView.setImageResource(R.drawable.iframe);
        }

        return catRow;

    }
}
