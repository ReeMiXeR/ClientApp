package com.roadtob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Voyager on 21.04.2016.
 */
public interface HttpService {

    @GET("/api/latest/points/all/names")
    Call<HashMap<Long, String>> getPoints();

    @GET("/api/latest/points/all/names/reverted")
    Call<Map<String, Integer>> getPointsIds();

    @GET("api/latest/trips/search/basic")
    Call<List<Trips>> getTrips(@Query("from") int from, @Query("to") int to);
}
