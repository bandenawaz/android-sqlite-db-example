package com.sveri.databaseapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {

    public static final String MY_DB = "empdDB";
    SQLiteDatabase sdb;
    Cursor cursor;
    MyHelper mh;
    Context myContext;

    //lets create a constructor
    public MyDatabase(Context context){
        this.myContext = context;
        mh = new MyHelper(myContext,MY_DB,null,1);
    }

    //lets insert the values into Database
    public void insertEmp(ContentValues cv){

        sdb = mh.getWritableDatabase();
        sdb.insert("employee",null,cv);

    }

    //lets fetch the values from the database
    public Cursor getData(){

        sdb = mh.getReadableDatabase();
        cursor = sdb.query("employee",null,null,
                null,null,null,null);
        return  cursor;
    }
}
