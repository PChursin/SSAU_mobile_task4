package ru.ssau.mobile.ssau_mobile_task4;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

/**
 * Created by Pavel on 21.12.2016.
 */

public class MarkerData implements Serializable{
    MarkerOptions marker;

    public MarkerData(MarkerOptions m) {
        marker = m;
    }
}
