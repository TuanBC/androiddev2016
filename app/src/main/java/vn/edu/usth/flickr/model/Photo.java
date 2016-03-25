package vn.edu.usth.flickr.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by nguye_000 on 3/25/2016.
 */
public class Photo {

    private String mId;
    private String mCaption;
    private String mUrl;
    private String mOwner;
    private Bitmap mBitmap;

    public Bitmap getBitmap() { return mBitmap; }

    public void setBitmap(Bitmap mBitmap) { this.mBitmap = mBitmap; }

    public String getId() { return mId; }

    public void setId(String mId) { this.mId = mId;  }

    public String getCaption() { return mCaption; }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getUrl() { return mUrl; }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String mOwner) { this.mOwner = mOwner; }

    public Uri getPhotoPageUri() {
        return Uri.parse("http://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(mOwner)
                .appendPath(mId)
                .build();
    }

    @Override
    public String toString() {
        return mCaption;
    }
}
