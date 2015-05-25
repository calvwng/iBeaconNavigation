package com.seniorproject.ibeaconnavigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.matabii.dev.scaleimageview.ScaleImageView;

/**
 * Created by Calvin on 5/2/2015.
 */
public class FloorplanNavigationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorplan_nav);
    }
}
