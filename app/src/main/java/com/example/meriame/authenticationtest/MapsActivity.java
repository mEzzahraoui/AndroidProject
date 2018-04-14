package com.example.meriame.authenticationtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Initialize Location Manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        //Check if the network provider is enable
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the longitude
                    double longitude=location.getLongitude();
                    //get the latitude
                    double latitude=location.getLatitude();
                    //instantiate the class latlng
                    LatLng latLng=new LatLng(latitude, longitude);
                    //instantiate the class Geocoder
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
                        String str=listAddress.get(0).getLocality();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.2f));

                    } catch (IOException e) {
                        e.printStackTrace();
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
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the longitude
                    double longitude=location.getLongitude();
                    //get the latitude
                    double latitude=location.getLatitude();
                    //instantiate the class latlng
                    LatLng latLng=new LatLng(latitude, longitude);
                    //instantiate the class Geocoder
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
                        String str=listAddress.get(0).getLocality();
                        Toast.makeText(MapsActivity.this, "l empacement "+str+"latitude "+latitude+ longitude+longitude+"", Toast.LENGTH_LONG).show();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.2f));

                    } catch (IOException e) {
                        e.printStackTrace();
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
            });
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
/*        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.2f));*/

    }
    public void onSearch(View v){
        switch (v.getId()){
            case R.id.B_search:{
                EditText Tf_Location=(EditText)findViewById(R.id.Tf_Location);
                String location =Tf_Location.getText().toString();
                List<Address> addressList=null;
                MarkerOptions myMarker=new MarkerOptions();
                if(!location.equals("")){
                    Geocoder geocoder=new Geocoder(this);
                    try {
                        addressList=geocoder.getFromLocationName(location,20);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<addressList.size();i++){
                        Address myAdd=addressList.get(i);
                        LatLng latLng=new LatLng(myAdd.getLatitude(),myAdd.getLongitude());
                        myMarker.position(latLng);
                        myMarker.title("your search result");
                        mMap.addMarker(myMarker);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }break;
    }
}
}
