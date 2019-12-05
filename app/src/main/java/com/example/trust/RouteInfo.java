package com.example.trust;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import android.location.Location;

public class RouteInfo {
    public String name = "name";
    public ArrayList<LatLng> arrayPoints;
    //    public ArrayList<Long> arrayTimes;
    public ArrayList<Double> arraySpeeds;
    public ArrayList<Location> arrayLocations;

    public RouteInfo(String name){
        this.name = name;
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
    }

    public RouteInfo(){
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
    }

    public double getSpeed(){
        double p_speed = 0;

        if(arraySpeeds.size()>=1) {
            double deltaTime = (arrayLocations.get(arrayLocations.size()-1).getTime() - arrayLocations.get(arrayLocations.size()-2).getTime()) / 1000;
            p_speed = arrayLocations.get(arrayLocations.size()-2).distanceTo(arrayLocations.get(arrayLocations.size()-1)) / deltaTime;

        }

        return p_speed;
    }

    public void remove(int index){
        arrayPoints.remove(index);
        arraySpeeds.remove(index);
        arrayLocations.remove(index);
    }

    public void clear(){
        arrayPoints.clear();
        arraySpeeds.clear();
        arrayLocations.clear();
    }

    public void addWayPoint(Location location){
        arrayLocations.add(location);
        arrayPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        arraySpeeds.add(this.getSpeed());
    }

}
