package com.roadtob;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Search");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        AutoCompleteTextView from = (AutoCompleteTextView) findViewById(R.id.from_edittext);
        ArrayList<String> list_from = new ArrayList<>();
        list_from.add("start");
        ArrayAdapter adapter_from = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list_from);
        from.setAdapter(adapter_from);

        AutoCompleteTextView to = (AutoCompleteTextView) findViewById(R.id.to_edittext);
        to.setCompletionHint("Finish");
        ArrayList<String> list_to = new ArrayList<>();
        list_to.add("finish");
        ArrayAdapter adapter_to = new ArrayAdapter<String>(this,
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
