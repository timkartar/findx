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
        String temp = intent.getStringExtra("int_value");
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

        Cursor cursor = myDbHelper.login_check_worker("a", "b", temp);
        cursor.moveToFirst();
        String name = cursor.getString(0);

        text.setText(name);
    }

}
