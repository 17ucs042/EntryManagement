package com.appsaga.entrymanagement.Models;

import java.io.Serializable;

public class Hosts implements Serializable {

    String Name;
    String Post;
    String Phone;
    String Department;
    String About;
    String Email;

    public Hosts(String name, String post, String phone, String department, String about , String email) {
        Name = name;
        Post = post;
        Phone = phone;
        Department = department;
        About = about;
        Email=email;
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

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }
}
