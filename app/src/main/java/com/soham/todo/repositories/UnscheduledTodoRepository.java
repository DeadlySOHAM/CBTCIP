package com.soham.todo.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.soham.todo.roomModules.TaskRoomDatabase;
import com.soham.todo.roomModules.UnscheduledDAO;
import com.soham.todo.roomModules.UnscheduledEntity;

import java.util.List;

public class UnscheduledTodoRepository {

    private UnscheduledDAO unsDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    public UnscheduledTodoRepository(Application application) {
        unsDao = TaskRoomDatabase.getDatabase(application).UnscheduledDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<UnscheduledEntity>> getAllTasks() {
        return unsDao.getAllTask();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(UnscheduledEntity task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            unsDao.insertTask(task);
        });
    }

    public void delete(UnscheduledEntity task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            unsDao.deleteTask(task);
        });
    }

}
