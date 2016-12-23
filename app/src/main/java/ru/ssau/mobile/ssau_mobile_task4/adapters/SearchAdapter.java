package ru.ssau.mobile.ssau_mobile_task4.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pavel on 23.12.2016.
 */

public class SearchAdapter extends ArrayAdapter<String> {

    ArrayList<String> data = new ArrayList<>();
    int resource;
    Context context;



    public SearchAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        TextView text = (TextView) view.findViewById(android.R.id.text1);
        text.setText(data.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetInvalidated();
    }
}
