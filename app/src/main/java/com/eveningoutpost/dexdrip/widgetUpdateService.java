package com.eveningoutpost.dexdrip;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class widgetUpdateService extends Service {
    private String TAG = "widgetUpdateService";
    private BroadcastReceiver _broadcastReceiver;
    public widgetUpdateService() {}

    @Override
    public IBinder onBind(Intent intent) { throw new UnsupportedOperationException("Not yet implemented"); }

    @Override
    public void onCreate() {
        super.onCreate();
        setFailoverTimer();
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    updateCurrentBgInfo();
                }
            }
        };
        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setFailoverTimer();
        updateCurrentBgInfo();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_broadcastReceiver != null) {
            unregisterReceiver(_broadcastReceiver);
        }
    }

    private void setFailoverTimer() { //Keep it alive!
        if(AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), xDripWidget.class)).length > 0) {
            long retry_in = (1000 * 60 * 5);
            Log.d(TAG, "Fallover Restarting in: " + (retry_in / (60 * 1000)) + " minutes");
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + retry_in, PendingIntent.getService(this, 0, new Intent(this, widgetUpdateService.class), 0));
        } else {
            stopSelf();
        }
    }

    private void updateCurrentBgInfo() {
        Log.d(TAG, "Sending update flag to widget");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), xDripWidget.class));
        Log.d(TAG, "Updating " + ids.length + " widgets");
        Intent intent = new Intent(this,xDripWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
}
