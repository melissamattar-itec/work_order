package com.example.login_signup;

public class T_User {
    private Integer _id;
    private String Name;
    private String password;
    private String address_Book;


    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getUserID() {
        return Name;
    }

    public void setUserID(String userID) {
        this.Name = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress_Book() {
        return address_Book;
    }

    public void setAddress_Book(String address_Book) {
        this.address_Book = address_Book;
    }

    public T_User(Integer _id, String userID, String password, String address_Book) {
        this._id = _id;
        this.Name = userID;
        this.password = password;
        this.address_Book = address_Book;

    }

}