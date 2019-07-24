package com.example.stackproject.di.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import com.example.stackproject.di.util.ViewModelKey;
import com.example.stackproject.ui.detail.DetailsViewModel;
import com.example.stackproject.ui.main.ListViewModel;
import com.example.stackproject.util.ViewModelFactory;

import javax.inject.Singleton;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailsViewModel detailsViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
