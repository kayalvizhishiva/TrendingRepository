package com.example.trendingrepository;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trendingrepository.DB.DBManager;
import com.example.trendingrepository.DB.DatabaseHelper;
import com.example.trendingrepository.Utiles.CollectionFilter;
import com.example.trendingrepository.Utiles.ProgressActivity;
import com.example.trendingrepository.model.Builtby;
import com.example.trendingrepository.model.Repo;
import com.google.gson.annotations.SerializedName;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.trendingrepository.Utiles.CollectionFilter.filter;

public class MainActivity extends ProgressActivity {

    public RepolistAdapter repolistAdapter;
    public List<Repo> repolist;
    public List<Repo> filterrepolist;
    public DBManager dbManager;

    @BindView(R.id.repositorylist)
    RecyclerView repolistview;
    @BindView(R.id.no_internet)
    TextView no_internet;
    @BindView(R.id.tryagain_btn)
    Button tryagain_btn;
    @BindView(R.id.swipeControl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_result)
    ImageView no_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repolist = new ArrayList<>();
        DatabaseHelper.getInstance(this);
        dbManager = new DBManager();

        if (isOnline()) {
            no_internet.setVisibility(View.INVISIBLE);
            tryagain_btn.setVisibility(View.INVISIBLE);
            no_result.setVisibility(View.INVISIBLE);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    if(isOnline()){
                        repolistview.setVisibility(View.VISIBLE);
                        getRepositoryList();
                    }else {
                        swipeRefreshLayout.setRefreshing(false);
                        repolistview.setVisibility(View.INVISIBLE);
                        no_internet.setVisibility(View.VISIBLE);
                        tryagain_btn.setVisibility(View.VISIBLE);
                        no_result.setVisibility(View.INVISIBLE);
                        tryagain_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(isOnline()){
                                    no_internet.setVisibility(View.INVISIBLE);
                                    tryagain_btn.setVisibility(View.INVISIBLE);
                                    no_result.setVisibility(View.INVISIBLE);
                                    repolistview.setVisibility(View.VISIBLE);
                                    getRepositoryList();

                                }else {
                                    no_internet.setVisibility(View.VISIBLE);
                                    tryagain_btn.setVisibility(View.VISIBLE);
                                    repolistview.setVisibility(View.INVISIBLE);
                                    no_result.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }

                }
            });
            getRepositoryList();
        } else {
            try {
                no_internet.setVisibility(View.VISIBLE);
                tryagain_btn.setVisibility(View.VISIBLE);
                no_result.setVisibility(View.INVISIBLE);
                tryagain_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isOnline()){
                            no_internet.setVisibility(View.INVISIBLE);
                            tryagain_btn.setVisibility(View.INVISIBLE);
                            no_result.setVisibility(View.INVISIBLE);
                            repolistview.setVisibility(View.VISIBLE);
                            getRepositoryList();

                        }else {
                            no_internet.setVisibility(View.VISIBLE);
                            tryagain_btn.setVisibility(View.VISIBLE);
                            repolistview.setVisibility(View.INVISIBLE);
                            no_result.setVisibility(View.INVISIBLE);

                        }
                    }
                });

            } catch (Exception e) {

            }
        }
    }
    public void getRepositoryList(){
        showProgress("Loading");
        Call<List<Repo>> call = ApiClient.getInstance().getMyApi().getrepo();
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                dismissProgress();
                repolist = response.body();
                dbManager.DeleteDBList();
                    if(repolist!=null){
                        for (Repo dbRepolist:repolist){
                            int id = dbManager.create(dbRepolist);
                            dbRepolist.setId(id);
                            for(Builtby builtbylist : dbRepolist.getBuiltBy()){
                                builtbylist.setRepoID(dbRepolist);
                                dbManager.bulidbycreate(builtbylist);
                            }
                        }
                    }


                InitListView(repolist);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                dismissProgress();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void InitListView(List<Repo> repodetails){
        repolistview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        repolistAdapter = new RepolistAdapter(getApplicationContext(), repodetails);
        repolistview.setAdapter(repolistAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchview, menu);

        MenuItem search_item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(true);
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                String key = s.toUpperCase();
                if(!TextUtils.isEmpty(s)) {
                    no_result.setVisibility(View.INVISIBLE);
                    SearchRepository(key);
                }
                else {
                    no_result.setVisibility(View.INVISIBLE);
                    filterrepolist = repolist;
                    InitListView(filterrepolist);
                }

                return false;
            }
        });
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

    public void SearchRepository(final String searchValue){
        try {

            if(searchValue != null) {
                CollectionFilter.Predicate<Repo> validPersonPredicate = new CollectionFilter.Predicate<Repo>() {
                    public boolean apply(Repo repodata) {
                        return repodata.getName().toUpperCase().contains(searchValue)||repodata.getLanguage().toUpperCase().contains(searchValue);
                    }
                };
                Collection<Repo> result = filter(repolist, validPersonPredicate);
                filterrepolist = (ArrayList<Repo>)result;
            }
            else{
            }
            if(filterrepolist != null && filterrepolist.size()>0){
                for (Repo salemodelname:filterrepolist)
                {
                    salemodelname.setSearchValue(searchValue);
                }
            }else{
                no_result.setVisibility(View.VISIBLE);
                repolistview.setVisibility(View.GONE);
            }
            repolistview.setVisibility(View.VISIBLE);
            InitListView(filterrepolist);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
