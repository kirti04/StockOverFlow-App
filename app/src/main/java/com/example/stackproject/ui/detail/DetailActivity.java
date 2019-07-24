package com.example.stackproject.ui.detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stackproject.BaseApplication;
import com.example.stackproject.R;
import com.example.stackproject.data.model.Repo;
import com.example.stackproject.ui.main.MainActivity;
import com.example.stackproject.ui.main.RepoListAdapter;
import com.example.stackproject.util.ViewModelFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailActivity extends DaggerAppCompatActivity {

    public static final String USER_INTENT_KEY  = "USER_KEY" ;
    @BindView(R.id.user_name_text)
    TextView user_name;
    @BindView(R.id.text_user_id)
    TextView user_id;
    @BindView(R.id.user_reputation_text)
    TextView user_reputation;
    @BindView(R.id.gold_badges_text)
    TextView user_gold;
    @BindView(R.id.silver_badges_text)
    TextView user_silver;
    @BindView(R.id.bronze_badges_text)
    TextView user_bronze;
    @BindView(R.id.user_location_text)
    TextView user_location;
    @BindView(R.id.user_creation_date_text)
    TextView user_date;
    @BindView(R.id.btn_follow)
    Button btn_follow;
    @BindView(R.id.btn_block)
    Button btn_block;
    @BindView(R.id.avatar_image_view)
    ImageView image_user;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.newView) View newView;
    public int item_id;
    Repo detail_repo;

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });
        detail_repo = RepoListAdapter.selected_list;
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.setSelectedRepo(detail_repo);
        displayRepo();
        funFollow_block();
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!BaseApplication.followed_list.contains(item_id)){
                    BaseApplication.followed_list.add(item_id);
                    btn_follow.setText("FOLLOWED");
                    String follow_msg = "You have started following User"+ item_id;
                    Toast.makeText(DetailActivity.this,follow_msg,Toast.LENGTH_SHORT).show();
                }else{
                    String follow_msg = "You have Unfollowed the User "+ item_id;
                    Toast.makeText(DetailActivity.this,follow_msg,Toast.LENGTH_SHORT).show();
                    btn_follow.setText("FOLLOW");
                    unFollowUser();

                }


            }
        });

        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!BaseApplication.blocked_list.contains(item_id)) {
                    BaseApplication.blocked_list.add(item_id);
                    btn_block.setBackgroundColor(Color.LTGRAY);
                    btn_block.setText("BLOCKED");
                    String block_msg = "User Id "+ item_id +" is blocked";
                    Toast.makeText(DetailActivity.this,block_msg,Toast.LENGTH_SHORT).show();
                }
                else{
                    String block_msg = "User Id "+ item_id +" is already blocked";
                    Toast.makeText(DetailActivity.this,block_msg,Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void funFollow_block() {
        item_id = detail_repo.getId();
        if(BaseApplication.followed_list.contains(item_id)){
            btn_follow.setText("FOLLOWED");
        }else
        {
            btn_follow.setText("FOLLOW");
        }

    }

    private void unFollowUser() {
        for(int i=0;  i <BaseApplication.followed_list.size(); i++)
        { if(BaseApplication.followed_list.get(i) == RepoListAdapter.selected_list.getId()){
            BaseApplication.followed_list.remove(i);
            return;
        }
        }
    }

    private void displayRepo() {
    detailsViewModel.getSelectedRepo().observe(this, new Observer<Repo>() {
        @Override
        public void onChanged(Repo repo) {
            if (repo != null) {
                user_name.setText("UserName :"+ repo.getName());
                user_id.setText("UserId :"+ String.valueOf(repo.getId()));
                user_reputation.setText("Reputation :"+ String.valueOf(repo.getReputation()));
                user_location.setText("Location :"+ String.valueOf(repo.getLocation()));
                user_date.setText(getDateFromTimeStamp(repo.getCreation_date()));
                user_gold.setText("Gold :"+ String.valueOf(repo.getBadgeCounts().getGold()));
                user_silver.setText("Silver :"+ String.valueOf(repo.getBadgeCounts().getSilver()));
                user_bronze.setText("Bronze :"+ String.valueOf(repo.getBadgeCounts().getBronze()));
                Glide.with(DetailActivity.this).load(repo.getUrl_image()).into(image_user);


            }

        }
    });
    }

    @Override
    protected void onResume() {
        super.onResume();
        funFollow_block();
        if(BaseApplication.blocked_list.contains(item_id)){
            btn_block.setBackgroundColor(Color.LTGRAY);
            btn_block.setText("BLOCKED");
        }
    }

    private String getDateFromTimeStamp(long timestamp) {
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return dateFormat.format(new Date(timestamp * 1000));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailActivity.this.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("chnageeeeeeeeeeee"+newConfig.orientation);
    }
}
