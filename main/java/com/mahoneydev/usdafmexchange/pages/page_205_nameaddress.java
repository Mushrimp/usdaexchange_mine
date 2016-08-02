package com.mahoneydev.usdafmexchange.pages;

import android.util.Log;
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by bichongg on 7/26/2016.
 */
public class page_205_nameaddress extends PageOperations{
    public static void preparevendorprofileform(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        JSONArray ja=new JSONArray();
        ja.put("business_name");
        ja.put("business_state");
        ja.put("business_street");
        ja.put("business_city");
        ja.put("business_zip");
        ht.put("form_args",ja.toString());
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject jo=new JSONObject(result.getString("results"));
                ((EditText)hashelements.get("busnameInput")).setText(jo.getString("business_name"));
                ((EditText)hashelements.get("busaddressInput")).setText(jo.getString("business_street"));
                ((EditText)hashelements.get("buscityInput")).setText(jo.getString("business_city"));
                ((EditText)hashelements.get("buszipcodeInput")).setText(jo.getString("business_zip"));
                int length=AppCodeResources.state_list.size();
                SpinnerElement[] arraySpinner = new SpinnerElement[length];
                int selected=0;
                String exstate=jo.getString("business_state");
                for(int i=0;i<length;i++)
                {
                    arraySpinner[i] = new SpinnerElement(AppCodeResources.state_list.get(i).getName(),AppCodeResources.state_list.get(i).getValue());
                    if (exstate.equals(AppCodeResources.state_list.get(i).getValue()))
                    {
                        selected=i;
                    }
                }
                ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                        android.R.layout.simple_spinner_item, arraySpinner);
                ((Spinner) hashelements.get("busstateSpinner")).setAdapter(adapter);
                ((Spinner) hashelements.get("busstateSpinner")).setSelection(selected);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdamobile", "usda_form_read", ht));
    }

    public static void saveNameAddressAction(){
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("post_type","user");
        String busname= ((EditText) hashelements.get("busnameInput")).getText().toString();
        String busstate=((SpinnerElement)((Spinner)hashelements.get("busstateSpinner")).getSelectedItem()).getValue();
        String busstreet=((EditText) hashelements.get("busaddressInput")).getText().toString();
        String buscity=((EditText) hashelements.get("buscityInp ut")).getText().toString();
        String buszip=((EditText) hashelements.get("buszipcodeInput")).getText().toString();
        TextView etv=((TextView)hashelements.get("erroraddressView"));
        boolean flag=true;
        if (busname.equals(""))
        {
            flag=false;
            etv.setText("Business Name Cannot be Empty!");
        }
        else if (busstate.equals(""))
        {
            flag=false;
            etv.setText("State Cannot be Empty!");
        }
        else if (busstreet.equals(""))
        {
            flag=false;
            etv.setText("Business Address Cannot be Empty!");
        }
        else if (buscity.equals(""))
        {
            flag=false;
            etv.setText("City Cannot be Empty!");
        }
        else if (!AppCodeResources.isZipCodeValid(buszip))
        {
            flag=false;
            etv.setText("Zip Code is Invalid!");
        }
        if (!flag)
            return;
        try {
            JSONObject jo = new JSONObject();
            jo.put("business_name",busname);
            jo.put("business_state", busstate);
            jo.put("business_street", busstreet);
            jo.put("business_city",buscity);
            jo.put("business_zip",buszip);
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
