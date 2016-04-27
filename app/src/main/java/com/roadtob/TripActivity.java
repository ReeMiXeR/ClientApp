package com.roadtob;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripActivity extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Routes");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.trips_list);
        List<Map<String, String>> list = new ArrayList<>(1);
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "Минск");
        list.add(map);
        list.add(map);
        SimpleAdapter adapter = new SimpleAdapter(this,
                list, R.layout.routes_listview_item,
                new String[] {"a"},
                new int[] {R.id.start_city_name});
        gridView.setAdapter(adapter);
    }
}
