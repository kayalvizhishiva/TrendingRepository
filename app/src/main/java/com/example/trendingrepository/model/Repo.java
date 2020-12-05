package com.example.trendingrepository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Repo {

            @SerializedName("author")
            private String author;
            @SerializedName("name")
            private String name;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("url")
            private String url;
            @SerializedName("description")
            private String description;
            @SerializedName("language")
            private String language;
            @SerializedName("languageColor")
            private String languageColor;
            @SerializedName("stars")
            private Integer stars;
            @SerializedName("forks")
            private Integer  forks;
            @SerializedName("currentPeriodStars")
            private Integer currentPeriodStars;
            @SerializedName("builtBy")
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

    public class Builtby{
        @SerializedName("href")
        private String href;
        @SerializedName("avatar")
        private String avatar;

        public String getHref() {
            return href;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getUsername() {
            return username;
        }

        @SerializedName("username")
        private String username;

    }
private String SearchValue;

    public String getSearchValue() {
        return SearchValue;
    }

    public void setSearchValue(String searchValue) {
        SearchValue = searchValue;
    }
}
