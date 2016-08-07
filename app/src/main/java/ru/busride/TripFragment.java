package ru.busride;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.roadtob.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class TripFragment extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> tripsArray;
    TripFragment activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        listView = (ListView) findViewById(R.id.trips_list);

        Log.e("qwe", "1");
        Intent intent = getIntent();
        Bundle extras = intent.getBundleExtra("trips");
        if (extras != null) {
            tripsArray = (ArrayList<HashMap<String, String>>) extras.getSerializable("trips");

            Log.e("qwe", "3 " + tripsArray.toString());
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    tripsArray,
                    R.layout.trips_item,
                    new String[]{"departure_date", "arrival_date", "price", "price_currency", "departure_point", "arrival_point", "seats"},
                    new int[]{R.id.from_time, R.id.to_time, R.id.price, R.id.currency, R.id.from_city, R.id.to_city, R.id.seats});
            listView.setAdapter(adapter);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView depart_point = (TextView) findViewById(R.id.toolbar_from);
        TextView arrive_point = (TextView) findViewById(R.id.toolbar_to);
        TextView date = (TextView) findViewById(R.id.toolbar_date);

        depart_point.setText(tripsArray.get(0).get("departure_point"));
        arrive_point.setText(tripsArray.get(1).get("arrival_point"));
        date.setText(tripsArray.get(0).get("toolbar_date"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }
}
