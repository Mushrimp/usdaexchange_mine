package com.mahoneydev.usdafmexchange.pages;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.Frontpage;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.QuickstartPreferences;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_102_login extends PageOperations {
    public static class loginListener extends ClickOnceListener {
        public loginListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            String username_s = (((EditText) hashelements.get("usernameInput")).getText()).toString();
            Log.d("username", username_s);
            final String password_s = (((EditText) hashelements.get("passwordInput")).getText()).toString();
            Log.d("password", password_s);
            String token_s = UserFileUtility.get_token();
            if (token_s.equals("")) {
                ((TextView) hashelements.get("loginErrorView")).setText("Network Error!");
                return;
            }
            UserFileUtility.set_userlogininfo(username_s, password_s);
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("username", username_s);
            ht.put("password", password_s);
            ht.put("os", "Android");
            ht.put("token", token_s);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    UserFileUtility.save_userinfo();
                    squashNewPage(R.array.page_001_front, null);
                    ((TextView) context.findViewById(R.id.username_menu_display)).setText(UserFileUtility.get_username());
                    String role=result.getString("usda_role");
                    if (role.equals("vendor")) {
                        setMenu(R.id.login_vendor);
                    } else if (role.equals("customer")) {
                        setMenu(R.id.login_customer);
                    }
                    setAvatar(true);
                }

                @Override
                protected void executeFailed(JSONObject result) throws JSONException {
                if (!result.getString("error").equals("-9")) {
                    ((TextView) hashelements.get("loginErrorView")).setText(result.getString("error"));
                } else if (result.getString("error").equals("-10")){
                    ((TextView) hashelements.get("loginErrorView")).setText("Network Error!");
                }
                }

            }.execute(AppCodeResources.postUrl("usdamobile", "mobile_login", ht));
        }
    }
}
