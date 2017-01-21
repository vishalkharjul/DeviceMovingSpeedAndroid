package com.example.android.devicemovingspeed;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.util.Log;
import android.widget.*;
import android.view.View.*;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import 	com.google.android.gms.maps.GoogleMap;
import android.os.Build;



public class DeviceMovingSpeed extends AppCompatActivity implements OnClickListener {

    public static EditText ET1;
    public static EditText ET2;
    private static final int  MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Remember to find solution to below problem as network operations are not permitted in main thread.

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_device_moving_speed);
        Log.v("Debug", "Activity started..");
        //GoogleMap googleMap = new GoogleMap();

  /*     if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {*/
        if ((int)Build.VERSION.SDK_INT < 23)
        {
            ET1=(EditText) findViewById(R.id.BusNo);
            ET2=(EditText) findViewById(R.id.RouteNo);
            Button  btn1=(Button)findViewById(R.id.Start_Tracking);
            Button  btn2=(Button)findViewById(R.id.Stop_Tracking);
            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        }else
        {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                //   googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            }else
            {
                ET1=(EditText) findViewById(R.id.BusNo);
                ET2=(EditText) findViewById(R.id.RouteNo);
                Button  btn1=(Button)findViewById(R.id.Start_Tracking);
                Button  btn2=(Button)findViewById(R.id.Stop_Tracking);
                btn1.setOnClickListener(this);
                btn2.setOnClickListener(this);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            }
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ET1=(EditText) findViewById(R.id.BusNo);
                    ET2=(EditText) findViewById(R.id.RouteNo);
                    Button  btn1=(Button)findViewById(R.id.Start_Tracking);
                    Button  btn2=(Button)findViewById(R.id.Stop_Tracking);
                    btn1.setOnClickListener(this);
                    btn2.setOnClickListener(this);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });


                } else {


                }
                return;

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_moving_speed, menu);
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

    @Override
    public void onClick(View v)
    {
// TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.Start_Tracking:

           //     Toast.makeText(ServiceDemo.this, “Start_Tracking”, Toast.LENGTH_LONG).show();
                if (ET1.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please set appropriate BusNo.It should not be empty.", Toast.LENGTH_SHORT).show();
                    break;
                }else if(ET2.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please set appropriate RouteNo.It should not be empty.", Toast.LENGTH_SHORT).show();
                    break;
                }else
                {
                    startService(new Intent(this, MyService.class));
                    Toast.makeText(getApplicationContext(), "Location Tracking service Started", Toast.LENGTH_SHORT).show();
                    ET1.setEnabled(false);
                    ET2.setEnabled(false);
                    break;
                }
            case R.id.Stop_Tracking:
             //   Toast.makeText(ServiceDemo.this, “Button2”, Toast.LENGTH_LONG).show();

                stopService(new Intent(this, MyService.class));
                Toast.makeText(getApplicationContext(), "Location Tracking service Stopped", Toast.LENGTH_SHORT).show();
                ET1.setEnabled(true);
                ET2.setEnabled(true);
                ET1.requestFocus();
                break;
        }
    }
}
