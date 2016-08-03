package com.mahoneydev.usdafmexchange.pages;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.MatchAdapter;
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
public class page_306_addproductform extends PageOperations{
    public static void prepareproductform(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ((Spinner) hashelements.get("categorySpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                    Hashtable<String, String> ht = new Hashtable<String, String>();
                    String token_s = UserFileUtility.get_token();
                    ht.put("os", "Android");
                    ht.put("token", token_s);
                    ht.put("catetoryid", selecteditem.getValue());
                    new FetchTask() {
                        @Override
                        protected void executeSuccess(JSONObject result) throws JSONException {
                            JSONArray ja = result.getJSONArray("results");
                            String[] arrayString = new String[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jsonobject = ja.getJSONObject(i);
                                arrayString[i] = jsonobject.getString("label");
                            }
                            MatchAdapter adapter = new MatchAdapter(context,
                                    android.R.layout.simple_spinner_item, arrayString);
                            AutoCompleteTextView actv = ((AutoCompleteTextView) hashelements.get("productnameInput"));
                            actv.setAdapter(adapter);
                            actv.setThreshold(2);
                        }

                    }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht));
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_item, new String[0]);
                    AutoCompleteTextView actv = ((AutoCompleteTextView) hashelements.get("productnameInput"));
                    actv.setAdapter(adapter);
                    actv.setThreshold(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONArray ja = result.getJSONArray("results");
                SpinnerElement[] arraySpinner = new SpinnerElement[ja.length() + 1];
                arraySpinner[0] = new SpinnerElement("Select a Category", "0");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jsonobject = ja.getJSONObject(i);
                    arraySpinner[i + 1] = new SpinnerElement(jsonobject.getString("Prd_Category1"), jsonobject.getString("Prd_Cat_ID1"));
                }

                ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                        android.R.layout.simple_spinner_item, arraySpinner);
                ((Spinner) hashelements.get("categorySpinner")).setAdapter(adapter);
                Hashtable<String, String> ht = new Hashtable<String, String>();
                String token_s = UserFileUtility.get_token();
                ht.put("os", "Android");
                ht.put("token", token_s);
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        JSONArray ja = result.getJSONArray("results");
                        String[] arrayString = new String[ja.length()];
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jsonobject = ja.getJSONObject(i);
                            arrayString[i] = jsonobject.getString("label");
                        }
                        MatchAdapter adapter = new MatchAdapter(context,
                                android.R.layout.simple_spinner_item, arrayString);
                        AutoCompleteTextView actv = ((AutoCompleteTextView) hashelements.get("unitInput"));
                        actv.setAdapter(adapter);
                        actv.setThreshold(2);
                        setupUI(playout);
                    }

                }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductunit", ht));
            }

        }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));
    }

    public static class saveproductListener extends ClickOnceListener {
        public saveproductListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            String productcategoryid = ((SpinnerElement) (((Spinner) hashelements.get("categorySpinner")).getSelectedItem())).getValue();
            String productname = ((AutoCompleteTextView) hashelements.get("productnameInput")).getText().toString();
            String unit = ((AutoCompleteTextView) hashelements.get("unitInput")).getText().toString();
            String organic = "";
            if (((CheckBox) hashelements.get("organicCheckbox")).isChecked())
                organic = "yes";
            String other = "";
            if (((CheckBox) hashelements.get("otherCheckbox")).isChecked())
                other = "yes";
            String otherdesc = ((EditText) hashelements.get("otherorganicInput")).getText().toString();
            boolean flag = true;
            TextView errortv = ((TextView) hashelements.get("producterrorView"));
            if (productcategoryid.equals("0")) {
                errortv.setText("Please Select a Product Category");
                flag = false;
            } else if (productname == null || productname.equals("")) {
                errortv.setText("Please Input Product Name");
                flag = false;
            } else if (unit == null || unit.equals("")) {
                errortv.setText("Please Input Unit");
                flag = false;
            }
            if (flag) {
                //Build Post Data
                Hashtable<String, String> postdataht = new Hashtable<String, String>();
                postdataht.put("Prd_Category1", productcategoryid);
                postdataht.put("Prd_Name", productname);
                postdataht.put("Prd_Unit", unit);
                postdataht.put("Prd_organic_usda", organic);
                postdataht.put("Prd_organic_other", other);
                postdataht.put("Prd_organic_other_desc", otherdesc);
                String jsonpostdata = (new JSONObject(postdataht)).toString();

                Hashtable<String, String> ht = new Hashtable<String, String>();
                String token_s = UserFileUtility.get_token();
                ht.put("os", "Android");
                ht.put("token", token_s);
                ht.put("postdata", jsonpostdata);
                ht.put("formargs", "2");
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        ((TextView) hashelements.get("producterrorView")).setText("Success!");
                        removeRecentPage();
                    }
                    @Override
                    protected void executeFailed (JSONObject result) throws JSONException {
                        enableButton();
                    }
                }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_product_addto_list", ht));
            }
            else
            {
                enableButton();
            }
        }
    }
}
