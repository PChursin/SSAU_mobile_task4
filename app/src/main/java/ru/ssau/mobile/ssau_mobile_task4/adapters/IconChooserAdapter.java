package ru.ssau.mobile.ssau_mobile_task4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import ru.ssau.mobile.ssau_mobile_task4.R;

/**
 * Created by Pavel on 22.12.2016.
 */

public class IconChooserAdapter extends BaseAdapter {
    Context context;
    int[] data;
    Marker marker;

    public IconChooserAdapter(Context context, int[] objects, Marker marker) {
        this.context = context;
        data = objects;
        this.marker = marker;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        int iconId = data[position];

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.icon_item, parent, false);


        } else {
            //viewHolder = (ViewHolder) view.getTag();
        }
        ImageButton button = (ImageButton) view.findViewById(R.id.icon_chooser_image);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                iconId);
        button.setImageDrawable(new BitmapDrawable(context.getResources(), icon));
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
