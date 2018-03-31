package com.app.egh.tripplanner.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
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
       Trip currentTrip = (Trip) intent.getSerializableExtra("tripReminder");

        new MaterialStyledDialog.Builder(this)
                .setTitle(currentTrip.getTrip_name())
                .setDescription("Do you want to start your trip now?")
                .setPositiveText("Start")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        Log.i("MaterialStyledDialogs", "Do something!");
                        Toast.makeText(getApplicationContext(),"start trip",Toast.LENGTH_SHORT).show();

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

                    }})
                .show();
    }
}
