package com.example.newuser.vangmaterialdesign.MDB;

import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;
import com.example.newuser.vangmaterialdesign.MDB.Task.TaskLoadMoviesBoxOffice;

import java.util.ArrayList;


import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Windows on 23-02-2015.
 */
public class ServiceMoviesBoxOffice extends JobService implements BoxOfficeMoviesLoadedListener {
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadMoviesBoxOffice(this).execute();
        L.m("Service:onstartjob");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        L.m("Service:onstopJob");
        return false;
    }


    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<MusicModel> listMovies) {
        L.t(this, "onBoxOfficeMoviesLoaded");
        jobFinished(jobParameters, false);
        L.m("Service:jobFinsh");
    }
}

