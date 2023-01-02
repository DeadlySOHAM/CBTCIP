package com.soham.todo.roomModules;

import java.util.*;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Scheduled Tasks")
public class ScheduledEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id ;

    @NonNull
    @ColumnInfo(name = "Task Text")
    public String task;

    @NonNull
    @ColumnInfo(name = "Day of Month")
    public int day_of_month;

    @NonNull
    @ColumnInfo(name = "Month")
    public int month;

    @NonNull
    @ColumnInfo(name = "Year")
    public int year;

    @NonNull
    @ColumnInfo(name = "Hour")
    public int hour;

    @NonNull
    @ColumnInfo(name = "Minutes")
    public int minute;

    public ScheduledEntity(){}

    public ScheduledEntity(String task, Calendar cal){
        this.task = task;
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.day_of_month = cal.get(Calendar.DAY_OF_MONTH);
        this.month=cal.get(Calendar.MONTH);
        this.year=cal.get(Calendar.YEAR);
    }

    @NonNull
    @Override
    public String toString() {
        return this.task;
    }

    public String getDate(){
        return this.day_of_month+"/"+(this.month+1)+"/"+this.year;
    }

    public String getTime(){
        return this.hour+":"+getZeroMinute();
    }

    private String getZeroMinute() {
        return this.minute < 10 ?
                "0"+(this.minute+1) : String.valueOf(this.minute) ;
    }

}

