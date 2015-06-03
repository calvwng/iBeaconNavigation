package com.seniorproject.ibeaconnavigation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;

import com.seniorproject.ibeaconnavigation.model.Building;
import com.seniorproject.ibeaconnavigation.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * MainActivity provides the tab navigation for the application.
 * @author Calvin Wong
 */
public class MainActivity extends ActionBarActivity {
    private ListView buildingListView;
    private ListView favoritesListView;
    private SearchView searchView;

    // Dummy data for favorited rooms
    private ArrayList<Room> favRooms = new ArrayList<Room>(){{
            this.add(Building.getBuilding(14).getRoom(201).setFavName("My favorite class!"));
            this.add(Building.getBuilding(14).getRoom(212));
            this.add(Building.getBuilding(3).getRoom(111).setFavName("This class is also the biz"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildingListView = (ListView)findViewById(R.id.listBuilding);
        searchView = (SearchView)findViewById(R.id.searchBuilding);

        setupTabHost();
        populateSearchList();
        populateFavoritesList();
        setupSearchFiltering();
    }

    /**
     * Populate the Search page's list with mock building data
     */
    private void populateSearchList() {
        // Filling an ArrayList to allow new additions to ArrayAdapter
        BuildingListAdapter listAdapter =
                new BuildingListAdapter(this, new ArrayList<Building>(Building.getBuildings()));
        buildingListView.setAdapter(listAdapter);
    }

    private void populateFavoritesList() {
        favoritesListView = (ListView)findViewById(R.id.listFavorites);
        // Filling an ArrayList to allow new additions to ArrayAdapter
        RoomListAdapter listAdapter = new RoomListAdapter(this, favRooms, true);
        favoritesListView.setAdapter(listAdapter);
    }

    /**
     * Set up the TabHost view for the Search, Favorites, and Recent tabs
     */
    private void setupTabHost() {
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("search");
        tabSpec.setContent(R.id.tabSearch);
        tabSpec.setIndicator("Search");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("favorites");
        tabSpec.setContent(R.id.tabFavorites);
        tabSpec.setIndicator("Favorites");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("recent");
        tabSpec.setContent(R.id.tabRecent);
        tabSpec.setIndicator("Recent");
        tabHost.addTab(tabSpec);
    }

    private void setupSearchFiltering() {
        // Handle building search by filtering list as user types
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Building> temp = new ArrayList<Building>();
                int queryLen = newText.length();
                Collection<Building> buildings = Building.getBuildings();

                for (Building building : buildings) {
                    String bLabel = building.toString();
                    if (queryLen <= bLabel.length()
                            && bLabel.toLowerCase().contains(newText.toLowerCase())) {
                        temp.add(building);
                    }
                }
                BuildingListAdapter listAdapter = new BuildingListAdapter(MainActivity.this, temp);
                buildingListView.setAdapter(listAdapter);
                return true; // true b/c action is handled by the listener, not default
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

}
