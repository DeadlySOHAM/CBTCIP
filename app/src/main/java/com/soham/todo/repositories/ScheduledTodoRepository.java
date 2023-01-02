package com.soham.todo.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.soham.todo.roomModules.ScheduledDAO;
import com.soham.todo.roomModules.ScheduledEntity;
import com.soham.todo.roomModules.TaskRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ScheduledTodoRepository{

        private ScheduledDAO sDao;

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        public ScheduledTodoRepository(Application application) {
            sDao = TaskRoomDatabase.getDatabase(application).ScheduledDAO();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<ScheduledEntity>> getAllTasks() {
            return sDao.getAllTask();
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public Long insert(ScheduledEntity task) {
            Future<Long> f = TaskRoomDatabase.databaseWriteExecutor.submit(new CallableInsertReturn(sDao,task));
            try {
                return f.get();
            }catch (Exception e){
                return -1L;
            }
        }

        public void delete(ScheduledEntity task) {
            TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
                sDao.deleteTask(task);
            });
        }

        public ScheduledEntity getTaskById(int id){
            Future<ScheduledEntity> f = TaskRoomDatabase.databaseWriteExecutor.submit(new CallableGetReturn(sDao,id));
            try {
                return f.get();
            }catch (Exception e){
                return null;
            }
        }
}


class CallableInsertReturn implements Callable<Long> {
    private ScheduledDAO scheduledDAO;
    private ScheduledEntity task;
    public CallableInsertReturn(ScheduledDAO scheduledDAO, ScheduledEntity task){
        this.scheduledDAO = scheduledDAO;
        this.task = task;
    }
    @Override
    public Long call(){
        long f = scheduledDAO.insertTask(task);
        return f;
    }
}



class CallableGetReturn implements Callable<ScheduledEntity> {
    private ScheduledDAO scheduledDAO;
    private int taskId;
    public CallableGetReturn(ScheduledDAO scheduledDAO, int taskId){
        this.scheduledDAO = scheduledDAO;
        this.taskId = taskId;
    }
    @Override
    public ScheduledEntity call(){
        ScheduledEntity f = scheduledDAO.getTaskById(this.taskId);
        return f;
    }
}