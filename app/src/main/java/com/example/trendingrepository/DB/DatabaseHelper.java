package com.example.trendingrepository.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Switch;

import com.example.trendingrepository.model.Builtby;
import com.example.trendingrepository.model.Repo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "TrendingRepository.db";
    private static final int DATABASE_VERSION = 1;
    public static DatabaseHelper instance;

    private Dao<Repo, Integer> mRepoDao = null;
    private Dao<Builtby, Integer>mBuiltbyDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Repo.class);
            TableUtils.createTable(connectionSource, Builtby.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {


            switch (newVersion) {
                case 1:
                    break;
                case 2:

                    break;
            }

    }

    public Dao<Repo, Integer> getRepoDao() throws SQLException {
        if (mRepoDao == null) {
            mRepoDao = getDao(Repo.class);
        }
        return mRepoDao;
    }

    public Dao<Builtby,Integer> getmBuiltbyDao() throws SQLException{
        if (mBuiltbyDao == null) {
            mBuiltbyDao = getDao(Builtby.class);
        }
        return mBuiltbyDao;
    }

    @Override
    public void close() {
        mRepoDao = null;
        mBuiltbyDao = null;
        super.close();
    }
}
