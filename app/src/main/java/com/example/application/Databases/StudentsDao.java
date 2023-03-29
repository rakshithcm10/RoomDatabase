package com.example.application.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentsDao {

    @Insert
    void insertRecord(Student student);

    @Delete
    void deleteRecord(Student student);

    @Update
    void UpdateRecord(Student student);

    @Query("Select * from Student")
    LiveData<List<Student>> getAllStudents();

    @Query("select Exists(select * from Student where Email = :email)")
    Boolean checkEmail(String email);

    @Query("select Exists(Select * from Student where Phone_Number = :phone)")
    Boolean checkPhone(String phone);

    @Query("select Exists(Select * from Student where Email = :email and Password = :password)")
    Boolean checkEmailPassword(String email,String password);

    @Query("SELECT * FROM Student WHERE id = :id")
    Student getStudentById(int id);

}
