package com.mahoneydev.usdafmexchange.pages;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by xianan on 9/14/16.
 */
public class page_103_retrievepin extends PageOperations {
    public static class retrievepinListener extends ClickOnceListener {
        public retrievepinListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            final String email = (((EditText) hashelements.get("reemailInput")).getText()).toString();
            final TextView etv = (TextView) hashelements.get("emailerrorView");
            boolean flag = true;
            if (!AppCodeResources.isEmailValid(email)) {
                etv.setText("Email is Invalid!");
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
                        ht.put("email", email);
                        ht.put("nonce", nonce);
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                Toast.makeText(context, "Success.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void executeFailed(JSONObject result) throws JSONException {
                                etv.setText(result.getString("error"));
                                enableButton();
                            }

                        }.execute(AppCodeResources.postUrl("usdalogin", "retrievepassowrd", ht));
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
