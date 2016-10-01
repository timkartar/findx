package com.example.findx.findx;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class profilehirer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilehirer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final String curr_id = intent.getStringExtra("int_value");
        TextView text = (TextView)findViewById(R.id.hirername);

        final DatabaseHelper myDbHelper = new DatabaseHelper(profilehirer.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ice) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDatabase();
        } catch (SQLiteException e) {
            throw e;
        }

        Cursor cursor = myDbHelper.login_check_hirer("a", "b", curr_id);
        cursor.moveToFirst();
        String name = cursor.getString(0);
        final String city = cursor.getString(4);

        text.setText(name);

        // Update new jobs
        Cursor jobs;
        jobs=myDbHelper.job_find(curr_id);
        // Toast.makeText(profilehirer.this, String.valueOf(jobs.getCount()), Toast.LENGTH_SHORT).show();


        Button signup = (Button) findViewById(R.id.askforjob);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Spinner jobE = (Spinner) findViewById(R.id.jobquery);
                final DatePicker dateE = (DatePicker) findViewById(R.id.datePicker);
                String date = String.valueOf(dateE.getDayOfMonth()) + "-" + String.valueOf(dateE.getMonth());
                String job = String.valueOf(jobE.getSelectedItem());
                hire(view, city, job, curr_id, date);
            }
        });


    }

    public void hire(View view, String city, String job, String curr_id, String date) {

        final DatabaseHelper myDbHelper = new DatabaseHelper(profilehirer.this);
        Cursor workers = myDbHelper.worker_find(city, job);
        int count = workers.getCount();
        int a = new Random().nextInt(count);
        workers.moveToPosition(a);
        myDbHelper.insert_record_job(curr_id, workers.getString(1),date);
        Intent intent = new Intent(profilehirer.this, profilehirer.class);
        intent.putExtra("int_value", curr_id);
        startActivity(intent);
    }

}
