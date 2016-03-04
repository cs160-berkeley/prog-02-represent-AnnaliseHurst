package com.example.annalise.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
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
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Reps extends WearableActivity implements SensorEventListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private String county = "RCounty,RState";
//    private String state;
//    private int oba;
//    private int rom;

    // How I set up shake functionality http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    // How I set up 2D picker http://www.sprima.com/blog/?p=144
    //

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
            county = locName;
        }

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
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

        private class Page {
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

        private final Page[][] PAGES = {
                {
                        new Page("Barbara Boxer","Democratic", ""),
                        new Page("Name","Republican", ""),
                        new Page("Name","Independent",""),
                        new Page("2012 Votes", county.split(",")[0]+", "+county.split(",")[1],"65,35"),
                },

        };

        @Override
        public Fragment getFragment(int row, int col) {
            Page page = PAGES[row][col];
            String title = page.name;
            String text;
            String numss = page.nums;
            if (col < PAGES[row].length-1) {
                text  = page.info+ " Party\n\nTap To View";
            } else {
                String num1 = numss.split(",")[0];
                String num2 = numss.split(",")[1];
                text = page.info+"\n"+"\nObama "+num1+"% \nRomney "+num2+"%";
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
            Page page = PAGES[row][column];
            if (page.info == "Democratic") {
                return mContext.getResources().getDrawable(BG_IMAGES[0][3]);
            }
            if (page.info == "Republican") {
                return mContext.getResources().getDrawable(BG_IMAGES[0][2]);
            }
            if (page.info == "Independent") {
                return mContext.getResources().getDrawable(BG_IMAGES[0][1]);
            }
            return mContext.getResources().getDrawable(BG_IMAGES[0][0]);
        }

        @Override
        public int getRowCount() {
            return PAGES.length;
        }

        @Override
        public int getColumnCount(int rowNum) {
            return PAGES[rowNum].length;
        }
    }
}
