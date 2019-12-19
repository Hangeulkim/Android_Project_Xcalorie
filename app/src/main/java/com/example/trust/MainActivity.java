package com.example.trust;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{
    LinearLayout Select_More_Layout;
    RelativeLayout Select_Start_Layout;
    LinearLayout Start_First_Layout;
    LinearLayout Timer;

    long hour;
    long minute;
    long second;

    ImageButton Start_More;
    ImageButton Start_Start;
    TextView Speed;
    int count = 0;
    boolean moving = false;

    ImageButton Start_Fast;
    ImageButton Start_Timer;
    ImageButton Start_Path;
    ImageButton End;
    ImageButton now_Location;

    AlertDialog alertDialog;
    AlertDialog pathSaveDialog;

    DBHelper helper = null;

    EditText in;

    SharedPreferences prefs;

    private GoogleMap mMap;
    private GoogleMap gMap;
    //    private ArrayList<LatLng> arrayPoints;
    double p_lat, p_lng;
    private RouteInfo routeInfo;


    String HowToEx;
    Boolean Vibe;
    Boolean Sound;


    public void Now_location(View view) {
        if (routeInfo.arrayPoints.size() - 1 >= 0) {
            LatLng p_latlng = routeInfo.arrayPoints.get(routeInfo.arrayPoints.size() - 1);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 20));
        }
