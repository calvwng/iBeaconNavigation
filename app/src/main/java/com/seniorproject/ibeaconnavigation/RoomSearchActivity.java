package com.seniorproject.ibeaconnavigation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.seniorproject.ibeaconnavigation.model.Building;
import com.seniorproject.ibeaconnavigation.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class RoomSearchActivity extends ActionBarActivity {
    private ArrayList<Room> rooms;
    private ListView roomListView;
    private SearchView roomSearchView;
    private Building bldg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_search);
        roomListView = (ListView)findViewById(R.id.listRoom);
        roomSearchView = (SearchView)findViewById(R.id.searchRoom);
        int bldgNum = this.getIntent().getExtras().getInt(Building.TAG_BLDG_NUM);
        bldg = Building.getBuilding(bldgNum);
        rooms = new ArrayList<Room>(bldg.getRooms());

        setTitle("Bldg." + bldg.toString());
        populateRoomList();
        setupSearchFiltering();
    }

    private void populateRoomList() {
        RoomListAdapter listAdapter = new RoomListAdapter(this, rooms);
        roomListView.setAdapter(listAdapter);
    }

    private void setupSearchFiltering() {
        // Handle building search by filtering list as user types
        roomSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Room> matched = new ArrayList<Room>();
                int queryLen = newText.length();
                Collection<Room> rooms = bldg.getRooms();

                for (Room room : rooms) {
                    String rLabel = room.toString();
                    if (queryLen <= rLabel.length()
                            && rLabel.toLowerCase().contains(newText.toLowerCase())) {
                        matched.add(room);
                    }
                }
                RoomListAdapter listAdapter = new RoomListAdapter(RoomSearchActivity.this, matched);
                roomListView.setAdapter(listAdapter);
                return true; // true b/c action is handled by the listener, not default
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room_search, menu);
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
}
