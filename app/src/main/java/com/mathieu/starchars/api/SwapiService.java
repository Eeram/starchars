package com.mathieu.starchars.api;

import com.mathieu.starchars.api.models.PeopleResponse;

import retrofit.Call;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SwapiService {

    String BASE_URL = "http://swapi.co/api/";

    @FormUrlEncoded
    @POST("people")
    Call<PeopleResponse> getPeople(@Query("page") int page);

    @FormUrlEncoded
    @POST("people/{id]/")
    Call<PeopleResponse> getPerson(@Path("id") int id);
}