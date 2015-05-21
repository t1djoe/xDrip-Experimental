package com.eveningoutpost.dexdrip.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eveningoutpost.dexdrip.Models.BgReading;
import com.eveningoutpost.dexdrip.UtilityModels.Notifications;

import java.util.Calendar;

public class MissedReadingService extends IntentService {
    private SharedPreferences prefs;
    private boolean bg_missed_alerts;
    private int bg_missed_minutes;
    private int otherAlertSnooze;
    private Context mContext;

    public MissedReadingService() {
        super("MissedReadingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mContext = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        bg_missed_alerts =  prefs.getBoolean("bg_missed_alerts", false);
        bg_missed_minutes =  Integer.parseInt(prefs.getString("bg_missed_minutes", "30"));
        otherAlertSnooze =  Integer.parseInt(prefs.getString("other_alerts_snooze", "20"));

        if (bg_missed_alerts && BgReading.getTimeSinceLastReading() > (bg_missed_minutes * 1000 * 60)) {
            Notifications.bgMissedAlert(mContext);
            checkBackAfterSnoozeTime();
        } else {
            checkBackAfterMissedTime();
        }
    }

   private void checkBackAfterSnoozeTime() {
       setAlarm(otherAlertSnooze * 1000 * 60);
   }

    private void checkBackAfterMissedTime() {
        setAlarm(bg_missed_minutes * 1000 * 60);
    }

    private void setAlarm(long alarmIn) {
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + alarmIn, PendingIntent.getService(this, 0, new Intent(this, MissedReadingService.class), 0));
        } else {
            alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + alarmIn, PendingIntent.getService(this, 0, new Intent(this, MissedReadingService.class), 0));
        }
    }
}
