package com.smenglish.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.smenglish.BaseTitleFragment;
import com.smenglish.R;
import com.smenglish.news.model.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

public class NewsFragment extends BaseTitleFragment implements NewsContract.View {

    public static final String TAG = NewsFragment.class.getSimpleName();

    private NewsPresenter presenter;

    @BindView(R.id.news_list)
    RecyclerView news_list;
    @BindView(R.id.news_feed_progress_bar)
    ProgressBar news_feed_progress_bar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        ButterKnife.bind(this, view);

        presenter = new NewsPresenter(this);

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.retrieveNewsFeed();
                news_list.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public int getSectionNameResource() {
        return R.string.news;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.retrieveNewsFeed();
        news_feed_progress_bar.setVisibility(View.VISIBLE);
        news_list.setVisibility(View.GONE);
    }

    @Override
    public void onFeedRetrieved(List<News> newsList) {
        news_list.setVisibility(View.VISIBLE);
        news_feed_progress_bar.setVisibility(View.GONE);
        swipe_container.setRefreshing(false);

        NewsFeedAdapter mNewsFeedAdapter = new NewsFeedAdapter(getActivity(), newsList);
        news_list.setAdapter(mNewsFeedAdapter);
        news_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        news_list.setHasFixedSize(true);
    }
}
