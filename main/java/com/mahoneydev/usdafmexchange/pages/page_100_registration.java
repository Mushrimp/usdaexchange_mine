package com.mahoneydev.usdafmexchange.pages;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/21/2016.
 */
public class page_100_registration extends PageOperations {
    public static class registrationListener extends ClickOnceListener {
        public registrationListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            final String username = (((EditText) hashelements.get("usernameInput")).getText()).toString();
            final String password_1 = (((EditText) hashelements.get("passwordInput")).getText()).toString();
            final String password_2 = (((EditText) hashelements.get("passwordInput2")).getText()).toString();
            final String email = (((EditText) hashelements.get("emailInput")).getText()).toString();
            final String displayname = (((EditText) hashelements.get("displayInput")).getText()).toString();
            final TextView etv = (TextView) hashelements.get("registrationErrorView");
            boolean flag = true;
            if (!password_1.equals(password_2)) {
                etv.setText("Passwords Do Not Match!");
                flag = false;
            } else if (!AppCodeResources.isEmailValid(email)) {
                etv.setText("Email is Invalid!");
                flag = false;
            } else if (password_1.equals("")) {
                etv.setText("Password Cannot be Empty!");
                flag = false;
            } else if (username.equals("")) {
                etv.setText("User Name Cannot be Empty!");
                flag = false;
            }
            if (flag) {
                Hashtable<String, String> ht = new Hashtable<String, String>();
                ht.put("controller", "usdalogin");
                ht.put("method", "register");
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        String nonce = result.getString("nonce");
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        ht.put("username", username);
                        ht.put("user_pass", password_1);
                        ht.put("user_pass2", password_2);
                        ht.put("email", email);
                        ht.put("display_name", displayname);
                        ht.put("nonce", nonce);
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                String token_s = UserFileUtility.get_token();
                                UserFileUtility.set_userlogininfo(username, password_1);
                                Hashtable<String, String> ht = new Hashtable<String, String>();
                                ht.put("username", username);
                                ht.put("password", password_1);
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
                                    }
                                }.execute(AppCodeResources.postUrl("usdamobile", "mobile_login", ht));
                            }

                            @Override
                            protected void executeFailed(JSONObject result) throws JSONException {
                                etv.setText(result.getString("error"));
                                enableButton();
                            }

                        }.execute(AppCodeResources.postUrl("usdalogin", "register", ht));
                    }

                    @Override
                    protected void executeFailed(JSONObject result) throws JSONException {
                        enableButton();
                    }
                }.execute(AppCodeResources.postUrl("", "get_nonce", ht));
            }
        }
    }
}
