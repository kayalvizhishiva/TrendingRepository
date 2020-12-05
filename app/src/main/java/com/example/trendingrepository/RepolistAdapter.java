package com.example.trendingrepository;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trendingrepository.model.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;

public class RepolistAdapter extends RecyclerView.Adapter<RepolistAdapter.MyviewHolder> {
    public List<Repo> repolist;
    public List<Repo> filterrepolist;
    private LayoutInflater inflater;
    private Context context;

    public RepolistAdapter(Context context,List<Repo> repolist) {
        this.repolist = repolist;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.li_repolist, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.setRepoList(repolist.get(position));
    }

    @Override
    public int getItemCount() {
        if (repolist != null && repolist.size() > 0) {
            return repolist.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_heading)
        TextView repo_heading;
        @BindView(R.id.repo_desc)
        TextView repo_desc;
        @BindView(R.id.language_btn)
        Button language_btn;
        @BindView(R.id.language_name)
        TextView language_name;
        @BindView(R.id.rating_image)
        ImageView rating_image;
        @BindView(R.id.rate_nos)
        TextView rate_nos;

        Repo repodetails;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setRepoList(Repo repo) {
            if(repo == null){
                return;
            }
            this.repodetails = repo;
            repo_heading.setText(repodetails.getName());
            repo_desc.setText(repodetails.getDescription());
            language_btn.setVisibility(View.VISIBLE);
            language_btn.setBackgroundResource(R.drawable.round_btn);
            Drawable roundDrawable = context.getResources().getDrawable(R.drawable.round_btn);
            roundDrawable.setColorFilter(Color.parseColor(repodetails.getLanguageColor()), PorterDuff.Mode.SRC_ATOP);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                language_btn.setBackgroundDrawable(roundDrawable);
            } else {
                language_btn.setBackground(roundDrawable);
            }

            rate_nos.setText(repodetails.getStars().toString());
            language_name.setText(repodetails.getLanguage());
        }
    }


}
