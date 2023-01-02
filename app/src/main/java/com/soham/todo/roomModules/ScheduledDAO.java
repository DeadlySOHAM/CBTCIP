package com.soham.todo.roomModules;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ScheduledDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTask(ScheduledEntity task);

    @Delete
    void deleteTask(ScheduledEntity task);

    @Query("SELECT * FROM `Scheduled Tasks`")
    LiveData<List<ScheduledEntity>> getAllTask();

    @Query("SELECT * FROM `Scheduled Tasks` WHERE id=:id")
    ScheduledEntity getTaskById(int id);

}
