package com.seniorproject.ibeaconnavigation;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MapActivity extends Activity implements OnMapReadyCallback {
//, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
//    // FIXME: Unused b/c may use beacon detection vs. location checking to trigger indoor nav
//    private GoogleApiClient mGoogleApiClient;
//    private Location mLastLocation;
//    private double mLatitude;
//    private double mLongitude;
//    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Retrieve the Google MapFragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);       // Tell MapFragment to retrieve map

        Button navButton = (Button)findViewById(R.id.map_navButton);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate launching floorplan navigation logic when close to building
                Intent bpService = new Intent(MapActivity.this, BuildingProximityService.class);
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

//        // FIXME: Unused b/c may use beacon detection vs. location checking to trigger indoor nav
//        // Build & connect to Google API Client to ask for last known location
//        buildGoogleApiClient();
//        mGoogleApiClient.connect();
//
//        mWebView = (WebView)findViewById(R.id.map_webView);
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    //    // FIXME: Unused b/c may use beacon detection vs. location checking to trigger indoor nav
//    @Override
//    public void finish() {
//        mGoogleApiClient.disconnect();
//        super.finish();
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)        // Required callback interface
//                .addOnConnectionFailedListener(this) // Required callback interface
//                .addApi(LocationServices.API)
//                .build();
//    }
//
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
//            mLatitude = mLastLocation.getLatitude();
//            mLongitude = mLastLocation.getLongitude();
////            Toast.makeText(this, "Lat/Long : " + mLatitude + ", " + mLongitude, Toast.LENGTH_SHORT).show();
//            String lastKnownLocation = mLatitude + "," + mLongitude;
//            mWebView.loadUrl("http://maps.google.com/maps?saddr=" + lastKnownLocation
//                    + "&daddr=Frank+E.+Pilling,+San+Luis+Obispo,+CA");
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        // The connection has been interrupted.
//        // Disable any UI components that depend on Google APIs
//        // until onConnected() is called.
//        Toast.makeText(this, "Connection to Google API Client suspended.", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Toast.makeText(this, "Connection to Google API Client failed.", Toast.LENGTH_SHORT).show();
//    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng targetCoord = new LatLng(35.30019, -120.6623);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(targetCoord, 13));

        map.addMarker(new MarkerOptions()
                .title("Frank E. Pilling Computer Science")
                .snippet("A hub of hands-on computational creativity and analysis.")
                .position(targetCoord));
    }
}

