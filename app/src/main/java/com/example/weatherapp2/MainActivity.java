package com.example.weatherapp2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.weatherapp2.R.id.icon;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_PERMISSION = 1;
    LocationManager locationManager;

    RequestQueue requestQueue;

    TextView _city,_temp,_description,_date,_press,_humid,_wind;
    ImageView _icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _city=(TextView) findViewById(R.id.city);
        _temp=(TextView) findViewById(R.id.tem);
        _description=(TextView) findViewById(R.id.desc);
        _date=(TextView) findViewById(R.id.date);
        _press=(TextView) findViewById(R.id.press);
        _wind=(TextView) findViewById(R.id.wind);
        _humid     =(TextView) findViewById(R.id.humid);










        requestQueue= Volley.newRequestQueue(getApplicationContext());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        _getLocation();

        double latitu=_latitude();
        double longi=_longitude();

        GetWeather( latitu,longi);
    }


    public void _getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location!=null){
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

            Log.d("Main",String.valueOf(latitude));
            Log.d("Main",String.valueOf(longitude));


        }



    }

    public double _latitude() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 0;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location!=null){
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

            Log.d("Main",String.valueOf(latitude));
            Log.d("Main",String.valueOf(longitude));


        }

        return location.getLatitude();
    }

    public double _longitude() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 0;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location!=null){
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

            Log.d("Main",String.valueOf(latitude));
            Log.d("Main",String.valueOf(longitude));


        }

        return location.getLongitude();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case (REQUEST_PERMISSION):{
                _getLocation();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Main","In Resume");

        GetWeather(_latitude(),_longitude());

    }

    public void GetWeather(double latitu,double longi){

        String URL="https://api.openweathermap.org/data/2.5/weather?lat="+latitu+"&lon="+longi+"&appid=15a016ffc1428e2df72e1f00eef21a9a";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object= response.getJSONObject("main");
                    JSONObject wind_object=response.getJSONObject("wind");
                    JSONArray array=response.getJSONArray("weather");
                    JSONObject object=array.getJSONObject(0);

                    String temp=String.valueOf(main_object.getDouble("temp"));
                    String press=main_object.getString("pressure");
                    String humid=main_object.getString("humidity");
                    String description=object.getString("description");
                    String icon=object.getString("icon");
                    String city=response.getString("name");
                    String wind=wind_object.getString("speed");


                    _city.setText(city);
                    _press.setText(press);
                    _humid.setText(humid);
                    _description.setText(description.toUpperCase());
                    _wind.setText(wind);


                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf= new SimpleDateFormat("EEEE-dd-MM");
                    String formatted_date=sdf.format(calendar.getTime());

                    _date.setText(formatted_date);

                    double temp_int= Double.parseDouble(temp);
                    double centi = (temp_int -273.15);
                    centi=Math.round(centi);
                    int p=(int) centi;
                    _temp.setText(String.valueOf(p));




                    ImageView _icon;
                    _icon= findViewById(R.id.icon);

                    switch(icon){

                        case "01d":{

                            _icon.setImageResource(R.drawable.sun);
                        }


                        case "01n":{

                            _icon.setImageResource(R.drawable.two);
                        }

                        case "02d":{

                            _icon.setImageResource(R.drawable.cloudy);
                        }

                        case "02n":{

                            _icon.setImageResource(R.drawable.four);
                        }
                        case "03d":{

                            _icon.setImageResource(R.drawable.five);
                        }
                        case "03n":{

                            _icon.setImageResource(R.drawable.six);
                        }
                        case "04d":{

                            _icon.setImageResource(R.drawable.seven);
                        }
                        case "04n":{

                            _icon.setImageResource(R.drawable.eight);
                        }
                        case "09d":{

                            _icon.setImageResource(R.drawable.rain);
                        }
                        case "09n":{

                            _icon.setImageResource(R.drawable.rain);
                        }
                        case "10d":{

                            _icon.setImageResource(R.drawable.eleven);
                        }
                        case "10n":{

                            _icon.setImageResource(R.drawable.twelve);
                        }
                        case "11d":{

                            _icon.setImageResource(R.drawable.thirteen);
                        }case "11n":{

                            _icon.setImageResource(R.drawable.forteen);
                        }
                        case "13d":{

                            _icon.setImageResource(R.drawable.fifteen);
                        }
                        case "13n":{

                            _icon.setImageResource(R.drawable.sixteen);
                        }
                        case "50n":{

                            _icon.setImageResource(R.drawable.seventeen);
                        }
                        case "50d":{

                            _icon.setImageResource(R.drawable.eighteen);
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

     }












}
