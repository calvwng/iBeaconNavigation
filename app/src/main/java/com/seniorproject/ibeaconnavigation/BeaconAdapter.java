package com.seniorproject.ibeaconnavigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * Created by Sterling on 4/20/2015.
 */
public class BeaconAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List<Beacon> mBeacons;

    public BeaconAdapter(Context context, List<Beacon> beacons) {
        super(context, R.layout.simplerow, beacons);
        mContext = context;
        mBeacons = beacons;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.simplerow, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.rowTextView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        // Get corresponding room from list
        final Beacon beacon = mBeacons.get(position);

        // Update corresponding room's Views
        holder.name.setText("Beacon: " + beacon.getBluetoothAddress() + " - "
                + String.format("%.2f", beacon.getDistance()));

        return convertView;
    }

    /**
     * ViewHolder object for a room list entry
     */
    public static class ViewHolder {
        TextView name;
    }
}
