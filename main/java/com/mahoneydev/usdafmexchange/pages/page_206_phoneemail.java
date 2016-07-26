package com.mahoneydev.usdafmexchange.pages;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.SpinnerElement;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/26/2016.
 */
public class page_206_phoneemail extends PageOperations{
    public static void preparevendorcontactform(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        JSONArray ja=new JSONArray();
        ja.put("business_phone");
        ja.put("business_email");
        ht.put("form_args",ja.toString());
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject jo=new JSONObject(result.getString("results"));
                ((EditText)hashelements.get("phoneInput")).setText(jo.getString("business_phone"));
                ((EditText)hashelements.get("emailInput")).setText(jo.getString("business_email"));
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_read", ht));
    }

    public static void saveContactAction(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("post_type","user");
        String busphone= ((EditText) hashelements.get("phoneInput")).getText().toString();
        String busemail=((EditText) hashelements.get("emailInput")).getText().toString();
        TextView etv=((TextView)hashelements.get("errorphoneView"));
        boolean flag=true;
        if (busphone.equals(""))
        {
            flag=false;
            etv.setText("Business Phone Cannot be Empty!");
        }
        else if (!AppCodeResources.isEmailValid(busemail))
        {
            flag=false;
            etv.setText("Email is Invalid!");
        }
        if (!flag)
            return;
        try {
            JSONObject jo = new JSONObject();
            jo.put("business_phone",busphone);
            jo.put("business_email", busemail);
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
