package com.example.annalise.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Intent intent = new Intent(this, CongressionalView.class);
        startActivity(intent);

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("LOCATION", "current");
        startService(sendIntent);
    }

    public void zip_send(View view) {
        Intent intent = new Intent(this, CongressionalView.class);
        startActivity(intent);

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("LOCATION", "zip");
        startService(sendIntent);
    }

}
