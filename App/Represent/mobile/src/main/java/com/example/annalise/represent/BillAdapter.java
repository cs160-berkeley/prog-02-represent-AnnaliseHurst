package com.example.annalise.represent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Annalise on 2/29/16.
 */
public class BillAdapter extends BaseAdapter{
    private Context context = null;
    private String[] billNames = null;
    private String[] date = null;

    public BillAdapter(Context context, String[] comNames, String[] date) {
        this.context = context;

        this.billNames = comNames;

        this.date = date;

    }

    @Override
    public int getCount() {
        return billNames.length;
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

        View catRow = inflater.inflate(R.layout.billlayout, parent, false);

        TextView nameView = (TextView) catRow.findViewById(R.id.bill);

        nameView.setText(billNames[position]);

        TextView dateView = (TextView) catRow.findViewById(R.id.date);

        dateView.setText(date[position]);

        return catRow;

    }
}
