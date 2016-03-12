package com.example.annalise.represent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Annalise on 2/29/16.
 */
public class CommitteAdapter extends BaseAdapter {
    private Context context = null;
    private String[] comNames = null;

    public CommitteAdapter(Context context, String[] comNames) {
        this.context = context;

        this.comNames = comNames;

    }

    @Override
    public int getCount() {
        return comNames.length;
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

        View catRow = inflater.inflate(R.layout.committeelayout, parent, false);

        TextView nameView = (TextView) catRow.findViewById(R.id.committeename);

        nameView.setText(comNames[position]);

        return catRow;

    }
}