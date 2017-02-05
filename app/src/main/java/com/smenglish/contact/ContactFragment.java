package com.smenglish.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smenglish.BaseTitleFragment;
import com.smenglish.R;

import butterknife.ButterKnife;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

public class ContactFragment extends BaseTitleFragment implements ContactContract.View {

    public static final String TAG = ContactFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public int getSectionNameResource() {
        return R.string.contact;
    }
}
