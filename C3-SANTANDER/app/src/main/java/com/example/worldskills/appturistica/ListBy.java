package com.example.worldskills.appturistica;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.worldskills.appturistica.modelo.Mymodel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListBy extends Menus {
RecyclerView rC;
LinearLayout lN;
static boolean towCards;
String tipoL;
NavigationView nV;
static List<Mymodel> data;
ImageView btnC;
FloatingActionButton fab;

private static  final  int intervalo = 2000;
private long click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fr = findViewById(R.id.frameL);
        getLayoutInflater().inflate(R.layout.activity_list_by, fr);

        nV = findViewById(R.id.nav_view);
        rC = findViewById(R.id.recyclerViews);
        rC.setLayoutManager(new LinearLayoutManager(this));
        rC.setHasFixedSize(true);
        towCards = false;
        tipoL = "hotel";
        rC.setVisibility(View.GONE);
        iniB();
        navigationD("Inicio", 0);
        getData();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListBy.this, MapsActivit1.class));
            }
        });
        fab.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);

    }
    public void onBackPressed(){
        if (intervalo + click > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }else {
            Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
        }
        click = System.currentTimeMillis();
    }

    public void iniB(){
        lN = findViewById(R.id.linearL);
        btnC = findViewById(R.id.cambiarV);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                towCards = !towCards;
                adapter();
            }
        });
    }

    public void navigationD(String titleT, int num){
        setTitle(titleT);
        nV.getMenu().getItem(num).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fab.setVisibility(View.VISIBLE);
        lN.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);
        rC.setVisibility(View.VISIBLE);
        if (id == R.id.nav_camera) {
            btnC.setVisibility(View.VISIBLE);
            tipoL = "hotel";
            MapsActivit1.tipoL = "hotel";
            adapter();
            navigationD("Hotel",1);
        } else if (id == R.id.nav_gallery) {
            MapsActivit1.tipoL = "sitio";
            tipoL = "sitio";
            navigationD("Sitio",2);
            btnC.setVisibility(View.VISIBLE);
            adapter();
        } else if (id == R.id.nav_slideshow) {
            MapsActivit1.tipoL = "restaurante";
            tipoL = "restaurante";
            navigationD("Restaurante",3);
            adapter();
            btnC.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_manage) {
            rC.setVisibility(View.GONE);
            btnC.setVisibility(View.GONE);
            lN.setVisibility(View.VISIBLE);
            navigationD("Inicio",0);
            fab.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void adapter(){
        RvPlaces rvPlaces = new RvPlaces(filter(data, tipoL));
        rC.setAdapter(rvPlaces);
    }

    public void getData(){
    String url = "http://smartgeeks.com.co/WS/webserviceturisapp.php";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                data = Arrays.asList(gson.fromJson(response, Mymodel[].class));
                adapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListBy.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public List<Mymodel> filter(List<Mymodel> filterList, String tipoL){
        List<Mymodel> filterM = new ArrayList<Mymodel>();
        for (int i = 0; i<filterList.size(); i++){
            if (tipoL.equals(filterList.get(i).getTipolugar())){
                filterM.add(filterList.get(i));
            }
        }
        return  filterM;
    }
}
