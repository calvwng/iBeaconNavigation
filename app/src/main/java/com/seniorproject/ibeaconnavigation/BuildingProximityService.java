package com.seniorproject.ibeaconnavigation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.seniorproject.ibeaconnavigation.model.Room;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Background Service that polls for the target room's beacon, and launches the
 * floorplan navigation once it is found.
 *
 * Created by Calvin on 5/2/2015.
 */
public class BuildingProximityService extends Service implements BeaconConsumer {
    protected static final String TAG = "cwong";
    private BeaconManager beaconManager;
    String beaconAddr; // Bluetooth address of the beacon corresponding to the target room
    Room targetRoom;
    boolean beaconFound = false; // So that beacon's handled once per detection, then service stops


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
        Log.d(TAG, "Started BuildingProximityService successfully.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        beaconAddr = intent.getStringExtra(Room.TAG_BEACON_ADDR);
        targetRoom = ((Room)intent.getSerializableExtra(Room.TAG_ROOM));
        return result;
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

                    for (Beacon beacon : beacons) {
                        if (!beaconFound && beacon.getBluetoothAddress().equals(beaconAddr)) {
                            beaconFound = true;

                            try {
                                BeaconManager.getInstanceForApplication(getApplicationContext())
                                        .stopRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
                            }
                            catch (RemoteException re) {
                                Log.d(TAG, "" + re);
                            }

                            // Finding the beacon triggers the floorplan navigation and stops this service
//                            Toast.makeText(BuildingProximityService.this, "Found the room beacon!", Toast.LENGTH_SHORT).show();
                            Intent fpNavIntent = new Intent(getBaseContext(), FloorplanNavigationActivity.class);
                            fpNavIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String beaconAddress = beacons.iterator().next().getBluetoothAddress();
                            fpNavIntent.putExtra(Room.TAG_BEACON_ADDR, beaconAddress);
                            fpNavIntent.putExtra(Room.TAG_ROOM, targetRoom);
                            getApplication().startActivity(fpNavIntent);
                            stopSelf();
                        }
                    }
                }
            }
        });

        try {
            if (beaconFound) {
                stopSelf();
            }
            else {
                beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            }
        }
        catch (RemoteException e) {
            Log.d(TAG, "" + e);
        }
    }
}
