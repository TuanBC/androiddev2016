package vn.edu.usth.flickr.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.flickr.PhotoPagerActivity;
import vn.edu.usth.flickr.R;
import vn.edu.usth.flickr.model.Photo;
import vn.edu.usth.flickr.networking.FlickrFetch;
import vn.edu.usth.flickr.networking.ThumbnailDownloader;

/**
 * Created by nguye_000 on 3/23/2016.
 */

public class FavoriteGalleryFragment extends Fragment {
    private static final String TAG = "FavoriteGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<Photo> mListPhotos = new ArrayList<>();
    private ThumbnailDownloader mThumbnailDownloader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemsTask().execute();

        Handler responseHandler = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        photoHolder.bindDrawable(drawable);
                        photoHolder.bindBitMap(bitmap);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_recent_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mListPhotos));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mPhotoImageView;

        private Photo mPhoto;
        private Bitmap photoBitmap;
        private List<Photo> mListPhotos;

        public PhotoHolder(View itemView) {
            super(itemView);

            mPhotoImageView = (ImageView) itemView.findViewById(R.id.fragment_recent_gallery_image_view);
            itemView.setOnClickListener(this);
        }

        public void bindDrawable(Drawable drawable) {
            mPhotoImageView.setImageDrawable(drawable);
        }

        public void bindBitMap (Bitmap bitmap) {
            photoBitmap = bitmap;
            mPhoto.setBitmap(bitmap);
        }

        public void bindPhoto (Photo photo, List<Photo> listPhotos) {
            mPhoto = photo;
            mListPhotos = listPhotos;
        }

        @Override
        public void onClick(View v) {
            Intent intent = PhotoPagerActivity.newIntent(getActivity(), mPhoto.getId(), mListPhotos);
            startActivity(intent);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<Photo> mListPhotos;

        public PhotoAdapter(List<Photo> listPhotos) {
            mListPhotos = listPhotos;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.photo, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Photo photo = mListPhotos.get(position);
            Drawable placeholder = getResources().getDrawable(R.drawable.dog);
            photoHolder.bindDrawable(placeholder);
            photoHolder.bindPhoto(photo, mListPhotos);
            mThumbnailDownloader.queueThumbnail(photoHolder, photo.getUrl());
        }

        @Override
        public int getItemCount() {
            return mListPhotos.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Photo>> {

        @Override
        protected List<Photo> doInBackground(Void... params) {
            return new FlickrFetch().fetchImages("flickr.interestingness.getList");
        }

        @Override
        protected void onPostExecute(List<Photo> listPhotos) {
            mListPhotos = listPhotos;
            setupAdapter();
        }

    }
}



