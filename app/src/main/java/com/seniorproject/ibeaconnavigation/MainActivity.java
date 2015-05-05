package com.seniorproject.ibeaconnavigation;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MainActivity provides the tab navigation for the application.
 * @author Calvin Wong
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rangingBtn = (Button) findViewById(R.id.rangingButton);
        rangingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RangingActivity.class);
                startActivity(intent);
            }
        });

        setupTabHost();
        populateSearchList();
        populateFavoritesList();
    }

    /**
     * Populate the Search page's list with mock building data
     */
    private void populateSearchList() {
        ListView buildingListView = (ListView)findViewById(R.id.listBuilding);
        // Dummy data for buildings
        String[] buildings = {
                "1 Administration",
                "2 Cochett Education",
                "3 Business",
                "4 Research Development",
                "5 Architecture & Environmental",
                "14 Frank E. Pilling Computer Science"
        };
        // Filling an ArrayList to allow new additions to ArrayAdapter
        ArrayList buildingsList = new ArrayList(Arrays.asList(buildings));
        BuildingListAdapter listAdapter = new BuildingListAdapter(this, buildingsList);
        buildingListView.setAdapter(listAdapter);
    }

    private void populateFavoritesList() {
        ListView favoritesListView = (ListView)findViewById(R.id.listFavorites);
        // Dummy data for rooms
        String[] buildings = {
                "14-201 (Frank E. Pilling)"
        };
        // Filling an ArrayList to allow new additions to ArrayAdapter
        ArrayList buildingsList = new ArrayList(Arrays.asList(buildings));
        RoomListAdapter listAdapter = new RoomListAdapter(this, buildingsList);
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
