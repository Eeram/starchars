package com.mathieu.starchars.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class Specie implements Serializable {
    @SerializedName("name") public String name;
    @SerializedName("classification") public String classification;
    @SerializedName("designation") public String designation;
    @SerializedName("average_height") public String averageHeight;
    @SerializedName("skin_colors") public String skinColors;
    @SerializedName("hair_colors") public String hairColors;
    @SerializedName("eye_colors") public String eyeColors;
    @SerializedName("average_lifespan") public String averageLifespan;
    @SerializedName("homeworld") public String homeworld;
    @SerializedName("language") public String language;
    @SerializedName("people") public List<String> people = new ArrayList<String>();
    @SerializedName("films") public List<String> films = new ArrayList<String>();
    @SerializedName("created") public String created;
    @SerializedName("edited") public String edited;
    @SerializedName("url") public String url;
}
