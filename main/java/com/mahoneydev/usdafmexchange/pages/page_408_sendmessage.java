package com.mahoneydev.usdafmexchange.pages;

import android.view.View;
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
 * Created by bichongg on 7/28/2016.
 */
public class page_408_sendmessage extends PageOperations {
    public static void preparemessage(Hashtable<String,String> params){
        if (params.containsKey("recipientsid"))
        {
            ((TextView)hashelements.get("recipientname")).setText(params.get("recipientsname"));
        }
        String subject="";
        if (params.containsKey("subject"))
        {
            subject=params.get("subject");
            ((EditText)hashelements.get("subjectInput")).setText(params.get("subjectInput"));
            ((EditText)hashelements.get("subjectInput")).setEnabled(false);
        }

        setupUI(playout);

    }
    public static void sendmessage(){
        final TextView etv=((TextView)hashelements.get("errormessageView"));
        Hashtable<String,String> ht=new Hashtable<>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        Hashtable<String,String> params=getRecentPage().params;
        if (params.containsKey("recipientsid"))
            ht.put("recipients",params.get("recipientsid"));
        else
            return;
        if (params.containsKey("threadid"))
            ht.put("thread_id",params.get("threadid"));
        String subject=((EditText)hashelements.get("subjectInput")).getText().toString();
        if (subject.equals("")) {
            etv.setText("Subject Cannot be Empty!");
            return;
        }
        String content=((EditText)hashelements.get("contentInput")).getText().toString();
        if (content.equals("")) {
            etv.setText("Content Cannot be Empty!");
            return;
        }
        ht.put("subject",subject);
        ht.put("content",content);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                ((TextView)hashelements.get("errormessageView")).setText("Success!");
                removeRecentPage();
                Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                toast.show();

            }
            @Override
            protected void executeFailed(JSONObject result) throws JSONException {
                ((TextView)hashelements.get("errormessageView")).setText(result.getString("error"));
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "messages_send_new_message", ht));

    }
}
