package com.example.findx.findx;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class profileworker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileworker);
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
        TextView text = (TextView)findViewById(R.id.workername);

        final DatabaseHelper myDbHelper = new DatabaseHelper(profileworker.this);
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

        Cursor cursor = myDbHelper.login_check_worker("a", "b", curr_id);
        cursor.moveToFirst();
        String name = cursor.getString(0);

        text.setText(name);

        // Update new jobs

        // Update new jobs
        Button buttons[]= new Button[5];
        buttons[0]= (Button)findViewById(R.id.button001);
        buttons[1]= (Button)findViewById(R.id.button002);
        buttons[2]= (Button)findViewById(R.id.button003);
        buttons[3]= (Button)findViewById(R.id.button004);
        buttons[4]= (Button)findViewById(R.id.button005);

        Cursor jobs;
        jobs=myDbHelper.job_find_W(curr_id);
        jobs.moveToLast();
        int i=0;
        String worker_id;
        String buttonText;
        String wdate;
        String wstatus;

        while(i< 5 && i<jobs.getCount() && i>=0)
        {
            worker_id = jobs.getString(2);
            wdate = jobs.getString(3);
            wstatus = jobs.getString(4);
            buttonText = wdate;
            buttons[i].setText(buttonText);
            if (Integer.valueOf(wstatus) == 0) {
                buttons[i].setBackgroundColor(Color.parseColor("#ef5350"));
            } else {
                buttons[i].setBackgroundColor(Color.parseColor("#00e676"));
            }
            i++;
            jobs.moveToPrevious();
        }


    }

    public void jobcomplete(View view, String jobid, String curr_id) {

        final DatabaseHelper myDbHelper = new DatabaseHelper(profileworker.this);
        myDbHelper.job_finish(jobid);
        Intent intent = new Intent(profileworker.this, profileworker.class);
        intent.putExtra("int_value", curr_id);
        startActivity(intent);
    }

}
