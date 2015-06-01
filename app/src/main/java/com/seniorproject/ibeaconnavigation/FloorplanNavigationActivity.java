package com.seniorproject.ibeaconnavigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class FloorplanNavigationActivity extends ActionBarActivity {
    private ScaleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String targetRoomLabel = getIntent().getExtras().getString("TARGET_ROOM_LABEL");
        setTitle("Room " + targetRoomLabel);
        setContentView(R.layout.activity_floorplan_nav);
        imageView = (ScaleImageView) findViewById(R.id.imageView);

        String beaconAddr = getIntent().getExtras().getString("BEACON_ADDRESS");
        if (beaconAddr.equals("00:07:80:15:89:E0")) {
            imageView.setBeacon(1);
        }
        else if (beaconAddr.equals("00:07:80:15:73:34")) {
            imageView.setBeacon(2);
        }
        else {
            imageView.setBeacon(1);
        }
        imageView.invalidate();
    }

    /*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ScaleImageView imageView = (ScaleImageView)findViewById(R.id.imageView);
        Log.d("starng", "size is " + imageView.getWidth() + " || " + imageView.getHeight());
        Bitmap viewBitmap = Bitmap.createBitmap(imageView.getWidth(),
                imageView.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(viewBitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        canvas.drawCircle(100, 500, 250, paint);
        //imageView.setImageBitmap(viewBitmap);
        imageView.draw(canvas);
    }
    */
}
