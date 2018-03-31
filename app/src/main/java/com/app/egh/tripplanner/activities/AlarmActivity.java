package com.app.egh.tripplanner.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.data.model.Trip;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

public class AlarmActivity extends AppCompatActivity {
    final static String TAG = "powerLock";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        this.setFinishOnTouchOutside(false);


       setContentView(R.layout.activity_alarm);

       Intent intent = getIntent();
       final Trip currentTrip = (Trip) intent.getSerializableExtra("tripReminder");

        new MaterialStyledDialog.Builder(this)
                .setTitle(currentTrip.getTrip_name())
                .setDescription("Do you want to start your trip now?")
                .setPositiveText("Start")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        Log.i("MaterialStyledDialogs", "Do something!");
                        Toast.makeText(getApplicationContext(),"start trip" + currentTrip.getTrip_id(),Toast.LENGTH_SHORT).show();

                    }})
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorAccent)
                .setNegativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        Log.i("MaterialStyledDialogs", "Do something!");
                        Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();

                    }})
                .setNeutralText("Later")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        Log.i("MaterialStyledDialogs", "Do something!");
                        Toast.makeText(getApplicationContext(),"postpone trip",Toast.LENGTH_SHORT).show();

                        showNotification(AlarmActivity.this,currentTrip.getTrip_id(), currentTrip.getTrip_name(),"Want to start the Trip?", currentTrip);


                    }})
                .show();
    }
    private void showNotification(Context mContext, int notificationId, String title, String content, Trip trip) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");

        Intent intent = new Intent(mContext, AlarmActivity.class);
        if (trip != null) {
            intent.putExtra("tripReminder", trip);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent)
                // .setSmallIcon(R.drawable.app_logo_notification)
                .setSmallIcon(R.drawable.places_ic_search)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setOngoing(true)

                //  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo));
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.places_ic_search));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder.setPriority(Notification.PRIORITY_MAX);
        }

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }

}
