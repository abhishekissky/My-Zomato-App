package com.example.myzomatoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity   {

    String lat, lon;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView recyclerView;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        searchView = (SearchView) findViewById(R.id.search_bar);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermissions();
        getData();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100 &&grantResults.length>0 && (grantResults[0] + grantResults[1])==PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
    }

    //Retrofit
    private void getData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        String lati = Double.toString(lat);
//        String longt= Double.toString(lon);
        ZomatoApiInterface zomatoApiInterface = retrofit.create(ZomatoApiInterface.class);
        zomatoApiInterface.getData(lat,lon).enqueue(new Callback<Zomato>() {

            @Override
            public void onResponse(Call<Zomato> call, Response<Zomato> response) {
                Zomato zomato = response.body();
                Log.v("Tab", String.valueOf(zomato.getResults_found()));
                Log.v("Lat",lat +"\n"+lon);
                List<Restaurants> list = new ArrayList<>(Arrays.asList(zomato.getRestaurants()));
                for (Restaurants restaurants:list){
                    Log.v("Tab",restaurants.toString());
                    sendData(list);
                }
            }

            @Override
            public void onFailure(Call<Zomato> call, Throwable t) {
                t.printStackTrace();

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                zomatoApiInterface.getSearchData(query).enqueue(new Callback<Zomato>() {
                    @Override
                    public void onResponse(Call<Zomato> call, Response<Zomato> response) {
                        Zomato zomato = response.body();
                        List<Restaurants> list = new ArrayList<>(Arrays.asList(zomato.getRestaurants()));
                        for (Restaurants restaurants:list){
                            Log.v(" ",restaurants.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<Zomato> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void sendData(List<Restaurants> list) {
        Adapter adapter = new Adapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            //when location service is enable
            //get last location
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location

                    Location location = task.getResult();
                    //check condition
                    if (location != null){
                        Log.v("Ram lal", location.getLatitude() +"\n"+ location.getLongitude());
                        lat = String.valueOf(location.getLatitude());
                        lon = String.valueOf(location.getLongitude());

                    }else {
                        LocationRequest request = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                Log.v("Ram lal", String.valueOf(location1.getLatitude()));
                                Log.v("Ram lal", String.valueOf(location1.getLongitude()));
                                lat = String.valueOf(location1.getLatitude());
                                lon = String.valueOf(location1.getLongitude());
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(request,locationCallback, Looper.myLooper());
                    }
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


}