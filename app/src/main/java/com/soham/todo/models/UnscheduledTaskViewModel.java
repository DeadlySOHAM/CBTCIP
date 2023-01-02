package com.soham.todo.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.soham.todo.repositories.UnscheduledTodoRepository;
import com.soham.todo.roomModules.UnscheduledEntity;

public class UnscheduledTaskViewModel extends AndroidViewModel {

    public UnscheduledTodoRepository unscheduledTodoRepository;

    public UnscheduledTaskViewModel(@NonNull Application application) {
        super(application);
        unscheduledTodoRepository = new UnscheduledTodoRepository(application);
    }

    public void addTask(String text){
        unscheduledTodoRepository.insert(new UnscheduledEntity(text));
    }

}
