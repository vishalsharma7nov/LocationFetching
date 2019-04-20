package com.locationfetching;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    TextView Latitude,Longitude;
    LocationRequest mLocationRequest;
    String Address;
    TextView address;
    Button button,map;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Latitude = (TextView)findViewById(R.id.latitude);
        Longitude = (TextView)findViewById(R.id.longitude);
        address = (TextView)findViewById(R.id.address);
        button  = (Button)findViewById(R.id.button);
        map = (Button)findViewById(R.id.map);
        map.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                map.setVisibility(View.VISIBLE);
            }
        });




    }

    ProgressDialog Location;
    void getLocation() {
        Location = ProgressDialog.show(this,"Fetching Location","Please wait...",false,false);

        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, (LocationListener) MainActivity.this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(android.location.Location location) {
        Latitude.setText("" + location.getLatitude());
        Longitude.setText("" +location.getLongitude());

        Location.dismiss();
        try
        {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            final double lat = location.getLatitude();
            final double lng = location.getLongitude();

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent = new Intent(MainActivity.this,MapsActivity.class);
                    intent = new Intent(MainActivity.this , MapsActivity.class);
                    intent.putExtra("latitude",lat);
                    intent.putExtra("longitude",lng);
                    startActivity(intent);
                }
            });



            List<android.location.Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0)
            {
                Address address = addresses.get(0);

                sb.append(address.getAddressLine(0));

            }

            Address = sb.toString();
            address.setText(Address);
            Log.e("Address from lat,long", Address);
        }
        catch (IOException e)
        {

        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
