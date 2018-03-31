package com.app.egh.tripplanner.activitiesHelpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.app.egh.tripplanner.data.model.Trip;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Hasnaa on 30/03/18.
 */

public class NotificationScheduler
{
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";

    public static void setReminder(Context context, Class<?> cls, int hour, int min, int day , int month, int year, Trip trip )
   //public static void setReminder(Context context, Class<?> cls, int hour, int min )
    {
        Calendar calendar = Calendar.getInstance();

        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);
        setcalendar.set(Calendar.DAY_OF_MONTH,day);
        setcalendar.set(Calendar.MONTH,month);
        setcalendar.set(Calendar.YEAR,year);


        // cancel already scheduled reminders
        //cancelReminder(context,cls);

        if(setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE,1);

        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("tripReminder",trip);
       PendingIntent pendingIntent = PendingIntent.getActivity(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), pendingIntent);

    }




}