//        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//        }
//        else {
//
////            routeInfo = new RouteInfo("name");
////            arrayPoints = new ArrayList<LatLng>();
//            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
//
//        }
    }

    public void Click_Select_Log(View view) {
        Intent intent = new Intent(this, RecordsScrollingActivity.class);
        startActivity(intent);
    }

    public void Click_Select_Setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void Click_Select_Makers(View view) {
        Toast.makeText(getApplicationContext(), "숭실대학교 김찬진, 임가균, 정종인, 2019.12", Toast.LENGTH_LONG).show();
    }

    public void Click_More_Quit(View view) {
        Select_More_Layout.setVisibility(View.INVISIBLE);
        Start_First_Layout.setVisibility(View.VISIBLE);
    }


    public void Click_Start_More(View view) {
        Start_First_Layout.setVisibility(View.INVISIBLE);
        Select_More_Layout.setVisibility(View.VISIBLE);
    }

    public void Click_Start_Start(View view) {
//        now_Location.setVisibility(View.VISIBLE);
        gMap.clear();
        Start_More.setVisibility(View.VISIBLE);
        Start_Start.setVisibility(View.INVISIBLE);
        Select_Start_Layout.setVisibility(View.VISIBLE);
        Speed = findViewById(R.id.speed);
        Speed.setText("시작!");
        //Speed.setVisibility(View.VISIBLE);


//        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//        }
//        else {
////            routeInfo = new RouteInfo("name");
////            arrayPoints = new ArrayList<LatLng>();
//            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
//
//        }
        routeInfo = new RouteInfo();
        routeInfo.weight = 65;
        routeInfo.prefs=this.prefs;
        routeInfo.HowToEx=this.HowToEx;
        Start_Start.setVisibility(View.GONE);
        Select_Start_Layout.setVisibility(View.VISIBLE);

    }

    public void Click_Start_Timer(View view) {
        moving = true;
        gMap.clear();
        Timer.setVisibility(View.VISIBLE);
        now_Location.setVisibility(View.VISIBLE);
        Start_First_Layout.setVisibility(View.GONE);
        End.setVisibility(View.VISIBLE);
        routeInfo.set_selectMenu(1);
//        routeInfo.moving = true;

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            if (moving == true) {
                final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
            }
        }
    }

    public void Start_Count_down(View view){
        
    }

    public void sum(long t){
        hour =  t/3600;
        minute = (t%3600)/60;
        second = (t%3600)%60;

    }

    public void Click_Start_Select(View view) {
        moving = true;
        gMap.clear();
        now_Location.setVisibility(View.VISIBLE);
        Start_First_Layout.setVisibility(View.GONE);
        End.setVisibility(View.VISIBLE);
        routeInfo.set_selectMenu(3);
//        routeInfo.moving = true;

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            if (moving == true) {
                final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
            }
        }
    }

    public void Click_Start_Fast(View view) {
        moving = true;
        gMap.clear();
        now_Location.setVisibility(View.VISIBLE);
        Start_First_Layout.setVisibility(View.GONE);
        End.setVisibility(View.VISIBLE);
        routeInfo.set_selectMenu(2);
//        routeInfo.moving = true;

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            if (moving == true) {
                final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
            }
        }
    }

    public void Click_End(View view) {
        /*
        SQLiteDatabase sqlDB=null;
        sqlDB=SQLiteDatabase.openOrCreateDatabase(routeInfo.name,null);

        sqlDB.execSQL("CREATE TABLE EXERCISE("+
                "double latitude," +
                "double longitude," +
                "double speed"+
                ")");
*/
        if(Vibe==true){
            Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
        }
        if(Sound==true){
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),notification);
            ringtone.play();
        }

        moving = false;
        gMap.clear();

        ////
        AlertDialog.Builder builder_ = new AlertDialog.Builder(this);
        builder_.setIcon(android.R.drawable.ic_dialog_alert);
        builder_.setTitle("알림");
        builder_.setMessage("경로를 저장하시겠습니까?");
        builder_.setPositiveButton("예", dialogListener);
        builder_.setNegativeButton("아니오", null);

        alertDialog = builder_.create();
        alertDialog.show();
        ////

        Start_First_Layout.setVisibility(View.VISIBLE);
        End.setVisibility(View.GONE);
        Timer.setVisibility(View.GONE);

        routeInfo.clear();
        Speed.setText("끝!");
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(dialog == alertDialog && which == DialogInterface.BUTTON_POSITIVE){
                //새로 알람띄워서 입력받고 경로 저장.
                setPathSaveDialog();
            }
        }
    };

    public void setPathSaveDialog(){
        final EditText input = new EditText(this);

        AlertDialog.Builder builder_ = new AlertDialog.Builder(this);
        builder_.setIcon(android.R.drawable.ic_menu_directions);
        builder_.setTitle("경로 이름 입력");
        builder_.setView(input);

        builder_.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues value = new ContentValues();

                SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String time1 = format.format(time);


                //이부분에 끝나고 저장할 것들 넣어주면 댑니다. 다 String 타입으로 통일했어요
                if(input.getText().toString().length() <= 0){
                    routeInfo.name = time1;//입력이 없으면 시간으로
                }
                else{
                    routeInfo.name = input.getText().toString();// 입력이 있으면 그 입력으로 DB에 저장.
                }
                String tmpSpeed="";
                for(int i=0; i<routeInfo.arraySpeeds.size();i++){
                    tmpSpeed = tmpSpeed.concat(Double.toString(routeInfo.arraySpeeds.get(i).doubleValue()));
                    //tmpSpeed=tmpSpeed.concat(routeInfo.arraySpeeds.get(i).toString()+"/");
                    if(i<routeInfo.arraySpeeds.size()-1){
                        tmpSpeed = tmpSpeed.concat("/");
                    }
                }
                String tmpLat="";
                String tmpLong="";
                for(int i=0;i<routeInfo.arrayPoints.size();i++){
                    tmpLat=tmpLat.concat(Double.toString(routeInfo.arrayPoints.get(i).latitude));
                    tmpLong=tmpLong.concat(Double.toString(routeInfo.arrayPoints.get(i).longitude));
                    if(i<routeInfo.arrayPoints.size()-1){
                        tmpLat = tmpLat.concat("/");
                        tmpLong = tmpLong.concat("/");
                    }
                }
                value.put("title", routeInfo.name);
                value.put("speed",tmpSpeed);
                value.put("latitude",tmpLat);
                value.put("longitude",tmpLong);
                value.put("cal", routeInfo.cal + "kcal");

                db.insert("log", null, value);
            }
        });
        builder_.setNegativeButton("아니오", null);

        pathSaveDialog = builder_.create();
        pathSaveDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Select_More_Layout = (LinearLayout) findViewById(R.id.Select_More);
        Select_Start_Layout = (RelativeLayout) findViewById(R.id.Select_Start);
        Start_First_Layout = (LinearLayout) findViewById(R.id.Start_First);
        Timer = (LinearLayout)findViewById(R.id.Timer);

        now_Location = (ImageButton) findViewById(R.id.Now_Location);

        Start_More = (ImageButton) findViewById(R.id.Start_More);
        Start_Start = (ImageButton) findViewById(R.id.Start_Start);
        Start_Fast = (ImageButton) findViewById(R.id.Start_Fast);
        Start_Timer = (ImageButton) findViewById(R.id.Start_Timer);
        Start_Path = (ImageButton) findViewById(R.id.Start_Path);
        End = (ImageButton) findViewById(R.id.End);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        in=(EditText)findViewById(R.id.time);

        prefs= PreferenceManager.getDefaultSharedPreferences(this);

        HowToEx = prefs.getString("exercise","걷기");
        Vibe = prefs.getBoolean("Vibrate",true);
        Sound = prefs.getBoolean("Sound",true);


        helper = new DBHelper(this);
    }

