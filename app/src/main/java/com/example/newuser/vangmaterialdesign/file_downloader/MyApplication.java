package com.example.newuser.vangmaterialdesign.file_downloader;

import android.app.Application;
import android.content.Context;

import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.DBMusic;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by hal on 8/19/16.
 */
public class MyApplication extends Application {
    public static Context CONTEXT;
    private  static DBMusic mDatabase;

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }
    public static Context getAppContext (){
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        L.m("oncreate application");
        super.onCreate();
        // for demo.
        sInstance = this;
        mDatabase =new DBMusic(this);
        CONTEXT=this;

        FileDownloader.init(getApplicationContext());

    }
    public synchronized static DBMusic getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new DBMusic(getAppContext());
        }
        return mDatabase;
    }

}