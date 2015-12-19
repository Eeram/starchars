package com.mathieu.starchars.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleResponse {
    @SerializedName("count")
    public int count;
    @SerializedName("next")
    public String nextPage;
    @SerializedName("previous")
    public String previousPage;
    @SerializedName("results")
    public List<People> results;
}
