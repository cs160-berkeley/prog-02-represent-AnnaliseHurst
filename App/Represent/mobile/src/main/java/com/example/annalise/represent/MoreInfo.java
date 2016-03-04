package com.example.annalise.represent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class MoreInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView billList = (ListView) findViewById(R.id.billview);
        String[] billNames = {"Bill 1", "Bill 2", "Bill 3"};
        String[] billdates = {"Date Introduced", "Date Introduced", "Date Introduced"};

        //Create the Adapter

        final BillAdapter billadapter = new BillAdapter(this, billNames, billdates);

        //Set the Adapter

        billList.setAdapter(billadapter);


        final ListView ComList = (ListView) findViewById(R.id.comview);
        String[] comNames = {"Committee 1", "Committee 2", "Committee 3", "Committee 4"};
        //Create the Adapter

        final CommitteAdapter comadapter = new CommitteAdapter(this, comNames);

        //Set the Adapter

        ComList.setAdapter(comadapter);
    }

}
