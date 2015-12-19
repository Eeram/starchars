//package com.mathieu.starchars.api.models;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.util.ArrayList;
//
//import retrofit.Call;
//import retrofit.Callback;
//import retrofit.Response;
//import retrofit.Retrofit;
//
///**
// * Project :    Star Chars
// * Author :     Mathieu
// * Date :       19/12/2015
// */
//
//public class PeopleProvider implements Callback<PeopleResponse> {
//    private final static String TAG = "PeopleProvider";
//
//    public interface OnPeopleLoadedListener {
//        void contentListLoaded(ArrayList<People> posts, boolean animate);
//
//        void contentListError(String error);
//    }
//
//    private ArrayList<People> peoples = new ArrayList<>();
//    private OnPeopleLoadedListener listener;
//    private int currentPage = 1;
//
//    public PeopleProvider(int currentPage, OnPeopleLoadedListener listener) {
//        this.listener = listener;
//        this.currentPage = currentPage;
//    }
//
//    public ArrayList<People> getPeoples() {
//        return peoples;
//    }
//
//    public void refresh() {
//        this.currentPage = 1;
//        this.peoples.clear();
//        getPeople(currentPage);
//    }
//
//    public void getPeople(int page) {
//        Call<PeopleResponse> articleCallback = new RetrofitManager().initKuidRetrofit().getSaved(preferencesManager.getAccessToken());
//        articleCallback.enqueue(this);
//    }
//
//    @Override
//    public void onResponse(Response<PeopleResponse> response, Retrofit retrofit) {
//        try {
//            Log.e(TAG, ", contentsResponse.success = " + response.body().data.size());
//            boolean animate = peoples.isEmpty();
//            for (People people : response.body().data) {
//                peoples.add(people);
//            }
//            listener.contentListLoaded(peoples, animate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            listener.contentListError(e.getLocalizedMessage());
//        }
//    }
//
//    @Override
//    public void onFailure(Throwable t) {
//        Log.e(TAG, "Error : " + t.getMessage());
//        listener.contentListError(t.getMessage());
//    }
//}
