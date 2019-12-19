package com.example.trust;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

        log_info = new RouteInfo();

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

    public void drawPath(LatLng p_latlng) {
        p_lng = p_latlng.longitude;
        p_lat = p_latlng.latitude;
        Double latitude = p_lat;
        Double longitude = p_lng;

        gMap.clear();

//        MarkerOptions mOptions = new MarkerOptions();
//        mOptions.title("마커 좌표");


//        mOptions.snippet(string);
//        mOptions.position(p_latlng);

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 18));
//        gMap.addMarker(mOptions);



//                arrayPoints.add(p_latlng);


        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(log_info.arrayPoints);
        gMap.addPolyline(polylineOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gMap = googleMap;


        LatLng p_latlng = log_info.arrayPoints.get(log_info.arrayPoints.size()-1);
        p_lng = p_latlng.longitude;
        p_lat = p_latlng.latitude;





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

        drawPath(p_latlng);

    }
}
