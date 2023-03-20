package com.example.daba;
//
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity implements LocationListener {

    private LocationManager lm;
    private TextView displayLocation;
    private Button getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getLocation = findViewById(R.id.getLocationButton);
        displayLocation  = findViewById(R.id.getLocationView);


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // check for runtime permission
                if (ContextCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request location updates
                    ActivityCompat.requestPermissions(Location.this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

            // getLocationMethod
            getLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try{
            lm = (LocationManager) getApplicationContext().getSystemService(LOCALE_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Location.this);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        Toast.makeText(Location.this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();


        try {
            Geocoder gc = new Geocoder(Location.this, Locale.getDefault());
            List<Address> allAddress = gc.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            String address = allAddress.get(0).getAddressLine(0);

            displayLocation.setText(address);
            displayLocation.setText("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}