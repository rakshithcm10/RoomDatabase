package com.example.application.Authentication;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.application.Databases.Student;
import com.example.application.Databases.StudentRepository;

import java.util.List;

public class AuthenticationViewModel extends androidx.lifecycle.ViewModel implements ViewModelProvider.Factory {

    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;
    Application mApplication;

    public AuthenticationViewModel(Application application){
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
    }

    private MutableLiveData<String> email = new MutableLiveData<>();

    private MutableLiveData<String> password = new MutableLiveData<>();

    public void setEmail(String input) {
        email.setValue(input);
    }

    public void setPassword(String input) {
        password.setValue(input);
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword(){
        return password;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new AuthenticationViewModel(mApplication);
    }




    public void insertRecord(Student student){
        repository.insertRecord(student);
    }

    public LiveData<List<Student>> getAllStudents(){
        return allStudents;
    }

    public void deleteRecord(Student student){
        repository.deleteRecord(student);
    }

}
