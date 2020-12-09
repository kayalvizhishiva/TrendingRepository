package com.example.trendingrepository.DB;

import com.example.trendingrepository.model.Builtby;
import com.example.trendingrepository.model.Repo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private Dao<Repo, Integer> repoDao;
    private Dao<Builtby,Integer> builtbyDao;
    private Repo repolist = new Repo();

    public DBManager() {
        try {
            repoDao = DatabaseHelper.getInstance().getRepoDao();
            builtbyDao = DatabaseHelper.getInstance().getmBuiltbyDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public List<Repo> getAllRepoList() {
        try {
            List<Repo> repolist = repoDao.queryForAll();
            return  repolist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int create(Repo repolist) {
        try {
            repoDao.create(repolist);
            return repolist.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int bulidbycreate(Builtby builtbylist) {
        try {
             builtbyDao.create(builtbylist);
            return builtbylist.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public boolean DeleteDBList()
    {
        try {
            repoDao.deleteBuilder().delete();
            builtbyDao.deleteBuilder().delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Repo> getAllList() {
        try {
            List<Repo> getlist = repoDao.queryBuilder().query();
            int i = 0;
            for (Repo Itme: getlist) {

                    ArrayList<Builtby> Detail = (ArrayList<Builtby>)builtbyDao.queryForEq(Builtby.COLUMN_RepositoryID,Itme);
                    if(Detail != null)
                        getlist.get(i).setBuiltBy(Detail);

                i++;
            }

            return  getlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
