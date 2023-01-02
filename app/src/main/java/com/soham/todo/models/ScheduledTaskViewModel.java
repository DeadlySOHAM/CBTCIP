package com.soham.todo.models;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.soham.todo.broadcastRecievers.ScheduledTaskAlarmBroadcastReciever;
import com.soham.todo.repositories.ScheduledTodoRepository;
import com.soham.todo.roomModules.ScheduledEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class ScheduledTaskViewModel extends AndroidViewModel {

    public ScheduledTodoRepository scheduledTodoRepository;
//    private Application application;

    public Calendar cal = Calendar.getInstance();

    public ScheduledTaskViewModel(@NonNull Application application) {
        super(application);
        scheduledTodoRepository = new ScheduledTodoRepository(application);
    }


    public int insert(String task) {
        return (this.scheduledTodoRepository.insert(new ScheduledEntity(task,this.cal))).intValue();
    }
}
