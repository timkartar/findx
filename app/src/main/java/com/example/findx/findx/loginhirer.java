package com.example.findx.findx;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginhirer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginhirer);
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

        Button buttonworker = (Button) findViewById(R.id.buttonhirerli);
        buttonworker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login1(view);
            }
        });

        Button signuph = (Button) findViewById(R.id.loginhsubmit);
        signuph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    public void login1(View view) {
        Intent intent = new Intent(this, signuphirer.class);
        startActivity(intent);
    }

    public void login(View view) {

        final EditText userE = (EditText) findViewById(R.id.userlh);
        final EditText passE = (EditText) findViewById(R.id.passlh);

        String user = (String) userE.getText().toString();
        String pass = (String) passE.getText().toString();

        final DatabaseHelper myDbHelper = new DatabaseHelper(loginhirer.this);
        final Cursor cursor = myDbHelper.login_check_hirer(user, pass);

        boolean isEmpty = cursor.getCount() < 1;

        if (isEmpty) {
            Toast.makeText(loginhirer.this, "Username/Password Incorrect", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, profilehirer.class);
            startActivity(intent);

        }
    }
}
