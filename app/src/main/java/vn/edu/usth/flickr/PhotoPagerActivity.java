package vn.edu.usth.flickr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import vn.edu.usth.flickr.fragment.PhotoPagerFragment;
import vn.edu.usth.flickr.model.Photo;

/**
 * Created by nguye_000 on 3/25/2016.
 */
public class PhotoPagerActivity extends AppCompatActivity{
    private static final String EXTRA_PHOTO_ID =
            "vn.edu.usth.flickr.photo_id";
    private static final String EXTRA_PHOTO_CAPTION =
            "vn.edu.usth.flickr.photo_caption";
    private static final String EXTRA_PHOTO_BITMAP =
            "vn.edu.usth.flickr.photo_drawable";

    private ViewPager mPhotoViewPager;
    private static List<Photo> mListPhotos;

    public static Intent newIntent(Context packageContext, String photoId, List<Photo> listPhotos) {
        Intent intent = new Intent(packageContext, PhotoPagerActivity.class);
        intent.putExtra(EXTRA_PHOTO_ID, photoId);
        mListPhotos = listPhotos;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pager);

        String photoId = (String) getIntent()
                .getSerializableExtra(EXTRA_PHOTO_ID);

        mPhotoViewPager = (ViewPager) findViewById(R.id.activity_photo_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mPhotoViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Photo photo = mListPhotos.get(position);
                return PhotoPagerFragment.newInstance(photo.getId(), photo.getCaption(), photo.getBitmap());
            }

            @Override
            public int getCount() {
                return mListPhotos.size();
            }
        });

        for (int i = 0; i < mListPhotos.size(); i++) {
            if (mListPhotos.get(i).getId().equals(photoId)) {
                mPhotoViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}

