package com.example.trendingrepository.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

@DatabaseTable(tableName = Repo.TABLE_NAME)
public class Repo {

    public static final String TABLE_NAME = "tRepository";

    @DatabaseField(generatedId = true)
    public transient int id;

    @SerializedName("author")
    @DatabaseField
    private String author;
    @SerializedName("name")
    @DatabaseField
    private String name;
    @SerializedName("avatar")
    @DatabaseField
    private String avatar;
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    @DatabaseField
    private String description;
    @SerializedName("language")
    @DatabaseField
    private String language;
    @SerializedName("languageColor")
    @DatabaseField
    private String languageColor;
    @SerializedName("stars")
    @DatabaseField
    private Integer stars;
    @SerializedName("forks")
    @DatabaseField
    private Integer forks;
    @SerializedName("currentPeriodStars")
    @DatabaseField
    private Integer currentPeriodStars;
    @SerializedName("builtBy")
//    @DatabaseField(dataType = DataType.SERIALIZABLE)
    ArrayList<Builtby> builtBy;

        public Repo(String author, String name, String avatar, String url, String description, String language, String languageColor, Integer stars, Integer forks, Integer currentPeriodStars, ArrayList<Builtby> builtBy) {
        this.author = author;
        this.name = name;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
        this.language = language;
        this.languageColor = languageColor;
        this.stars = stars;
        this.forks = forks;
        this.currentPeriodStars = currentPeriodStars;
        this.builtBy = builtBy;
    }

    public Repo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public Integer getStars() {
        return stars;
    }

    public Integer getForks() {
        return forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

        public ArrayList<Builtby> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(ArrayList<Builtby> builtBy) {
        this.builtBy = builtBy;
    }

    private String SearchValue;

    public String getSearchValue() {
        return SearchValue;
    }

    public void setSearchValue(String searchValue) {
        SearchValue = searchValue;
    }
}
