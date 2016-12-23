package ru.ssau.mobile.ssau_mobile_task4;

import android.content.Context;
import android.location.Geocoder;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

/**
 * Created by Pavel on 23.12.2016.
 */

public class AddressSearchAutoComplete extends AutoCompleteTextView {

    private Geocoder geocoder;

    private static final String TAG = "AutoCompleteTextView";

    public AddressSearchAutoComplete(Context context, Geocoder geocoder) {
        super(context);
        this.geocoder = geocoder;
    }

    public AddressSearchAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        super.performFiltering(text, keyCode);
        Log.d(TAG, "performFiltering: "+text);
    }
}
