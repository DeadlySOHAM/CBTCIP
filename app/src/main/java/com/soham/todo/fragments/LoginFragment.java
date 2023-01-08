package com.soham.todo.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.soham.todo.R;
import com.soham.todo.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding ;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Login");
        binding.setLoginFragment(this);
    }

    public void loginUser(View view){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.getContext())
                .setMessage("Backend not present so login cannot be demonstrated")
                .setPositiveButton("ok",(dialog, which)->{goToUnscheduledTaskPage();});
        builder.show();
    }

    private void goToUnscheduledTaskPage() {
        NavDirections action = LoginFragmentDirections.actionFragmentLoginToUnscheduledTaskFragment();
        findNavController(this).navigate(action);
    }
}