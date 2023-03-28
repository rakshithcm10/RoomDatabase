package com.example.application.Data;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.Adapters.StudentAdapter;
import com.example.application.Authentication.AuthenticationViewModel;
import com.example.application.Databases.Student;
import com.example.application.R;

import java.util.List;

public class DisplayData extends AppCompatActivity {

    AuthenticationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        setTitle("Students Data");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        StudentAdapter adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        viewModel = new ViewModelProvider(this,new AuthenticationViewModel(this.getApplication())).get(AuthenticationViewModel.class);
        viewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

    }
}