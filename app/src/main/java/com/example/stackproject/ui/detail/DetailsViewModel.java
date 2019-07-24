package com.example.stackproject.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.stackproject.data.model.Repo;

import javax.inject.Inject;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();

    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    @Inject
    public DetailsViewModel() {

    }

    public void setSelectedRepo(Repo repo) {
        selectedRepo.setValue(repo);
    }

    }






