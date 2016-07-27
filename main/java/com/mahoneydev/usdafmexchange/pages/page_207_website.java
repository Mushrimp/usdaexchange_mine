package com.mahoneydev.usdafmexchange.pages;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/26/2016.
 */
public class page_207_website extends PageOperations {
    public static void preparevendorwebform(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        JSONArray ja=new JSONArray();
        ja.put("business_website");
        ja.put("business_facebook");
        ja.put("business_twitter");
        ja.put("business_othermedia1");
        ja.put("business_othermedia2");
        ht.put("form_args",ja.toString());
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject jo=new JSONObject(result.getString("results"));
                ((EditText)hashelements.get("websiteInput")).setText(jo.getString("business_website"));
                ((EditText)hashelements.get("facebookInput")).setText(jo.getString("business_facebook"));
                ((EditText)hashelements.get("twitterInput")).setText(jo.getString("business_twitter"));
                ((EditText)hashelements.get("other1Input")).setText(jo.getString("business_othermedia1"));
                ((EditText)hashelements.get("other2Input")).setText(jo.getString("business_othermedia2"));

                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_read", ht));
    }

    public static void saveWebAction(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("post_type","user");
        String website= ((EditText) hashelements.get("websiteInput")).getText().toString();
        String facebook=((EditText) hashelements.get("facebookInput")).getText().toString();
        String twitter= ((EditText) hashelements.get("twitterInput")).getText().toString();
        String other1=((EditText) hashelements.get("other1Input")).getText().toString();
        String other2=((EditText) hashelements.get("other2Input")).getText().toString();

        try {
            JSONObject jo = new JSONObject();
            jo.put("business_website",website);
            jo.put("business_facebook", facebook);
            jo.put("business_twitter", twitter);
            jo.put("business_othermedia1", other1);
            jo.put("business_othermedia2", other2);
            ht.put("postdata",jo.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return;
        }

        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                toast.show();
                removeRecentPage();
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_save_from_mobile", ht));
    }
}
