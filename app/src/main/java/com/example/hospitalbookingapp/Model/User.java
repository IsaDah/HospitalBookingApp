package com.example.hospitalbookingapp.Model;

public class User
{
    public String id;
    public String firstname;
    public String lastname;
    public String password;
    public String email;
    public String phone;


    public User(String id, String firstname, String lastname, String password, String email, String phone)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

}
