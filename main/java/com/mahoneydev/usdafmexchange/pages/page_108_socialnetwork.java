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
public class page_108_socialnetwork extends PageOperations {
    public static void preparesocial(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        JSONArray ja=new JSONArray();
        ja.put("userfriendship_allowaddfriend");
        ja.put("userfriendship_allowrecievemessage");
        ja.put("usernoti_addremovefriendrequest");
        ja.put("usernoti_friendmessage");
        ht.put("form_args",ja.toString());
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject jo=new JSONObject(result.getString("results"));
                if (jo.getString("userfriendship_allowaddfriend").equals("on"))
                    ((CheckBox) hashelements.get("nofriendCheckbox")).setChecked(true);
                if (jo.getString("userfriendship_allowrecievemessage").equals("on"))
                    ((CheckBox) hashelements.get("nomessageCheckbox")).setChecked(true);
                if (jo.getString("usernoti_addremovefriendrequest").equals("on"))
                    ((CheckBox) hashelements.get("friendCheckbox")).setChecked(true);
                if (jo.getString("usernoti_friendmessage").equals("on"))
                    ((CheckBox) hashelements.get("messageCheckbox")).setChecked(true);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_read", ht));
    }

    public static void savesocialsettings(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("post_type","user");
        String nofriend="";
        String nomessage="";
        String friend="";
        String message="";
        if  (((CheckBox) hashelements.get("nofriendCheckbox")).isChecked())
            nofriend="on";
        if  (((CheckBox) hashelements.get("nomessageCheckbox")).isChecked())
            nomessage="on";
        if  (((CheckBox) hashelements.get("friendCheckbox")).isChecked())
            friend="on";
        if  (((CheckBox) hashelements.get("messageCheckbox")).isChecked())
            message="on";
        try {
            JSONObject jo = new JSONObject();
            jo.put("userfriendship_allowaddfriend",nofriend);
            jo.put("userfriendship_allowrecievemessage", nomessage);
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
