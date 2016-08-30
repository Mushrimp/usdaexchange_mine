package com.mahoneydev.usdafmexchange.pages;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_105_accountinfo extends PageOperations {
    public static void prepareaccountinfoform() {
        String name = UserFileUtility.get_username();
        ((TextView)hashelements.get("nameView")).setText("Username:  "+name);

        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject ja = (result.getJSONObject("results")).getJSONObject("userinfo");
                String email = ja.getString("email");
                String displayname = ja.getString("displayname");
                ((EditText) hashelements.get("emailInput")).setText(email);
                ((EditText) hashelements.get("displaynameInput")).setText(displayname);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdalogin", "get_userinfo", ht));
    }
    public static void saveUserInfoAction() {
        final String currentpass = (((EditText) hashelements.get("currentpassInput")).getText()).toString();
        final String password_1 = (((EditText) hashelements.get("newpassInput")).getText()).toString();
        final String password_2 = (((EditText) hashelements.get("confirmpassInput")).getText()).toString();
        final String email = (((EditText) hashelements.get("emailInput")).getText()).toString();
        final String displayname = (((EditText) hashelements.get("displaynameInput")).getText()).toString();
        final TextView etv = (TextView) hashelements.get("accountErrorView");
        boolean flag = true;
        if (!password_1.equals(password_2)) {
            etv.setText("Passwords Do Not Match!");
            flag = false;
        } else if (!AppCodeResources.isEmailValid(email)) {
            etv.setText("Email is Invalid!");
            flag = false;
        } else if (currentpass.equals("") && (!password_1.equals(""))) {
            etv.setText("Current Password is Needed!");
            flag = false;
        }
        if (flag) {
            Hashtable<String, String> ht = new Hashtable<String, String>();
            String token_s = UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("email", email);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    if (currentpass.equals("")) {
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        String token_s = UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token", token_s);
                        ht.put("email", email);
                        ht.put("display_name", displayname);
                        ht.put("user_pass", "");
                        ht.put("user_pass2", "");
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                removeRecentPage();
                                Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            @Override
                            protected void executeFailed(JSONObject result) throws JSONException {
                                etv.setText(result.getString("error"));
                            }

                        }.execute(AppCodeResources.postUrl("usdalogin", "update_user_account", ht));
                    } else {
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        String token_s = UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token", token_s);
                        ht.put("currentpassword", currentpass);
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                Hashtable<String, String> ht = new Hashtable<String, String>();
                                String token_s = UserFileUtility.get_token();
                                ht.put("os", "Android");
                                ht.put("token", token_s);
                                ht.put("email", email);
                                ht.put("display_name", displayname);
                                ht.put("user_pass", password_1);
                                ht.put("user_pass2", password_2);
                                new FetchTask() {
                                    @Override
                                    protected void executeSuccess(JSONObject result) throws JSONException {
                                        removeRecentPage();
                                        Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                    @Override
                                    protected void executeFailed(JSONObject result) throws JSONException {
                                        etv.setText(result.getString("error"));
                                    }

                                }.execute(AppCodeResources.postUrl("usdalogin", "update_user_account", ht));
                            }
                            @Override
                            protected void executeFailed(JSONObject result) throws JSONException {
                                etv.setText(result.getString("error"));
                            }
                        }.execute(AppCodeResources.postUrl("usdalogin", "check_current_password", ht));
                    }
                }
                @Override
                protected void executeFailed(JSONObject result) throws JSONException {
                    etv.setText(result.getString("error"));
                }

            }.execute(AppCodeResources.postUrl("usdalogin", "check_email", ht));
        }
    }
}
