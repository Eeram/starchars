package com.mathieu.starchars.api;

import android.os.AsyncTask;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PictureProvider {
    private final static String TAG = "PictureProvider";
    private static final String HOST = "http://www.starwars.com/databank/";

    public OnPictureRetrievedListener onPictureRetrievedListener;

    @WorkerThread
    public static String getCharacterPicture(String query) {
        String html = null;
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("www.starwars.com")
                .addPathSegment("databank")
                .addPathSegment(query)
                .build();

        Log.d(TAG, "url = " + url.toString());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            html = response.body().string();
        } catch (IOException ioe) {
            return null;
        }

        String img;
        try {
            Elements elements = Jsoup.parse(html, HOST).select("div[class=aspect]");
            img = elements.select("img.thumb.reserved-ratio")
                    .first().attr("src");
            int i = img.indexOf(".jpeg") + 5;
            Log.d(TAG, "img before = " + img + ", i = " + i);

            img = img.substring(0, i);
        } catch (Exception e) {
            img = null;
        }

        Log.d(TAG, "img = " + img);

        return img;
    }

    public void retrieveCharacterPicture(final String query) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return getCharacterPicture(query);
            }

            @Override
            protected void onPostExecute(String pictureUrl) {
                if (pictureUrl != null) {
                    onPictureRetrievedListener.onPictureRetrieved(pictureUrl);
                } else {
                    onPictureRetrievedListener.onError();
                }
            }
        }.execute();
    }

    public void addOnPictureRetrievedListener(OnPictureRetrievedListener onPictureRetrievedListener) {
        this.onPictureRetrievedListener = onPictureRetrievedListener;
    }

    public interface OnPictureRetrievedListener {
        void onPictureRetrieved(String pictureUrl);

        void onError();
    }
}
