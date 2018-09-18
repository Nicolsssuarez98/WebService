package com.example.worldskills.appturistica;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {
    int i = 0,  t = 0;
    private GoogleMap mMap;
    TextView txtT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtT = findViewById(R.id.tipo);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        String url ="https://maps.googleapis.com/maps/api/directions/json"+
                "?origin=4.5951771,-74.111714316" +
                "&destination="+ Details.latitud + "," + Details.longitud;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                JsonObject object = gson.fromJson(response, JsonElement.class).getAsJsonObject();
                String points = object.get("routes").getAsJsonArray().get(0)
                        .getAsJsonObject().get("overview_polyline").getAsJsonObject().get("points").getAsString();
                PolylineOptions polylineOptions = new PolylineOptions().color(Color.BLUE).width(5).addAll(PolyUtil.decode(points));
                mMap.addPolyline(polylineOptions);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity2.this, "Sin conexión", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(4.5951771,-74.111714316);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Estamos acá."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));

        Double lat = Double.parseDouble(Details.latitud);
        Double lon = Double.parseDouble(Details.longitud);

        LatLng destino = new LatLng( lat,lon);
        mMap.addMarker(new MarkerOptions().position(destino).title("Punto llegada: " + Details.Nombre));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destino));
    }

    public void cambiarM(View view) {
        t++;
        if (t == 1){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            txtT.setText("MAP TYPE HYBRID");
        }else  if(t==2) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            txtT.setText("MAP TYPE NONE");
        }else if (t==3) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            txtT.setText("MAP TYPE NORMAL");
        }else if (t==4) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            txtT.setText("MAP TYPE SATELLITE");
        }else if (t==5){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            txtT.setText("MAP TYPE TERRAIN");
            t=0;
        }

    }
    public void verT(View view) {
            i++;
            if (i==1){
                mMap.setTrafficEnabled(true);
            }else if(i ==2){
                i=0;
                mMap.setTrafficEnabled(false);
            }
        }

}
