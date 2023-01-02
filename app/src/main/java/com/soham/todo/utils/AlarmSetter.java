package com.soham.todo.utils;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.soham.todo.broadcastRecievers.ScheduledTaskAlarmBroadcastReciever;
import com.soham.todo.roomModules.ScheduledEntity;

public class AlarmSetter {
    private Application application;

    public AlarmSetter(Application application){
        this.application = application;
    }

    private PendingIntent getPendingIntent(ScheduledEntity task){
        Intent intent = new Intent(application, ScheduledTaskAlarmBroadcastReciever.class);
        intent.putExtra(String.valueOf(AlarmIntentEnum.TASK_ID),task.id);
        intent.putExtra(String.valueOf(AlarmIntentEnum.TASK_MESSAGE),task.task);
        return PendingIntent.getBroadcast(
                application.getApplicationContext(),
                task.id,intent,0
        );
    }


    public void setAlarm(ScheduledEntity task,long timeInMillis){
        PendingIntent pendingIntent = getPendingIntent(task);
        Log.d("testing","setting alarm id:"+task.id);
        AlarmManager alarmManager = (AlarmManager) application.getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
    }

    public void cancelAlarm(ScheduledEntity task){
        PendingIntent pendingIntent = getPendingIntent(task);
        Log.d("testing","cancelling alarm id :"+task.id);
        AlarmManager alarmManager = (AlarmManager) application.getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
