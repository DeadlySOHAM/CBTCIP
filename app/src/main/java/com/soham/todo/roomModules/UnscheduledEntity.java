package com.soham.todo.roomModules;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Unscheduled Tasks")
public class UnscheduledEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id ;

    @NonNull
    @ColumnInfo(name = "Task Text")
    public String task;

    public UnscheduledEntity(String task){
        this.task = task;
    }

    @NonNull
    @Override
    public String toString() {
        return this.task;
    }
}
