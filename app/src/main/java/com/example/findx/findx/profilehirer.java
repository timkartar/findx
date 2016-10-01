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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class profilehirer extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
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
        Button buttons[]= new Button[5];
        buttons[0]= (Button)findViewById(R.id.button01);
        buttons[1]= (Button)findViewById(R.id.button02);
        buttons[2]= (Button)findViewById(R.id.button03);
        buttons[3]= (Button)findViewById(R.id.button04);
        buttons[4]= (Button)findViewById(R.id.button05);

        Cursor jobs;
        jobs=myDbHelper.job_find_H(curr_id);
        jobs.moveToLast();
        int i=0;
        String worker_id;
        String wname;
        String wjob;
        String buttonText = "def";
        String wdate;
        String wstatus;

        while(i< 5 && i<jobs.getCount() && i>=0)
        {
            worker_id = jobs.getString(2);
            wdate = jobs.getString(3);
            wname = jobs.getString(5);
            wjob = jobs.getString(6);
            wstatus = jobs.getString(4);
            buttonText = wdate + "    " + wname + "    " + wjob;
            buttons[i].setText(buttonText);
            if (Integer.valueOf(wstatus) == 0) {
                buttons[i].setBackgroundColor(Color.parseColor("#ef5350"));
            } else {
                buttons[i].setBackgroundColor(Color.parseColor("#00e676"));
            }


            i++;
            jobs.moveToPrevious();
        }
        // Toast.makeText(profilehirer.this, String.valueOf(jobs.getCount()), Toast.LENGTH_SHORT).show();

        jobs.moveToLast();
        int jobc = jobs.getCount();

        if (jobc >= 1) {
            final String jobid1 = jobs.getString(0);
            buttons[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobcomplete(view, jobid1, curr_id);
                }
            });
        }
        jobs.moveToPrevious();

        if (jobc >= 2) {
            final String jobid2 = jobs.getString(0);
            buttons[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobcomplete(view, jobid2, curr_id);
                }
            });
        }
        jobs.moveToPrevious();

        if (jobc >= 3) {
            final String jobid3 = jobs.getString(0);
            buttons[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobcomplete(view, jobid3, curr_id);
                }
            });
        }
        jobs.moveToPrevious();

        if (jobc >= 4) {
            final String jobid4 = jobs.getString(0);
            buttons[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobcomplete(view, jobid4, curr_id);
                }
            });
        }
        jobs.moveToPrevious();

        if (jobc >= 5) {
            final String jobid5 = jobs.getString(0);
            buttons[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobcomplete(view, jobid5, curr_id);
                }
            });
        }


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
        myDbHelper.insert_record_job(curr_id, workers.getString(1),date, workers.getString(0), workers.getString(8));
        Intent intent = new Intent(profilehirer.this, profilehirer.class);
        intent.putExtra("int_value", curr_id);
        startActivity(intent);
    }

    public void jobcomplete(View view, String jobid, String curr_id) {

        final DatabaseHelper myDbHelper = new DatabaseHelper(profilehirer.this);
        myDbHelper.job_finish(jobid);
        Intent intent = new Intent(profilehirer.this, profilehirer.class);
        intent.putExtra("int_value", curr_id);
        startActivity(intent);
    }
}
