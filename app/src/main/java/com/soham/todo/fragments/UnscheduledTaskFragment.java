package com.soham.todo.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.soham.todo.adapters.UnscheduledRecyclerViewAdapter;
import com.soham.todo.adapters.UnscheduledRecyclerViewHolder;
import com.soham.todo.models.UnscheduledTaskViewModel;
import com.soham.todo.R;
import com.soham.todo.databinding.FragmentUnscheduledTaskBinding;

public class UnscheduledTaskFragment extends Fragment implements MenuProvider {

    private FragmentUnscheduledTaskBinding binding ;
    private UnscheduledTaskViewModel viewModel;
    private RecyclerView unscheduledRecyclerView;
    private UnscheduledRecyclerViewAdapter unscheduledRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_unscheduled_task,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UnscheduledTaskViewModel.class);
        getActivity().addMenuProvider(this,getViewLifecycleOwner());
        getActivity().setTitle("Unscheduled Tasks");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        binding.setUnscheduledTaskFragment(this);

        unscheduledRecyclerView = binding.unscheduledTaskList;
        unscheduledRecyclerViewAdapter = new UnscheduledRecyclerViewAdapter(
                new UnscheduledRecyclerViewAdapter.UnscheduledTaskDiff());

        unscheduledRecyclerView.setAdapter(unscheduledRecyclerViewAdapter);
        unscheduledRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        ItemTouchHelper ith = new ItemTouchHelper(getSimpleCallbackForSwipe());
        ith.attachToRecyclerView(binding.unscheduledTaskList);


        viewModel.unscheduledTodoRepository.getAllTasks().observe(this.getViewLifecycleOwner(), tasks->{
            unscheduledRecyclerViewAdapter.submitList(tasks);
        });


        binding.addTaskButton.setOnLongClickListener(v -> {
            goToScheduledTask();
            return true;
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        this.viewModel = null;
        this.unscheduledRecyclerView = null;
        this.unscheduledRecyclerViewAdapter = null;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu,menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.go_to_schedule_tasks) {
            goToScheduledTask();
            return true;
        }
        return false;
    }

    public void addTask(View view){
        if(binding.taskTextInput.getText().toString().length()>1) {
            viewModel.addTask(String.valueOf(binding.taskTextInput.getText()));
            binding.taskTextInput.setText("");
            ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return ;
    }

    public void goToScheduledTask(){
        NavDirections action = UnscheduledTaskFragmentDirections.actionUnscheduledTaskFragmentToScheduledTaskFragment();
        findNavController(this).navigate(action);
    }

    private ItemTouchHelper.SimpleCallback getSimpleCallbackForSwipe(){
        return new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(viewHolder instanceof UnscheduledRecyclerViewHolder){
                    viewModel.unscheduledTodoRepository.delete(((UnscheduledRecyclerViewHolder) viewHolder).task);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Something Major Went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        }  ;
    }
}