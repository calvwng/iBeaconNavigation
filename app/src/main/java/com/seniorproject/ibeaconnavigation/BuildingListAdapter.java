package com.seniorproject.ibeaconnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Custom ListAdapter for lists of buildings.
 * Created by Calvin on 4/19/2015.
 */
public class BuildingListAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List mBuildings;

    /**
     * Constructor that temporarily takes in a String list of building names
     * @param context
     * @param buildings
     */
    public BuildingListAdapter(Context context, List buildings) {
        super(context, R.layout.simplerow, buildings);
        mContext = context;
        mBuildings = buildings;
    }

    /**
     * Inflate row layout for each building and attach listeners
     */
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

        // Get corresponding building from list
        final Object building = mBuildings.get(position);

        // Update corresponding building's Views
        holder.name.setText((String)building);
        // Temporarily handles click events by displaying a Toast with building name
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Selected building " + (String)building,
                               Toast.LENGTH_SHORT).show();
                // TODO: launch fragment that lists rooms for selected building
            }
        });

        return convertView;
    }

    /**
     * ViewHolder object for a building list entry
     */
    public static class ViewHolder {
//        // TODO: add number TextView to row layout
//        TextView number;
        TextView name;
    }
}
