package com.example.annalise.represent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
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

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MoreInfo extends AppCompatActivity {
    private String name = "Name";
    private String party = "Party";
    private String date = "Date";
    private String id;
    private String coms = "";
    private String bills = "";
    private static List<String> com1 = new ArrayList<String>();
    private static List<String> bil1 = new ArrayList<String>();
    private static List<String> bil2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String nameg = extras.getString("NAME");
            String pg = extras.getString("PARTY");
            String d = extras.getString("DATE");
            coms = extras.getString("COM");
            bills = extras.getString("BILL");
            id = extras.getString("ID");
            Log.d("T", "here"+(coms != null));
            Log.d("T", "hereb"+(bills != null));
            this.name = nameg;
            this.party = pg;
            this.date = d;
        } else {
            this.name = "Barbara Boxer";
            this.party = "Democratic";
            this.date = "End date of term";
        }
        if (coms != null) {
            try {
                JSONArray mainObject = new JSONArray(coms);
                for (int i = 0; i < mainObject.length(); i++) {
                    JSONObject jo_inside = mainObject.getJSONObject(i);
                    String name_value = jo_inside.getString("name");
                    Log.d("D",name_value);
                    this.com1.add(name_value);
                    Log.d("com1", this.com1.toString());

                }

            } catch (JSONException e) {
//                Toast.makeText(getApplicationContext(),
//                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (bills != null) {
            try {
                JSONArray mainObject = new JSONArray(bills);
                for (int i = 0; i < mainObject.length(); i++) {
                    JSONObject jo_inside = mainObject.getJSONObject(i);
                    String name_value = jo_inside.getString("official_title");
                    String date_value = jo_inside.getString("introduced_on");
                    Log.d("D",name_value);
                    Log.d("D",date_value);
                    this.bil1.add(name_value);
                    this.bil2.add("Introduced on " + date_value);
                    Log.d("bil", this.bil1.toString());
                    Log.d("bil", this.bil2.toString());
                }

            } catch (JSONException e) {
//                Toast.makeText(getApplicationContext(),
//                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(this.name);
        TextView part = (TextView) findViewById(R.id.party);
        if (this.party.equals("D")){
            part.setText("Democratic Party");
        }
        if (this.party.equals("R")){
            part.setText("Republican Party");
        }
        if (this.party.equals("I")){
            part.setText("Independent Party");
        }
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(this.date);

        ImageView im = (ImageView) findViewById(R.id.picture);
        im.setBackground(getDrawable(R.drawable.b));
        Picasso.with(this).load("https://theunitedstates.io/images/congress/225x275/"+id+".jpg").resize(275,275).into(im);

        TextView com = (TextView) findViewById(R.id.committees);
        TextView bills = (TextView) findViewById(R.id.bills);
        ImageView frame = (ImageView) findViewById(R.id.frame);
        if (this.party.equals("D")){
            com.setBackgroundColor(getResources().getColor(R.color.democrat));
            bills.setBackgroundColor(getResources().getColor(R.color.democrat));
            frame.setBackground(getDrawable(R.drawable.dframe));
        }
        if (this.party.equals("R")){
            com.setBackgroundColor(getResources().getColor(R.color.republican));
            bills.setBackgroundColor(getResources().getColor(R.color.republican));
            frame.setBackground(getDrawable(R.drawable.rframe));
        }
        if (this.party.equals("I")){
            com.setBackgroundColor(getResources().getColor(R.color.independent));
            bills.setBackgroundColor(getResources().getColor(R.color.independent));
            frame.setBackground(getDrawable(R.drawable.iframe));
        }

        final ListView billList = (ListView) findViewById(R.id.billview);
        String[] billNames = this.bil1.toArray(new String[0]);
        String[] billdates = this.bil2.toArray(new String[0]);
//        String[] billNames = {"Bill 1", "Bill 2", "Bill 3"};
//        String[] billdates = {"Date Introduced", "Date Introduced", "Date Introduced"};

        //Create the Adapter

        final BillAdapter billadapter = new BillAdapter(this, billNames, billdates);

        //Set the Adapter

        billList.setAdapter(billadapter);


        final ListView ComList = (ListView) findViewById(R.id.comview);
//        String[] comNames = {"Committee 1", "Committee 2", "Committee 3", "Committee 4"};
        String[] comNames = this.com1.toArray(new String[0]);
        //Create the Adapter

        final CommitteAdapter comadapter = new CommitteAdapter(this, comNames);

        //Set the Adapter

        ComList.setAdapter(comadapter);
    }

}
