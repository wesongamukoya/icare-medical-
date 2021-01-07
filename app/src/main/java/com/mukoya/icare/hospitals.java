package com.mukoya.icare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class hospitals extends AppCompatActivity {

    //initialize variables

    Spinner spinner_type;
    Button btn_find;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        //assign variable
        spinner_type = findViewById(R.id.spinner_type);
        btn_find = findViewById(R.id.btn_find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id. google_map);

        //initialize array of place type

        final String[] placeTypeList = {"hospital", "bank", "atm", "restaurant"};
        //initialize array of place name
        String[] placeNameList = {"Hospital", "Bank", "Atm", "Restaurant"};

        //set adaptor on spinner
        spinner_type.setAdapter(new ArrayAdapter<>(hospitals.this, android
        .R.layout.simple_spinner_dropdown_item, placeNameList));

        //initialize fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(hospitals.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            //when permission granted
            //call method
            getCurrentLocation();

        }else {
            //when permission denied
            //request permission
            ActivityCompat.requestPermissions(hospitals.this
            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        btn_find . setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get selected position of spinner
                int i = spinner_type.getSelectedItemPosition();
                //Initialize url
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + //Url
                "?location=" + currentLat + ",";
                final String s = currentLong + //location latitude and longitude
                        "&radius = 5000" + //nearby radius
                        "&types = " + placeTypeList[i] + //place type
                        "&sensor = true" + //sensor
                        "&key = " + getResources().getString(R.string.google_map_key);//google map key

                //execute place task method to download json data
                new PlaceTask().execute(url);


            }
        });




    }

    private void getCurrentLocation(){
        //initialize task location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success

                if (location != null){
                    //when success is not equal to null
                    //get current latitude
                    currentLat = location.getLatitude();
                    //get current longitude
                    currentLong = location.getLongitude();
                    //sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //when map is ready
                            map = googleMap;
                            //zoom current location on map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat, currentLong), 10
                            ));

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //when permission granted
                //call method
                getCurrentLocation();
            }
        }
    }
}
