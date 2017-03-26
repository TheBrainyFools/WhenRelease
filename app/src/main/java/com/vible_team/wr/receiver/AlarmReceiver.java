package com.vible_team.wr.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.vible_team.wr.MainActivity;
import com.vible_team.wr.R;
import com.vible_team.wr.database.DatabaseHelper;

public class AlarmReceiver extends BroadcastReceiver {
    DatabaseHelper databaseHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        int ID = intent.getIntExtra("ID", 0);
        String title = intent.getStringExtra("title");

        Intent result = new Intent(context, MainActivity.class);
        result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, result, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        builder.setContentText(title + " is already released!");
        builder.setSmallIcon(R.drawable.ic_new_release_white_48dp);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID, notification);

        databaseHelper = new DatabaseHelper(context);
        databaseHelper.update().notificated(ID);
    }
}
