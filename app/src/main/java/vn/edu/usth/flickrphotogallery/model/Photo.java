package vn.edu.usth.flickrphotogallery.model;

import java.util.UUID;

/**
 * Created by thangit on 3/11/2016.
 */
public class Photo {
    private UUID mId;
    private String mCaption;
    private int imgUrl;
    private boolean mLiked;

    public Photo(String title, int imgUrl) {
        mId = UUID.randomUUID();
        this.imgUrl = imgUrl;
        this.mCaption = title;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }
    public UUID getId() {
        return mId;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public boolean isLiked() {
        return mLiked;
    }

    public void setLiked(boolean liked) {
        mLiked = liked;
    }
}
