package com.smenglish.contact;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smenglish.contact.model.ContactInfo;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

public class ContactPresenter implements ContactContract.Presenter {

    private static final String TAG = ContactPresenter.class.getSimpleName();

    private ContactContract.View mView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference chatElementReference = database.getReference("contact_info");

    ContactPresenter(ContactContract.View contactFragmentView) {
        mView = contactFragmentView;
    }

    @Override
    public void getContactInfo() {
        chatElementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Got contact info");

                ContactInfo contactInfo = dataSnapshot.getValue(ContactInfo.class);

                mView.refreshContactInfo(contactInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Cancelled info get");
                mView.contactInfoRetrieveFailed();
            }
        });
    }

}
