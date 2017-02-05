package com.smenglish.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.smenglish.BaseTitleFragment;
import com.smenglish.R;
import com.smenglish.contact.model.ContactInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

public class ContactFragment extends BaseTitleFragment implements ContactContract.View {

    public static final String TAG = ContactFragment.class.getSimpleName();

    @BindView(R.id.phone_number_value)
    TextView phone_number_value;
    @BindView(R.id.address_value)
    TextView address_value;
    @BindView(R.id.email_value)
    TextView email_value;
    @BindView(R.id.facebook_page)
    TextView facebook_page;

    @BindView(R.id.contact_info_progress_bar)
    ProgressBar contact_info_progress_bar;

    @BindView(R.id.contact_info_scroll)
    ScrollView contact_info_scroll;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;

    private ContactPresenter presenter;

    private ContactInfo mCurrentContactInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ButterKnife.bind(this, view);

        presenter = new ContactPresenter(this);
        presenter.getContactInfo();

        contact_info_progress_bar.setVisibility(View.VISIBLE);

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contact_info_scroll.setVisibility(View.GONE);
                presenter.getContactInfo();
            }
        });

        return view;
    }

    @Override
    public int getSectionNameResource() {
        return R.string.contact;
    }

    @Override
    public void refreshContactInfo(ContactInfo contactInfo) {
        Log.d(TAG, "Got contact info: " + contactInfo.getEmail());

        mCurrentContactInfo = contactInfo;

        swipe_container.setRefreshing(false);
        contact_info_progress_bar.setVisibility(View.GONE);
        contact_info_scroll.setVisibility(View.VISIBLE);

        phone_number_value.setText(contactInfo.getPhone());
        address_value.setText(contactInfo.getAddress().getReadable());
        email_value.setText(contactInfo.getEmail());

        facebook_page.setClickable(true);
        facebook_page.setMovementMethod(LinkMovementMethod.getInstance());
        String facebookTextUrl = "<a href='"+contactInfo.getFacebook()+"'> Facebook Page </a>";
        facebook_page.setText(Html.fromHtml(facebookTextUrl));
    }

    @Override
    public void contactInfoRetrieveFailed() {
        Toast.makeText(getActivity(), "Failed to retrieve contact info", Toast.LENGTH_SHORT).show();
        contact_info_progress_bar.setVisibility(View.GONE);
    }

    @OnClick(R.id.phone_number_layout)
    public void onClickPhoneNumber(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mCurrentContactInfo.getPhone()));
        startActivity(intent);
    }

    @OnClick(R.id.address_layout)
    public void onClickAddress(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/" + mCurrentContactInfo.getAddress().getGmapsurl()));
        startActivity(intent);
    }

    @OnClick(R.id.email_layout)
    public void onClickEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, mCurrentContactInfo.getEmail());

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
