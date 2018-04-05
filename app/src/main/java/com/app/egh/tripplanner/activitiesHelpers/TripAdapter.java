package com.app.egh.tripplanner.activitiesHelpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;
import com.app.egh.tripplanner.activities.AlarmActivity;
import com.app.egh.tripplanner.activities.DetailedActivity;
import com.app.egh.tripplanner.data.model.Adapter;
import com.app.egh.tripplanner.data.model.NoteHeadService;
import com.app.egh.tripplanner.data.model.Trip;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by gehad on 3/19/18.
 */

public class TripAdapter extends RecyclerView.Adapter <TripAdapter.ViewHolder> {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    private static final String TAG = "TripAdapter";
    public List<Trip> tripDataList;
    private Context context;
    private Activity activity;
    private RecyclerView recyclerView;
    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            Trip tripData = tripDataList.get(itemPosition);
            Log.i(TAG , String.valueOf(tripData.getDate_time()));
            Intent intent = new Intent(context, DetailedActivity.class);
            //Bundle bundle = new Bundle();
            //bundle.putParcelable();
            intent.putExtra("trip",tripData);
            activity.finish();
            context.startActivity(intent);
        }
    };


    public  TripAdapter(Context context, List<Trip> tripDataList, RecyclerView recyclerView){

        this.context = context;
        this.tripDataList = tripDataList;
        this.recyclerView = recyclerView;
    }
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_row, null);
        view.setOnClickListener(clickListener);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TripAdapter.ViewHolder holder, final int position) {
        Trip tripData = tripDataList.get(position);
        holder.imageView.setImageResource(R.drawable.rowicon);
        holder.tripNameTextView.setText(tripData.getTrip_name());
        holder.startLocationTextView.setText(tripData.getStart_name());
        holder.destinationTextView.setText(tripData.getEnd_name());
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }
    public  Context getContext(){  return this.context;}
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tripNameTextView, startLocationTextView, destinationTextView;
        Button startTrip;
        public ViewHolder(final View itemView) {
            super(itemView);
            tripNameTextView = itemView.findViewById(R.id.title);
            startLocationTextView = itemView.findViewById(R.id.end2);
            destinationTextView = itemView.findViewById(R.id.end);
            imageView = itemView.findViewById(R.id.imageView);
            startTrip = itemView.findViewById(R.id.startTrip);
            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = getLayoutPosition();
                    Trip tripData = tripDataList.get(x);
                    //int itemPosition = recyclerView.getChildLayoutPosition(v);
                    //Trip tripData = tripDataList.get(itemPosition);
                   // Trip tripData = tripDataList.get(0);
                    tripData.setStarted(true);
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", tripData.getStart_lat(), tripData.getStart_long(), tripData.getStart_name(),  tripData.getEnd_lat() , tripData.getEnd_long(), tripData.getEnd_name());
                    if(tripData.isRoundtrip()){
                        tripData.setStarted(false);
                        tripData.setRoundtrip(false);
                        // add 2 hours
                        final long millisToAdd = 7_200_000; //two hours
                        Date d = tripData.getDate_time();
                        d.setTime(d.getTime() + millisToAdd);
                        tripData.setDate_time(d);
                        // swip lat long
                        double temp_lat = tripData.getStart_lat();
                        double temp_long = tripData.getStart_long();
                        String temp_name = tripData.getStart_name();

                        tripData.setStart_name(tripData.getEnd_name());
                        tripData.setStart_lat(tripData.getEnd_lat());
                        tripData.setStart_long(tripData.getEnd_long());

                        tripData.setEnd_name(temp_name);
                        tripData.setEnd_lat(temp_lat);
                        tripData.setEnd_long(temp_long);

                    }
                    Adapter dbAdapter = new Adapter(getContext());

                    dbAdapter.updateTrip(tripData);

                    //Toast.makeText( context, "Start trip activity", Toast.LENGTH_LONG).show();

                    /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);*/

                    // new code for notes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                        Intent intent_permission = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        intent_permission.putExtra("trip",tripData);
                        activity.startActivityForResult(intent_permission, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                    } else {
                        initializeView(tripDataList.get(x));
                    }

                }
            });
        }
    }

    private void initializeView(Trip trip) {
        //List<String> notes = new ArrayList<>();
        //notes = trip.getNotes();
        Intent intent = new Intent(context, NoteHeadService.class);
        //Bundle b = new Bundle();
        //b.putSerializable("trip", trip);
        intent.putExtra("trip", trip.getTrip_id());
        activity.finish();
        context.startService(intent);

    }

    public void setThisActivity(Activity activity){

        this.activity = activity;
    }

}
