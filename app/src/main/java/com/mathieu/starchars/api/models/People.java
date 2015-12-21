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
    @SerializedName("birth_year") public String birthYear;
    @SerializedName("eye_color") public String eyeColor;
    @SerializedName("films") public List<String> films = new ArrayList<String>();
    @SerializedName("gender") public String gender;
    @SerializedName("hair_color") public String hairColor;
    @SerializedName("height") public String height;
    @SerializedName("homeworld") public String homeworld;
    @SerializedName("mass") public String mass;
    @SerializedName("name") public String name;
    @SerializedName("skin_color") public String skinColor;
    @SerializedName("created") public String created;
    @SerializedName("edited") public String edited;
    @SerializedName("species") public List<String> species = new ArrayList<String>();
    @SerializedName("starships") public List<String> starships = new ArrayList<String>();
    @SerializedName("url") public String url;
    @SerializedName("vehicles") public List<String> vehicles = new ArrayList<String>();

    private String description = null;
    private String slug = null;

    public boolean isInFilm(int filmNumber) {
        for (int i = 0; i < films.size(); ++i) {
            String film = films.get(i);
            if (filmNumber == getFilmNumber(film)) {
                return true;
            }
        }
        return false;
    }

    private Integer getFilmNumber(String film) {
        int id = Integer.valueOf(film.substring(film.length() - 2, film.length() - 1));
        switch (id) {
            case 1:
                return 4;
            case 2:
                return 5;
            case 3:
                return 6;
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 7;
        }
        return 0;
    }

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

    public String getHeight() {
        try {
            return Float.valueOf(this.height) / 100f + " m";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public String getMass() {
        try {
            return Integer.valueOf(this.mass) + " Kg";
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
