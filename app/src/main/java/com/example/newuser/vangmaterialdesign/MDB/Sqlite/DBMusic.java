package com.example.newuser.vangmaterialdesign.MDB.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.newuser.vangmaterialdesign.L;

import java.util.ArrayList;
import java.util.Date;



/**
 * Created by Windows on 25-02-2015.
 */
public class DBMusic {
    public static final String MUSICTB = MusicHelper.TABLE_NAME;
    public static final int UPCOMING = 1;
    private MusicHelper mHelper;
    private SQLiteDatabase mDatabase;

    public DBMusic(Context context) {
        mHelper = new MusicHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertMovies(ArrayList<MusicModel> listMovies, boolean clearPrevious) {
        if (clearPrevious) {

            deleteMovies();

        }


        //create a sql prepared statement
        String sql = "INSERT INTO " + (MusicHelper.TABLE_NAME) + " VALUES (?,?,?,?,?,?);";
        L.m(sql);
        //compile the statement and start a transaction

        SQLiteStatement statement = mDatabase.compileStatement(sql);
        L.m("st"+statement);
        mDatabase.beginTransaction();
        L.m("begin");
        for (int i = 0; i < listMovies.size(); i++) {
            MusicModel currentMusic = listMovies.get(i);
            statement.clearBindings();
            L.m("clear");
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, currentMusic.getSingerName());
            statement.bindString(3, currentMusic.getMusicTitle());
            statement.bindString(4, currentMusic.getImg_url());
            statement.bindString(5, currentMusic.getDl_url());
            statement.bindString(6, currentMusic.getShahr());



            statement.execute();
            L.m("stexcute");
        }
        //set the transaction as successful and end the transaction
//        L.m("inserting entries " + listMovies.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<MusicModel> readMovies(String table) {
        ArrayList<MusicModel> listMovies = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {MusicHelper.ID,
                MusicHelper.NAME,
                MusicHelper.Title,
                MusicHelper.IMG_URL,
                MusicHelper.DL_URL,
                MusicHelper.SHAHR
        };
        Cursor cursor = mDatabase.query(MusicHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            L.m("loading entries " + cursor.getCount() + new Date(System.currentTimeMillis()));
            do {

                //create a new music object and retrieve the data from the cursor to be stored in this music object
                MusicModel music = new MusicModel();
                //each step is a 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank music object to contain our data
                music.setId(cursor.getInt(cursor.getColumnIndex(MusicHelper.ID)));
//                long releaseDateMilliseconds = cursor.getLong(cursor.getColumnIndex(MoviesHelper.COLUMN_RELEASE_DATE));
//                music.setReleaseDateTheater(releaseDateMilliseconds != -1 ? new Date(releaseDateMilliseconds) : null);
                music.setSingerName(cursor.getString(cursor.getColumnIndex(MusicHelper.NAME)));
                music.setMusicTitle(cursor.getString(cursor.getColumnIndex(MusicHelper.Title)));

                music.setImg_url(cursor.getString(cursor.getColumnIndex(MusicHelper.IMG_URL)));
                music.setDl_url(cursor.getString(cursor.getColumnIndex(MusicHelper.DL_URL)));
                music.setShahr(cursor.getString(cursor.getColumnIndex(MusicHelper.SHAHR)));
                //add the music to the list of music objects which we plan to return
                listMovies.add(music);
            }
            while (cursor.moveToNext());
        }
        return listMovies;
    }

    public void deleteMovies() {
        mDatabase.delete(MusicHelper.TABLE_NAME, null, null);
        L.m("delete");
    }

    private static class MusicHelper extends SQLiteOpenHelper {
        public final static String ID = "id";
        public final static String NAME = "singerName";
        public final static String Title = "musicTitle";
        public final static String SHAHR = "shahr";
        public final static String IMG_URL = "img_url";
        public final static String DL_URL = "dl_url";
        public final static String TABLE_NAME = "musicTable";

        private static final String CREATE_TABLE_MUSIC = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT," +
                Title + " TEXT," +

                IMG_URL + " TEXT," +
                DL_URL + " TEXT," +
                SHAHR + " TEXT" +
                ");";
        private static final   String yazd = "SELECT * FROM "+TABLE_NAME+" WHERE "+ ID +" ='yazd';";
        private static final String DB_NAME = "music_db";
        private static final int DB_VERSION = 12;
        private Context mContext;

        public MusicHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }
         public void selectyazd (SQLiteDatabase db){
             db.execSQL(yazd);
         }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_MUSIC);
                L.m("create table Music");
            } catch (SQLiteException exception) {
                L.m("create table Musicerror"+exception);
                L.t(mContext, exception + "");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                L.m("upgrade table ");
//                db.execSQL(" DROP TABLE " + TABLE_NAME +" IF EXISTS;");
               db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );

                onCreate(db);
            } catch (SQLiteException exception) {
                L.m("upgrade tableerror "+exception);
                L.t(mContext, exception + "");
            }
        }
    }
}


