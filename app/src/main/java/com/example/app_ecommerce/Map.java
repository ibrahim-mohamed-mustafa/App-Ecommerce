package com.example.app_ecommerce;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locManager;
    MyLocationListener locListener;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},11);
        if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "You should accept location permissions", Toast.LENGTH_SHORT).show();
            return;
        }
        locListener=new MyLocationListener(getApplicationContext());
        locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,locListener);
        }
        catch (SecurityException ex){
            Toast.makeText(getApplicationContext(), "You are not allowed to access the current location", Toast.LENGTH_LONG).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapgoogle);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        mMap.clear();
        Geocoder coder=new Geocoder(getApplicationContext());
        List<Address>addressList;
        Location loc=null;
        try {
            loc=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch (SecurityException x){
            Toast.makeText(getApplicationContext(), "You did not allow to access the current location", Toast.LENGTH_LONG).show();
        }
        if(loc!=null){
            LatLng myPosition=new  LatLng(loc.getLatitude(),loc.getLatitude());
            try {
                addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                if(!addressList.isEmpty()) {
                    String address = "";
                    for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                        address += addressList.get(0).getAddressLine(i) + ", ";
                    Log.i("tag",address);
                    mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(address)).setDraggable(true);
                    Main.locationtxt.setText(address);
                }
            }catch (IOException e){
                mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
        }
        else{
            Toast.makeText(getApplicationContext(), "Please wait until your position is determined", Toast.LENGTH_LONG).show();
        }


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {


            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
           public void onMarkerDragEnd(Marker marker) {
          Geocoder coder=new Geocoder(getApplicationContext());
          List<Address>addressList;
          try {
              addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
              if(!addressList.isEmpty()){
                  String address="";
                  for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++){
                      address+=addressList.get(0).getAddressLine(i)+", ";
                      Main.locationtxt.setText(address);
                  }
              }
              else{
                  Toast.makeText(getApplicationContext(), "No address for this location", Toast.LENGTH_LONG).show();
                Main.locationtxt.setText("");
              }
          }catch (IOException e){
              Toast.makeText(getApplicationContext(), "Can't go rhe address check your network", Toast.LENGTH_LONG).show();
          }
           }
       });
        // Add a marker in Sydney and move the camera
    }
}