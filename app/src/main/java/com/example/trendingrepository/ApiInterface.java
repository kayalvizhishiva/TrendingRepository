package com.example.trendingrepository;

import com.example.trendingrepository.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface  ApiInterface {
   String BASE_URL ="https://private-anon-525ae09438-githubtrendingapi.apiary-mock.com";
    @GET("repositories?language=&since=&spoken_language_code=")
    Call<List<Repo>> getrepo();
}
