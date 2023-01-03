package com.soham.todo.broadcastRecievers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.soham.todo.R;
import com.soham.todo.utils.AlarmIntentEnum;
import com.soham.todo.utils.CONSTANT_CLASS;

public class ScheduledTaskAlarmBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("testing","Alarm Recieved for id : "+
                intent.getIntExtra(String.valueOf(AlarmIntentEnum.TASK_ID),-1)
        );
        this.sendNotification(
                context,
                intent.getStringExtra(String.valueOf(AlarmIntentEnum.TASK_MESSAGE)),
                intent.getIntExtra(String.valueOf(AlarmIntentEnum.TASK_ID),-1)
        );
    }

    private void sendNotification(Context context,String taskMessage, int taskId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            NotificationChannel notificationChannel = new NotificationChannel(
                    CONSTANT_CLASS.VERBOSE_NOTIFICATION_CHANNEL_ID,
                    CONSTANT_CLASS.VERBOSE_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,CONSTANT_CLASS.VERBOSE_NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Todo")
                .setContentText(taskMessage)
                .setSmallIcon(R.drawable.ic_baseline_date_range_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 500 });

        NotificationManagerCompat.from(context).notify(taskId,notificationBuilder.build());
        Log.d("testing","notify id : "+taskId);
    }
}
