package vn.edu.usth.flickr.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.usth.flickr.R;
import vn.edu.usth.flickr.model.Comment;

/**
 * Created by nguye_000 on 3/25/2016.
 */
public class PhotoPagerFragment extends Fragment{
    private static final String TAG = "PhotoPagerFragment";

    private static final String ARG_PHOTO_ID = "photo_id";
    private static final String ARG_PHOTO_CAPTION = "photo_caption";
    private static final String ARG_PHOTO_BITMAP = "photo_drawable";

    private List<Comment> mListComments;

    private ImageView mPhotoImageView;
    private TextView mCaptionTextView;
    private RecyclerView mCommentRecyclerView;

    private String mPhotoId;
    private String mPhotoCaption;
    private Drawable mPhotoDrawable;

    public static PhotoPagerFragment newInstance(String photoId, String photoCaption, Bitmap photoBitmap) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO_ID, photoId);
        args.putSerializable(ARG_PHOTO_CAPTION, photoCaption);
        args.putParcelable(ARG_PHOTO_BITMAP, photoBitmap);

        PhotoPagerFragment fragment = new PhotoPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_photo_pager, container, false);

        mPhotoId = (String) getArguments().getSerializable(ARG_PHOTO_ID);

        mPhotoDrawable = new BitmapDrawable(getResources(), (Bitmap) getArguments().getParcelable(ARG_PHOTO_BITMAP));
        mPhotoImageView = (ImageView) v.findViewById(R.id.fragment_photo_page_image_view);
        mPhotoImageView.setImageDrawable(mPhotoDrawable);


        mPhotoCaption = (String) getArguments().getSerializable(ARG_PHOTO_CAPTION);
        mCaptionTextView = (TextView) v.findViewById(R.id.fragment_photo_page_text_view);
        mCaptionTextView.setText(mPhotoCaption);

//        mCommentRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_page_comment_recycler_view);
//        mCommentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//
//        setupAdapter();

        return v;
    }

//    private void setupAdapter() {
//        if (isAdded()) {
//            mCommentRecyclerView.setAdapter(new CommentAdapter(mListComments));
//        }
//    }


//    private class CommentHolder extends RecyclerView.ViewHolder {
//        private TextView mAuthorNameTextView;
//        private TextView mContentTextView;
//        private Comment mComment;
//
//        public CommentHolder(View itemView) {
//            super(itemView);
//
//            mAuthorNameTextView = (TextView) itemView.findViewById(R.id.comment_author_text_view);
//            mContentTextView = (TextView) itemView.findViewById(R.id.comment_content_text_view);
//        }
//
//        public void bindText() {
//            mAuthorNameTextView.setText(mComment.getAuthorName());
//            mContentTextView.setText(mComment.getComment());
//        }
//
//        public void bindComment (Comment comment) { mComment = comment; }
//    }
//
//    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
//
//        private List<Comment> mListComments;
//
//        public CommentAdapter(List<Comment> listComments) {
//            mListComments = listComments;
//        }
//
//        @Override
//        public CommentHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            View view = inflater.inflate(R.layout.comment, viewGroup, false);
//            return new CommentHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(CommentHolder commentHolder, int position) {
//            Comment comment = mListComments.get(position);
//            commentHolder.bindComment(comment);
//            commentHolder.bindText();
//        }
//
//        @Override
//        public int getItemCount() {
//            return mListComments.size();
//        }
//    }
//
//    private class FetchItemsTask extends AsyncTask<Void,Void,List<Comment>> {
//
//        @Override
//        protected List<Comment> doInBackground(Void... params) {
//            return new FlickrFetch().fetchComment("flickr.photos.comments.getList", mPhotoId);
//        }
//
//        @Override
//        protected void onPostExecute(List<Comment> listComments) {
//            mListComments = listComments;
//            //setupAdapter();
//        }
//
//    }
}

