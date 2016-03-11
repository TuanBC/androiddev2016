package vn.edu.usth.flickrphotogallery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.edu.usth.flickrphotogallery.R;
import vn.edu.usth.flickrphotogallery.PhotoPagerActivity;
import vn.edu.usth.flickrphotogallery.model.Gallery;
import vn.edu.usth.flickrphotogallery.model.Photo;

/**
 * Created by thangit on 3/11/2016.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        Gallery gallery = Gallery.get(getActivity());
        List<Photo> photos = gallery.getPhotos();

        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(photos));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAdapter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView mPhotoView;
        private Photo mPhoto;

        public PhotoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPhotoView = (ImageView) itemView.findViewById(R.id.list_item_photo_image_view);
        }

        public void bindPhoto(Photo photo) {
            mPhoto = photo;
            mPhotoView.setImageResource(mPhoto.getImgUrl());
        }

        @Override
        public void onClick(View v) {
            Intent intent = PhotoPagerActivity.newIntent(getActivity(), mPhoto.getId());
            startActivity(intent);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<Photo> mPhotos;

        public PhotoAdapter(List<Photo> photos) {
            mPhotos = photos;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_photo, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            photoHolder.bindPhoto(mPhotos.get(position));
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }
    }
}
