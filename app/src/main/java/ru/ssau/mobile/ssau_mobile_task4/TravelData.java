package ru.ssau.mobile.ssau_mobile_task4;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pavel on 24.12.2016.
 */

public class TravelData implements Serializable{
    String title;
    ArrayList<MarkerData> markers;
    ArrayList<String> photos;
    int color;
}
