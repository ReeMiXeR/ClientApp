package ru.busride;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Voyager on 21.04.2016.
 */
public interface HttpService {

    @GET("points")
    Call<List<SearchFragment.Points>> getPoints();

    @GET("search")
    Call<List<Trips>> getTrips(@Query("from") int from, @Query("to") int to, @Query("date") String date, @Query("seats") String seats);

    @GET("points/")
    Call<Map<String, Integer>> getPointsIds();

    @GET("api/latest/trips/search/basic")
    Call<List<Trips>> getTrips(@Query("from") int from, @Query("to") int to);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.busride.ru/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
