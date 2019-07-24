package com.example.stackproject.ui.main;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stackproject.BaseApplication;
import com.example.stackproject.ConnectivityReceiver;
import com.example.stackproject.R;
import com.example.stackproject.data.model.Repo;
import com.example.stackproject.ui.detail.DetailActivity;
import com.example.stackproject.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements RepoSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    ViewModelFactory viewModelFactory;
    ConnectivityReceiver connectivityReceiver;


    //public CountingIdlingResource countingIdlingResource =  new CountingIdlingResource("DATA_LOADER");

    ListViewModel viewModel;
    RepoListAdapter repoListAdapter;
    //@BindView(R.id.items_recycler) EmptyRecyclerView listView;
    @BindView(R.id.emptyView) LinearLayout emptyView;

    @BindView(R.id.recyclerView) RecyclerView listView;
    @BindView(R.id.text_error) TextView errorTextView;
    @BindView(R.id.loading_view) View loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_list);
        ButterKnife.bind(this);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        BaseApplication.getInstance().setConnectivityListener(this);
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ListViewModel.class);
        viewModel.fetchRepos();
        setUpListView();
        observableViewModel();

    }

    private void setUpListView() {
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        repoListAdapter = new RepoListAdapter(this);
        listView.setAdapter(repoListAdapter);


    }


    private void observableViewModel() {
        viewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(List<Repo> repos) {
                if(repos != null){
                    repoListAdapter.setUserList(repos);
                    listView.setVisibility(View.VISIBLE);

                }

            }
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if(isError) {
                emptyView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            }else {
                emptyView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }




    @Override
    public void onRepoSelected(Repo repo) {
        RepoListAdapter.selected_list =repo;
        if(!BaseApplication.blocked_list.contains(repo.getId())) {
            startActivity(new Intent(MainActivity.this, DetailActivity.class));
        }
        else{
            UnblockView(repo);
        }

    }


    public void UnblockView(final Repo repo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unblock User");
        builder.setMessage("Do you want to unblock the User ?");
        builder.setCancelable(false);
        builder.setPositiveButton("UNBLOCK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                for(int i=0;  i <BaseApplication.blocked_list.size(); i++)
                { if(BaseApplication.blocked_list.get(i) == repo.getId()){
                        BaseApplication.blocked_list.remove(i);
                        startActivity(new Intent(MainActivity.this, DetailActivity.class));
                    }
                }

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        observableViewModel();
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        viewModel = viewModelFactory.create(ListViewModel.class);
        viewModel.fetchRepos();
        observableViewModel();
        setUpListView();

    }

    @Override
    protected void onDestroy() {
        ConnectivityReceiver.connectivityReceiverListener = null;
        super.onDestroy();
    }
}


