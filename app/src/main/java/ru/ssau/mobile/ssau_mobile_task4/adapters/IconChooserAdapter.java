package ru.ssau.mobile.ssau_mobile_task4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import ru.ssau.mobile.ssau_mobile_task4.MarkerData;
import ru.ssau.mobile.ssau_mobile_task4.R;

/**
 * Created by Pavel on 22.12.2016.
 */

public class IconChooserAdapter extends BaseAdapter {
    Context context;
    int[] data;
    MarkerData marker;
    View lastView;
    int selectedId;

    public IconChooserAdapter(Context context, int[] objects, MarkerData marker) {
        this.context = context;
        data = objects;
        this.marker = marker;
        selectedId = data[0];
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        final int iconId = data[position];

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.icon_item, parent, false);


        } else {
            //viewHolder = (ViewHolder) view.getTag();
        }
        final ImageButton button = (ImageButton) view.findViewById(R.id.icon_chooser_image);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                iconId);
        button.setImageDrawable(new BitmapDrawable(context.getResources(), icon));
        button.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.colorGray, null));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marker.setIconId(iconId);
                if (lastView != null)
                    lastView.setBackgroundColor(ResourcesCompat.getColor(
                            context.getResources(), R.color.colorGray, null));
                view.setBackgroundColor(Color.GREEN);
                lastView = view;
                selectedId = iconId;
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.length;
    }

    @Override
    public Integer getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return getItem(i);
    }

    public int getSelectedId() {
        return selectedId;
    }

    /*
    @Nullable
    @Override
    public Integer getItem(int position) {
        if (data == null)
            return null;
        return data[position];
    }*/

    /*
    @Override
    public long getItemId(int i) {
        return 0;
    }*/
}
