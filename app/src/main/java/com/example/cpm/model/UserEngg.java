package com.example.cpm.model;

import java.io.Serializable;

public class UserEngg implements Serializable {
    String authority,name,email,contact,gender;

    public UserEngg() {
    }

    public UserEngg(String authority, String name, String email, String contact, String gender) {
        this.authority = authority;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
