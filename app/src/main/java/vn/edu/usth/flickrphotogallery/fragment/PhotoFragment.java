package vn.edu.usth.flickrphotogallery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import vn.edu.usth.flickrphotogallery.R;
import vn.edu.usth.flickrphotogallery.model.Gallery;
import vn.edu.usth.flickrphotogallery.model.Photo;

/**
 * Created by thangit on 3/11/2016.
 */
public class PhotoFragment extends Fragment {
    private static final String ARG_PHOTO_ID = "photo_id";

    private Photo mPhoto;

    private ImageView mPhotoImageView;
    private EditText mCaptionTextView;
    private ImageButton mLikeImageButton;
    private TextView mLikeTextView;

    public static PhotoFragment newInstance(UUID crimdId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO_ID, crimdId);

        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        UUID mPhotoId = (UUID) getArguments().getSerializable(ARG_PHOTO_ID);
        mPhoto = Gallery.get(getActivity()).getPhoto(mPhotoId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_photo, container, false);

        mPhotoImageView = (ImageView) v.findViewById(R.id.fragment_photo_image_view);
        mPhotoImageView.setImageResource(mPhoto.getImgUrl());

        mCaptionTextView = (EditText) v.findViewById(R.id.fragment_photo_caption_text_view);
        mCaptionTextView.setText(mPhoto.getCaption());
        mCaptionTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhoto.setCaption(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLikeTextView = (TextView) v.findViewById(R.id.fragment_photo_like_text_view);
        mLikeTextView.setText("Like");
        if (mPhoto.isLiked())
            mLikeTextView.setTextColor(Color.BLUE);
        mLikeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhoto.isLiked() == false) {
                    mPhoto.setLiked(true);
                    mLikeTextView.setTextColor(Color.BLUE);
                    mLikeTextView.setText("Liked");
                } else {
                    mPhoto.setLiked(false);
                    mLikeTextView.setTextColor(Color.GRAY);
                    mLikeTextView.setText("Like");
                }
            }
        });

        mLikeImageButton = (ImageButton) v.findViewById(R.id.fragment_photo_like_image_button);
        mLikeImageButton.setImageResource(R.drawable.like);
        mLikeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhoto.isLiked() == false) {
                    mPhoto.setLiked(true);
                    mLikeTextView.setTextColor(Color.BLUE);
                    mLikeTextView.setText("Liked");
                } else {
                    mPhoto.setLiked(false);
                    mLikeTextView.setTextColor(Color.GRAY);
                    mLikeTextView.setText("Like");
                }
            }
        });
        return v;
    }
}
