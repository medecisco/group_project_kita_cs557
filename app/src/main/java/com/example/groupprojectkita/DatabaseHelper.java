package com.example.groupprojectkita;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//DatabaseHelper class is a subclass of SQLiteOpenHelper class
//it is used to manage the database - CRUD
public class DatabaseHelper extends SQLiteOpenHelper {

    //this is a normal constructor that has one formal parameter - context
    //it creates a new database named Login.db with version 1
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    //onCreate function has one formal parameter - db and returns no value
    //execSQL() - creates a new table named user with three columns/fields - email as a primary key, password and name
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(email text primary key, password text, name text);");
    }

    //onUpgrade function has three formal parameters - db, oldVersion and newVersion and returns no value
    //it is used to make the alteration of table
    //this is a simple function definition for the beginners.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        onCreate(db);
    }

    //insert() has three formal parameters - emel, pwd and nama and returns a boolean value
    //it inserts a new row/record into the table (database)
    public boolean insert(String emel, String pwd, String nama){
        //local variables declaration
        //long = long int
        long ins;
        SQLiteDatabase db;
        ContentValues values;

        //opens the database for writing the table - INSERT
        db = this.getWritableDatabase();
        //creates the ContentValues object
        values = new ContentValues();
        //saves the pair of column's name and its value
        //the order of the columns must be the same in the table
        values.put("email", emel);
        values.put("password", pwd);
        values.put("name", nama);

        //executes the insert statement and assigning the row ID to ins
        //the row ID of the newly inserted row, or -1 if an error occurred
        ins = db.insert("user",null, values);

        //closes the database - Login.db
        db.close();

        //returns false if the error occured otherwise returns true
        return ins != -1;
        // if (ins==-1)
        //     return false;
        // else
        //     return true;
    }

    //chkemail() has one formal parameter - emel and returns a boolean value
    //it checks whether the email exists or not in the table (database)
    public boolean chkemail(String emel){


        //local variables declaration
        SQLiteDatabase db;
        Cursor cursor;
        int count;

        //opens the database for reading the table - SELECT
        db = this.getReadableDatabase();
        //rawQuery() executes the SELECT statement and assigns result to cursor
        cursor = db.rawQuery("SELECT * FROM user WHERE email=?", new String[]{emel});
        //cursor = db.rawQuery("select * from user where email=emel", null);

        //returns the number of affected records
        count = cursor.getCount();

        //closes the database
        db.close();
        //closes the cursor
        cursor.close();

        //returns false if count>0/found otherwise returns true
        return count <= 0;
        // if (count>0)
        //     return false;
        // else
        //     return true;
    }

    //chklogin() has two formal parameters - emel and password and returns a boolean value
    //it checks whether the email and password exist or not in the table (database)
    //is there a row/record found in the table (database)?
    public boolean chklogin(String emel, String password){
        //local variables declaration
        SQLiteDatabase db;
        Cursor cursor;
        int count;

        //opens the database for reading the table - SELECT
        db = this.getReadableDatabase();

        //rawQuery() executes the SELECT statement
        cursor = db.rawQuery("SELECT * FROM user WHERE email=? and password=?", new String[]{emel, password});

        //assigns the number of affected rows to cursor
        //it must be only one row
        count = cursor.getCount();

        //closes the database
        db.close();
        //closes the cursor
        cursor.close();

        //returns true if count=1/found otherwise returns false
        return count == 1;
        // if (count==1)
        //     return true;
        // else

        //     return false;
    }
}