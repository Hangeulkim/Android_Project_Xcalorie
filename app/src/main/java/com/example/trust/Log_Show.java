package com.example.trust;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Log_Show extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private GoogleMap gMap;
    //    private ArrayList<LatLng> arrayPoints;
    double p_lat, p_lng;
    private RouteInfo log_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__show);

        Intent intent = getIntent();
        String[] tmpSpeed=intent.getExtras().getString("speed").split("/");
        String[] tmpLong=intent.getExtras().getString("longitude").split("/");
        String[] tmpLat=intent.getExtras().getString("latitude").split("/");
        for(String speed : tmpSpeed){
            log_info.arraySpeeds.add(Double.parseDouble(speed));
        }
        for(int i=0;i<tmpLong.length;i++){
            log_info.arrayPoints.add(new LatLng(Double.parseDouble(tmpLat[i]),Double.parseDouble(tmpLong[i])));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gMap = googleMap;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

            }

        });


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            public void onMapLongClick (LatLng point){
                mMap.clear();
                log_info.clear();

            }
        });


        // Add a marker in start and move the camera
        LatLng start = new LatLng(37.5193, 126.9778);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));


    }
}
