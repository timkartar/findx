package com.example.findx.findx;

/**
 * Created by raktim on 6/14/16.
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.sql.SQLDataException;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.StringTokenizer;
        import java.util.zip.ZipInputStream;

public class DatabaseHelper extends SQLiteOpenHelper{


    String DB_PATH = null;
    public static final String DATABASE_NAME = "database.db3";
    public static final String TABLE_NAME = "workers";
    public static final String COL_ID = "_Id";
    public static final String COL_NAME = "Name";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_CITY = "City";
    public static final String COL_PASSWORD = "Password";
    public static final String COL_WFROM = "Wfrom";
    public static final String COL_WTO = "Wto";
    public static final String COL_Phone = "Phone";



    public SQLiteDatabase myDatabase;
    private final Context myContext;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1",DB_PATH);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
        } else {
            this.getReadableDatabase();
            try{
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY );
        } catch (SQLiteException e) {
        }
        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null? true :false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH +DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
    }
    @Override
    public synchronized void close() {
        if(myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);}

    public void insert_record_workers(String name, String user, String pass, String city, String phone, String wfrom, String wto, String job){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT into 'workers' (Name, Username, Password, City, Phone, Wfrom, Wto, Job) VALUES('";
        query += name;
        query += "', '";
        query += user;
        query += "', '";
        query += pass;
        query += "', '";
        query += city;
        query += "', '";
        query += phone;
        query += "', '";
        query += wfrom;
        query += "', '";
        query += wto;
        query += "', '";
        query += job;
        query += "')";
        db.execSQL(query);
    }

    public void insert_record_hirers(String name, String user, String pass, String city, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT into 'hirers' (Name, Username, Password, City, Phone) VALUES('";
        query += name;
        query += "', '";
        query += user;
        query += "', '";
        query += pass;
        query += "', '";
        query += city;
        query += "', '";
        query += phone;
        query += "')";
        db.execSQL(query);
    }

    public void insert_record_job(String Hid, String Wid, String date, String Workern, String Jobdes) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT into 'jobs' (Hid, Wid, Date, Status, WorkerN, JobDes) VALUES('";
        query += Hid;
        query += "', '";
        query += Wid;
        query += "', '";
        query += date;
        query += "', '";
        query += "0";
        query += "', '";
        query += Workern;
        query += "', '";
        query += Jobdes;
        query += "')";
        db.execSQL(query);
    }

    public Cursor login_check_worker(String user, String pass, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        if (id == null) {
            String[] args = {user, pass};
            c = db.query("workers", null, "Username=? AND Password=?", args, null, null, null);
            c.moveToLast();
        } else {
            String[] args = {id};
            c = db.query("workers", null, "Id=?", args , null, null, null);
        }
        return c;
    }

    public Cursor login_check_hirer(String user, String pass, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        if (id == null) {
            String[] args = {user, pass};
            c = db.query("hirers", null, "Username=? AND Password=?", args, null, null, null);
            c.moveToLast();
        } else {
            String[] args = {id};
            c = db.query("hirers", null, "Id=?", args , null, null, null);
        }
        return c;
    }

    public Cursor worker_find(String city, String job) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String[] args = {city, job};
        c = db.query("workers", null, "City=? AND Job=?", args, null, null, null);
        c.moveToFirst();
        return c;
    }

    public Cursor job_find(String Hid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String[] args = {Hid};
        c = db.query("jobs", null, "Hid=?", args, null, null, null);
        c.moveToFirst();
        return c;
    }

    public void job_finish(String jobID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE jobs SET Status=1 WHERE Jobid=";
        query += jobID;
        query += ";";
        db.execSQL(query);
    }
}