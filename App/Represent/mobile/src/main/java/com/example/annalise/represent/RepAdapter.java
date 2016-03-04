package com.example.annalise.represent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Annalise on 2/29/16.
 */
public class RepAdapter extends BaseAdapter {
    private Context context = null;
    private String[] catNames = null;
    private String[] catFullness = null;
    private String[] tweethandle = null;
    private String[] tweet = null;
    private int[] catPhotos = null;

    public RepAdapter(Context context, String[] catNames, String[] catFullness, String[] tweethandle, String[] tweet, int[]

            catPhotos) {
        this.context = context;

        this.catNames = catNames;

        this.catFullness = catFullness;

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

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context

                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View catRow = inflater.inflate(R.layout.replayout, parent, false);

        TextView nameView = (TextView) catRow.findViewById(R.id.name);

        TextView twithandView = (TextView) catRow.findViewById(R.id.twithand);

        TextView tweetView = (TextView) catRow.findViewById(R.id.tweet);

        ImageView pictureView = (ImageView) catRow.findViewById(R.id.repimage);

        ImageView frameView = (ImageView) catRow.findViewById(R.id.repframe);

        nameView.setText(catNames[position]);

        twithandView.setText(tweethandle[position]);
        tweetView.setText(tweet[position]);

//        fullnessView.setText("Fullness: " + catFullness[position].toString());

        pictureView.setImageResource(catPhotos[position]);

        if (catFullness[position] == "Democratic") {
            frameView.setImageResource(R.drawable.dframe);
        }
        if (catFullness[position] == "Republican") {
            frameView.setImageResource(R.drawable.rframe);
        }
        if (catFullness[position] == "Independent") {
            frameView.setImageResource(R.drawable.iframe);
        }

        return catRow;

    }
}
