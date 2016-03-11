package vn.edu.usth.flickrphotogallery.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.edu.usth.flickrphotogallery.R;

/**
 * Created by thangit on 3/11/2016.
 */
public class Gallery {
    private static Gallery sGallery;

    private ArrayList<Photo> mPhotos;

    public static Gallery get(Context context) {
        if (sGallery == null)
            sGallery = new Gallery(context);
        return sGallery;
    }

    private Gallery(Context context) {
        mPhotos = new ArrayList<Photo>();
        for (int i = 0; i < 20; i ++) {
            Photo photo = new Photo("The " + i + "th confused dog", R.drawable.dog);
            mPhotos.add(photo);
        }
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public Photo getPhoto(UUID id) {
        for (Photo crime : mPhotos) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
