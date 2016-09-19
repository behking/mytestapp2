package com.example.newuser.vangmaterialdesign;

import android.widget.Filter;


import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;

import java.util.ArrayList;

/**
 * Filter for search
 */
public class CustomFilter extends Filter {
AdapterMovies adapter;
    ArrayList<MusicModel> filterlist;

    public CustomFilter(ArrayList<MusicModel> filterlist,AdapterMovies adapter){
this.adapter = adapter;
        this.filterlist = filterlist;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length()>0){
            constraint= constraint.toString().toUpperCase();
            ArrayList<MusicModel> filterMusicList = new ArrayList<>();
            for (int i =0 ; i<filterlist.size();i++){
                if (filterlist.get(i).getSingerName().toUpperCase().contains(constraint)){
filterMusicList.add(filterlist.get(i));
                    L.m("filterList"+filterlist);
                }

            }
            results.count=filterMusicList.size();
            results.values=filterMusicList;
        }else{
            results.count = filterlist.size();
            results.values = filterlist;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
adapter.mListMovies = (ArrayList<MusicModel>) results.values;
        L.m("results"+results);
        adapter.notifyDataSetChanged();

    }
}
