package com.example.stackproject.data.rest;

import com.example.stackproject.data.model.Items;
import io.reactivex.Single;
import retrofit2.http.GET;


public interface RepoService {

    @GET("users?pagesize=20&order=desc&sort=reputation&site=stackoverflow")
    Single<Items> getRepositories();

}
