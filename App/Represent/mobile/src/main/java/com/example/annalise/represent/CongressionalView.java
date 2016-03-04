package com.example.annalise.represent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
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

public class CongressionalView extends AppCompatActivity {
    private String loc = "mobile";

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
            loc = locName;
        }



        Typeface robm = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        Typeface robr = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
//        TextView ussenators = (TextView) findViewById(R.id.ussenators);
//        ussenators.setTypeface(robr);
//        TextView name1 = (TextView) findViewById(R.id.name1);
//        name1.setTypeface(robr);
//        TextView twithand1 = (TextView) findViewById(R.id.twithand1);
//        twithand1.setTypeface(robr);
//        TextView tweet1 = (TextView) findViewById(R.id.tweet1);
//        tweet1.setTypeface(robr);
//        TextView time1 = (TextView) findViewById(R.id.time1);
//        time1.setTypeface(robr);
//        Button email1 = (Button) findViewById(R.id.email1);
//        email1.setTypeface(robr);
//        Button website1 = (Button) findViewById(R.id.website1);
//        website1.setTypeface(robr);
//        Button moreinfo1 = (Button) findViewById(R.id.moreinfo1);
//        moreinfo1.setTypeface(robr);

        final ListView repList = (ListView) findViewById(R.id.replistview1);
        String[] repNames = {"Barbara Boxer", "Name"};

        String[] catFullness = {"Democratic", "Republican"};

        String[] tweethandle = {"@SENATORBOXER", "@TweetHandle"};

        String[] tweet = {"Lorem ipsum dolor sit amet, adipiscing \n" +
                "#dolore #magna #aliqua #Ut", "Lorem ipsum dolor sit amet, adipiscing \n" +
                "#dolore #magna #aliqua #Ut"};

        int[] catPhotos = {R.drawable.b, R.drawable.b};

        //Create the Adapter

        final RepAdapter adapter = new RepAdapter(this, repNames, catFullness, tweethandle, tweet,

                catPhotos);

        //Set the Adapter

        repList.setAdapter(adapter);

        final ListView repList2 = (ListView) findViewById(R.id.replistview2);
        String[] repNames2 = {"Barbara Boxer", "Name"};

        String[] catFullness2 = {"Independent", "Republican"};

        String[] tweethandle2 = {"@TweetHandle", "@TweetHandle"};

        String[] tweet2 = {"Lorem ipsum dolor sit amet, adipiscing \n" +
                "#dolore #magna #aliqua #Ut", "Lorem ipsum dolor sit amet, adipiscing \n" +
                "#dolore #magna #aliqua #Ut"};

        int[] catPhotos2 = {R.drawable.b, R.drawable.b};

        //Create the Adapter

        final RepAdapter adapter2 = new RepAdapter(this, repNames2, catFullness2, tweethandle2, tweet2,

                catPhotos2);

        //Set the Adapter

        repList2.setAdapter(adapter2);

        TextView uss = (TextView) findViewById(R.id.ussenators);
        if (catFullness[0] == "Democratic"){
            uss.setBackgroundColor(getResources().getColor(R.color.democrat));
        }
        if (catFullness[0] == "Republican"){
            uss.setBackgroundColor(getResources().getColor(R.color.republican));
        }
        if (catFullness[0] == "Independent"){
            uss.setBackgroundColor(getResources().getColor(R.color.independent));
        }

        TextView hr = (TextView) findViewById(R.id.housereps);
        if (catFullness2[0] == "Democratic"){
            hr.setBackgroundColor(getResources().getColor(R.color.democrat));
        }
        if (catFullness2[0] == "Republican"){
            hr.setBackgroundColor(getResources().getColor(R.color.republican));
        }
        if (catFullness2[0] == "Independent"){
            hr.setBackgroundColor(getResources().getColor(R.color.independent));
        }


    }

    public void showmore(View view) {
        Intent intent = new Intent(this, MoreInfo.class);
        startActivity(intent);
    }
}
