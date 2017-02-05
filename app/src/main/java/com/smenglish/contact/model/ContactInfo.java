package com.smenglish.contact.model;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

public class ContactInfo {

    private Address address;
    private String email;
    private String phone;
    private String facebook;

    public ContactInfo() {

    }

    public ContactInfo(Address address, String email, String phone, String facebook) {
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.facebook = facebook;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
