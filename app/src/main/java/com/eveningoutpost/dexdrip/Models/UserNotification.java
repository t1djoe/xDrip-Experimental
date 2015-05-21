package com.eveningoutpost.dexdrip.Models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.Objects;

/**
 * Created by stephenblack on 11/29/14.
 */

@Table(name = "Notifications", id = BaseColumns._ID)
public class UserNotification extends Model {

    @Column(name = "timestamp", index = true)
    public double timestamp;

    @Column(name = "message")
    private String message;

    @Column(name = "bg_alert")
    private boolean bg_alert;

    @Column(name = "calibration_alert")
    private boolean calibration_alert;

    @Column(name = "double_calibration_alert")
    private boolean double_calibration_alert;

    @Column(name = "extra_calibration_alert")
    private boolean extra_calibration_alert;

    @Column(name = "bg_unclear_readings_alert")
    private boolean bg_unclear_readings_alert;

    @Column(name = "bg_missed_alerts")
    private boolean bg_missed_alerts;

    public static UserNotification lastBgAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("bg_alert = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification lastCalibrationAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("calibration_alert = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification lastDoubleCalibrationAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("double_calibration_alert = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification lastExtraCalibrationAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("extra_calibration_alert = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification lastUnclearReadingsAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("bg_unclear_readings_alert = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification LastMissedAlert() {
        return new Select()
                .from(UserNotification.class)
                .where("bg_missed_alerts = ?", true)
                .orderBy("_ID desc")
                .executeSingle();
    }
    public static UserNotification create(String message, String type) {
        UserNotification userNotification = new UserNotification();
        userNotification.timestamp = new Date().getTime();
        userNotification.message = message;
        if (Objects.equals(type, "bg_alert")) {
            userNotification.bg_alert = true;
        } else if (Objects.equals(type, "calibration_alert")) {
            userNotification.calibration_alert = true;
        } else if (Objects.equals(type, "double_calibration_alert")) {
            userNotification.double_calibration_alert = true;
        } else if (Objects.equals(type, "extra_calibration_alert")) {
            userNotification.extra_calibration_alert = true;
        } else if (Objects.equals(type, "bg_unclear_readings_alert")) {
        userNotification.bg_unclear_readings_alert = true;
        } else if (Objects.equals(type, "bg_missed_alerts")) {
        userNotification.bg_missed_alerts = true;
        }
        userNotification.save();
        return userNotification;

    }
}
