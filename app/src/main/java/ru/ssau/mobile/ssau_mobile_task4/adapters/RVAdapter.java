package ru.ssau.mobile.ssau_mobile_task4.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ru.ssau.mobile.ssau_mobile_task4.EditActivity;
import ru.ssau.mobile.ssau_mobile_task4.R;

/**
 * Created by Pavel on 24.12.2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    public static final int GALLERY_REQUEST = 24;
    ArrayList<Uri> data;
    private final String TAG = "RVAdapter";
    Context context;

    public RVAdapter(Context context, ArrayList<Uri> photos) {
        this.context = context;
        if (photos == null)
            data = new ArrayList<>();
        else
            data = photos;
        data.add(data.size(), null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);

        ViewHolder holder = new ViewHolder(v);
        holder.itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Uri link = data.get(position);
        if (link == null) {
            holder.cross.setVisibility(View.GONE);
            holder.photo.setImageResource(R.drawable.ic_photo);
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    ((EditActivity) context).startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                }
            });
        } else {
            holder.cross.setVisibility(View.VISIBLE);
            holder.cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //photoOperations.deletePhoto(photo);
                    data.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), link);
                BitmapDrawable bd = new BitmapDrawable(context.getResources(),
                        getSmallImage(getBitmapAsByteArray(bitmap), context));
                holder.photo.setImageDrawable(bd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton photo, cross;

        public ViewHolder(View itemView) {
            super(itemView);

            photo = (ImageButton) itemView.findViewById(R.id.photo_content);
            cross = (ImageButton) itemView.findViewById(R.id.photo_delete);
        }
    }

    public ArrayList<Uri> getData() {
        return data;
    }

    public void setData(ArrayList<Uri> data) {
        this.data = data;
    }

    public static Bitmap getSmallImage(byte[] image, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
        options.outHeight = (int) context.getResources().getDimension(R.dimen.photo_size);
        options.outWidth = (int) context.getResources().getDimension(R.dimen.photo_size);
        options.inSampleSize = 16;
        options.inScaled = true;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
