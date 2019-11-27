package com.appsaga.entrymanagement.Models;

import java.io.Serializable;

public class Visitors implements Serializable {

    String Name;
    String Checkin;
    String Checkin_Date;
    String Phone;
    String Email;
    String Checkout;
    String Ongoing;
    Hosts Host;

    public Visitors(String name, String checkin,String checkin_date, String phone, String email,String on_going,Hosts host) {
        Name = name;
        Checkin = checkin;
        Phone = phone;
        Email = email;
        Ongoing = on_going;
        Host=host;
        Checkin_Date=checkin_date;
    }

    /*public Visitors(String name, String checkin, String phone, String email, String checkout, String ongoing,Hosts host) {
        Name = name;
        Checkin = checkin;
        Phone = phone;
        Email = email;
        Checkout = checkout;
        Ongoing = ongoing;
        Host=host;
    }*/

    public Visitors()
    {

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

    public Hosts getHost() {
        return Host;
    }

    public void setHost(Hosts host) {
        Host = host;
    }

    public String getCheckin_Date() {
        return Checkin_Date;
    }

    public void setCheckin_Date(String checkin_Date) {
        Checkin_Date = checkin_Date;
    }
}
