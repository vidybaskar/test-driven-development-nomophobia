package com.example.project_nomophobia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationActivity extends NotificationListenerService
{
    private String tag = this.getClass().getSimpleName();
    private NotificationReceiver notificationReceiver;

    @Override
    public void onCreate()
    {
        super.onCreate();
        notificationReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.project_nomophobia.NOTIFICATION_LISTENER");
        registerReceiver(notificationReceiver, filter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(notificationReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification notif) {
        Intent i1 = new Intent("com.example.project_nomophobia.NOTIFICATION_LISTENER");
        i1.putExtra("notification_event", "onNotificationPosted: " + notif.getPackageName() + "\n");

        sendBroadcast(i1);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification notif)
    {
        Intent i1 = new Intent("com.example.project_nomophobia.NOTIFICATION_LISTENER");
        i1.putExtra("notification_event", "onNotificationRemoved: " + notif.getPackageName() + "\n");

        sendBroadcast(i1);
    }

    private class NotificationReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getStringExtra("command").equals("clearall"))
            {
                NotificationActivity.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list"))
            {
                Intent intent1 = new Intent("com.example.project_nomophobia.NOTIFICATION_LISTENER");
                intent1.putExtra("notification_event", "=====================");
                sendBroadcast(intent1);

                int val = 0;
                for (StatusBarNotification notif : NotificationActivity.this.getActiveNotifications())
                {
                    Intent intent2 = new Intent("com.example.project_nomophobia.NOTIFICATION_LISTENER");
                    intent2.putExtra("notification_event", val + " " + notif.getPackageName() + "\n");
                    sendBroadcast(intent2);
                    val++;
                }

                Intent intent3 = new Intent("com.example.project_nomophobia.NOTIFICATION_LISTENER");
                intent3.putExtra("notification_event", "===== Notification List =====");
                sendBroadcast(intent3);
            }
        }
    }
}


