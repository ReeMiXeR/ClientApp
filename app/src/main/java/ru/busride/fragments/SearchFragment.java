package ru.busride.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.roadtob.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.busride.activities.TripsSearchActivity;
import ru.busride.models.Points;
import ru.busride.network.HttpService;
import ru.busride.models.Trips;
import ru.busride.activities.PointSelectActivity;


public class SearchFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    static final String LOG_TAG = SearchFragment.class.getSimpleName();
    Button find;
    View rootView;
    TextView dateView;
    Calendar findDate = Calendar.getInstance();
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    String dateStr;
    TextView fromHeadView;
    TextView fromStrView;
    TextView toHeadView;
    TextView toStrView;


    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        toHeadView = (TextView) rootView.findViewById(R.id.to_view);
        fromHeadView = (TextView) rootView.findViewById(R.id.from_view);
        fromStrView = (TextView) rootView.findViewById(R.id.from_str);
        toStrView = (TextView) rootView.findViewById(R.id.to_str);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (PreferenceManager.getDefaultSharedPreferences(getContext()) != null) {
            if (preferences.getString("fromStr", null) != null) {
                addSubLine(fromHeadView, fromStrView, "fromStr");
            }
            Log.e(LOG_TAG, preferences.getString("toStr", "empty"));
            if (preferences.getString("toStr", null) != null) {
                addSubLine(toHeadView, toStrView, "toStr");
            }
        }

        final Spinner seatsView = (Spinner) rootView.findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        seatsView.setAdapter(adapter);

        dateView = (TextView) rootView.findViewById(R.id.dateView);

        if (preferences.getString("date", null) != null) {
            dateStr = DateFormat(preferences.getString("date", null), 2);
        } else {
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM");
            dateStr = outputFormat.format(findDate.getTime());
        }
        dateView.setText(dateStr);

        dateView.setOnClickListener(this);

        final HttpService httpService = HttpService.retrofit.create(HttpService.class);
        Call<List<Points>> callPoints = httpService.getPoints();
        callPoints.enqueue(new Callback<List<Points>>() {
            @Override
            public void onResponse(Call<List<Points>> call, Response<List<Points>> response) {
                if (response.isSuccessful()) {
                    Log.e("Response:", "Succesful");
                    List<Points> listPoints = response.body();
                    for (Points point : listPoints) {
                        HashMap map = new HashMap();
                        map.put("id", point.getId());
                        map.put("name", point.getName());
                        map.put("longitude", point.getLongitude());
                        map.put("latitude", point.getLatitude());
                        list.add(map);
                    }
                    String[] departurePointStr = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        departurePointStr[i] = list.get(i).get("name");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Points>> call, Throwable t) {
                Log.e(LOG_TAG, "Failed to get points");
                t.printStackTrace();
            }
        });

        LinearLayout fromLayout = (LinearLayout) rootView.findViewById(R.id.from_layout);
        LinearLayout toLayout = (LinearLayout) rootView.findViewById(R.id.to_layout);

        fromLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PointSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("points", list);
                intent.putExtra("points", bundle);
                intent.addCategory("from");
                startActivity(intent);
            }
        });

        toLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PointSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("points", list);
                intent.putExtra("points", bundle);
                intent.addCategory("to");
                startActivity(intent);
            }
        });


        find = (Button) rootView.findViewById(R.id.find);
        find.setOnClickListener(this);


        return rootView;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.dateView:
                DialogFragment fragment = new DatePickerFragment();
                fragment.setTargetFragment(new SearchFragment(), 1);
                fragment.show(getFragmentManager(), fragment.getClass().getSimpleName());
                break;

            case R.id.find:
                Spinner seatsView = (Spinner) rootView.findViewById(R.id.spinner1);
                String seats = seatsView.getSelectedItem().toString();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                int fromId = preferences.getInt("fromId", -1);
                int toId = preferences.getInt("toId", -1);

                dateStr = preferences.getString("date", null);

                Log.e(LOG_TAG, " idFrom - " + fromId + " idTo - " + toId + " date - " + dateStr + " seats - " + seats);
                final HttpService httpService = HttpService.retrofit.create(HttpService.class);
                Call<List<Trips>> callTrips = httpService.getTrips(fromId, toId, dateStr, seats);
                callTrips.enqueue(new Callback<List<Trips>>() {
                    @Override
                    public void onResponse(Call<List<Trips>> call, Response<List<Trips>> response) {
                        if (response.isSuccessful()) {
                            Log.e("Log", "response !- " + response.body().toString());
                            ArrayList<HashMap<String, String>> list1 = new ArrayList<>();
                            List<Trips> listTrip = response.body();
                            Log.e("Log", "size - " + listTrip.size());

                            if (listTrip.size() > 0) {
                                for (Trips trip : listTrip) {
                                    Log.e("test", "one");
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("id", trip.getId().toString());
                                    map.put("transport_id", trip.getTransport_id().toString());
                                    map.put("departure_date", DateFormat(trip.getDeparture_date(), 0));
                                    map.put("arrival_date", DateFormat(trip.getArrival_date(), 0));
                                    map.put("routine_id", trip.getRoutine_id().toString());
                                    map.put("price", trip.getPrice().toString());
                                    map.put("price_currency", trip.getPrice_currency());
                                    map.put("departure_point", trip.getDeparture_point());
                                    map.put("arrival_point", trip.getArrival_point());
                                    map.put("seats", trip.getSeats().toString());
                                    map.put("sits_total", trip.getSeatsTotal().toString());
                                    map.put("toolbar_date", DateFormat(trip.getDeparture_date(), 1));
                                    Log.e("Map - ", map.toString() + "");
                                    list1.add(map);
                                }

                                Intent intent = new Intent(getActivity(), TripsSearchActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("trips", list1);
                                intent.putExtra("trips", bundle);

                                startActivity(intent);
                            } else {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Ошибка");
                                alert.setMessage("Рейсов не найдено");
                                alert.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Trips>> call, Throwable t) {
                        Log.e(LOG_TAG, "Find failed");
                        t.printStackTrace();
                    }
                });
                break;
        }
    }

    public String DateFormat(String date, int mode) {
        try {
            if (mode == 0) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
                Date newDate = inputFormat.parse(date);
                String outputDate = outputFormat.format(newDate);
                return outputDate;

            }
            if (mode == 1) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
                Date newDate = inputFormat.parse(date);
                String outputDate = outputFormat.format(newDate);
                return outputDate;
            }
            if (mode == 2) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM");
                Date newDate = inputFormat.parse(date);
                String outputDate = outputFormat.format(newDate);
                return outputDate;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSubLine(TextView head, TextView sub, String mode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String city = preferences.getString(mode, "null");
        ViewGroup.LayoutParams params = sub.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        sub.setLayoutParams(params);
        sub.setText(city);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
    }


}
