package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.MatchAdapter;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.SpinnerElement;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by xianan on 8/1/16.
 */
public class page_310_addnewmarket extends PageOperations {
    public static void preparemarketform() {

        int length = AppCodeResources.state_list.size();
        SpinnerElement[] arraySpinner = new SpinnerElement[length];
        arraySpinner[0] = new SpinnerElement("Select a State", "0");
        for (int i = 0; i < length; i++) {
            arraySpinner[i] = new SpinnerElement(AppCodeResources.state_list.get(i).getName(), AppCodeResources.state_list.get(i).getValue());
        }
        ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                android.R.layout.simple_spinner_item, arraySpinner);
        ((Spinner) hashelements.get("newmarketstateSpinner")).setAdapter(adapter);

        setupUI(playout);
    }

    public static class savenewmarketListener extends ClickOnceListener {
        public savenewmarketListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            Hashtable<String, String> ht = new Hashtable<String, String>();
            String token_s = UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("formargs", "");
            String name = ((EditText) hashelements.get("marketnameInput")).getText().toString();
            String email = ((EditText) hashelements.get("emailInput")).getText().toString();
            String street = ((EditText) hashelements.get("streetInput")).getText().toString();
            String city = ((EditText) hashelements.get("cityInput")).getText().toString();
            String state = ((SpinnerElement) ((Spinner) hashelements.get("newmarketstateSpinner")).getSelectedItem()).getValue();
            String zip = ((EditText) hashelements.get("zipcodeInput")).getText().toString();
            TextView etv = ((TextView) hashelements.get("errorView"));
            boolean flag = true;
            if (name.equals("")) {
                flag = false;
                etv.setText("Market Name Cannot be Empty!");
            }
            if (email.equals("")) {
                flag = false;
                etv.setText("Email Cannot be Empty!");
            } else if (state.equals("")) {
                flag = false;
                etv.setText("State Cannot be Empty!");
            } else if (street.equals("")) {
                flag = false;
                etv.setText("Market Address Cannot be Empty!");
            } else if (city.equals("")) {
                flag = false;
                etv.setText("City Cannot be Empty!");
            } else if (!AppCodeResources.isZipCodeValid(zip)) {
                flag = false;
                etv.setText("Zip Code is Invalid!");
            }
            if (!flag) {
                enableButton();
                return;
            }
            try {
                JSONObject jo = new JSONObject();
                jo.put("MarketName", name);
                jo.put("marketemail", email);
                jo.put("location_state", state);
                jo.put("location_street", street);
                jo.put("location_city", city);
                jo.put("location_zip", zip);
                ht.put("postdata", jo.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                    toast.show();
                    removeRecentPage();
                    enableButton();
                }

                @Override
                protected void executeFailed(JSONObject result) throws JSONException {
                    enableButton();
                }
            }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_markets_addto_fromuser", ht));
        }
    }
}

