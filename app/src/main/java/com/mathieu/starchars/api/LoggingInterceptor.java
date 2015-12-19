package com.mathieu.starchars.api;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okio.Buffer;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        String requestLog = String.format("%s%n%s", chain.connection(), request.headers());
        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request);
        }
        Log.i("Retrofit", "=====> REQUEST : " + request.url());
        Log.v("Retrofit", requestLog);
        Log.i("Retrofit", "=====>");

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String responseLog = String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers());

        String bodyString = response.body().string();

        Log.d("Retrofit", "<===== RESPONSE : " + request.url());
        Log.v("Retrofit", requestLog);
        try {
            Log.d("Retrofit", "====== BODY (Pretty) =====");
            Log.v("Retrofit", (new JSONObject(bodyString)).toString(1));
        } catch (JSONException e) {
            Log.d("Retrofit", "====== BODY =====");
            Log.v("Retrofit", bodyString);
        }
        Log.d("Retrofit", "<=====");

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            e.printStackTrace();
            return "Error while reading body.";
        }
    }
}