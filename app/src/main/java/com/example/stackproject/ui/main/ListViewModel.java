package com.example.stackproject.ui.main;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.stackproject.data.model.Items;
import com.example.stackproject.data.model.Repo;
import com.example.stackproject.data.rest.RepoRepository;
import com.example.stackproject.di.module.NoConnectivityException;

import java.net.NetPermission;
import java.util.List;

import javax.inject.Inject;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ListViewModel extends ViewModel {

    private final RepoRepository repoRepository;
    private Disposable disposable;

    public final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    public final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ListViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;

    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }
    public LiveData<Boolean> getError() {
        return repoLoadError;
    }
    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void fetchRepos() {
        loading.setValue(true);
        repoRepository.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Items>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;

                    }

                    @Override
                    public void onSuccess(Items value) {
                        repoLoadError.setValue(false);
                        repos.setValue(value.getItems());
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                        if(e instanceof NoConnectivityException)
                        {
                            Log.d("error",e.toString());
                            //No connection found.
                        }
                    }
                });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
