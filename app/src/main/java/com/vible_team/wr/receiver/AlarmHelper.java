package com.vible_team.wr.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class AlarmHelper {
    private static AlarmHelper instance;
    private Context context;
    private AlarmManager alarmManager;

    static AlarmHelper getInstance() {
        if (instance == null)
            instance = new AlarmHelper();
        return instance;
    }

    void init(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(int ID, String title, long dateMill) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ID", ID);
        intent.putExtra("title", title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, dateMill, pendingIntent);

        Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show();
    }

    public void deleteAlarm(int ID) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show();
    }
}