/*
    CalcDistance(시작 위치, 나중 위치)
        return Km;
 */
    public double CalcDistance(LatLng s_latlng, LatLng l_latlng){
        double dDistance = 0;
        double dLat1Rad = ((double)s_latlng.latitude)*(Math.PI/180.0);
        double dLong1Rad = ((double)s_latlng.longitude)*(Math.PI/180.0);
        double dLat2Rad = ((double)l_latlng.latitude)*(Math.PI/180.0);
        double dLong2Rad = ((double)l_latlng.longitude)*(Math.PI/180.0);

        double dLongitude = dLong2Rad - dLong1Rad;
        double dLatitude = dLat2Rad - dLat1Rad;
        double a = Math.pow(Math.sin(dLatitude/2.0), 2.0) + Math.cos(dLat1Rad) * Math.cos(dLat2Rad) * Math.pow(Math.sin(dLongitude/2.0),2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
        double kEarth = 6376.5;
        dDistance = kEarth * c;

        return dDistance;
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if(moving == true) {
                // String provider = location.getProvider();
                p_lng = location.getLongitude();
                p_lat = location.getLatitude();
                Double latitude = p_lat;
                Double longitude = p_lng;
                LatLng p_latlng = new LatLng(p_lat, p_lng);


 //               if (routeInfo.arrayLocations.size() >= 0) {
                    routeInfo.addWayPoint(location);
                    if (routeInfo.arrayPoints.size() >= 1 && routeInfo.arraymarkerPoints.size() - 1 >= count) {
                        Marker p_marker = mMap.addMarker(routeInfo.arraymarkerPoints.get(count));
//                    for(int i=0; i<routeInfo.arraymarkerPoints.size()-1; i++) {
//                        Marker marker = mMap.addMarker(routeInfo.arraymarkerPoints.get(i));
//                    }
                        LatLng point = p_marker.getPosition();
                        if (routeInfo.arrayPoints.size() >= 1) {
                            routeInfo.arraymarkerPoints.get(routeInfo.arraymarkerPoints.size() - 1).snippet(String.valueOf(CalcDistance(point, routeInfo.arrayPoints.get(routeInfo.arrayPoints.size() - 1))));
                        } else {
                            routeInfo.arraymarkerPoints.get(routeInfo.arraymarkerPoints.size() - 1).snippet(latitude.toString() + "," + longitude.toString());
                        }
                        if (CalcDistance(point, routeInfo.arrayPoints.get(routeInfo.arrayPoints.size() - 1)) <= 0.005) {
                            Speed.setText("도착!");
                            if (routeInfo.arraymarkerPoints.size() - 1 >= count) {
                                count++;
                            }
                        } else {
                            Speed.setText("가는중!");
                        }
                    } else {
                        Speed.setText("경로 미정");
                    }
//                arrayPoints.add(p_latlng);
//              LatLng s_latlng = arrayPoints.get(arrayPoints.size()-2);
//              LatLng l_latlng = arrayPoints.get(arrayPoints.size()-1);
//                LatLng s_latlng = routeInfo.arrayPoints.get(routeInfo.arrayPoints.size()-2);
//                LatLng l_latlng = routeInfo.arrayPoints.get(routeInfo.arrayPoints.size()-1);
                    if (routeInfo.arrayLocations.size() >= 2) {
                        if (routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 2).distanceTo(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 1)) < 10) {
                            if (routeInfo.arrayLocations.size() >= 3) {
                                if (routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 3).distanceTo(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 2))
                                        > routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 3).distanceTo(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 1))) {
                                    routeInfo.remove(routeInfo.arrayLocations.size() - 2);
                                    String.valueOf(routeInfo.arraySpeeds.get(routeInfo.arraySpeeds.size() - 1));

                                }
                            }
                            if (routeInfo.vectorArrow(routeInfo.arrayVector.get(routeInfo.arrayVector.size() - 2), routeInfo.arrayVector.get(routeInfo.arrayVector.size() - 1))
                                    <= (1 / Math.sqrt(2.0))) {
                                if (routeInfo.degree_b == true) {
                                    routeInfo.degree_b = false;
                                } else {
                                    routeInfo.remove(routeInfo.arrayLocations.size() - 2);
                                    routeInfo.degree_b = true;

                                    drawPath(location, p_latlng, (routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 2).distanceTo(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 1)) + "m"));

