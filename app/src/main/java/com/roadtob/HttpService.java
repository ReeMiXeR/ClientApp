package com.roadtob;

import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Voyager on 21.04.2016.
 */
public interface HttpService {

    @GET("/api/latest/trips")
    Response fetchUser();
}
