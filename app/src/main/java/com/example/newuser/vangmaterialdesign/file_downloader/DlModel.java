package com.example.newuser.vangmaterialdesign.file_downloader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by new user on 8/26/2016.
 */
public class DlModel {

    public static class TasksManagerDBOpenHelper extends SQLiteOpenHelper {
        public final static String DATABASE_NAME = "tasksmanager.db";
        public final static int DATABASE_VERSION = 3;

        public TasksManagerDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TasksManagerDBController.TABLE_NAME
                    + String.format(
                    "("
                            + "%s INTEGER PRIMARY KEY, " // id, download id
                            + "%s VARCHAR, " // name
                            + "%s VARCHAR, " // url
                            + "%s VARCHAR " // path
                            + ")"
                    , TasksManagerModel.ID
                    , TasksManagerModel.NAME
                    , TasksManagerModel.URL
                    , TasksManagerModel.PATH

            ));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1 && newVersion == 2) {
                db.delete(TasksManagerDBController.TABLE_NAME, null, null);
            }
        }
    }
    //    // ----------------------- model
        public static class TasksManagerModel {
        public final static String ID = "id";
        public final static String NAME = "name";
        public final static String URL = "url";
        public final static String PATH = "path";

        private int id;
        private String name;
        private String url;
        private String path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public ContentValues toContentValues() {
            ContentValues cv = new ContentValues();
            cv.put(ID, id);
            cv.put(NAME, name);
            cv.put(URL, url);
            cv.put(PATH, path);
            return cv;
        }
    }
}
