package com.example.annalise.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Reps extends WearableActivity implements SensorEventListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private String county = "Alameda";
    private String jsonString;
    private String state = "CA";
    private String obamap = "65";
    private String romp;
    private String[] nam;
    private String[] par;

    private String names = ",";
    private String parties = ",";
    private String names31 = "";
    private String names32 = "";
    private String names33 = "";
    private String parties31 = "";
    private String parties32 = "";
    private String parties33 = "";
    private String names41 = "";
    private String names42 = "";
    private String names43 = "";
    private String names44 = "";
    private String parties41 = "";
    private String parties42 = "";
    private String parties43 = "";
    private String parties44 = "";

    // How I set up shake functionality http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    // How I set up 2D picker http://www.sprima.com/blog/?p=144
    // How I parsed json string http://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps);
        setAmbientEnabled();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String locName = extras.getString("LOCATION");
            String name = extras.getString("NAME");
            String party = extras.getString("PARTY");
            if (!(locName.equals(","))) {
                state = locName.split(",")[0];
                county = locName.split(",")[1];
            }
            if (name != null) {
                names = name;
            }
            if (party != null) {
                parties = party;
            }
        }
        nam = names.split(",");
        par = parties.split(",");
        if (nam.length == 3) {
            names31 = nam[0].trim();
            names32 = nam[1].trim();
            names33 = nam[2].trim();
            parties31 = par[0].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
            parties32 = par[1].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
            parties33 = par[2].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
        } else if (nam.length == 4) {
            names41 = nam[0].trim();
            names42 = nam[1].trim();
            names43 = nam[2].trim();
            names44 = nam[3].trim();
            parties41 = par[0].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
            parties42 = par[1].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
            parties43 = par[2].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
            parties44 = par[3].replace("D", "Democratic").replace("R", "Republican").replace("I", "Independent").replace(" ","");
        }

        try {
            InputStream stream = this.getAssets().open("election-county-2012.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String jsonString = new String(buffer, "UTF-8");
            try {
                JSONArray mainObject = new JSONArray(jsonString);
                HashMap<String, HashMap<String, String>> locmap = new HashMap<String, HashMap<String, String>>();
                HashMap<String, String> m_li;
                for (int i = 0; i < mainObject.length(); i++) {
                    JSONObject jo_inside = mainObject.getJSONObject(i);
                    String county_value = jo_inside.getString("county-name");
                    String obama_value = jo_inside.getString("obama-percentage");
                    String romney_value = jo_inside.getString("romney-percentage");

                    //Add your values in your `ArrayList` as below:
                    m_li = new HashMap<String, String>();
                    m_li.put("obama-percentage", obama_value);
                    m_li.put("romney-percentage", romney_value);

                    locmap.put(county_value, m_li);
                }
                obamap = locmap.get(county).get("obama-percentage");
                romp = locmap.get(county).get("romney-percentage");

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),
                        "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        MyGridPagerAdapter mg = new MyGridPagerAdapter(this, getFragmentManager());
        pager.setAdapter(new MyGridPagerAdapter(this, getFragmentManager()));
        pager.getBackground();
        DotsPageIndicator dots = (DotsPageIndicator) findViewById(R.id.indicator);
        dots.setPager(pager);

    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    this.finish();
                    Intent intent = new Intent(this, Reps.class);
                    startActivity(intent);

                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("WEAR", "location");
                    startService(sendIntent);
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public class MyGridPagerAdapter extends FragmentGridPagerAdapter {

        private final Context mContext;

        public MyGridPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;
        }

        public class Page {
            String name;
            String info;
            String nums;
            int cardGravity = Gravity.BOTTOM;
            boolean expansionEnabled = false;
            float expansionFactor = 1.0f;
            int expansionDirection = CardFragment.EXPAND_DOWN;

            public Page(String name, String info, String nums) {
                this.name = name;
                this.info = info;
                this.nums = nums;
            }
        }

        final int[][] BG_IMAGES = new int[][] {
                {
                        R.drawable.repbackground,
                        R.drawable.indb,
                        R.drawable.repb,
                        R.drawable.demb
                },
        };


        private final Page[][] PAGES3 = {
                {
                        new Page(names31,parties31,""),
                        new Page(names32,parties32,""),
                        new Page(names33,parties33,""),
                        new Page("2012 Votes", state+", "+county, obamap+","+romp),
                }
        };

        private final Page[][] PAGES4 = {
                {
                        new Page(names41,parties41,""),
                        new Page(names42,parties42,""),
                        new Page(names43,parties43,""),
                        new Page(names44,parties44,""),
                        new Page("2012 Votes", state+", "+county, obamap+","+romp),
                }
        };

        @Override
        public Fragment getFragment(int row, int col) {
            Page[][] PAGES = PAGES3;
            if (nam.length == 4) {
                PAGES = PAGES4;
            }
            Page page = PAGES[row][col];
            String title = page.name;
            String text;
            String pic = "R.drawable.indb";
            String numss = page.nums;
            if (col < PAGES[row].length-1) {
                text  = page.info+ " Party\n\nTap To View";
                pic = "R.drawable.indb";
            } else {
                String num1 = numss.split(",")[0];
                String num2 = numss.split(",")[1];
                text = page.info+"\n"+"\nObama "+num1+"% \nRomney "+num2+"%";
                ImageView o = (ImageView) findViewById(R.id.obama);
                ImageView r = (ImageView) findViewById(R.id.romney);
            }

            RepFragment fragment = RepFragment.newInstance(title, text);

            // Advanced settings
            fragment.setCardGravity(page.cardGravity);
            fragment.setExpansionEnabled(page.expansionEnabled);
            fragment.setExpansionDirection(page.expansionDirection);
            fragment.setExpansionFactor(page.expansionFactor);
            return fragment;
        }

        public Drawable getBackgroundForPage(int row, int column) {
            Page[][] PAGES = PAGES3;
//            if (nam.length == 4) {
//                PAGES = PAGES4;
//            }
            Page page = PAGES[row][column];
            Log.d("D", page.info);
            if (page.info.equals("Democratic")) {
                return mContext.getResources().getDrawable(BG_IMAGES[0][3]);
            }
            if (page.info.equals("Republican")) {
                return mContext.getResources().getDrawable(BG_IMAGES[0][2]);
            }
            if (page.info.equals("Independent")) {
                return mContext.getResources().getDrawable(BG_IMAGES[0][1]);
            }
            return mContext.getResources().getDrawable(BG_IMAGES[0][0]);
        }

        @Override
        public int getRowCount() {
            Page[][] PAGES = PAGES3;
            if (nam.length == 4) {
                PAGES = PAGES4;
            }
            return PAGES.length;
        }

        @Override
        public int getColumnCount(int rowNum) {
            Page[][] PAGES = PAGES3;
            if (nam.length == 4) {
                PAGES = PAGES4;
            }
            return PAGES[rowNum].length;
        }
    }
}
