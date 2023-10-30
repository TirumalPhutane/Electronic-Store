package com.example.electronic_store.Entity;

public class User {

    private String uname;
    private String email;
    private String mobileNo;
    private String password;
    private String address;
    private String pincode;
    private String gender;

    public User() {
    }

    public User(String uname, String email, String mobileNo, String password, String address, String pincode, String gender) {
        this.uname = uname;
        this.email = email;
        this.mobileNo = mobileNo;
        this.password = password;
        this.address = address;
        this.pincode = pincode;
        this.gender = gender;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
