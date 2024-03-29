package com.example.trust;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.MarkerOptions;

public class RouteInfo{
    public String name = "name";
    public ArrayList<LatLng> arrayPoints;
    //    public ArrayList<Long> arrayTimes;
    public ArrayList<Double> arraySpeeds;
    public ArrayList<LatLng> arrayVector;
    public ArrayList<Location> arrayLocations;
    public ArrayList<MarkerOptions> arraymarkerPoints;
    public double cal;
    public double weight = 65;
    public boolean degree_b = true;
    public int select_menu = 0;     //타이머 = 1, 빠시 = 2, 경로지정 = 3;
    public SharedPreferences prefs;
    public String HowToEx="걷기";
    //public String HowToEx = prefs.getString("howtoex","걷기");
//    public boolean moving = false;  //시작하면 true, 종료하면 false;

    public RouteInfo(String name, Context context){
        this.name = name;
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
        arrayVector = new ArrayList<LatLng>();
        arraymarkerPoints = new ArrayList<MarkerOptions>();
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        HowToEx = prefs.getString("howtoex","걷기");
    }
    public RouteInfo(String name){
        this.name = name;
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
        arrayVector = new ArrayList<LatLng>();
        arraymarkerPoints = new ArrayList<MarkerOptions>();
    }

    public RouteInfo(){
        arrayPoints = new ArrayList<LatLng>();
        arraySpeeds = new ArrayList<Double>();
        arrayLocations = new ArrayList<Location>();
        arrayVector = new ArrayList<LatLng>();
        arraymarkerPoints = new ArrayList<MarkerOptions>();
    }

    public void set_selectMenu(int i){
        select_menu = i;
    }

    public double getCal(double t_time){
        if(HowToEx.equals("걷기")){
            return weight*3.5*2.3*t_time/1000;
        }
        else if(HowToEx.equals("뛰기")){
            return weight*3.5*3.6*t_time/1000;
        }
        else{
            return weight*3.5*8.0*t_time/1000;
        }
    }

    public double get_totaltime(){
        double t_time = 0;


        for(int i=1; arrayLocations.size() - i >= 1; i++){
            t_time += (arrayLocations.get(arrayLocations.size()-i).getTime() - arrayLocations.get(arrayLocations.size()-(i+1)).getTime()) / 1000;
        }
        return t_time;
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

    public void markerRemove(int index){
        arraymarkerPoints.remove(index);
    }

    public void clear(){
        arrayPoints.clear();
        arraySpeeds.clear();
        arrayLocations.clear();
        arrayVector.clear();
        arraymarkerPoints.clear();
    }

    public void addWayPoint(Location location){
        arrayLocations.add(location);
        arrayPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        arraySpeeds.add(getSpeed());
        arrayVector.add(getVector());
    }

    public void addMarkerPoint(MarkerOptions markeroptions){
        arraymarkerPoints.add(markeroptions);
    }

}
