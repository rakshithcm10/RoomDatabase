package com.example.application.Adapters;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.Databases.Student;
import com.example.application.Databases.StudentRepository;
import com.example.application.databinding.ActivityEditStudentBinding;


public class EditStudentActivity extends AppCompatActivity {

    private ActivityEditStudentBinding binding;
    private StudentRepository repository;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = new StudentRepository(getApplication());

        System.out.println("=============================" + getIntent().getSerializableExtra("student") + "==========================" );
        student = repository.getStudentById((Integer) getIntent().getSerializableExtra("student")) ;

        binding.etFirstName.setText(student.getFirstName());
        binding.etEmail.setText(student.getEmail());
        binding.etPassword.setText(student.getPassword());

        binding.btnSave.setOnClickListener(v -> {
            String firstName = binding.etFirstName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
            } else {
                Student updatedStudent = new Student(firstName, student.getLastName(), email, student.getPhoneNumber(), password, student.getID(), student.getAddress());
                updatedStudent.setID(student.getID());
                repository.updateRecord(updatedStudent);
                Toast.makeText(this, "Student record updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

