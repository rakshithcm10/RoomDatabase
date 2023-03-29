package com.example.application.Databases;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {

    @PrimaryKey(autoGenerate = true)
    public  int ID;

    @ColumnInfo(name = "First_Name")
    @NonNull
    public String firstName;

    @ColumnInfo(name = "Last_Name")
    public String lastName;

    @ColumnInfo(name = "Email")
    public String email;

    @ColumnInfo(name = "Phone_Number")
    public String phoneNumber;

    @ColumnInfo(name = "Password")
    public String password;

    @ColumnInfo(name = "Student_Id", defaultValue = "0")
    public int studentId;

    @ColumnInfo(name = "Address")
    public String address;


    public Student(String firstName, String lastName, String email, String phoneNumber, String password,int studentId,String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.studentId = studentId;
        this.address = address;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {return firstName;}

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String name) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {return address;
    }
}
