package com.mathieu.starchars.api;

import com.mathieu.starchars.api.models.PeoplesResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SwapiService {

    String BASE_URL = "http://swapi.co/api/";
    int ITEMS_PER_PAGE = 10;

    @GET("people/")
    Call<PeoplesResponse> getPeople(@Query("page") int page);

    @GET("people/{id]/")
    Call<PeoplesResponse> getPerson(@Path("id") int id);
}