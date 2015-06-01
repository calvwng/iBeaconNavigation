package com.seniorproject.ibeaconnavigation;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.seniorproject.ibeaconnavigation.model.Room;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MapActivity extends ActionBarActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final String beaconAddr = getIntent().getExtras().getString(Room.TAG_BEACON_ADDR);
        final String roomLabel = getIntent().getExtras().getString(Room.TAG_LABEL);
        setTitle("Room " + roomLabel);

        // Retrieve the Google MapFragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);       // Tell MapFragment to retrieve map

        Button navButton = (Button)findViewById(R.id.map_navButton);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch background service to detect iBeacon
                Intent bpService = new Intent(MapActivity.this, BuildingProximityService.class);
                bpService.putExtra(Room.TAG_BEACON_ADDR, beaconAddr);
                bpService.putExtra(Room.TAG_LABEL, roomLabel);
                MapActivity.this.startService(bpService);

                // Build Google Nav URI and launch Google MapsActivity with it
                String queryDestination = "Frank+E.+Pilling,+San+Luis+Obispo,+CA";
                String queryMode = "&mode=w";
                Uri gmapBuildingUri = Uri.parse("google.navigation:q=" + queryDestination + queryMode);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmapBuildingUri);
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                MapActivity.this.startActivity(mapIntent);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng targetCoord = new LatLng(35.30019, -120.6623);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(targetCoord, 13));

        Marker marker = map.addMarker(new MarkerOptions()
            .title("Frank E. Pilling Computer Science")
            .snippet("A hub of hands-on computational creativity and analysis.")
            .position(targetCoord));
        marker.showInfoWindow();
    }
}

