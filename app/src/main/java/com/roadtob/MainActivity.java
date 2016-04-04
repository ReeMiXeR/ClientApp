package com.roadtob;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Search");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        Spinner from = (Spinner) findViewById(R.id.from_spinner);
        ArrayList<String> list_from = new ArrayList<>();
        list_from.add("Отправление");
        SpinnerAdapter adapter_from = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list_from);
        from.setAdapter(adapter_from);

        Spinner to = (Spinner) findViewById(R.id.to_spinner);
        ArrayList<String> list_to = new ArrayList<>();
        list_to.add("Прибытие");
        SpinnerAdapter adapter_to = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list_to);
        to.setAdapter(adapter_to);

        Button find = (Button) findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                startActivity(intent);
            }
        });
    }
}