//                            Speed.setText( routeInfo.arraySpeeds.get(routeInfo.arraySpeeds.size() - 1) + " m/s");
//                            gMap.clear();
//
//                            MarkerOptions mOptions = new MarkerOptions();
//                            mOptions.title("마커 좌표");
//
//
//                            mOptions.snippet(String.valueOf(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 2).distanceTo(routeInfo.arrayLocations.get(routeInfo.arrayLocations.size() - 1))));
//                            mOptions.position(p_latlng);
//
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 18));
//                            gMap.addMarker(mOptions);
//
//
//                            PolylineOptions polylineOptions = new PolylineOptions();
//                            polylineOptions.color(Color.RED);
//                            polylineOptions.width(5);
//                            polylineOptions.addAll(routeInfo.arrayPoints);
//                            gMap.addPolyline(polylineOptions);
                                }
                                //routeInfo.arraySpeeds.get(routeInfo.arraySpeeds.size() - 1)) + "m/s"
                            } else {
                                routeInfo.cal = routeInfo.getCal(routeInfo.get_totaltime());
                                drawPath(location, p_latlng, String.valueOf(routeInfo.cal) + "cal");
                                String.valueOf(routeInfo.arraySpeeds.get(routeInfo.arraySpeeds.size() - 1));

                            }
                        } else {
                            routeInfo.remove(routeInfo.arrayLocations.size() - 1);
                        }

                    } else {
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 20));
                        drawPath(location, p_latlng, latitude.toString() + "," + longitude.toString());
