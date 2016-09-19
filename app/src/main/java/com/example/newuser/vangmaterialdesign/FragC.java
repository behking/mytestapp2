package com.example.newuser.vangmaterialdesign;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newuser.vangmaterialdesign.file_downloader.DlAdapter;
import com.example.newuser.vangmaterialdesign.file_downloader.DownloadManager;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by new user on 8/5/2016.
 */
public class FragC extends Fragment implements FragmentLifecycle  {

    AdapterMovies mAdapter;
    RecyclerView recyclerView;
    EmptyRecyclerViewAdapter emptyrecyclerAdapter;
    DlAdapter.TaskItemAdapter recyclerAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.m("onactivity created");

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        L.m("onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_c, container, false);


        L.m("oncreate view ");

//        if (dlURL != null || dlURL.size() != 0) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        DownloadManager.getImpl().onCreate(new WeakReference<FragC>(this));
//            emptyrecyclerAdapter = new EmptyRecyclerViewAdapter();
//
//            mAdapter = new AdapterMovies(getActivity(), communicator);
//            recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
//            L.m("ddddllluuurrlllfragc" + dlURL.size());
//            recyclerView.setHasFixedSize(true);
//
////        recyclerView.setLayoutManager(mLayoutManager);
//
////        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerAdapter = new DlAdapter.TaskItemAdapter();
//
//            recyclerView.setAdapter(emptyrecyclerAdapter);
//
//        } else {


        return v;


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        L.m("saveinstance ");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        L.m("onstart");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);

        recyclerView.setHasFixedSize(true);

//        recyclerView.setLayoutManager(mLayoutManager);

//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new DlAdapter.TaskItemAdapter();
        L.m("recyclerAdapter"+recyclerAdapter);
        recyclerView.setAdapter(recyclerAdapter);
        ItemTouchHelper.Callback itemTouchCallBack=new ItemSwipeHelper(Direction.RIGHT,recyclerAdapter);
        ItemTouchHelper swipeToDismissTouchHelper =new ItemTouchHelper(itemTouchCallBack);
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);
        L.m("Fragc dl oncreat");

        super.onStart();
    }

    @Override
    public void onStop() {
        L.m("onstop");
        super.onStop();
    }

    @Override
    public void onResume() {
L.m("onResume ");
        super.onResume();
    }

    @Override
    public void onPause() {
        DownloadManager.getImpl().onDestroy();
        recyclerAdapter = null;
        FileDownloader.getImpl().pauseAll();
        super.onPause();
        L.m("onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.m("ondestroy view");
    }



    public void postNotifyDataChanged() {
        L.m("postNotify");
        if (recyclerAdapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (recyclerAdapter != null) {
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        DownloadManager.getImpl().onDestroy();
        recyclerAdapter = null;
        FileDownloader.getImpl().pauseAll();

        super.onDestroy();
        L.m("ondestroy");
    }

    @Override
    public void onDetach() {
        L.m("ondetach");
        super.onDetach();
    }

    @Override
    public void onPauseFragment() {

//        onPause();
    }

    @Override
    public void onResumeFragment() {

    }
}
