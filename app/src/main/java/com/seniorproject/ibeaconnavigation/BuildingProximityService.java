package com.seniorproject.ibeaconnavigation;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;

/**
 * Created by Calvin on 5/2/2015.
 */
public class BuildingProximityService extends IntentService {

    public BuildingProximityService() {
        super("BuildingProximityService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Get data from intent and check current proximity of target building or its beacons

        Toast.makeText(this, "Simulating proximity check (10 sec)...", Toast.LENGTH_SHORT).show();
        Log.d("BuildProxService", "Simulating proximity check...");
        SystemClock.sleep(10000); // Simulated wait time
        Log.d("BuildProxService", "Done simulating proximity check.");

        Toast.makeText(this, "Simulating successful proximity check...", Toast.LENGTH_SHORT).show();
        Intent fpNavIntent = new Intent(getBaseContext(), FloorplanNavigationActivity.class);
        fpNavIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(fpNavIntent);

        try {
            BeaconManager.getInstanceForApplication(getApplicationContext())
                    .stopRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        }
        catch (RemoteException re) {
            Log.d("bpServ", "" + re);
        }

        // Service automatically stops upon completion
    }
}
