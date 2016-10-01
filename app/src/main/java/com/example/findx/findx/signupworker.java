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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findx.findx.DatabaseHelper;

import java.io.IOException;
import java.sql.SQLException;

public class signupworker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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

        final DatabaseHelper myDbHelper = new DatabaseHelper(signupworker.this);
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


        Button signup = (Button) findViewById(R.id.submitsignupworker);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup(view);
            }
        });

    }


    public void signup(View view) {

        final EditText nameE = (EditText) findViewById(R.id.name);
        final EditText userE = (EditText) findViewById(R.id.user);
        final EditText passE = (EditText) findViewById(R.id.pass);
        final EditText phoneE = (EditText) findViewById(R.id.number);
        final EditText cityE = (EditText) findViewById(R.id.location);
        final EditText wfromE = (EditText) findViewById(R.id.tstart);
        final EditText wtoE = (EditText) findViewById(R.id.tend);
        final Spinner jobE = (Spinner) findViewById(R.id.job);

        String name = (String) nameE.getText().toString();
        String user = (String) userE.getText().toString();
        String pass = (String) passE.getText().toString();
        String phone = (String) phoneE.getText().toString();
        String city = (String) cityE.getText().toString();
        String wfrom = (String) wfromE.getText().toString();
        String wto = (String) wtoE.getText().toString();
        String job = (String) String.valueOf(jobE.getSelectedItem());

        final DatabaseHelper myDbHelper = new DatabaseHelper(signupworker.this);

        myDbHelper.insert_record_workers(name, user, pass, city, phone, wfrom, wto, job);

        Toast.makeText(this,"User Added", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,loginworker.class) ;
        startActivity(intent);

    }
}