package com.roadtob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AutoCompleteTextView from;
    AutoCompleteTextView to;
    ArrayList<String> pointsList = new ArrayList<>();
    HttpService service;
    HashMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        from = (AutoCompleteTextView) findViewById(R.id.from_edittext);
        to = (AutoCompleteTextView) findViewById(R.id.to_edittext);

        Button find = (Button) findViewById(R.id.find);
        assert find != null;
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Map<String, Integer>> call = service.getPointsIds();
                call.enqueue(new Callback<Map<String, Integer>>() {
                    @Override
                    public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                        if (response.isSuccessful()) {
                            Log.d("Succesful", "response");
                            Map<String, Integer> mapIds = response.body();
                            Log.d("Map", mapIds.toString());
                            if (mapIds.containsKey(from.getText().toString()) && mapIds.containsKey(to.getText().toString())) {
                                int fromInt = mapIds.get(from.getText().toString());
                                int toInt = mapIds.get(to.getText().toString());
                                Intent intent = new Intent(NavigationActivity.this, TripActivity.class);
                                intent.putExtra("from", fromInt);
                                intent.putExtra("to", toInt);
                                intent.putExtra("points", map);
                                startActivity(intent);
                            } else {
                                Toast.makeText(NavigationActivity.this, "Points not found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d("Error", response.code() + "");
                            try {
                                Log.d("Error", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gotob.by:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(HttpService.class);
        Call<HashMap<Long, String>> call = service.getPoints();
        call.enqueue(new Callback<HashMap<Long, String>>() {
            @Override
            public void onResponse(Call<HashMap<Long, String>> call, Response<HashMap<Long, String>> response) {
                if (response.isSuccessful()) {
                    Log.d("Succesful", "response");
                    map = response.body();
                    Log.d("Points", map.values().toString());
                    pointsList.addAll(map.values());
                    ArrayAdapter adapter = new ArrayAdapter<String>(NavigationActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, pointsList);
                    from.setAdapter(adapter);
                    to.setAdapter(adapter);
                } else {
                    Log.d("Error", response.code() + "");
                    try {
                        Log.d("Error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HashMap<Long, String>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        DatePicker datePicker;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
