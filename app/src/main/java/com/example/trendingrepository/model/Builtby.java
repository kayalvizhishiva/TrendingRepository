package com.example.trendingrepository.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = Builtby.TABLE_NAME)
public class Builtby implements Serializable {


    public static final String TABLE_NAME = "tBuildby";
    public static final String COLUMN_RepositoryID = "tRepositoryID";
    @DatabaseField(generatedId = true)
    int id;

    @SerializedName(COLUMN_RepositoryID)
    @DatabaseField(foreign = true)
    private Repo repoID;

    @SerializedName("href")
    @DatabaseField
    private String href;
    @SerializedName("avatar")
    @DatabaseField
    private String avatar;
    @DatabaseField
    @SerializedName("username")
    private String username;

    public Builtby(String href, String avatar, String username) {
        this.href = href;
        this.avatar = avatar;
        this.username = username;
    }

    public Builtby() {
    }


    public Repo getRepoID() {
        return repoID;
    }

    public void setRepoID(Repo repoID) {
        this.repoID = repoID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }





}
