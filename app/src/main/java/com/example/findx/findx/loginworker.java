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
import android.widget.Toast;

import java.io.IOException;

public class loginworker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginworker);
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

        final DatabaseHelper myDbHelper = new DatabaseHelper(loginworker.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ice) {
            throw new Error("Unable to create datbase");
        }
        try {
            myDbHelper.openDatabase();
        } catch (SQLiteException e) {
            throw e;
        }

        // myDbHelper.onUpgrade(myDbHelper.myDatabase,1,2);

        Button button1 = (Button) findViewById(R.id.buttonworkersu);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login1(view);
            }
        });

        Button signup = (Button) findViewById(R.id.buttonworkerli);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

    }
    public void login1(View view){
        Intent intent = new Intent(this,signupworker.class);
        startActivity(intent);
    }

    public void login(View view){

        final EditText userE = (EditText) findViewById(R.id.namelw);
        final EditText passE = (EditText) findViewById(R.id.passlw);

        String user = (String) userE.getText().toString();
        String pass = (String) passE.getText().toString();

        final DatabaseHelper myDbHelper = new DatabaseHelper(loginworker.this);
        final Cursor cursor = myDbHelper.login_check_worker(user, pass);

        boolean isEmpty = cursor.getCount() < 1;

        if (isEmpty) {
            Toast.makeText(loginworker.this, "Username/Password Incorrect", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(loginworker.this, profileworker.class);
            intent.putExtra("int_value",cursor.getString(1));
            startActivity(intent);
        }
    }

}
