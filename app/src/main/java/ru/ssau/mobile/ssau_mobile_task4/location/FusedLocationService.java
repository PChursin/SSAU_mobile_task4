package ru.ssau.mobile.ssau_mobile_task4.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pavel on 22.12.2016.
 */

public class FusedLocationService implements LocationListener, GoogleApiClient.ConnectionCallbacks {
    private static final long INTERVAL = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;

    private LocationRequest locationRequest;
    private FusedLocationReceiver receiver;
    private GoogleApiClient googleApiClient;
    private Location location;
    Activity locationActivity;
    private static final float MINIMUM_ACCURACY = 50.0f;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    private static final String TAG = "FusedLocationService";


    public FusedLocationService(Activity mapActivity, FusedLocationReceiver receiver) {
        this.receiver = receiver;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.locationActivity = mapActivity;

        googleApiClient = new GoogleApiClient.Builder(locationActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                //.addOnConnectionFailedListener(this)
                .build();

        if (googleApiClient != null) {
            Log.d(TAG, "FusedLocationService: before connect");
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "I'm connected now");
        if (ActivityCompat.checkSelfPermission(locationActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "onConnected: no permissions!");
            return;
        }
        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null && currentLocation.getTime() > REFRESH_TIME) {
            location = currentLocation;
            Log.d(TAG, "onConnected: lat "+location.getLatitude() + " lng " + location.getLongitude());
            receiver.onLocationChanged();
        } else {
            Log.d(TAG, "onConnected: requesting location update listener");
            fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            // Schedule a Thread to unregister location listeners
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: removing location update listener");
                    fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
                            (com.google.android.gms.location.LocationListener) FusedLocationService.this);
                }
            }, ONE_MIN, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location is changed!");
        //if the existing location is empty or
        //the current location accuracy is greater than existing accuracy
        //then store the current location
        if (null == this.location || location.getAccuracy() < this.location.getAccuracy()) {
            this.location = location;
// let's inform my client class through the receiver
            receiver.onLocationChanged();
            //if the accuracy is not better, remove all location updates for this listener
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
            }
        }
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
