/**
 * WordCamp Kanpur
 *
 * @package    WordCamp Kanpur
 * @author     Sanyog Shelar <sanyog@hotmail.com>
 * @copyright  Copyright (c) WHMCS Limited 2005-2013
 * @license    WordCamp is released under the GPL
 * @version    1.3
 * @link       https://2017.kanpur.wordcamp.org
 */

package com.version.next.wpkanpur.Notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import com.version.next.wpkanpur.DatabaseHelper;
import com.version.next.wpkanpur.Drawer.Drawer_main;
import com.version.next.wpkanpur.R;
import java.util.List;

/**
 * Created by chintal on 10/25/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    Notification notification;
    DatabaseHelper databaseHelper;
    ActivityManager manager;
    List<ActivityManager.RunningTaskInfo> runningTaskInfo;
    ComponentName componentInfo;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
                 Log.d("remoteMessage->","remoteMessage->"+remoteMessage.toString());
                 Log.d("title->","title->"+remoteMessage.getNotification().getTitle());
                 Log.d("message->","message->"+remoteMessage.getNotification().getBody());

                 manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                 runningTaskInfo = manager.getRunningTasks(1);
                 componentInfo = runningTaskInfo.get(0).topActivity;

                 showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                //showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
    }

    public Notification getNotification()
    {
        return notification;
    }

    private void showNotification(String title, String message)
    {
                    databaseHelper = new DatabaseHelper(getApplicationContext());
                    long time= System.currentTimeMillis();
                    databaseHelper.Insert_Notification(String.valueOf(time),title,message);

                    NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancel(0);

                    Intent intent = new Intent(this, Drawer_main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("intent_type","notification");
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    builder.setStyle(new NotificationCompat.BigTextStyle(builder)
                        .bigText(message)
                        .setBigContentTitle(title)
                        .setSummaryText(getString(R.string.app_name)))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.logo_noti)
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true)
                        .setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary))
                        .setContentIntent(pendingIntent);

                    final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(0, builder.build());
    }
}