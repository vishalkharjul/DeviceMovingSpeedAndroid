package com.example.android.devicemovingspeed;
import android.location.Location;

import java.util.Date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.json.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by kharjuvi on 3/10/2016.
 */

public class ServiceHandler {
    static InputStream is = null;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    private static final int TWO_MINUTES = 1000 * 5;

    public ServiceHandler() {

    }

    public void makeServiceCall(double lon, double lat,float speed ,float acc) {
//        String query = "http://149.56.45.190";
//      String json = "{\"key\":1}";
        JSONObject jsonObject ;
        URL url;
        try {
            url = new URL("http://149.56.45.190:8082/requests");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
           // DeviceMovingSpeed.ET1.getText();
            //DeviceMovingSpeed.ET2.getText();
//            conn.setDoInput(true);
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("User-Agent", USER_AGENT);
            JSONObject loc = new JSONObject();

            loc.put("busno",DeviceMovingSpeed.ET1.getText());
            loc.put("routeno",DeviceMovingSpeed.ET2.getText());
            loc.put("longitude", lon);
            loc.put("latitude", lat);
            loc.put("loc_acc", acc);
            loc.put("bus_speed",speed);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            OutputStream os = conn.getOutputStream();
            wr.write(loc.toString().getBytes());
            wr.close();
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + loc);
            System.out.println("Response Code : " + responseCode);
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = in.toString();
            jsonObject = new JSONObject(result);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ;
    }


    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isLessAccurate && isFromSameProvider && !isSignificantlyLessAccurate) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}