package com.app.egh.tripplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.egh.tripplanner.R;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    LinearLayout notes_LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes_LinearLayout = findViewById(R.id.trip_notes);

        Intent intent = getIntent();
        List<String> notes = (List<String>) intent.getSerializableExtra("notes");
        //Toast.makeText(this,"notes here "+notes.get(0),Toast.LENGTH_LONG).show();

        // Dynamicly add notes
        LayoutInflater inflater = (LayoutInflater) NotesActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<notes.size();i++) {
            View dynamic_view = inflater.inflate(R.layout.dynamic_note_row,null);
            TextView textView = dynamic_view.findViewById(R.id.note);
            textView.setText(notes.get(i));
            //if(dynamic_view.getParent()!=null)
            //    ((ViewGroup)dynamic_view.getParent()).removeView(dynamic_view);
            notes_LinearLayout.addView(dynamic_view);
            //Log.i(TAG , "note "+i);
        }

    }
}
