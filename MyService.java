package com.example.android.devicemovingspeed;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    static final Double EARTH_RADIUS = 6371.00;
    Thread t;
    Location currentBestLocation;
    private LocationManager locManager;
    private LocationListener locListener = new myLocationListener();
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private Handler handler = new Handler();
    private ServiceHandler serviceHandler = new ServiceHandler();
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    Runnable  r ;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {

        handler.removeCallbacks(r);
        super.onDestroy();
        stopSelf();
        Log.v("Debug", "in on Destroy ..");
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

      Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_SHORT).show();

        r = new Runnable() {
            public void run() {
                Log.v("Debug", "Inside runnable..");
                location();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(r, 5000);

        return START_NOT_STICKY;
    }

    public void location() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        Log.v("Debug", "in on create.. 2");
        if (gps_enabled) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
            Log.v("Debug", "Enabled..");
        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
            Log.v("Debug", "Disabled..");
        }
        Log.v("Debug", "in on create..3");
    }

  /*  public double CalculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        double Radius = EARTH_RADIUS;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Radius * c;
    }*/

    private class myLocationListener implements LocationListener {
        double lat_old = 0.0;
        double lon_old = 0.0;
        double lat_new;
        double lon_new;
        double time = 10;
        double speed = 0.0;

        @Override
        public void onLocationChanged(Location location) {
            Log.v("Debug", "in onLocation changed..");
            if (location != null) {
                locManager.removeUpdates(locListener);
              //  String Speed = "Device Speed: " +location.getSpeed();
                lat_new = location.getLongitude();
                lon_new = location.getLatitude();

                String longitude = "Longitude: " + location.getLongitude();
                String latitude = "Latitude: " + location.getLatitude();

/*              double distance = CalculationByDistance(lat_new, lon_new, lat_old, lon_old);
                speed = distance / time;
                Toast.makeText(getApplicationContext(), longitude + "\n" + latitude + "\nDistance is: "
                + distance + "\nSpeed is: " + speed, Toast.LENGTH_SHORT).show();*/
              //  Toast.makeText(getApplicationContext(), longitude + "\n" + latitude , Toast.LENGTH_SHORT).show();

                //comment added 14th April night
                if(serviceHandler.isBetterLocation(location,currentBestLocation)){
                    currentBestLocation = location;
                    serviceHandler.makeServiceCall(location.getLongitude() ,location.getLatitude(),location.getSpeed(),location.getAccuracy());
                }
               // serviceHandler.makeServiceCall(location.getLongitude() ,location.getLatitude(),location.getSpeed(),location.getAccuracy());


            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}
