package com.mathieu.starchars.api.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class People implements Serializable {
    @SerializedName("birth_year")
    public String birthYear;
    @SerializedName("eye_color")
    public String eyeColor;
    @SerializedName("films")
    public List<String> films = new ArrayList<String>();
    @SerializedName("gender")
    public String gender;
    @SerializedName("hair_color")
    public String hairColor;
    @SerializedName("height")
    public String height;
    @SerializedName("homeworld")
    public String homeworld;
    @SerializedName("mass")
    public String mass;
    @SerializedName("name")
    public String name;
    @SerializedName("skin_color")
    public String skinColor;
    @SerializedName("created")
    public String created;
    @SerializedName("edited")
    public String edited;
    @SerializedName("species")
    public List<String> species = new ArrayList<String>();
    @SerializedName("starships")
    public List<String> starships = new ArrayList<String>();
    @SerializedName("url")
    public String url;
    @SerializedName("vehicles")
    public List<String> vehicles = new ArrayList<String>();

    private String description = null;
    private String slug = null;

    public String getDescription() {
        if (description == null) {
            description = name + " is a " + getGender() + " with ";
            if (!hairColor.equals("n/a")) {
                description += hairColor + " hair, ";
            }
            if (!eyeColor.equals("n/a")) {
                description += eyeColor + " eyes, ";
            }
            if (!skinColor.equals("n/a")) {
                description += "and a " + skinColor + " skin";
            }
            description += ".";
        }
        return description;
    }

    public String getGender() {
        return gender.equals("n/a") ? "assexual" : gender;
    }

    public String getSlug() {
        if (slug == null) {
            slug = name.toLowerCase()
                    .replaceAll(" ", "-")
                    .replaceAll("é", "e");
            Log.e("PEOPLE", ", slug = " + slug);
        }
        return slug;
    }
}
