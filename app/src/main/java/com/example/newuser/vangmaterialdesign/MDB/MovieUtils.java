package com.example.newuser.vangmaterialdesign.MDB;



import com.android.volley.RequestQueue;
import com.example.newuser.vangmaterialdesign.MDB.Json.Endpoints;
import com.example.newuser.vangmaterialdesign.MDB.Json.Parser;
import com.example.newuser.vangmaterialdesign.MDB.Json.Requestor;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.DBMusic;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;
import com.example.newuser.vangmaterialdesign.file_downloader.MyApplication;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Windows on 02-03-2015.
 */
public class MovieUtils {
    public static ArrayList<MusicModel> loadBoxOfficeMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));
        ArrayList<MusicModel> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(listMovies, true);
        return listMovies;
    }


}
