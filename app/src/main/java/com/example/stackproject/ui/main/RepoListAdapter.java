package com.example.stackproject.ui.main;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.stackproject.BaseApplication;
import com.example.stackproject.R;
import com.example.stackproject.data.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>{

    private RepoSelectedListener repoSelectedListener;
    private List<Repo> data;
    public static Repo selected_list;
    public static Context context;
    public int item_position;

   RepoListAdapter(RepoSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;
        this.data = new ArrayList<>();
    }

    public void setUserList(List<Repo> repos) {
        data.clear();
        data.addAll(repos);
        notifyDataSetChanged();


    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item_list, parent, false);
        return new RepoViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
        final Repo repo = data.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repoSelectedListener.onRepoSelected(repo);
            }
        });
        holder.layout.setBackgroundColor(Color.WHITE);
        holder.text_followed.setText("");
        if(BaseApplication.blocked_list.contains(data.get(position).getId())){
            holder.layout.setBackgroundColor(Color.LTGRAY);
        }
        if(BaseApplication.followed_list.contains(data.get(position).getId())){
            holder.text_followed.setText("Followed");
        }


    }


    @Override
    public int getItemCount() {
       System.out.println("size of data"+ data.size());
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }


    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView text_name;
        @BindView(R.id.text_reputation)
        TextView text_reputation;
        @BindView(R.id.text_followed)
        TextView text_followed;
        @BindView(R.id.image_profile)
        ImageView image_profie;
        @BindView(R.id.recycler_layout)
        LinearLayout layout;
        private Repo repo;
        RepoViewHolder(View itemView, RepoSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Repo repo) {
            this.repo = repo;
            text_name.setText("UserName: "+repo.getName());
            text_reputation.setText("Reputation: "+ String.valueOf(repo.getReputation()));
            Glide.with(context).load(repo.getUrl_image()).into(image_profie);

        }
    }
}
