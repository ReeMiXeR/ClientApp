package com.roadtob;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TripActivity extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;
    List<Trips> tripsList;
    Call<List<Trips>> callTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Routes");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gotob.by:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final HttpService service = retrofit.create(HttpService.class);
        int from = getIntent().getIntExtra("from", 0);
        int to = getIntent().getIntExtra("to", 0);
        callTrips = service.getTrips(from, to);
        callTrips.enqueue(new Callback<List<Trips>>() {
            @Override
            public void onResponse(Call<List<Trips>> call, Response<List<Trips>> response) {
                if (response.isSuccessful()) {
                    Log.d("Succesful", "response");
                    List<Trips> listTrips = response.body();
                    Log.d("List", listTrips.size() + "");
                    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                    for (Trips trip : listTrips) {
                        HashMap map = new HashMap();
                        HashMap<Long, String> mapCities = (HashMap<Long, String>) getIntent().getSerializableExtra("points");
                        Log.d("Cities", mapCities.toString());
                        String locale = Locale.getDefault().toString();
                        Log.d("Locale", locale);

                        map.put("button", "Order (" + trip.getPrice() + trip.getPriceCurrency() + ")");
                        map.put("city_from", mapCities.get(trip.getDeparturePoint()));
                        map.put("city_to", mapCities.get(trip.getArrivalPoint()));
                        map.put("date_from",
                                new SimpleDateFormat("HH:mm dd.MM.yy EEE", new Locale(locale))
                                        .format(trip.getArrivalDate()));
                        map.put("date_to",
                                new SimpleDateFormat("HH:mm dd.MM.yy EEE", new Locale(locale))
                                .format(trip.getDepartureDate()));
                        list.add(map);
                    }
                    Log.d("Hashes", list.size() + "");
                    SimpleAdapter adapter = new SimpleAdapter(TripActivity.this,
                            list, R.layout.routes_gridview_item,
                            new String[]{"button", "city_from", "city_to", "date_from", "date_to"},
                            new int[]{R.id.button,
                                    R.id.start_city_name,
                                    R.id.finish_city_name,
                                    R.id.start_time,
                                    R.id.finish_time});
                    gridView = (GridView) findViewById(R.id.trips_list);
                    gridView.setAdapter(adapter);
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
            public void onFailure(Call<List<Trips>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
