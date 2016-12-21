package ru.ssau.mobile.ssau_mobile_task4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    public static final int[] MARKER_ICONS = {R.drawable.blue1, R.drawable.blue2, R.drawable.blue3, R.drawable.blue4, R.drawable.blue5,
            R.drawable.blue6, R.drawable.blue7, R.drawable.red1, R.drawable.red2, R.drawable.red3, R.drawable.red4, R.drawable.red5,
            R.drawable.red6, R.drawable.red7, R.drawable.green1, R.drawable.green2, R.drawable.green3, R.drawable.green4, R.drawable.green5,
            R.drawable.green6, R.drawable.green7, R.drawable.orange1, R.drawable.orange2, R.drawable.orange3, R.drawable.orange4,
            R.drawable.orange5, R.drawable.orange6, R.drawable.orange7, R.drawable.blue_pin, R.drawable.red_pin, R.drawable.orange_pin, R.drawable.green_pin};
    public static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private ArrayList<MarkerData> markers;
    private HashMap<String, MarkerData> idToData;
    private View markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        idToData = new HashMap<>();
        //test
        if (savedInstanceState == null) {
            LatLng sydney = new LatLng(-34, 151);
            MarkerOptions m = new MarkerOptions().position(sydney).title("Marker in Sydney");
            markers = new ArrayList<>();
            markers.add(new MarkerData(m));
        } else {
            markers = (ArrayList<MarkerData>) savedInstanceState.get("markers");
        }

        markerOptions = findViewById(R.id.marker_buttons);
        Button left = (Button) markerOptions.findViewById(R.id.marker_left_button);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: LEFT");
            }
        });
        Button right = (Button) markerOptions.findViewById(R.id.marker_right_button);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: RIGHT");
            }
        });
        //end
    }

    public void showMarkerOptions() {
        markerOptions.setVisibility(View.VISIBLE);
    }

    public void hideMarkerOptions() {
        markerOptions.setVisibility(View.GONE);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            //private final View window = getLayoutInflater().inflate(R.layout.info_window, null);
            @Override
            public View getInfoWindow(Marker marker) {
                showMarkerOptions();
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        mMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                hideMarkerOptions();
            }
        });

        // Add a marker in Sydney and move the camera
        for (MarkerData md : markers) {
            Marker m = mMap.addMarker(md.getMarkerOptions(this));
            idToData.put(m.getId(), md);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(markers.size()-1).getPosition()));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick");
        marker.showInfoWindow();
        MarkerData md = idToData.get(marker.getId());
        int pos = (int) (Math.random()*MARKER_ICONS.length);
        //BitmapDescriptor desc = BitmapDescriptorFactory.fromResource(MARKER_ICONS[pos]);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                MARKER_ICONS[pos]);
        icon = Bitmap.createScaledBitmap(icon, 100, 100, false);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
        md.setIconId(MARKER_ICONS[pos]);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("markers", markers);
        super.onSaveInstanceState(outState);
    }
}
