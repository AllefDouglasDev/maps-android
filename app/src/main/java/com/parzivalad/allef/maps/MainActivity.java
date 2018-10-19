package com.parzivalad.allef.maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Maps";

    private SupportMapFragment mapFragment;

    private TextView txtPlayer01;

    private TextView txtPlayer02;

    private GoogleMap map;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        txtPlayer01 = (TextView) findViewById(R.id.player01);

        txtPlayer02 = (TextView) findViewById(R.id.player02);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                iniciarMapa();
            }
        });

        spinner = (Spinner) findViewById(R.id.type_maps);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_maps_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void iniciarMapa() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng masmorra = new LatLng(-8.298635, -35.974063);
        LatLng caverna = new LatLng(-8.2947765,-35.9798719);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(masmorra, 15);

        map.animateCamera(update, 3000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Bem-vindos", Toast.LENGTH_SHORT).show();
                adicionarMarcadores(masmorra, caverna);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void adicionarMarcadores(LatLng latLng, LatLng latLng2) {
        MarkerOptions m0 = new MarkerOptions();
        m0.draggable(true);
        m0.position(latLng).title("Player 01").snippet("Masmorra");
        m0.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_red_round));
        map.addMarker(m0);

        MarkerOptions m1 = new MarkerOptions();
        m1.draggable(true);
        m1.position(latLng2).title("Player 02").snippet("Caverna");
        m1.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_blue_round));
        map.addMarker(m1);

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.w(TAG, "onMarkerDragStart: " + marker.getId());
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.w(TAG, "onMarkerDragEnd: " + marker.getPosition());
                if (marker.getId().equals("m0"))
                    txtPlayer01.setText(" lat: " + marker.getPosition().latitude + "/ lng: " + marker.getPosition().longitude);
                else
                    txtPlayer02.setText(" lat: " + marker.getPosition().latitude + "/ lng: " + marker.getPosition().longitude);
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.w(TAG, "onItemSelected: " + "posicao " + position + " id " + id );

        if(map != null) {
            switch (position) {
                case 0:
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case 1:
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case 2:
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                default:
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
