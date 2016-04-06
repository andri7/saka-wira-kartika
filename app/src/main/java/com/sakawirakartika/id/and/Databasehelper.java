package com.sakawirakartika.id.and;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by cvglobalsolusindo on 4/4/2016.
 */
public class Databasehelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME = "edu_pramuka";
    private  static  final int DATABASE_VERSION = 1;

    public Databasehelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public  void onCreate (SQLiteDatabase db) {
        // TODO Auto-generated constructor stub
        String sql = ("create table user (" +
            " id_user integer primary key  AUTOINCREMENT, " +
            " username varchar(50) NOT NULL, " +
            " email varchar (30)NOT NULL, " +
            " password varchar (60) NOT NULL, " +
            " konfirmasipassword(60) NOT NULL, " +
                ")");
        db.execSQL(sql);

        db.execSQL("insert into user values(1,'andriandroid', 'user123','WRD1bjozlNK7KML4IQyvAQ==','WRD1bjozlNK7KML4IQyvAQ==',)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        String sql = "DROP TABLE IF EXISTS user";
        db.execSQL(sql);

        onCreate(db);
    }
}
