package com.example.findx.findx;

import android.content.Intent;
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

import java.io.IOException;

public class signuphirer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuphirer);
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

        final DatabaseHelper myDbHelper = new DatabaseHelper(signuphirer.this);
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


        Button signup = (Button) findViewById(R.id.submitsignuphirer);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup(view);
            }
        });

    }

    public void signup(View view) {

        final EditText nameE = (EditText) findViewById(R.id.nameh);
        final EditText userE = (EditText) findViewById(R.id.userh);
        final EditText passE = (EditText) findViewById(R.id.passh);
        final EditText phoneE = (EditText) findViewById(R.id.numberh);
        final EditText cityE = (EditText) findViewById(R.id.locationh);

        String name = (String) nameE.getText().toString();
        String user = (String) userE.getText().toString();
        String pass = (String) passE.getText().toString();
        String phone = (String) phoneE.getText().toString();
        String city = (String) cityE.getText().toString();

        final DatabaseHelper myDbHelper = new DatabaseHelper(signuphirer.this);

        myDbHelper.insert_record_hirers(name, user, pass, phone, city);

        Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,loginhirer.class) ;
        startActivity(intent);

    }

}
