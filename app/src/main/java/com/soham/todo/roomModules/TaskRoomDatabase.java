package com.soham.todo.roomModules;

import android.content.Context;

import androidx.room.*;
import androidx.room.RoomDatabase;

import java.util.concurrent.*;

@Database(entities = {UnscheduledEntity.class, ScheduledEntity.class},version=1,exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {

    public abstract UnscheduledDAO UnscheduledDAO();
    public abstract ScheduledDAO ScheduledDAO();

    private static volatile TaskRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskRoomDatabase.class, "ToDo Database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
