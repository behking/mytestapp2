package com.example.newuser.vangmaterialdesign;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newuser.vangmaterialdesign.MDB.BoxOfficeMoviesLoadedListener;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.Config;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.DBMusic;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;
import com.example.newuser.vangmaterialdesign.MDB.Task.TaskLoadMoviesBoxOffice;
import com.example.newuser.vangmaterialdesign.file_downloader.DlConstance;
import com.example.newuser.vangmaterialdesign.file_downloader.DownloadManager;
import com.example.newuser.vangmaterialdesign.file_downloader.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by new user on 8/5/2016.
 */
public class FragB extends Fragment implements BoxOfficeMoviesLoadedListener, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, FragmentLifecycle{


    private ArrayList<MusicModel> mListMovies = new ArrayList<>();

    //The key used to store arraylist of movie objects to and from parcelable
    private static final String STATE_MOVIES = "state_movies";
    //    RecyclerAdapter recyclerAdapter;
    EmptyRecyclerViewAdapter emptyrecyclerAdapter;
    private RecyclerView recyclerView;
    private SearchView mSearchView;
    //the adapter responsible for displaying our movies within a RecyclerView
    private AdapterMovies mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    String state = Environment.getExternalStorageState();

    ArrayList<String> url;
    //Volley Request Queue
    private RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_b, container, false);
        setHasOptionsMenu(true);

//           mTextError = (TextView) layout.findViewById(R.id.textVolleyError);
            mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeMovieHits);
           mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        //set the layout manager before trying to display data
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AdapterMovies(getActivity());
        recyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            //if this fragment starts after a rotation or configuration change, load the existing movies from a parcelable
            mListMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        } else {
            //if this fragment starts for the first time, load the list of movies from a database
            mListMovies = MyApplication.getWritableDatabase().readMovies(DBMusic.MUSICTB);
            L.m("mlist" + mListMovies);
            //if the database is empty, trigger an AsycnTask to download movie list from the web
            if (mListMovies.isEmpty()) {
                L.m("FragmentBoxOffice: executing task from fragment");
                new TaskLoadMoviesBoxOffice(this).execute();
            }
        }
        //update your Adapter to containg the retrieved movies
        mAdapter.setMovies(mListMovies);

        return v;
    }


    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the movie list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_MOVIES, mListMovies);
    }

    /**
     * Called when the AsyncTask finishes load the list of movies from the web
     */
    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<MusicModel> listMovies) {
        L.m("FragmentBoxOffice: onBoxOfficeMoviesLoaded Fragment");
        //update the Adapter to contain the new movies downloaded from the web
        if (mSwipeRefreshLayout.isRefreshing()) {
            L.m("onbox refresh");
            mSwipeRefreshLayout.setRefreshing(false);
        }
        L.m("lllllllist" + listMovies);
        mAdapter.setMovies(listMovies);
    }


    private void initSearchView(final Menu menu) {
        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            MenuItemCompat.setOnActionExpandListener(
                    searchItem, new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            MenuItem listTypeItem = menu.findItem(R.id.action_search);
                            if (listTypeItem != null)
                                listTypeItem.setVisible(false);
//                            hideFab();
                            Toast.makeText(getActivity(), "menu expand", Toast.LENGTH_SHORT).show();
                            L.m("onMenuItemActionExpand");
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            MenuItem listTypeItem = menu.findItem(R.id.action_search);
                            if (listTypeItem != null)
                                listTypeItem.setVisible(true);
                            Toast.makeText(getActivity(), "menu colapse", Toast.LENGTH_SHORT).show();
//                            showFab();
                            return true;
                        }
                    });
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            mSearchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
            mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
            mSearchView.setQueryHint(getString(R.string.action_search));
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            mSearchView.setOnQueryTextListener(this);
        }
    }

        @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        L.m("new Text"+ newText);
        return false;
    }


//    /**
//     * Converting dp to pixel
//     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_filter, menu);
      initSearchView(menu);

    }

    @Override
    public void onRefresh() {
        L.t(getActivity(), "onRefresh");
        //load the whole feed again on refresh, dont try this at home :)
        new TaskLoadMoviesBoxOffice(this).execute();
        L.m("onrefresh");
    }


    @Override
    public void onPauseFragment() {

        L.m("pause fragb");
    }

    @Override
    public void onResumeFragment() {
        L.m("resume fragb");
    }
}


//    /**
//     * Created by new user on 8/12/2016.
//     */
//    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MusicViewHolder> implements
//            Filterable {
//
//
//            ArrayList<MusicModel> list = new ArrayList<>();
//
//        ArrayList<MusicModel> filterList;
//
//        CustomFilter filter;
//        String url;
//
//        RecyclerAdapter(ArrayList<MusicModel> list) {
//            this.list = list;
//            this.filterList = list;
//
//        }
//
//
//        @Override
//        public MusicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_card_view, viewGroup, false);
//
//            return new MusicViewHolder(v);
//        }
//
//        @Override
//        public void onBindViewHolder(MusicViewHolder musicviewHolder, final int i) {
//
//            musicviewHolder.singerName.setText(list.get(i).getSingerName());
//            musicviewHolder.musicTitle.setText(list.get(i).getMusicTitle());
//
//            Glide.with(musicviewHolder.itemView.getContext())
//                    .load(list.get(i).getImg_url())
//                    .placeholder(R.drawable.ic_attachment)
//                    .into(musicviewHolder.personPhoto);
////
//
//            musicviewHolder.cv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                        Log.i("downurl", "onClick: "+list.get(i).downloadUrl);
////FragB.this.comm(samples.get(i).downloadUrl);
////                        onDownload(i);
//                }
//            });
//            musicviewHolder.setItemClickListner(new ItemClickListner() {
//                @Override
//                public void onItemClick(final View v, final int pos) {
//
////                    Snackbar.make(v, list.get(pos).getDownloadUrl(), Snackbar.LENGTH_LONG).show();
//
//                    //==================samples.get(pos).getDownloadUrl()
//
//
//                }
//            });
//
//
//        }

//        @Override
//        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//            super.onAttachedToRecyclerView(recyclerView);
//        }


//        @Override
//        public int getItemCount() {
//
//            return list.size();
//        }
//
//        @Override
//        public Filter getFilter() {
//            if (filter == null) {
//                filter = new CustomFilter(filterList, this);
//            }
//            return filter;
//        }

//        public class MusicViewHolder extends RecyclerView.ViewHolder
//                implements View.OnClickListener {
//
//            CardView cv;
//            TextView singerName;
//            TextView musicTitle;
//
//            ImageView personPhoto;
//            ItemClickListner itemClickListner;
//
//
//            MusicViewHolder(View itemView) {
//                super(itemView);
//                cv = (CardView) itemView.findViewById(R.id.cv);
//                cv.setOnClickListener(this);
//                singerName = (TextView) itemView.findViewById(R.id.singer_name);
//                musicTitle = (TextView) itemView.findViewById(R.id.music_name);
//
//                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
//            }
//
//            @Override
//            public void onClick(View v) {
//                this.itemClickListner.onItemClick(v, getLayoutPosition());
//
//                Toast.makeText(itemView.getContext(), "klickkkk" + getPosition(), Toast.LENGTH_SHORT).show();
//
//
//            }
//
//
//            public void setItemClickListner(ItemClickListner ic) {
//                this.itemClickListner = ic;
//            }
//        }
//
//    }

//    /**
//     * RecyclerView item decoration - give equal margin around grid item
//     */
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }










