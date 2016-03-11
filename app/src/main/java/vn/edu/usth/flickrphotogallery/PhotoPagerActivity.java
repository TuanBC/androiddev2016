package vn.edu.usth.flickrphotogallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import vn.edu.usth.flickrphotogallery.fragment.PhotoFragment;
import vn.edu.usth.flickrphotogallery.model.Gallery;
import vn.edu.usth.flickrphotogallery.model.Photo;

/**
 * Created by thangit on 3/11/2016.
 */
public class PhotoPagerActivity extends AppCompatActivity {
    private static final String EXTRA_PHOTO_ID =
            "vn.edu.usth.flickrphotogallery.photo_id";

    private ViewPager mPhotoViewPager;
    private List<Photo> mPhotos;

    public static Intent newIntent(Context packageContext, UUID photoId) {
        Intent intent = new Intent(packageContext, PhotoPagerActivity.class);
        intent.putExtra(EXTRA_PHOTO_ID, photoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pager);

        UUID photoId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PHOTO_ID);

        mPhotoViewPager = (ViewPager) findViewById(R.id.activity_photo_view_pager);

        mPhotos = Gallery.get(this).getPhotos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mPhotoViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Photo photo = mPhotos.get(position);
                return PhotoFragment.newInstance(photo.getId());
            }

            @Override
            public int getCount() {
                return mPhotos.size();
            }
        });

        for (int i = 0; i < mPhotos.size(); i++) {
            if (mPhotos.get(i).getId().equals(photoId)) {
                mPhotoViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
