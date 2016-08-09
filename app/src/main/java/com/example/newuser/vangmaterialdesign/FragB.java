package com.example.newuser.vangmaterialdesign;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by new user on 8/5/2016.
 */
public class FragB extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<music_data_sample> musics;
    /*
    initial recycleview/setlayoutmanager
     get data /
     initial adapter/setadapter to recycleview

     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_b, container, false);
//==================================================================
        musics = new ArrayList<>();
        musics.add(new music_data_sample("Edi", "noon o sabzi", R.drawable.aks));
        musics.add(new music_data_sample("Shadmehr Aghili", "roya", R.drawable.jegar));
        musics.add(new music_data_sample("neghineh amankholova", "dar aghosh to mimiram", R.drawable.ahmad_asadi));
//===========================================================================
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
//       recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(musics);
        recyclerView.setAdapter(recyclerAdapter);


        return v;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


}


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MusicViewHolder> {

        List<music_data_sample> samples;

        RecyclerAdapter(List<music_data_sample> music_data_samples){
            this.samples = music_data_samples;
        }





        @Override
        public MusicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_card_view, viewGroup, false);

            return new MusicViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MusicViewHolder musicviewHolder, int i) {
            musicviewHolder.singerName.setText(samples.get(i).singername);
            musicviewHolder.musicTitle.setText(samples.get(i).musicTitle);
            musicviewHolder.personPhoto.setImageResource(samples.get(i).photoId);
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        @Override
        public int getItemCount() {

            return  samples.size();
        }

        public static class MusicViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView singerName;
            TextView musicTitle;
            ImageView personPhoto;

            MusicViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                singerName = (TextView)itemView.findViewById(R.id.singer_name);
                musicTitle = (TextView)itemView.findViewById(R.id.music_name);
                personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            }
        }

    }

