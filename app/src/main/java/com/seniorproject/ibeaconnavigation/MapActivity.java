package com.seniorproject.ibeaconnavigation;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
                .snippet("A center of hands-on creation and analysis.")
                .position(targetCoord));
    }
}

