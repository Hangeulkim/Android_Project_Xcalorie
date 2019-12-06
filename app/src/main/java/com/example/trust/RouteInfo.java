package com.example.trust;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import android.location.Location;

public class RouteInfo {
    public String name = "name";
    public ArrayList<LatLng> arrayPoints;
    //    public ArrayList<Long> arrayTimes;
    public ArrayList<Double> arraySpeeds;
    public ArrayList<LatLng> arrayVector;
    public ArrayList<Location> arrayLocations;
    public boolean degree_b = true;


    public RouteInfo(String name){
        this.name = name;
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
        arrayVector = new ArrayList<LatLng>();
    }

    public RouteInfo(){
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
        arrayVector = new ArrayList<LatLng>();
    }




    public double getSpeed(){
        double p_speed = 0;

        if(arraySpeeds.size()>=1) {
            double deltaTime = (arrayLocations.get(arrayLocations.size()-1).getTime() - arrayLocations.get(arrayLocations.size()-2).getTime()) / 1000;
            p_speed = arrayLocations.get(arrayLocations.size()-2).distanceTo(arrayLocations.get(arrayLocations.size()-1)) / deltaTime;

        }

        return p_speed;
    }

    public LatLng getVector(){
        Double delta_latitude;
        Double delta_longitude;

        if(arrayVector.size()>=1){
            delta_latitude = arrayLocations.get(arrayLocations.size()-1).getLatitude() - arrayLocations.get(arrayLocations.size()-2).getLatitude();
            delta_longitude = arrayLocations.get(arrayLocations.size()-1).getLongitude() - arrayLocations.get(arrayLocations.size()-2).getLongitude();

            LatLng p_vector = new LatLng(delta_latitude, delta_longitude);

            return p_vector;
        }

        LatLng p_vector = new LatLng(0, 0);
        return p_vector;
    }

    public double vectorArrow(LatLng s_latlng, LatLng l_latlng){
        double degree = 0;

        degree = ((s_latlng.latitude * l_latlng.latitude) + (s_latlng.longitude * l_latlng.longitude))
                / (Math.sqrt(Math.pow(s_latlng.latitude, 2.0) + Math.pow(s_latlng.longitude, 2.0)) * Math.sqrt(Math.pow(l_latlng.latitude, 2.0) + Math.pow(l_latlng.longitude, 2.0)));

        return degree;
    }

    public void remove(int index){
        arrayPoints.remove(index);
        arraySpeeds.remove(index);
        arrayLocations.remove(index);
        arrayVector.remove(index);
    }

    public void clear(){
        arrayPoints.clear();
        arraySpeeds.clear();
        arrayLocations.clear();
        arrayVector.clear();
    }

    public void addWayPoint(Location location){
        arrayLocations.add(location);
        arrayPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        arraySpeeds.add(getSpeed());
        arrayVector.add(getVector());
    }

}
