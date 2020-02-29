package com.yk.attendancemanagement.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yk.attendancemanagement.Class.Student;

import java.util.ArrayList;
import java.util.List;


public class AttendanceDatabaseHandler extends SQLiteOpenHelper {
    private static final String Database_Name = "AttendanceManagement";
    private static final int Database_Version = 1;
    private final String Table_Name = "Student";
    private final String Column_Id = "id";
    private final String ID = "RollNo";
    private final String Status = "Status";


    public AttendanceDatabaseHandler(Context context ) {
        super(context, Database_Name, null, Database_Version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // executing the created table query
        db.execSQL("Create table " + Table_Name
                + " ( " + ID + " text primary key , " + Status
                + " text not null );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {

        // executing the drop table query if database version is changed
        db.execSQL("Drop table if exists "
                + Table_Name);
        onCreate(db);

    }

    // Insert data into database method
    public void insertData(Student D) {

        // Accessing SQL database to write data
        SQLiteDatabase db = this.getWritableDatabase();

        // Content values used for editing/writing data into database
        ContentValues values = new ContentValues();

        // Putting datas into content values
        values.put(ID, D.getRollNo());
        values.put(Status, Boolean.toString(D.getStatus()));

        // Now inserting content values data into table
        db.insert(Table_Name, null, values);

        // Closing database after using
        db.close();

    }

    // Getting all saved data
    public List<Student> getAllData() {

        // Data model list in which we have to return the data
        List<Student> data = new ArrayList<Student>();

        // Accessing database for reading data
        SQLiteDatabase db = this.getReadableDatabase();

        // Select query for selecting whole table data
        String select_query = "Select * from " + Table_Name;

        // Cursor for traversing whole data into database
        Cursor cursor = db.rawQuery(select_query, null);
        try {
            // check if cursor move to first
            if (cursor.moveToFirst()) {

                // looping through all data and adding to arraylist
                do {

                    Student data_model = new Student(cursor.getString(0),
                            Boolean.parseBoolean(cursor.getString(1)));
                    data.add(data_model);

                } while (cursor.moveToNext());

            }
        } finally {

            // After using cursor we have to close it
            cursor.close();

        }

        // Closing database
        db.close();

        // returning list
        return data;
    }

    // Deleting table from database
    public void deleteTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        // Deleting table
        db.delete(Table_Name, null, null);

        // Closing database
        db.close();

    }
}