package ru.ssau.mobile.ssau_mobile_task4;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import ru.ssau.mobile.ssau_mobile_task4.adapters.IconChooserAdapter;
import ru.ssau.mobile.ssau_mobile_task4.adapters.RVAdapter;

/**
 * Created by Pavel on 24.12.2016.
 */

public class EditActivity extends AppCompatActivity {
    ImageButton iconButton;
    EditText titleField;
    RecyclerView recyclerView;
    RVAdapter adapter;
    ArrayList<Uri> links;
    MarkerData markerData;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        markerData = (MarkerData) getIntent().getSerializableExtra("marker");

        iconButton = (ImageButton) findViewById(R.id.button_icon);
        titleField = (EditText) findViewById(R.id.field_title);
        recyclerView = (RecyclerView) findViewById(R.id.edit_photos);
        submitButton = (Button) findViewById(R.id.button_submit);

        iconButton.setImageDrawable(ResourcesCompat.getDrawable(
                EditActivity.this.getResources(), markerData.iconId, null));
        titleField.setText(markerData.title);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        links = new ArrayList<>();
        if (markerData.photos != null)
            for (String s : markerData.photos) {
                links.add(Uri.parse(s));
            }

        adapter = new RVAdapter(this, links);
        recyclerView.setAdapter(adapter);

        iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.icon_chooser, null);
                dialogBuilder.setView(convertView);
                dialogBuilder.setTitle("Choose marker icon");
                dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                final IconChooserAdapter adapter = new IconChooserAdapter(EditActivity.this, MapsActivity.MARKER_ICONS, markerData);
                dialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        markerData.setIconId(adapter.getSelectedId());
                        iconButton.setImageDrawable(ResourcesCompat.getDrawable(
                                EditActivity.this.getResources(), markerData.iconId, null));
                    }
                });
                GridView grid = (GridView) convertView.findViewById(R.id.icon_chooser);
                grid.setAdapter(adapter);
                dialogBuilder.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                markerData.title = titleField.getText().toString();
                ArrayList<String> photos = new ArrayList<String>();
                ArrayList<Uri> links = adapter.getData();
                for (int i = 0; i < links.size()-1; i++)
                    photos.add(links.get(i).toString());
                markerData.photos = photos;
                returnIntent.putExtra("marker", markerData);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RVAdapter.GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                adapter.getData().add(0, selectedImage);
                adapter.notifyItemInserted(0);
            }
        }
    }
}
