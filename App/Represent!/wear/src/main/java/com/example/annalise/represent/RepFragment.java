package com.example.annalise.represent;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Annalise on 3/2/16.
 */
public class RepFragment extends CardFragment{
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private  String title;
    private  String text;

    public RepFragment() {
    }

    public static RepFragment newInstance(String title, String text){
        RepFragment rf = new RepFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(TEXT, text);
        rf.setArguments(args);
        return rf;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            text = getArguments().getString(TEXT);
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View myView = inflater.inflate(R.layout.clicklayout, container, false);
        TextView ti = (TextView) myView.findViewById(R.id.title);
        TextView te = (TextView) myView.findViewById(R.id.text);
        ti.setText(title);
        te.setText(text);

        if (title != "2012 Votes") {
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("WEAR", "rep");
                    getActivity().startService(sendIntent);
                }
            });
        }
        return myView;
    }

}
