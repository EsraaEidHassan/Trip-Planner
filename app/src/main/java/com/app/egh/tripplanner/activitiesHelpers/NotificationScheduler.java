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
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.app.egh.tripplanner.data.model.Trip;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Hasnaa on 30/03/18.
 */

public class NotificationScheduler
{
   // public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";

    public static void setReminder(Context context, Class<?> cls, int id, int hour, int min, int day , int month, int year, Trip trip )
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
        cancelReminder(context,cls,id);

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
       PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("setReminder: ." , ""+ setcalendar.getTimeInMillis());
            am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, setcalendar.getTimeInMillis()+ SystemClock.elapsedRealtime(), pendingIntent);
        }
        else{
            System.out.println(setcalendar.getTimeInMillis());
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, setcalendar.getTimeInMillis() + SystemClock.elapsedRealtime(), pendingIntent);
        }

    }

    public static void cancelReminder(Context context, Class<?> cls, int id)
    {
        // Disable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }



}
