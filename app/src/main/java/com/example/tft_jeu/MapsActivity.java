package com.example.tft_jeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.GnssAntennaInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    //todo dans le layout afficher un boutton pour l'instant google map prend la place math parent
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Button buttonBack;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        buttonBack = findViewById(R.id.bt_map_back);

        buttonBack.setOnClickListener(v -> {
            finish();
        });
        Intent fromList = getIntent();
        if (fromList != null) {
            Log.d("LON_LAT", "Test");
            Bundle extra = fromList.getExtras();
            String latStr = extra.getString("Lat");
            String lonStr = extra.getString("Long");
            String name = extra.getString("Name");
            if (latStr == null || lonStr == null) {

            } else {
                Double lat = Double.parseDouble(latStr);
                Double lon = Double.parseDouble(lonStr);

                Log.d("LON-LAT", lonStr + "-" + latStr);
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //  Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

//        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //FIXME Ceci est une note pour Mister Pat =)
        Intent intentList = new Intent(getApplicationContext(), AfficherListeStreet.class);
        startActivity(intentList);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        Intent intent = getIntent();
        String latitude = intent.getStringExtra("Lat");
        String longitude = intent.getStringExtra("Long");
        String name = intent.getStringExtra("Name");
        LatLng PositionObject = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        mMap.addMarker(new MarkerOptions()
                .position(PositionObject)
                .title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(PositionObject));
        mMap.setMinZoomPreference(20);
        mMap.setMaxZoomPreference(10);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        // Position the map's camera at the location of the marker.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PositionObject,
                DEFAULT_ZOOM));

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
      //      outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("LAT", location.getLatitude() + "");
        Log.d("LON", location.getLongitude() + "");

        LatLng sydney = new LatLng(-34, 151);
        float[] result = new float[4];
        Location.distanceBetween(sydney.latitude, sydney.longitude, location.getLatitude(), location.getLongitude(), result);

        Log.d("RESULT", result[0] + "");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
    }
}
