package com.soham.todo.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHost;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soham.todo.R;
import com.soham.todo.databinding.FragmentSignUpBinding;
import com.soham.todo.utils.SignUpEnum;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( getLayoutInflater(),R.layout.fragment_sign_up,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SignUp");
        binding.setSignUpFragment(this);
        this.sharedPreferencesEditor = getActivity()
                .getSharedPreferences(String.valueOf(SignUpEnum.PREFERENCE), Context.MODE_PRIVATE).edit();
    }

    public void saveUserData(){
        if(binding.userNameField.getText().length()>0 &&
            binding.passkeyInputField.getText().length() > 0 &&
            binding.passwordField.getText().length() > 0 &&
            binding.confirmPasswordField.getText().length()>0 &&
                String.valueOf(binding.passwordField.getText())
                        .equals(String.valueOf(binding.confirmPasswordField.getText()))
        ) {
            // here you can make a post request to your api
            // if returned success status then :
            // saving for local use and no need of internet next time
            sharedPreferencesEditor.putString(
                    String.valueOf(SignUpEnum.USERNAME), String.valueOf(binding.userNameField.getText()));
            sharedPreferencesEditor.putString(
                    String.valueOf(SignUpEnum.PASSWORD), String.valueOf(binding.passwordField.getText()));
            sharedPreferencesEditor.putString(
                    String.valueOf(SignUpEnum.PASSKEY), String.valueOf(binding.passkeyInputField.getText()));
            this.goToUnscheduledTasks();
            sharedPreferencesEditor.commit();
        }
        else{
            Toast.makeText(
                    getActivity().getApplicationContext(), "Fill all field correctly", Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void goToUnscheduledTasks() {
        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToUnscheduledTaskFragment();
        findNavController(this).navigate(action);
    }

    public void goToLogin(){
        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToFragmentLogin();
        findNavController(this).navigate(action);
    }

}