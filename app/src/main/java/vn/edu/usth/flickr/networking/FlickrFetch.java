package vn.edu.usth.flickr.networking;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.flickr.model.Comment;
import vn.edu.usth.flickr.model.Photo;

/**
 * Created by nguye_000 on 3/25/2016.
 */
public class FlickrFetch {
    private static final String TAG = "FlickrFetch";

    private static final String API_KEY = "43072b6cdb71fd0723d99f64dc5539ba";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Photo> fetchImages(String method) {

        List<Photo> listPhotos = new ArrayList<Photo>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", method)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseImages(listPhotos, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return listPhotos;
    }

    private void parseImages(List<Photo> listPhotos, JSONObject jsonBody)
            throws IOException, JSONException {

        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            Photo photo = new Photo();
            photo.setId(photoJsonObject.getString("id"));
            photo.setCaption(photoJsonObject.getString("title"));
            photo.setOwner(photoJsonObject.getString("owner"));

            if (!photoJsonObject.has("url_s")) {
                continue;
            }

            photo.setUrl(photoJsonObject.getString("url_s"));
            listPhotos.add(photo);
        }
    }

    public List<Comment> fetchComment(String method, String photoId) {

        List<Comment> listComments = new ArrayList<Comment>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", method)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("photo_id", photoId)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseComment(listComments, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return listComments;
    }

    private void parseComment(List<Comment> listComments, JSONObject jsonBody)
            throws IOException, JSONException {

        JSONObject photosJsonObject = jsonBody.getJSONObject("comments");
        if (photosJsonObject.isNull("authorname"))
            return;
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("comment");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject commentJsonObject = photoJsonArray.getJSONObject(i);

            Comment comment = new Comment();
            comment.setAuthorName(commentJsonObject.getString("authorname"));
            comment.setComment(commentJsonObject.getString("_content"));

            listComments.add(comment);
        }
    }
}
