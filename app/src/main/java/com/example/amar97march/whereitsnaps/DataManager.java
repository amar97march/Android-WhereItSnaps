package com.example.amar97march.whereitsnaps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by amar97march on 13-09-2017.
 */

public class DataManager {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID="_id";
    public static final String TABLE_ROW_TITLE="image_title";
    public static final String TABLE_ROW_URI="image_uri";

    private static final String DB_NAME = "wis_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_PHOTOS = "wis_table_photos";
    private static final String TABLE_TAGS = "wis_table_tags";
    private static final String TABLE_ROW_TAG1 = "tag1";
    private static final String TABLE_ROW_TAG2 = "tag2";
    private static final String TABLE_ROW_TAG3 = "tag3";
    public static final String TABLE_ROW_TAG = "tag";

    public DataManager(Context context){

        CustomSQLiteOpenHelper helper=new CustomSQLiteOpenHelper(context);
        db=helper.getWritableDatabase();
    }

    public void addPhoto(Photo photo){
        String query = "INSERT INTO " + TABLE_PHOTOS + " (" +
                TABLE_ROW_TITLE + ", " +
                TABLE_ROW_URI + ", " +
                TABLE_ROW_TAG1 + ", " +
                TABLE_ROW_TAG2 + ", " +
                TABLE_ROW_TAG3 +
                ") " +
                "VALUES (" +
                "'" + photo.getTitle() + "'" + ", " +
                "'" + photo.getmStorageLocation() + "'" + ", " +
                "'" + photo.getTag1() + "'" + ", " +
                "'" + photo.getTag2() + "'" + ", " +
                "'" + photo.getTag3() + "'" +
                ");";

        Log.i("addPhoto()",query);
        db.execSQL(query);

        query = "INSERT INTO " + TABLE_TAGS + "(" +
                TABLE_ROW_TAG + ") " +
                "SELECT '" + photo.getTag1() + "' " +
                "WHERE NOT EXISTS ( SELECT 1 FROM " +
                TABLE_TAGS +
                " WHERE " + TABLE_ROW_TAG + " = " +
                "'" + photo.getTag1() + "');";
        db.execSQL(query);
        query = "INSERT INTO " + TABLE_TAGS + "(" +
                TABLE_ROW_TAG + ") " +
                "SELECT '" + photo.getTag2() + "' " +
                "WHERE NOT EXISTS ( SELECT 1 FROM " +
                TABLE_TAGS +
                " WHERE " + TABLE_ROW_TAG + " = " +"'" + photo.getTag2() + "');";
        db.execSQL(query);
        query = "INSERT INTO " + TABLE_TAGS + "(" +
                TABLE_ROW_TAG + ") " +
                "SELECT '" + photo.getTag3() + "' " +
                "WHERE NOT EXISTS ( SELECT 1 FROM " +
                TABLE_TAGS +
                " WHERE " + TABLE_ROW_TAG + " = " +
                "'" + photo.getTag3() + "');";
        db.execSQL(query);
    }
    public Cursor getTitles(){
        Cursor c=db.rawQuery("SELECT "+TABLE_ROW_ID+" , "+TABLE_ROW_TITLE+" from "+TABLE_PHOTOS,null);
        c.moveToFirst();
        return c;
    }

    public Cursor getTitlesWithTag(String tag){
        Cursor c=db.rawQuery("SELECT "+ TABLE_ROW_ID
}
