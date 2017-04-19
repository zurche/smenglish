package com.smenglish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.smenglish.contact.ContactFragment;
import com.smenglish.news.NewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        setupBottomNavigationListener();

        replaceCurrentFragmentWith(new NewsFragment());
    }

    /**
     * Setup bottom navigation listener.
     */
    private void setupBottomNavigationListener() {
        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_news:
                                replaceCurrentFragmentWith(new NewsFragment());
                                break;
                            case R.id.action_contact:
                                replaceCurrentFragmentWith(new ContactFragment());
                                break;
                        }
                        return true;
                    }
                });
    }

    /**
     * Sets the main container fragment.
     *
     * @param fragment to show.
     */
    private void replaceCurrentFragmentWith(BaseTitleFragment fragment) {
        String currentSectionName = getString(fragment.getSectionNameResource());

        setTitle(currentSectionName);

        Log.d(TAG, "Replacing container with " + currentSectionName);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.base_container, fragment);
        transaction.commit();
    }
}
