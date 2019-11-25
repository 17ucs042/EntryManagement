package com.appsaga.entrymanagement;

import java.io.Serializable;

public class Visitors implements Serializable {

    String Name;
    String Checkin;
    String Phone;
    String Email;
    String Checkout;
    String Ongoing;

    public Visitors(String name, String checkin, String phone, String email,String on_going) {
        Name = name;
        Checkin = checkin;
        Phone = phone;
        Email = email;
        Ongoing = on_going;
    }

    public Visitors(String name, String checkin, String phone, String email, String checkout, String ongoing) {
        Name = name;
        Checkin = checkin;
        Phone = phone;
        Email = email;
        Checkout = checkout;
        Ongoing = ongoing;
    }

    public String getCheckout() {
        return Checkout;
    }

    public void setCheckout(String checkout) {
        Checkout = checkout;
    }

    public String getOngoing() {
        return Ongoing;
    }

    public void setOngoing(String ongoing) {
        Ongoing = ongoing;
    }

    public Visitors()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCheckin() {
        return Checkin;
    }

    public void setCheckin(String checkin) {
        Checkin = checkin;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
