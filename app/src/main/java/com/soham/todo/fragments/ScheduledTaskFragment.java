package com.soham.todo.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import com.soham.todo.adapters.ScheduledRecyclerViewAdapter;
import com.soham.todo.adapters.ScheduledRecyclerViewHolder;
import com.soham.todo.models.ScheduledTaskViewModel;
import com.soham.todo.R;
import com.soham.todo.databinding.FragmentScheduledTaskBinding;
import com.soham.todo.roomModules.ScheduledEntity;
import com.soham.todo.utils.AlarmSetter;

import java.util.Calendar;

public class ScheduledTaskFragment extends Fragment {

    private FragmentScheduledTaskBinding binding ;
    private ScheduledTaskViewModel viewModel ;
    private RecyclerView scheduledRecyclerView;
    private ScheduledRecyclerViewAdapter scheduledRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_scheduled_task,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ScheduledTaskViewModel.class);
        binding.setScheduledTaskFragment(this);

        getActivity().setTitle("Scheduled Tasks");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        scheduledRecyclerView = binding.unscheduledTaskList;
        scheduledRecyclerViewAdapter = new ScheduledRecyclerViewAdapter(
                new ScheduledRecyclerViewAdapter.ScheduledTaskDiff());

        scheduledRecyclerView.setAdapter(scheduledRecyclerViewAdapter);
        scheduledRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        ItemTouchHelper ith = new ItemTouchHelper(this.getSimpleCallBackForSwipe());
        ith.attachToRecyclerView(binding.unscheduledTaskList);


        viewModel.scheduledTodoRepository.getAllTasks().observe(this.getViewLifecycleOwner(), tasks->{
            scheduledRecyclerViewAdapter.submitList(tasks);
        });

    }

    private ItemTouchHelper.SimpleCallback getSimpleCallBackForSwipe(){
        return  new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(viewHolder instanceof ScheduledRecyclerViewHolder){
                    delete((ScheduledRecyclerViewHolder)viewHolder);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Something Major Went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void delete(ScheduledRecyclerViewHolder viewHolder) {
        viewModel.scheduledTodoRepository.delete(viewHolder.task);
        (new AlarmSetter(this.getActivity().getApplication())).cancelAlarm(viewHolder.task);
    }


    public void setDate(View view){
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, dayOfMonth) -> {
            binding.setDateButton.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            viewModel.cal.set(Calendar.YEAR,year);
            viewModel.cal.set(Calendar.MONTH,month);
            viewModel.cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                onDateSetListener,
                viewModel.cal.get(Calendar.YEAR),
                viewModel.cal.get(Calendar.MONTH),
                viewModel.cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setTime(View view){
        TimePickerDialog.OnTimeSetListener onDateSetListener = (timePicker, hour, minute) -> {
//            Log.d("Testing", hour + ":" + minute);
            viewModel.cal.set(Calendar.HOUR_OF_DAY,hour);
            viewModel.cal.set(Calendar.MINUTE,minute);
            viewModel.cal.set(Calendar.SECOND,0);
            binding.setTimeButton.setText(hour + ":" + minute);
        };
        TimePickerDialog datePickerDialog = new TimePickerDialog(
                getActivity(),
                onDateSetListener,
                viewModel.cal.get(Calendar.HOUR_OF_DAY),
                viewModel.cal.get(Calendar.MINUTE),
                false
        );
        datePickerDialog.show();
    }

    public void addTask(View view){
        if(binding.taskTextInput.getText().toString().length()>1) {
            int id = viewModel.insert(String.valueOf(binding.taskTextInput.getText()));
            new Thread(()->{setAlarm(id);}).start();
            binding.taskTextInput.setText("");
            ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return ;
    }

    public void setAlarm(int id){
        ScheduledEntity task = viewModel.scheduledTodoRepository.getTaskById(id);
        if(task!=null)
            (new AlarmSetter(this.getActivity().getApplication())).setAlarm(task, viewModel.cal.getTimeInMillis());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        this.viewModel = null;
        this.scheduledRecyclerView = null;
        this.scheduledRecyclerViewAdapter = null;
    }

}

