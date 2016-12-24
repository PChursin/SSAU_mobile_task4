package ru.ssau.mobile.ssau_mobile_task4.adapters;

import android.content.Context;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 23.12.2016.
 */

public class SearchAdapter extends ArrayAdapter<Address> {

    List<Address> data = new ArrayList<>();
    int resource;
    Context context;
    private static final String TAG = "SearchAdapter";


    public SearchAdapter(Context context, int resource, List<Address> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        notifyDataSetInvalidated();
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        Address a = data.get(position);
        ViewHolder viewHolder;
        Log.d(TAG, "getView!");
        if (view == null) {
            //view = LayoutInflater.from(context).inflate(resource, parent, true);
            view = LayoutInflater.from(this.getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.line = (TextView) view.findViewById(android.R.id.text1);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int maxLine = a.getMaxAddressLineIndex();
        String res = a.getAddressLine(0)+
                (maxLine > 0 ? ", "+a.getAddressLine(1)+
                (maxLine > 1 ? ", "+a.getAddressLine(2) : "") : "");
        viewHolder.line.setText(res);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public Address getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
        notifyDataSetInvalidated();
    }

    private static class ViewHolder {
        TextView line;
    }
}
