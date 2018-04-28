package com.example.meriame.authenticationtest;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;



/**
 * A simple {@link Fragment} subclass.
 */
public class FMaps extends Fragment implements OnMapReadyCallback,LocationListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mPlaces;
    Marker marker;
    private LocationManager locationManager;
    public static double longitude;
    public static double latitude;

    public FMaps() {
        // Required empty public constructor
    }


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fmaps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        ChildEventListener mChildEventListener;
        mPlaces= FirebaseDatabase.getInstance().getReference("Places");
        mPlaces.push().setValue(marker);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        //Check if the network provider is enable
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the longitude
                    longitude=location.getLongitude();
                    //get the latitude
                    latitude=location.getLatitude();
                    //instantiate the class latlng
                    LatLng latLng=new LatLng(latitude, longitude);
                    //instantiate the class Geocoder
                    Geocoder geocoder=new Geocoder(getActivity().getApplicationContext());
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
                    Geocoder geocoder=new Geocoder(getActivity().getApplicationContext());
                    try {
                        List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
                        String str=listAddress.get(0).getLocality();
                        Toast.makeText(getContext(), "l empacement "+str+"latitude "+latitude+ longitude+longitude+"", Toast.LENGTH_LONG).show();
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MyMapp);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mPlaces.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    Place place = s.getValue(Place.class);
                    LatLng location=new LatLng(place.getLatitude(),place.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).title(place.getName())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent myIntent = new Intent(getActivity(), InfoPlace.class);
    //    myIntent.putExtra("PlaceName",marker.getTitle());
        startActivity(myIntent);

        return false;
    }
}
