package com.example.newuser.vangmaterialdesign.MDB;

import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;

import java.util.ArrayList;



/**
 * Created by Windows on 02-03-2015.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<MusicModel> listMovies);
}
