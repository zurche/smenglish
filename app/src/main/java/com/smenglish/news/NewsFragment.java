package com.smenglish.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.smenglish.BaseTitleFragment;
import com.smenglish.R;
import com.smenglish.SplashActivity;
import com.smenglish.news.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

public class NewsFragment extends BaseTitleFragment implements NewsContract.View {

    public static final String TAG = NewsFragment.class.getSimpleName();

    private NewsPresenter presenter;

    @BindView(R.id.news_list)
    RecyclerView news_list;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;
    @BindView(R.id.facebook_logged_user_layout)
    RelativeLayout facebook_logged_user_layout;
    @BindView(R.id.non_facebook_logged_in_user)
    LinearLayout non_facebook_logged_in_user;

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
                getNews();
            }
        });

        swipe_container.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            presenter.retrieveNewsFeed();
            swipe_container.setRefreshing(true);
            news_list.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "Check your internet connection\nand try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void getNews() {
        if (isNetworkAvailable()) {
            presenter.retrieveNewsFeed();
            news_list.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "Check your internet connection\nand try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getSectionNameResource() {
        return R.string.news;
    }

    @Override
    public void onFeedRetrieved(List<News> newsList) {
        news_list.setVisibility(View.VISIBLE);
        swipe_container.setRefreshing(false);

        NewsFeedAdapter mNewsFeedAdapter = new NewsFeedAdapter(getActivity(), newsList);
        news_list.setAdapter(mNewsFeedAdapter);
        news_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        news_list.setHasFixedSize(true);
    }

    @Override
    public void showFailedToRetrieveNewsMessage() {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
