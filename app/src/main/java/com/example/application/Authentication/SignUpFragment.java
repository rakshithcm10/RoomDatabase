package com.example.application.Authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.Databases.AppDatabase;
import com.example.application.Databases.Student;
import com.example.application.Databases.StudentsDao;
import com.example.application.R;
import com.example.application.databinding.FragmentSignUpBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    FragmentSignUpBinding signUpBinding;
    AppDatabase db;
    StudentsDao studentsDao;
    AuthenticationViewModel authenticationViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        signUpBinding =  FragmentSignUpBinding.inflate(inflater, container, false);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "Students-info").allowMainThreadQueries().build();
        studentsDao = db.studentsDao();
        authenticationViewModel = new ViewModelProvider(requireActivity(),new AuthenticationViewModel(this.getActivity().getApplication())).get(AuthenticationViewModel.class);

        signUpBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validEmail =  isValidEmail(signUpBinding.emailText);
                boolean validPhone = isValidPhone(signUpBinding.mobileNumberText);
                boolean validPassword = isValidPassword(signUpBinding.passwordText);
                boolean validFirstName = characterValidation(signUpBinding.firstNameText);
                boolean validLastName = characterValidation(signUpBinding.lastNameText);

                Boolean isEmailExist = studentsDao.checkEmail(signUpBinding.emailText.getText().toString().toLowerCase().trim());

                Boolean isPhoneExist = studentsDao.checkPhone(signUpBinding.mobileNumberText.getText().toString().trim());


                if(validEmail && validPhone && validPassword && validFirstName && validLastName){
                    if( !(isEmailExist) && !(isPhoneExist)){



                        authenticationViewModel.insertRecord(new Student
                       (signUpBinding.firstNameText.getText().toString().trim(),
                        signUpBinding.lastNameText.getText().toString().trim(),
                        signUpBinding.emailText.getText().toString().toLowerCase().trim(),
                        signUpBinding.mobileNumberText.getText().toString().trim(),
                        signUpBinding.passwordText.getText().toString(),
                        Integer.parseInt(signUpBinding.idText.getText().toString()),
                        signUpBinding.addressText.getText().toString()));

                        Toast.makeText(getActivity(), "Registration Successful ", Toast.LENGTH_SHORT).show();

                        authenticationViewModel.setEmail(signUpBinding.emailText.getText().toString().trim());
                        authenticationViewModel.setPassword(signUpBinding.passwordText.getText().toString().trim());

                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_logInFragment);

                    }
                    else {
                        if(isEmailExist){
                            signUpBinding.emailText.setError("Email Already Exist");
                        }
                        else{
                            signUpBinding.mobileNumberText.setError("Phone Number Already Exist");
                        }
                    }


                }
            }
        });




        return signUpBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }






    public boolean isValidEmail(EditText Mail) {

        String emailToText = Mail.getText().toString().trim();
        if(Mail.length() ==0 ){
            Mail.setError("This field cannot be Empty");
            return false;
        }

        else if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return true;
        }

        else {
            signUpBinding.emailText.setError("Invalid Email");
            return false;
        }
    }



    public boolean isValidPhone(EditText phone)
    {
        String phoneNumber = phone.getText().toString().trim();
        String expression = "^([0-9\\+]|\\(\\d{1,10}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);

        if(phone.length() == 0){
            phone.setError("This field cannot be Empty");
            return false;
        }
        else if (!(matcher.matches())) {
            phone.setError("Invalid Mobile Number");
            return false;
        }
        else if (phone.length() != 10) {
            phone.setError("Phone Number must be 10 digits");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isValidPassword(@NonNull EditText passcode){

        if(passcode.length()==0){
            passcode.setError("This field cannot be Empty");
            return false;
        }
        else if(passcode.length() < 8){
            signUpBinding.passwordText.setError("Password must contains 8 characters");
            return false;
        }
        else{
            return true;
        }
    }


    public boolean characterValidation(EditText name){

        String str = name.getText().toString().trim();
        boolean nameResult = str.matches("[a-zA-Z]+");
        if(name.length()==0){
            name.setError("This field cannot be Empty");
            return false;
        }
        else if (!nameResult){
            name.setError("This field must contains only characters");
            return false;
        }
        else {
            return true;
        }
    }
}