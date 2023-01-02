package com.soham.todo.roomModules;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface UnscheduledDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTask(UnscheduledEntity task);

    @Delete
    void deleteTask(UnscheduledEntity task);

    @Query("SELECT * FROM `Unscheduled Tasks`")
    LiveData<List<UnscheduledEntity>> getAllTask();
}
