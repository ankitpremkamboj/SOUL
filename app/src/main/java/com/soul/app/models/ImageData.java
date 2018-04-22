package com.soul.app.models;

import java.util.ArrayList;

/**
 * Created by nirav on 05/10/15.
 */
public class ImageData {

    private String description;


    private ArrayList<userImageListEntity> mImageList;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<userImageListEntity> getmImageList() {
        return mImageList;
    }


    public void setmImageList(ArrayList<userImageListEntity> mImageList) {
        this.mImageList = mImageList;
    }

    public static class userImageListEntity {

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        private String imagePath;


    }
}
