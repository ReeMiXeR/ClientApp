package ru.busride;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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



public class SearchFragment extends android.support.v4.app.Fragment {

    AutoCompleteTextView from;
    AutoCompleteTextView to;
    Button find;
    String a;
    View rootView;
    TextView dateView;
    Calendar findDate;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    int fromId;
    int toId;
    String dateReq;
    static final String LOG_TAG = SearchFragment.class.getSimpleName();


    public SearchFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.search_fragment, container, false);



        final Spinner seatsView = (Spinner) rootView.findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        seatsView.setAdapter(adapter);

        dateView = (TextView) rootView.findViewById(R.id.dateView);
        findDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        String dateStr = dateFormat.format(findDate.getTime());
        dateView.setText(dateStr);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.setTargetFragment(new SearchFragment(), 1);
                fragment.show(getFragmentManager(), fragment.getClass().getSimpleName());
            }
        });

         final HttpService httpService = HttpService.retrofit.create(HttpService.class);
         Call<List<Points>> callPoints = httpService.getPoints();
         callPoints.enqueue(new Callback<List<Points>>() {
             @Override
             public void onResponse(Call<List<Points>> call, Response<List<Points>> response) {
                    if (response.isSuccessful()) {
                        Log.e("Response:", "Succesful");
                        List<Points> listPoints = response.body();

                        for (Points point : listPoints){
                            HashMap map = new HashMap();
                            map.put("id", point.getId());
                            map.put("name", point.getName());
                            map.put("longitude", point.getLongitude());
                            map.put("latitude", point.getLatitude());
                            list.add(map);
                        }

                        String[] departurePointStr = new String[list.size()];
                        for (int i = 0; i < list.size(); i++){
                             departurePointStr[i] = list.get(i).get("name");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.autocomplete_text_item, departurePointStr);
                        from.setAdapter(adapter);
                        to.setAdapter(adapter);
                    }
                }
             @Override
             public void onFailure(Call<List<Points>> call, Throwable t) {
                 Log.e(LOG_TAG,"Failed to get points");
                 t.printStackTrace();
                }
            });

        from = (AutoCompleteTextView) rootView.findViewById(R.id.from_edittext);
        to = (AutoCompleteTextView) rootView.findViewById(R.id.to_edittext);

        find = (Button) rootView.findViewById(R.id.find);
         find.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String seats = seatsView.getSelectedItem().toString();
                 String fromStr = from.getText().toString();
                 String toStr = to.getText().toString();

                 for(int i = 0; i < list.size(); i++) {
                     HashMap map = new HashMap();
                     map = list.get(i);
                    if (list.get(i).get("name").toString().equals(fromStr)){
                        fromId = (int) map.get("id");
                    }
                    else if(list.get(i).get("name").toString().equals(toStr)){
                         toId = (int) map.get("id");
                    }
                 }

                 TextView dateFind = (TextView) getActivity().findViewById(R.id.dateFind);

                 if (dateFind.toString().equals("")){
                     SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                     dateReq = date1.format(findDate.getTime());
                 }
                 else{
                     dateReq = dateFind.getText().toString();
                 }

                 Call<List<Trips>> callTrips = httpService.getTrips(fromId, toId, dateReq, seats);
                 callTrips.enqueue(new Callback<List<Trips>>() {
                     @Override
                     public void onResponse(Call<List<Trips>> call, Response<List<Trips>> response) {
                         if(response.isSuccessful()) {
                             Log.e("Log", "response !- " + response.body().toString());
                             ArrayList<HashMap<String, String>> list1 = new ArrayList<>();
                             List<Trips> listTrip = response.body();
                             Log.e("Log", "size - " + listTrip.size());

                             if(listTrip.size() > 0) {
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

                                 Intent intent = new Intent(getActivity(), TripFragment.class);
                                 Bundle bundle = new Bundle();
                                 bundle.putSerializable("trips", list1);
                                 intent.putExtra("trips", bundle);

                                 startActivity(intent);
                             }
                             else {
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
             }
         });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("qwe", "from id - " + fromId);
        Log.e("log", "string - " + a);
    }

    public String DateFormat(String date, int mode){
        if(mode == 0) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
                Date newDate = inputFormat.parse(date);
                String outputDate = outputFormat.format(newDate);
                return outputDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(mode == 1){
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMMMMMM yyyy");
                Date newDate = inputFormat.parse(date);
                String outputDate = outputFormat.format(newDate);
                return outputDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public class Points {
        int id;
        String name;
        int longitude;
        int latitude;


        public int getId(){
            return id;
        }
        public String getName(){
            return name;
        }
        public int getLongitude(){
            return longitude;
        }
        public int getLatitude() {
            return latitude;
        }

        @Override
        public String toString() {
            return id + " - " + name + " - " + longitude + " - " + latitude;
        }
    }

}
