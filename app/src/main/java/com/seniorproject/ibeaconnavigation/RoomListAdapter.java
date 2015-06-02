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

import com.seniorproject.ibeaconnavigation.model.Room;

import java.util.List;

/**
 * Custom ListAdapter for lists of rooms.
 *
 *  Created by Calvin on 4/19/2015.
 */
public class RoomListAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List mRooms;

    /**
     * Constructor that temporarily takes in a String list of room names
     * @param context
     * @param rooms
     */
    public RoomListAdapter(Context context, List<Room> rooms) {
        super(context, R.layout.simplerow, rooms);
        mContext = context;
        mRooms = rooms;
    }

    /**
     * Inflate row layout for each room and attach listeners
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

        // Get corresponding room from list
        final Room room = (Room)mRooms.get(position);

        // Update corresponding room's Views
        holder.name.setText(room.toString());
        // Temporarily handles click events by displaying a Toast with room name
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Selected room " + room.toString(),
                               Toast.LENGTH_SHORT).show();

                // launch Google Nav to building, then jump to our floorplan nav when beacon found
                // Start custom MapActivity
                Intent mapIntent = new Intent(getContext(), MapActivity.class);
                mapIntent.putExtra(Room.TAG_BEACON_ADDR, room.getBeaconAddr());
                mapIntent.putExtra(Room.TAG_ROOM, room);
                getContext().startActivity(mapIntent);
            }
        });

        return convertView;
    }

    /**
     * ViewHolder object for a room list entry
     */
    public static class ViewHolder {
//        // TODO: add number TextView to row layout
//        TextView number;
        TextView name;
    }
}
