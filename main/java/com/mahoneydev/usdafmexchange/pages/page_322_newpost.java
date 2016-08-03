package com.mahoneydev.usdafmexchange.pages;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_322_newpost extends PageOperations {
    public static void preparepostform() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ((Spinner) hashelements.get("productSpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                    String id = selecteditem.getValue();
                    ((AutoCompleteTextView) hashelements.get("unitInput")).setText(hashvalues.get(id));
                } else {
                    ((AutoCompleteTextView) hashelements.get("unitInput")).setText("");
                }
                ((AutoCompleteTextView) hashelements.get("unitInput")).clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                JSONObject ja = result.getJSONObject("results");
                SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                Iterator<?> keys = ja.keys();
                int i = 0;
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    arraySpinner[i] = new SpinnerElement(ja.getString(key), key);
                    i++;
                }

                ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                        R.layout.simple_spinner_item, arraySpinner);
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                ((Spinner) hashelements.get("productSpinner")).setAdapter(adapter);
                hashvalues = new Hashtable<String, String>();
                JSONArray results = result.getJSONArray("result");
                for (i = 0; i < results.length(); i++) {
                    JSONObject unititem = results.getJSONObject(i);
                    hashvalues.put(unititem.getString("id"), unititem.getString("productunit_name"));
                }
                Hashtable<String, String> ht = new Hashtable<String, String>();
                String token_s = UserFileUtility.get_token();
                ht.put("os", "Android");
                ht.put("token", token_s);
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        JSONObject ja = result.getJSONObject("results");
                        SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                        Iterator<?> keys = ja.keys();
                        int i = 0;
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            arraySpinner[i] = new SpinnerElement(ja.getString(key), key);
                            i++;
                        }

                        ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                                android.R.layout.simple_spinner_item, arraySpinner);
                        ((Spinner) hashelements.get("marketSpinner")).setAdapter(adapter);
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

                }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_user_marketlist", ht));
            }

        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_user_productlist", ht));
        ((TextView) hashelements.get("marketDay")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date;
                Calendar newCalendar = Calendar.getInstance();
                try {
                    date = dateFormatter.parse(((TextView) hashelements.get("marketDay")).getText().toString());
                    newCalendar.setTime(date);
                } catch (ParseException e) {

                }
                Calendar currentday = Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        ((TextView) hashelements.get("marketDay")).setText(dateFormatter.format(newDate.getTime()));
                        Calendar currentday = Calendar.getInstance();
                        Calendar publishday = Calendar.getInstance();
                        publishday.set(year, monthOfYear, dayOfMonth - 2);
                        if (currentday.getTimeInMillis() > publishday.getTimeInMillis())
                            ((TextView) hashelements.get("publishDay")).setText(dateFormatter.format(currentday.getTime()));
                        else
                            ((TextView) hashelements.get("publishDay")).setText(dateFormatter.format(publishday.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(currentday.getTimeInMillis());
                newCalendar.set(currentday.get(Calendar.YEAR), currentday.get(Calendar.MONTH), currentday.get(Calendar.DAY_OF_MONTH) + 42);
                fromDatePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
                fromDatePickerDialog.show();
            }
        });
        ((TextView) hashelements.get("publishDay")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date;
                try {
                    date = dateFormatter.parse(((TextView) hashelements.get("publishDay")).getText().toString());
                } catch (ParseException e) {
                    return;
                }
                newCalendar.setTime(date);
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        ((TextView) hashelements.get("publishDay")).setText(dateFormatter.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                Calendar currentday = Calendar.getInstance();
                Calendar marketday = Calendar.getInstance();
                try {
                    date = dateFormatter.parse(((TextView) hashelements.get("marketDay")).getText().toString());
                } catch (ParseException e) {
                    return;
                }
                marketday.setTime(date);
                fromDatePickerDialog.getDatePicker().setMaxDate(marketday.getTimeInMillis());
                Calendar publishday = Calendar.getInstance();
                publishday.set(newCalendar.get(marketday.YEAR), marketday.get(Calendar.MONTH), marketday.get(Calendar.DAY_OF_MONTH) - 2);
                if (currentday.getTimeInMillis() > publishday.getTimeInMillis())
                    fromDatePickerDialog.getDatePicker().setMinDate(currentday.getTimeInMillis());
                else
                    fromDatePickerDialog.getDatePicker().setMinDate(publishday.getTimeInMillis());

                fromDatePickerDialog.show();
            }
        });
    }

    public static class savepostListener extends ClickOnceListener {
        public savepostListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            String price_market_userindex_id = ((SpinnerElement) (((Spinner) hashelements.get("marketSpinner")).getSelectedItem())).getValue();
            String price_product_userindex_id = ((SpinnerElement) (((Spinner) hashelements.get("productSpinner")).getSelectedItem())).getValue();
            String price_productunit_name = ((AutoCompleteTextView) hashelements.get("unitInput")).getText().toString();
            String price_price = ((EditText) hashelements.get("priceInput")).getText().toString();
            String template = "";
            if (((CheckBox) hashelements.get("postCheckbox")).isChecked())
                template = "yes";
            boolean flag = true;
            TextView errortv = ((TextView) hashelements.get("newposterrorView"));
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
            if (price_market_userindex_id.equals("")) {
                errortv.setText("Please Select a Market");
                flag = false;
            } else if (price_product_userindex_id.equals("")) {
                errortv.setText("Please Select a Product");
                flag = false;
            } else if (price_price == null || price_price.equals("")) {
                errortv.setText("Please Input Price");
                flag = false;
            } else if (price_productunit_name == null || price_productunit_name.equals("")) {
                errortv.setText("Please Input Unit");
                flag = false;
            }
            try {
                float pricefloat = Float.parseFloat(price_price);
                price_price = String.format("%.2f", pricefloat);
            } catch (NumberFormatException e) {
                errortv.setText("Please Input Price");
                flag = false;
            }

            try {
                Date date1 = dateFormatter.parse(((TextView) hashelements.get("marketDay")).getText().toString());

            } catch (ParseException e) {
                errortv.setText("Please Select a Market Day");
                flag = false;
            }
            try {
                Date date2 = dateFormatter.parse(((TextView) hashelements.get("publishDay")).getText().toString());

            } catch (ParseException e) {
                errortv.setText("Please Select a Publish Day");
                flag = false;
            }

            if (flag) {
                //Build Post Data
                Hashtable<String, String> postdataht = new Hashtable<String, String>();
                postdataht.put("price_market_userindex_id", price_market_userindex_id);
                postdataht.put("price_product_userindex_id", price_product_userindex_id);
                postdataht.put("price_productunit_name", price_productunit_name);
                postdataht.put("price_price", price_price);
                postdataht.put("task", "add");
                postdataht.put("price_template", template);
                postdataht.put("price_market_date", ((TextView) hashelements.get("marketDay")).getText().toString());
                postdataht.put("price_publish_date", ((TextView) hashelements.get("publishDay")).getText().toString());
                postdataht.put("price_ad_desc", ((EditText) hashelements.get("descInput")).getText().toString());
                postdataht.put("price_publish_date", ((TextView) hashelements.get("publishDay")).getText().toString());
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
                        ((TextView) hashelements.get("newposterrorView")).setText("Success!");

                        removeRecentPage();
                        Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    @Override
                    protected void executeFailed (JSONObject result) throws JSONException {
                        enableButton();
                    }
                }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_save_postform", ht));
            }
            else
                enableButton();
        }
    }
}
