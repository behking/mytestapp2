package com.example.newuser.vangmaterialdesign.MDB.Sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.newuser.vangmaterialdesign.L;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by new user on 8/29/2016.
 */
//    // ----------------------- model
public class MusicModel implements Parcelable {
    public static final Parcelable.Creator<MusicModel> CREATOR
            = new Parcelable.Creator<MusicModel>() {
        public MusicModel createFromParcel(Parcel in) {
            L.m("create from parcel :Movie");
            return new MusicModel(in);
        }

        public MusicModel[] newArray(int size) {
            return new MusicModel[size];
        }
    };
    ArrayList sample;
    private long id;
    private String singerName;




    private String shahr;
    private String musicTitle;
    private String img_url;
    private String dl_url;

    public MusicModel() {

    }

    public MusicModel(Parcel input) {
        id = input.readLong();
        singerName = input.readString();
        musicTitle = input.readString();
        shahr = input.readString();
        img_url = input.readString();
       dl_url = input.readString();

    }

    public MusicModel(long id,
                 String singerName,
                 String musicTitle,
                      String shahr,
                 String img_url,
                 String dl_url) {
        this.id = id;
        this.singerName = singerName;
        this.musicTitle = musicTitle;
        this.shahr = shahr;
        this.img_url = img_url;
        this.dl_url = dl_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getShahr() {
        return shahr;
    }

    public void setShahr(String shahr) {
        this.shahr = shahr;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDl_url() {
        return dl_url;
    }

    public void setDl_url(String dl_url) {
        this.dl_url = dl_url;
    }



    @Override
    public String toString() {
        return "\nID: " + id +
                "\nSingerName " + singerName +
                "\nMusicTitle " + musicTitle +
                "\nShahr " + shahr +
                "\nImg_Url " + img_url +
                "\nDl-Url " + dl_url +
                "\n";
    }

    @Override
    public int describeContents() {
//        L.m("describe Contents Movie");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        L.m("writeToParcel Movie");
        dest.writeLong(id);
        dest.writeString(singerName);
        dest.writeString(musicTitle);
        dest.writeString(shahr);
        dest.writeString(img_url);
        dest.writeString(dl_url);

    }
}























//    ArrayList sample;
//    private long id;
//    private String singerName;
//    private String musicTitle;
//    private String img_url;
//    private String dl_url;
//
//
////        MusicModel(String singername, String musicTitle, int photoId , String imageUrl) {
////        this.singerName = singername;
////        this.musicTitle = musicTitle;
////        this.img_url=imageUrl;
//////        this.photoId = photoId;
////
////    }
////        public ArrayList getSample() {
////        return sample;
////    }
//
////    public void setSample(ArrayList sample) {
////        this.sample = sample;
////    }
//
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getSingerName() {
//        return singerName;
//    }
//
//    public void setSingerName(String singerName) {
//        this.singerName = singerName;
//    }
//
//    public String getMusicTitle() {
//        return musicTitle;
//    }
//
//    public void setMusicTitle(String musicTitle) {
//        this.musicTitle = musicTitle;
//    }
//
//    public String getImg_url() {
//        return img_url;
//    }
//
//    public void setImg_url(String img_url) {
//        this.img_url = img_url;
//    }
//
//    public String getDl_url() {
//        return dl_url;
//    }
//
//    public void setDl_url(String dl_url) {
//        this.dl_url = dl_url;
//    }


//}
