package uk.ac.lincoln.a14464662students.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;

    static TextView placeTextView;
    static TextView temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        httpConnect task = new httpConnect();


        //task.execute("http://api.openweathermap.org/data/2.5/weather?lat=53.22&lon=-0.53&appid=3af7c867307fc5f8ed9b5f252022657a");
        //  task.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(lat) + "&lon" + String.valueOf(lon) + "&appid=3af7c867307fc5f8ed9b5f252022657a");

        final TextView tv2 = (TextView) findViewById(R.id.textView3);
        tv2.setText("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon" + String.valueOf(longitude) + "&appid=3af7c867307fc5f8ed9b5f252022657a");
        final TextView tv1 = (TextView) findViewById(R.id.locationCords);
        placeTextView = (TextView) findViewById(R.id.placeTextView);
        temperature = (TextView) findViewById(R.id.temperature);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                tv1.setText("long=" + longitude + "lat=" + latitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        String locationProvider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if (latitude != 0.0){
            latitude = lastKnownLocation.getLatitude();
        }
        else;

        if (longitude != 0.0){
            longitude = lastKnownLocation.getLongitude();
        }
        else;
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }


    public void appSettings(View view) {
        Intent intent = new Intent(this, userSettings.class);
        startActivity(intent);
    }

    // added asynctask class methods below -  you can make this class as a separate class file


}

