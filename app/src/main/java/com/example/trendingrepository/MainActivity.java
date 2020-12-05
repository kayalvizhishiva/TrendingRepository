package com.example.trendingrepository;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trendingrepository.Utiles.CollectionFilter;
import com.example.trendingrepository.model.Repo;
import com.google.gson.annotations.SerializedName;

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

public class MainActivity extends AppCompatActivity {

    public RepolistAdapter repolistAdapter;
    public List<Repo> repolist;
    public List<Repo> filterrepolist;

    @BindView(R.id.repositorylist)
    RecyclerView repolistview;
    @BindView(R.id.no_internet_layout)
    LinearLayout no_internet_layout;
    @BindView(R.id.tryagain_btn)
    Button tryagain_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repolist = new ArrayList<>();

        if (isOnline()) {
            no_internet_layout.setVisibility(View.INVISIBLE);
            getRepositoryList();
        } else {
            try {
                no_internet_layout.setVisibility(View.VISIBLE);
                repolistview.setVisibility(View.INVISIBLE);
                tryagain_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            } catch (Exception e) {

            }
        }


    }

    public void getRepositoryList(){

        Call<List<Repo>> call = ApiClient.getInstance().getMyApi().getrepo();
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repolist = response.body();
                InitListView(repolist);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
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
                    SearchRepository(key);
                }
                else {
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
                Toast.makeText(getApplicationContext(),"No Record",Toast.LENGTH_LONG).show();
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
