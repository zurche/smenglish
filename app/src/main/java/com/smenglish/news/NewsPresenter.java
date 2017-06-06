package com.smenglish.news;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.smenglish.news.model.News;

import java.util.List;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

class NewsPresenter implements NewsContract.Presenter {

    private static final String TAG = NewsPresenter.class.getSimpleName();

    private final NewsContract.View mView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference newsElementsReference = database.getReference("news");

    NewsPresenter(NewsContract.View newsView) {
        mView = newsView;
    }

    @Override
    public void retrieveNewsFeed() {
        newsElementsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Got news");
                GenericTypeIndicator<List<News>> genericTypeIndicatorList = new GenericTypeIndicator<List<News>>() {};
                List<News> newsList = dataSnapshot.getValue(genericTypeIndicatorList);
                newsList.remove(0);
                mView.onFeedRetrieved(newsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Cancelled news get");
                mView.showFailedToRetrieveNewsMessage();
            }
        });
    }

}
