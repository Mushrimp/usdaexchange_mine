package com.mahoneydev.usdafmexchange.pages;

import android.widget.CheckBox;
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
 * Created by bichongg on 7/27/2016.
 */
public class page_107_setnotification extends PageOperations {
    public static void preparenotifications(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        JSONArray ja=new JSONArray();
        ja.put("usernoti_vendor_premarketreminder_email");
        ja.put("usernoti_vendor_premarketreminder_app");
        ja.put("usernoti_addfeaturesandupdate");
        ja.put("usernoti_addremovefriendrequest");
        ja.put("usernoti_friendmessage");
        ht.put("form_args",ja.toString());
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject jo=new JSONObject(result.getString("results"));
                if (jo.getString("usernoti_vendor_premarketreminder_email").equals("on"))
                    ((CheckBox) hashelements.get("emailpremarketCheckbox")).setChecked(true);
                if (jo.getString("usernoti_vendor_premarketreminder_app").equals("on"))
                    ((CheckBox) hashelements.get("premarketCheckbox")).setChecked(true);
                if (jo.getString("usernoti_addfeaturesandupdate").equals("on"))
                    ((CheckBox) hashelements.get("appupdateCheckbox")).setChecked(true);
                if (jo.getString("usernoti_addremovefriendrequest").equals("on"))
                    ((CheckBox) hashelements.get("friendCheckbox")).setChecked(true);
                if (jo.getString("usernoti_friendmessage").equals("on"))
                    ((CheckBox) hashelements.get("messageCheckbox")).setChecked(true);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_read", ht));
    }

    public static void savenotificationsettings(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("post_type","user");
        String emailpremarket="";
        String premarket="";
        String appupdate="";
        String friend="";
        String message="";
        if  (((CheckBox) hashelements.get("emailpremarketCheckbox")).isChecked())
            emailpremarket="on";
        if  (((CheckBox) hashelements.get("premarketCheckbox")).isChecked())
            premarket="on";
        if  (((CheckBox) hashelements.get("appupdateCheckbox")).isChecked())
            appupdate="on";
        if  (((CheckBox) hashelements.get("friendCheckbox")).isChecked())
            friend="on";
        if  (((CheckBox) hashelements.get("messageCheckbox")).isChecked())
            message="on";
        try {
            JSONObject jo = new JSONObject();
            jo.put("usernoti_vendor_premarketreminder_email",emailpremarket);
            jo.put("usernoti_vendor_premarketreminder_app", premarket);
            jo.put("usernoti_addfeaturesandupdate", appupdate);
            jo.put("usernoti_addremovefriendrequest", friend);
            jo.put("usernoti_friendmessage", message);
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
