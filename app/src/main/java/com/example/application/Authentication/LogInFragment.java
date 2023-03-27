package com.example.application.Authentication;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.application.Databases.AppDatabase;
import com.example.application.Databases.StudentsDao;
import com.example.application.Home.HomeActivity;
import com.example.application.R;
import com.example.application.databinding.FragmentLogInBinding;


public class LogInFragment extends Fragment {
    FragmentLogInBinding logInBinding;
    StudentsDao studentsDao;
    AppDatabase db;
    SharedPreferences sharedPreferences;
    AuthenticationViewModel authenticationViewModel;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        logInBinding = FragmentLogInBinding.inflate(inflater, container, false);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "Students-info").allowMainThreadQueries().build();
        studentsDao = db.studentsDao();

        sharedPreferences = this.requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


        if(sharedPreferences.contains("Email")){

            authenticationViewModel = new ViewModelProvider(requireActivity(),new AuthenticationViewModel(this.getActivity().getApplication())).get(AuthenticationViewModel.class);
            authenticationViewModel.setEmail(sharedPreferences.getString("Email","default"));

            Intent i = new Intent(getActivity(), HomeActivity.class);
            startActivity(i);

        }


        logInBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (logInBinding.loginEmailText.length() == 0 && logInBinding.loginPasswordText.length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean result = studentsDao.checkEmailPassword(logInBinding.loginEmailText.getText().toString().toLowerCase().trim(),
                                 logInBinding.loginPasswordText.getText().toString());

                if (result) {
                    myEdit.putString("Email", logInBinding.loginEmailText.getText().toString().trim());
                    myEdit.putString("Password", logInBinding.loginPasswordText.getText().toString());

                    myEdit.apply();

                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    startActivity(i);


                } else {
                    Toast.makeText(getActivity(), "Email or Password must be Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logInBinding.txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_signUpFragment);
            }
        });

        return logInBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authenticationViewModel = new ViewModelProvider(requireActivity(),new AuthenticationViewModel(this.getActivity().getApplication())).get(AuthenticationViewModel.class);
        authenticationViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                logInBinding.loginEmailText.setText(s);
            }
        });

        authenticationViewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                logInBinding.loginPasswordText.setText(s);
            }
        });
    }
}