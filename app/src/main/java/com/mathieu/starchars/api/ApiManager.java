package com.mathieu.starchars.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class ApiManager {

    private SwapiService swapiService;

    public ApiManager() {
        swapiService = createSwapiService(SwapiService.BASE_URL);
    }

    /**
     * Initialize SwapiService with given base url
     * @return Instance of SwapiService
     */
    private SwapiService createSwapiService(String baseUrl) {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SwapiService.class);
    }

    public SwapiService getSwapiService() {
        return swapiService;
    }
}
