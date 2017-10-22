package com.example.amar97march.whereitsnaps;

/**
 * Created by amar97march on 13-09-2017.
 */
import android.net.Uri;

public class Photo {

    private String mTitle;
    private Uri mStorageLocation;
    private String mTag1;
    private String mTag2;
    private String mTag3;

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        this.mTitle=title;
    }

    public Uri getmStorageLocation(){
        return mStorageLocation;
    }

    public void setmStorageLocation(Uri storageLocation){
        this.mStorageLocation=storageLocation;
    }

    public String getTag1(){
        return mTag1;
    }

    public void setTag1(String tag1){
        this.mTag1=tag1;
    }

    public String getTag2(){
        return mTag2;
    }

    public void setTag2(String tag2){
        this.mTag2=tag2;
    }

    public String getTag3(){
        return mTag3;
    }

    public void setTag3(String tag3){
        this.mTag3=tag3;
    }

}
