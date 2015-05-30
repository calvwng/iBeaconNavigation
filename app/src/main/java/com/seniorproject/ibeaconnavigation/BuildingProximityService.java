package com.seniorproject.ibeaconnavigation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by Calvin on 5/2/2015.
 */
public class BuildingProximityService extends Service implements BeaconConsumer {
    protected static final String TAG = "BuildingProximityService";
    private BeaconManager beaconManager;


    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        // Supposedly this is the RadBeacon Layout
        beaconManager
                .getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
//                    for (Beacon beacon : beacons) {
//                        Log.d("cwong", "The beacon " + beacon.getBluetoothAddress() +
//                                " is about " + beacon.getDistance() + " meters away.");
//                    }
//                    Log.d("cwong", "Number of beacons: " + beacons.size());

                    try {
                        BeaconManager.getInstanceForApplication(getApplicationContext())
                                .stopRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
                    }
                    catch (RemoteException re) {
                        Log.d("bpServ", "" + re);
                    }

                    // Finding the beacon triggers the floorplan navigation and stops this service
                    Toast.makeText(BuildingProximityService.this, "Found the room beacon!", Toast.LENGTH_SHORT).show();
                    Intent fpNavIntent = new Intent(getBaseContext(), FloorplanNavigationActivity.class);
                    fpNavIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(fpNavIntent);
                    stopSelf();
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }
}
