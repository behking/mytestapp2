package com.example.newuser.vangmaterialdesign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;
import com.example.newuser.vangmaterialdesign.MDB.VolleySingleton;
import com.example.newuser.vangmaterialdesign.file_downloader.DlConstance;
import com.example.newuser.vangmaterialdesign.file_downloader.DownloadManager;
import com.example.newuser.vangmaterialdesign.file_downloader.MyApplication;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Windows on 12-02-2015.
 */
public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.ViewHolderBoxOffice> implements Filterable {


    private  String currentMovie;
    private ArrayList<String> test;
    //contains the list of movies
     ArrayList<MusicModel> mListMovies = new ArrayList<>();

    private LayoutInflater mInflater;
    CustomFilter filter;
//    private VolleySingleton mVolleySingleton;
//    private ImageLoader mImageLoader;
ArrayList<MusicModel> filterList= new ArrayList<>();;
    //keep track of the previous position for animations where scrolling down requires a different animation compared to scrolling up
    private int mPreviousPosition = 0;


    public AdapterMovies(Context context ) {
        mInflater = LayoutInflater.from(context);

//        mVolleySingleton = VolleySingleton.getInstance();
//        mImageLoader = mVolleySingleton.getImageLoader();
    }

    public void setMovies(ArrayList<MusicModel> listMovies) {

        this.mListMovies = listMovies;
        this.filterList = listMovies;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();
    }
    public interface GetDataFromAdapter{
        void onCurrentMovie(String currentMovie);
    }
    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.music_card_view, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, final int position) {
//
        MusicModel currentMovie = mListMovies.get(position);
        //one or more fields of the Movie object may be null since they are fetched from the web
        holder.singerName.setText(currentMovie.getSingerName());

        holder.singerName.setText(currentMovie.getSingerName());
        holder.musicTitle.setText(currentMovie.getMusicTitle());

        Glide.with(holder.itemView.getContext())
                .load(currentMovie.getImg_url())
                .placeholder(R.drawable.ic_account_box_black_24dp)
                .into(holder.personPhoto);
//

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlConstance dlConstance = new DlConstance();
              String dl_url =  mListMovies.get(position).getDl_url();
                L.m("dl_url "+ dl_url);
                String fileName = dl_url.substring( dl_url.lastIndexOf('/')+1, dl_url.length() );
                 String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/NewFolder/"+fileName;
//                String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

//                String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
                  DownloadManager.getImpl().addTask(dl_url, path);
                  holder.cv.setBackgroundColor(v.getResources().getColor(R.color.fbutton_color_belize_hole));
                Toast.makeText(v.getContext(), "به صفحه دانلود اضافه شد ", Toast.LENGTH_LONG).show();
// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    holder.cv.setBackgroundColor(v.getResources().getColor(android.R.color.white, v.getTheme()));
//                }else {
//                    holder.cv.setBackgroundColor(v.getResources().getColor(android.R.color.white));
//                }

            }
        });

       holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClick(final View v, final int pos) {

//                    Snackbar.make(v, list.get(pos).getDownloadUrl(), Snackbar.LENGTH_LONG).show();




            }
        });





    }

//    @Override
//        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//            super.onAttachedToRecyclerView(recyclerView);
//        }




        @Override
        public Filter getFilter() {
            if (filter == null) {

                filter = new CustomFilter(filterList, this);
            }
            return filter;
        }




    @Override
    public int getItemCount() {
        return mListMovies.size();
    }



    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder  implements View.OnClickListener{

        CardView cv;
        TextView singerName;
        TextView musicTitle;

        ImageView personPhoto;
        ItemClickListner itemClickListner;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);

                cv = (CardView) itemView.findViewById(R.id.cv);
                singerName = (TextView) itemView.findViewById(R.id.singer_name);
                musicTitle = (TextView) itemView.findViewById(R.id.music_name);

                personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListner.onItemClick(v, getLayoutPosition());

            Toast.makeText(itemView.getContext(), "klickkkk" + getPosition(), Toast.LENGTH_SHORT).show();


        }


        public void setItemClickListner(ItemClickListner ic) {
            this.itemClickListner = ic;
        }
    }

}
