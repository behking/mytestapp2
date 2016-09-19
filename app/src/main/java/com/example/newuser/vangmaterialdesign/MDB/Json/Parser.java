package com.example.newuser.vangmaterialdesign.MDB.Json;

import android.util.Log;

import com.example.newuser.vangmaterialdesign.L;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.Config;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.MusicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by new user on 9/3/2016.
 */
public class Parser {
    public static ArrayList<MusicModel> parseMoviesJSON(JSONObject response) {

        ArrayList<MusicModel> listMovies = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayMovies = response.getJSONArray(Keys.EndpointBoxOffice.KEY_TITR);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    long id = -1;
                    String singer = Constants.NA;
                    String title = Constants.NA;
                    String img_url = Constants.NA;
                    String dl_url = Constants.NA;
                    String shahr = Constants.NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    //get the id of the current musicModel
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_ID)) {
                        id = currentMovie.getLong(Keys.EndpointBoxOffice.KEY_ID);
                    }
                    //get the singer of the current musicModel
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_SINGER)) {
                        singer = currentMovie.getString(Keys.EndpointBoxOffice.KEY_SINGER);
                    }

                    //get the date in theaters for the current musicModel
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_TITLE)) {
                        title = currentMovie.getString(Keys.EndpointBoxOffice.KEY_TITLE);

                    }
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_SHAHR)) {
                        shahr = currentMovie.getString(Keys.EndpointBoxOffice.KEY_SHAHR);

                    }

                    //get the date in theaters for the current musicModel
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_IMG_URL)) {
                        img_url = currentMovie.getString(Keys.EndpointBoxOffice.KEY_IMG_URL);

                    }

                    // get the synopsis of the current musicModel
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_DL_URL)) {
                        dl_url = currentMovie.getString(Keys.EndpointBoxOffice.KEY_DL_URL);
                    }


                    MusicModel musicModel = new MusicModel();
                    musicModel.setId(id);
                    musicModel.setSingerName(singer);
                    musicModel.setMusicTitle(title);
                    musicModel.setShahr(shahr);
                    musicModel.setImg_url(img_url);
                    musicModel.setDl_url(dl_url);

//                    L.t(getActivity(), musicModel + "");
                    if (id != -1 && !singer.equals(Constants.NA)) {
                        L.m("model"+musicModel);
                        listMovies.add(musicModel);
                    }
                }

            } catch (JSONException e) {

            }

        }
        L.m("listm"+listMovies);
        return listMovies;

    }






















//        ArrayList<MusicModel> listMovies = new ArrayList<>();
//        MusicModel musicModel = new MusicModel();
//        if (response!=null && response.length()>0){
//            for (int i = 0; i < response.length(); i++) {
//                //Creating the superhero object
////            music_data_sample dataSample = new music_data_sample();
//
////                musicDBHandler = new MusicDBHandler(myService);
//
//                JSONObject json = null;
//                try {
//                    //Getting json
//                    json = response.getJSONObject(i);
//                    JSONObject currentMovie = listMovies.getJSONObject(i);
//                    //Adding data to the superhero object
//                    musicModel.setId(json.getInt(Config.TAG_ID));
//                    musicModel.setSingerName(json.getString(Config.TAG_NAME));
//                    Log.i("jsonName", "parseData: " + json.getString(Config.TAG_NAME));
//                    musicModel.setMusicTitle(json.getString(Config.TAG_MTITLE));
//                    musicModel.setImg_url(json.getString(Config.TAG_IMAGE_URL));
//
//
////                dataSample.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
////                dataSample.setDownloadUrl(json.getString(Config.TAG_MDOWNLOAD));
//////               Log.e("seturl",""+json.getString(Config.TAG_IMAGE_URL));
////                dataSample.setSingername(json.getString(Config.TAG_NAME));
////                dataSample.setMusicTitle(json.getString(Config.TAG_MTITLE));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //Adding the superhero object to the list
//
////                modelList.add(musicModel);
//
//            }
//        }
//
//
//        return listMovies;
//    }
//
//























    //This method will parse json data
     public MusicModel parseData(JSONArray array) {
        Log.i("parse", "parseData: "+array);
        MusicModel musicModel = new MusicModel();
//            ArrayList<MusicModel> modelList = new ArrayList<>();
        if (array!=null && array.length()>0){
            for (int i = 0; i < array.length(); i++) {
                //Creating the superhero object
//            music_data_sample dataSample = new music_data_sample();

//                musicDBHandler = new MusicDBHandler(myService);

                JSONObject json = null;
                try {
                    //Getting json
                    json = array.getJSONObject(i);

                    //Adding data to the superhero object
                    musicModel.setId(json.getInt(Config.TAG_ID));
                    musicModel.setSingerName(json.getString(Config.TAG_NAME));
                    Log.i("jsonName", "parseData: " + json.getString(Config.TAG_NAME));
                    musicModel.setMusicTitle(json.getString(Config.TAG_MTITLE));
                    musicModel.setMusicTitle(json.getString(Config.TAG_SHAHR));
                    musicModel.setImg_url(json.getString(Config.TAG_IMAGE_URL));
                    musicModel.setImg_url(json.getString(Config.TAG_MDOWNLOAD));

//                dataSample.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
//                dataSample.setDownloadUrl(json.getString(Config.TAG_MDOWNLOAD));
////               Log.e("seturl",""+json.getString(Config.TAG_IMAGE_URL));
//                dataSample.setSingername(json.getString(Config.TAG_NAME));
//                dataSample.setMusicTitle(json.getString(Config.TAG_MTITLE));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Adding the superhero object to the list

//                modelList.add(musicModel);

            }
        }
        return  musicModel;
    }
}

