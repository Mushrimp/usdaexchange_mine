package com.mahoneydev.usdafmexchange.pages;

import android.content.Intent;
import android.widget.ImageView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.LoadImage;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_106_uploadlogo extends PageOperations {
    public static void loadavatar(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                String imageurl = result.getString("avatar_url");
                imageurl = imageurl.replace("\\", "");
                LoadImage li = new LoadImage();
                li.img = (ImageView) hashelements.get("logoView");
                li.execute(imageurl);
                setupUI(playout);
            }

        }.execute(AppCodeResources.postUrl("usdamobile", "get_avatarurl", ht));
    }
    public static void startUploadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, AppCodeResources.IMAGE_UPLOAD);
    }
}
