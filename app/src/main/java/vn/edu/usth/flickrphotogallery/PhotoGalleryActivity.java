package vn.edu.usth.flickrphotogallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import vn.edu.usth.flickrphotogallery.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
