package com.smenglish.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lb.auto_fit_textview.AutoResizeTextView;
import com.smenglish.R;
import com.smenglish.news.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private final Context mContext;
    private List<News> mNewsFeedList;

    NewsFeedAdapter(Context context, List<News> news) {
        mContext = context;
        mNewsFeedList = news;
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {
        final News currentNewsItem = mNewsFeedList.get(position);

        if (currentNewsItem != null) {
            holder.message.setText(currentNewsItem.getMessage());

            boolean linkIsValid = currentNewsItem.getLink() != null && URLUtil.isValidUrl(currentNewsItem.getLink());
            if (linkIsValid) {
                holder.news_feed_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent openUrl = new Intent(Intent.ACTION_VIEW);
                        openUrl.setData(Uri.parse(currentNewsItem.getLink()));
                        mContext.startActivity(openUrl);
                    }
                });
            }

            boolean imageUrlIsValid = currentNewsItem.getImage_url() != null && URLUtil.isValidUrl(currentNewsItem.getImage_url());
            if (imageUrlIsValid) {
                holder.image.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(currentNewsItem.getImage_url()).into(holder.image);
            }

            holder.created_time.setText(currentNewsItem.getPosted_date());

            if (currentNewsItem.getSource() != null) {
                holder.source.setText(currentNewsItem.getSource());
                holder.source.setVisibility(View.VISIBLE);
                holder.shared_by.setVisibility(View.VISIBLE);
            } else {
                holder.source.setVisibility(View.GONE);
                holder.shared_by.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNewsFeedList.size();
    }

    class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_feed_layout)
        LinearLayout news_feed_layout;
        @BindView(R.id.created_time)
        TextView created_time;
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.shared_by)
        TextView shared_by;
        @BindView(R.id.message)
        AutoResizeTextView message;
        @BindView(R.id.image)
        ImageView image;

        NewsFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
