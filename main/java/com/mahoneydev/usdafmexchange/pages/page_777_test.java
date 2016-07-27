package com.mahoneydev.usdafmexchange.pages;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
 * Created by bichongg on 7/25/2016.
 */
public class page_777_test extends PageOperations{
    public static void preparetest(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONArray ja = result.getJSONArray("results");
                SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jsonobject = ja.getJSONObject(i);
                    arraySpinner[i] = new SpinnerElement(jsonobject.getString("Prd_Category1"), jsonobject.getString("Prd_Cat_ID1"));
                }

                ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                        android.R.layout.simple_spinner_item, arraySpinner);
                ((Spinner) hashelements.get("testSpinner")).setAdapter(adapter);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));
    }
    public static void testAction(){
        boolean check = ((CheckBox) hashelements.get("testCheckbox")).isChecked();
        ((CheckBox) hashelements.get("testCheckbox")).setChecked(!check);
        String input1 = ((EditText) hashelements.get("testInput")).getText().toString();
        ((EditText) hashelements.get("testInput")).setText(input1 + " " + input1);
        SpinnerElement spinner1 = (SpinnerElement) (((Spinner) hashelements.get("testSpinner")).getSelectedItem());
        ((TextView) hashelements.get("testView")).setText("Selected: " + spinner1.getName() + ", Postion: " + spinner1.getValue());
    }
}
