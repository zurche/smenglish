package com.smenglish.contact.model;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

public class Address {

    private String gmapsurl;
    private String readable;

    public Address() {
    }

    public Address(String gmapsurl, String readable) {
        this.gmapsurl = gmapsurl;
        this.readable = readable;
    }

    public String getGmapsurl() {
        return gmapsurl;
    }

    public void setGmapsurl(String gmapsurl) {
        this.gmapsurl = gmapsurl;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }
}
