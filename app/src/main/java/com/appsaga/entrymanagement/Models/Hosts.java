package com.appsaga.entrymanagement.Models;

        import java.io.Serializable;

public class Hosts implements Serializable {

    String Name;
    String Phone;
    String Email;

    public Hosts(String name, String phone, String email) {
        Name = name;
        Phone = phone;
        Email = email;
    }

    public Hosts() {
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
