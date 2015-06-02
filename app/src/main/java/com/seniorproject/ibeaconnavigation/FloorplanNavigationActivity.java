package com.seniorproject.ibeaconnavigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matabii.dev.scaleimageview.ScaleImageView;
import com.seniorproject.ibeaconnavigation.model.Room;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Floorplan navigation for guiding the user to the target room.
 *
 * Created by Calvin on 5/2/2015.
 */
public class FloorplanNavigationActivity extends ActionBarActivity implements BeaconConsumer {
    private BeaconManager beaconManager;
    private ScaleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Room targetRoom = (Room)getIntent().getSerializableExtra(Room.TAG_ROOM);
        setTitle("Room " + targetRoom.toString());
        setContentView(R.layout.activity_floorplan_nav);
        imageView = (ScaleImageView) findViewById(R.id.imageView);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        // Supposedly this is the RadBeacon Layout
        beaconManager
                .getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (beacons.size() > 0) {
                            String address = beacons.iterator().next().getBluetoothAddress();
                            if (address.equals("00:07:80:15:89:E0")) {
                                setBeacon(1);
                            }
                            else if (address.equals("00:07:80:15:73:34")) {
                                setBeacon(2);
                            }
                        }
                        else {
                            setBeacon(0);
                        }
                    }
                });
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    private void setBeacon(int beacon) {
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.building14_layout_cropped);

        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        paint.setAlpha(125);

        //Draw the image bitmap into the canvas
        tempCanvas.drawBitmap(myBitmap, 0, 0, null);
        tempCanvas.drawCircle(500, 600, 100 * imageView.scale(), paint);

        Log.d("starng", "Beacon: " + beacon);
        if (beacon > 0) {
            paint.setColor(Color.BLUE);
            paint.setAlpha(125);
            if (beacon == 1) {
                tempCanvas.drawCircle(1350, 3025, 100 * imageView.scale(), paint);
            }
            else if (beacon == 2) {
                tempCanvas.drawCircle(1600, 3025, 100 * imageView.scale(), paint);
            }
        }
        //Attach the canvas to the ImageView
        imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }
}
