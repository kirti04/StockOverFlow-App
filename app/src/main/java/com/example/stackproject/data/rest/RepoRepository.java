package com.example.stackproject.data.rest;



import com.example.stackproject.data.model.Items;

import javax.inject.Inject;
import io.reactivex.Single;


public class RepoRepository {

    private final RepoService repoService;

    @Inject
    public RepoRepository(RepoService repoService) {
        this.repoService = repoService;
    }

    public Single<Items> getRepositories() {
        return repoService.getRepositories();
    }

}
