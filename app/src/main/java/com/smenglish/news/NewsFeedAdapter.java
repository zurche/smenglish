package com.smenglish.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smenglish.R;
import com.smenglish.news.model.News;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private final Context mContext;
    private List<News> mNewsFeedList;

    NewsFeedAdapter(Context context, List<News> newses) {
        mContext = context;
        mNewsFeedList = newses;
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {
        final News tmpNews = mNewsFeedList.get(position);

        holder.news_feed_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openUrl = new Intent(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(tmpNews.getPostUrl()));
                mContext.startActivity(openUrl);
            }
        });

        holder.story.setText(tmpNews.getStory());
        holder.created_time.setText(tmpNews.getCreatedTime());

        if (null != tmpNews.getMessage()) {
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(tmpNews.getMessage());
        } else {
            holder.message.setVisibility(View.GONE);
        }

        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return mNewsFeedList.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_feed_layout)
        LinearLayout news_feed_layout;
        @BindView(R.id.story_title)
        TextView story;
        @BindView(R.id.created_time)
        TextView created_time;
        @BindView(R.id.message)
        TextView message;

        NewsFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
