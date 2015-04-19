package com.seniorproject.ibeaconnavigation;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

        setupTabHost();
        populateSearchList();
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
        ArrayAdapter listAdapter = new ArrayAdapter(this, R.layout.simplerow, buildingsList);
        buildingListView.setAdapter(listAdapter);
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

        Button mapButton = (Button)findViewById(R.id.buttonMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(mapIntent);
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
