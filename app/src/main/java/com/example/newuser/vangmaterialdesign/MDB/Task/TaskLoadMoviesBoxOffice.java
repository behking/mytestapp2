package com.example.newuser.vangmaterialdesign.MDB.Task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MDB.BoxOfficeMoviesLoadedListener;
import com.example.newuser.vangmaterialdesign.MDB.MovieUtils;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;
import com.example.newuser.vangmaterialdesign.MDB.VolleySingleton;

import java.util.ArrayList;



/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<MusicModel>> {
    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        L.m("taskloadrequest"+requestQueue);
    }


    @Override
    protected ArrayList<MusicModel> doInBackground(Void... params) {

        ArrayList<MusicModel> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        L.m("doin background");
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<MusicModel> listMovies) {
        if (myComponent != null) {
            L.m("mycomp"+myComponent);
            myComponent.onBoxOfficeMoviesLoaded(listMovies);
            L.m("onpost execute");
        }
    }


}
