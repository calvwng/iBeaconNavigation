package com.seniorproject.ibeaconnavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.ibeaconnavigation.model.Building;
import com.seniorproject.ibeaconnavigation.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom ListAdapter for lists of buildings.
 * Created by Calvin on 4/19/2015.
 */
public class BuildingListAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List mBuildings;

    /**
     * Constructor that takes in a list of Buildings
     * @param context
     * @param buildings
     */
    public BuildingListAdapter(Context context, List<Building> buildings) {
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
        final Building building = (Building)mBuildings.get(position);

        // Update corresponding building's Views
        holder.name.setText(building.toString());
        // Temporarily handles click events by displaying a Toast with building name
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Selected Building " + building.toString(),
                               Toast.LENGTH_SHORT).show();
                final TextView bldgTxtVw = (TextView)v;
                final int oldTextColor = bldgTxtVw.getCurrentTextColor();
                bldgTxtVw.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bldgTxtVw.setTextColor(oldTextColor);
                    }
                }, 200);

                // Launch room search/filter activity for selected buildling
                Intent roomSearchIntent =
                        new Intent(getContext(), RoomSearchActivity.class);
                roomSearchIntent.putExtra(Building.TAG_BLDG_NUM, building.getNum());
                getContext().startActivity(roomSearchIntent);
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
