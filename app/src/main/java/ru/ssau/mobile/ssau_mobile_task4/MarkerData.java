package ru.ssau.mobile.ssau_mobile_task4;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pavel on 21.12.2016.
 */

public class MarkerData implements Serializable{
    double lat, lng;
    int iconId = -1;
    String title;
    ArrayList<String> photos;

    public MarkerData(double lat, double lng, int iconId, String title) {
        this.lat = lat;
        this.lng = lng;
        this.iconId = iconId;
        this.title = title;
    }
    public MarkerData(MarkerOptions m) {
        update(m);
    }
    public MarkerData(Marker m) {
        update(m);
    }

    private void update(Marker m) {
        LatLng latLng = m.getPosition();
        lat = latLng.latitude;
        lng = latLng.longitude;
        title = m.getTitle();
    }

    private void update(MarkerOptions m) {
        LatLng latLng = m.getPosition();
        lat = latLng.latitude;
        lng = latLng.longitude;
        title = m.getTitle();
    }

    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    public MarkerOptions getMarkerOptions(Context context) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions mo = new MarkerOptions().position(latLng).title((title == null ? "" : title));
        if (iconId != -1) {
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    iconId);
            icon = Bitmap.createScaledBitmap(icon, 100, 100, false);
            mo = mo.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
        return mo;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
