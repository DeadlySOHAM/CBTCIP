package com.soham.todo.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.util.Log;
import android.view.*;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.soham.todo.R;
import com.soham.todo.databinding.FragmentLoginBinding;
import com.soham.todo.utils.CONSTANT_CLASS;
import com.soham.todo.utils.SignUpEnum;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private String passkey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_login,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Login");

        binding.setFragmentLogin(this);
        this.passkey = getActivity().getSharedPreferences(
                String.valueOf(SignUpEnum.PREFERENCE), Context.MODE_PRIVATE)
                .getString(String.valueOf(SignUpEnum.PASSKEY), CONSTANT_CLASS.DEFAULT_PASSWORD);
        if(this.passkey == CONSTANT_CLASS.DEFAULT_PASSWORD )
            this.signUp();
    }

    private void ShowAlert(String message) {
        new MaterialAlertDialogBuilder(getContext())
                .setMessage(message)
                .setPositiveButton("Ok",null)
                .show();
    }

    public void Login(){
        if(String.valueOf(binding.passkeyInput.getText()).equals(this.passkey)){
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToUnscheduledTaskFragment();
            findNavController(this).navigate(action);
            binding.passkeyInput.setText("");
            return;
        }
        Log.d("testing",this.passkey);
        Log.d("testing",String.valueOf(binding.passkeyInput.getText()));
        Toast.makeText(getContext(),"Incorrect passkey",Toast.LENGTH_SHORT);
    }

    public void signUp(){
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
        findNavController(this).navigate(action);
    }

}