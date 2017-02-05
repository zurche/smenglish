package com.smenglish.contact;

import com.smenglish.contact.model.ContactInfo;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

interface ContactContract {
    interface View {

        void refreshContactInfo(ContactInfo contactInfo);

        void contactInfoRetrieveFailed();
    }

    interface Presenter {

        void getContactInfo();
    }
}