//                Speed.setText("0 m/s");
//                gMap.clear();
//
//                MarkerOptions mOptions = new MarkerOptions();
//                mOptions.title("마커 좌표");
//
//
//                mOptions.snippet(latitude.toString() + "," + longitude.toString());
//                mOptions.position(p_latlng);
//
//                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 18));
//                gMap.addMarker(mOptions);
//
////                arrayPoints.add(p_latlng);
//                routeInfo.addWayPoint(location);
//
//
//                PolylineOptions polylineOptions = new PolylineOptions();
//                polylineOptions.color(Color.RED);
//                polylineOptions.width(5);
//                polylineOptions.addAll(routeInfo.arrayPoints);
//                gMap.addPolyline(polylineOptions);
                    }
                }

                // Circle circle = mMap.addCircle(new CircleOptions().center(new LatLng(p_lat, p_lng)).radius(3).strokeColor(Color.RED).fillColor(Color.BLUE));
 //           }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

    };


    public void drawPath(Location location, LatLng p_latlng, String string) {
        p_lng = p_latlng.longitude;
        p_lat = p_latlng.latitude;
        Double latitude = p_lat;
        Double longitude = p_lng;

        gMap.clear();

        MarkerOptions mOptions = new MarkerOptions();
        MarkerOptions pOptions = new MarkerOptions();
        mOptions.title("마커 좌표");


        mOptions.snippet(string);
        mOptions.position(p_latlng);

//        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 20));
        gMap.addMarker(mOptions);
        if(routeInfo.arraymarkerPoints.size()>=1) {
//            pOptions = routeInfo.arraymarkerPoints.get(routeInfo.arraymarkerPoints.size() - 1);
//            gMap.addMarker(pOptions);
            for(int i=count; i<=routeInfo.arraymarkerPoints.size()-1; i++) {
                mMap.addMarker(routeInfo.arraymarkerPoints.get(i));
            }
        }


//                arrayPoints.add(p_latlng);
        routeInfo.addWayPoint(location);


        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(routeInfo.arrayPoints);
        gMap.addPolyline(polylineOptions);
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        gMap = googleMap;


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    try {
                        if (routeInfo.select_menu == 3) {
                            MarkerOptions mOptions = new MarkerOptions();
                            mOptions.title("마커 좌표");
                            Double latitude = point.latitude;
                            Double longitude = point.longitude;

                            mOptions.position(new LatLng(latitude, longitude));
                            mMap.addMarker(mOptions);
                            for(int i=0; i<routeInfo.arraymarkerPoints.size()-1; i++) {
                                mMap.addMarker(routeInfo.arraymarkerPoints.get(i));
                            }
                            routeInfo.addMarkerPoint(mOptions);

                            if(routeInfo.arrayPoints.size()>=1){
                                mOptions.snippet(String.valueOf(CalcDistance(point, routeInfo.arrayPoints.get(routeInfo.arrayPoints.size() - 1))));
                            }else{
                                mOptions.snippet(latitude.toString() + "," + longitude.toString());
                            }



                            if(routeInfo.arrayPoints.size()>=1) {
                                if (CalcDistance(point, routeInfo.arrayPoints.get(0)) <= 0.005) {
                                    Speed.setText("도착!");
                                } else {
                                    Speed.setText("가는중!");
                                }
                            }else{
                                Speed.setText("위치 미정");
                            }


                        }
                    }catch(NullPointerException e){

                    }

                    //
                    //                Double latitude = point.latitude;
                    //                Double longitude = point.longitude;
                    //                LatLng p_latlng = new LatLng(latitude, longitude);

                    //                if(arrayPoints.size()>=1){
                    //                    arrayPoints.add(p_latlng);
                    //                    LatLng s_latlng = arrayPoints.get(arrayPoints.size()-2);
                    //                    LatLng l_latlng = arrayPoints.get(arrayPoints.size()-1);
                    //
                    //                    if(CalcDistance(s_latlng, l_latlng) < 0.1) {
                    //
                    //                        //gMap.clear();
                    //
                    //                        MarkerOptions mOptions = new MarkerOptions();
                    //                        mOptions.title("마커 좌표");
                    //
                    //
                    //                        mOptions.snippet(String.valueOf(CalcDistance(s_latlng, l_latlng)));
                    //                        mOptions.position(p_latlng);
                    //
                    //                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 18));
                    //                        gMap.addMarker(mOptions);
                    //
                    //
                    //
                    //                        PolylineOptions polylineOptions = new PolylineOptions();
                    //                        polylineOptions.color(Color.RED);
                    //                        polylineOptions.width(5);
                    //                        polylineOptions.addAll(arrayPoints);
                    //                        gMap.addPolyline(polylineOptions);
                    //                    }else{
                    //                        arrayPoints.remove(arrayPoints.size()-1);
                    //                    }
                    //
                    //                }else{
                    //                    //gMap.clear();
                    //
                    //                    MarkerOptions mOptions = new MarkerOptions();
                    //                    mOptions.title("마커 좌표");
                    //
                    //
                    //                    mOptions.snippet(latitude.toString() + "," + longitude.toString());
                    //                    mOptions.position(p_latlng);
                    //
                    //                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p_latlng, 18));
                    //                    gMap.addMarker(mOptions);
                    //
                    //                    arrayPoints.add(p_latlng);
                    //
                    //                    PolylineOptions polylineOptions = new PolylineOptions();
                    //                    polylineOptions.color(Color.RED);
                    //                    polylineOptions.width(5);
                    //                    polylineOptions.addAll(arrayPoints);
                    //                    gMap.addPolyline(polylineOptions);
                    //                }
                }

            });





            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                public void onMapLongClick (LatLng point){
                    mMap.clear();
                    routeInfo.clear();

                }
            });


            // Add a marker in start and move the camera
            LatLng start = new LatLng(37.5193, 126.9778);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(start));


    }
}