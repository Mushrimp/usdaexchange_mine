package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
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
public class page_122_preferproduct_addnew extends PageOperations {
    public static void searchpreproduct() {

        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
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

                Hashtable<String, String> ht1 = new Hashtable<String, String>();
                ht1.put("catetoryid", "");
                ht1.put("term", "");
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        TableLayout tl = (TableLayout) hashelements.get("productScrollTable");
                        tl.removeAllViews();
                        JSONArray allproducts = result.getJSONArray("results");
                        for (int i = 0; i < allproducts.length(); i++) {
                            JSONObject products = allproducts.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                            LinearLayout ll = new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Name
                            TextView name = new TextView(context);
                            name.setText(products.getString("label"));
                            name.setTextAppearance(context, R.style.Normal);
                            name.setTextSize(width / 45);
                            ll.addView(name);

                            //
                            TextView br = new TextView(context);
                            ll.addView(br);

                            lv.addView(ll);

                            final String proname = products.getString("label");
                            final String catg = "";
                            lv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addpreproduct(catg,proname);
                                }
                            });

                            tl.addView(lv);
                            TableRow lk = new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider = new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                            View rdivider = new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }

                        setupUI(playout);

                    }

                }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht1));

            }

        }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));

        ((Spinner) hashelements.get("categorySpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    final SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                    Hashtable<String, String> ht = new Hashtable<String, String>();
                    ht.put("term", "");
                    ht.put("catetoryid", selecteditem.getValue());
                    new FetchTask() {
                        @Override
                        protected void executeSuccess(JSONObject result) throws JSONException {
                            JSONArray allproducts = result.getJSONArray("results");
                            String[] arrayString = new String[allproducts.length()];
                            TableLayout tl = (TableLayout) hashelements.get("productScrollTable");
                            tl.removeAllViews();
                            for (int i = 0; i < allproducts.length(); i++) {
                                TableRow lv = new TableRow(context);
                                lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                JSONObject products = allproducts.getJSONObject(i);
                                arrayString[i] = products.getString("label");

                                LinearLayout ll = new LinearLayout(context);
                                ll.setOrientation(LinearLayout.VERTICAL);
                                //Name
                                TextView name = new TextView(context);
                                name.setText(arrayString[i]);
                                name.setTextAppearance(context, R.style.Normal);
                                name.setTextSize(width / 45);
                                ll.addView(name);

                                //
                                TextView br = new TextView(context);
                                ll.addView(br);

                                lv.addView(ll);
//
                                final String proname = products.getString("label");
                                final String catg = selecteditem.getValue();
                                lv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addpreproduct(catg,proname);
                                    }
                                });

                                tl.addView(lv);

                                TableRow lk = new TableRow(context);
                                lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                View ldivider = new LinearLayout(context);
                                ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                                ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                                View rdivider = new LinearLayout(context);
                                rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                                rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                                lk.addView(ldivider);
                                lk.addView(rdivider);
                                tl.addView(lk);

                            }
                            setupUI(playout);

                        }

                    }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht));


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    public static void searchpreproduct(String name) {
        final String pn = name;
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
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

                Hashtable<String, String> ht1 = new Hashtable<String, String>();
                ht1.put("catetoryid", "");
                ht1.put("term", pn);
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        TableLayout tl = (TableLayout) hashelements.get("productScrollTable");
                        tl.removeAllViews();
                        JSONArray allproducts = result.getJSONArray("results");
                        for (int i = 0; i < allproducts.length(); i++) {
                            JSONObject products = allproducts.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                            LinearLayout ll = new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Name
                            TextView name = new TextView(context);
                            name.setText(products.getString("label"));
                            name.setTextAppearance(context, R.style.Normal);
                            name.setTextSize(width / 45);
                            ll.addView(name);

                            //
                            TextView br = new TextView(context);
                            ll.addView(br);

                            lv.addView(ll);

                            final String proname = products.getString("label");
                            final String catg = "";
                            lv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addpreproduct(catg,proname);
                                }
                            });

                            tl.addView(lv);
                            TableRow lk = new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider = new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                            View rdivider = new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }

                        setupUI(playout);

                    }

                }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht1));

            }

        }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));

        ((Spinner) hashelements.get("categorySpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    final SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                    Hashtable<String, String> ht = new Hashtable<String, String>();
                    ht.put("term", pn);
                    ht.put("catetoryid", selecteditem.getValue());
                    final String catid = selecteditem.getValue();
                    new FetchTask() {
                        @Override
                        protected void executeSuccess(JSONObject result) throws JSONException {
                            JSONArray allproducts = result.getJSONArray("results");
                            String[] arrayString = new String[allproducts.length()];
                            TableLayout tl = (TableLayout) hashelements.get("productScrollTable");
                            tl.removeAllViews();
                            for (int i = 0; i < allproducts.length(); i++) {
                                TableRow lv = new TableRow(context);
                                lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                JSONObject products = allproducts.getJSONObject(i);
                                arrayString[i] = products.getString("label");

                                LinearLayout ll = new LinearLayout(context);
                                ll.setOrientation(LinearLayout.VERTICAL);
                                //Name
                                TextView name = new TextView(context);
                                name.setText(arrayString[i]);
                                name.setTextAppearance(context, R.style.Normal);
                                name.setTextSize(width / 45);
                                ll.addView(name);

                                //
                                TextView br = new TextView(context);
                                ll.addView(br);

                                lv.addView(ll);

                                final String proname = products.getString("label");
                                final String catg = selecteditem.getValue();
                                lv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addpreproduct(catg,proname);
                                    }
                                });

                                tl.addView(lv);

                                TableRow lk = new TableRow(context);
                                lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                View ldivider = new LinearLayout(context);
                                ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                                ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                                View rdivider = new LinearLayout(context);
                                rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                                rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                                lk.addView(ldivider);
                                lk.addView(rdivider);
                                tl.addView(lk);

                            }
                            setupUI(playout);

                        }

                    }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht));


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private static void addpreproduct(String catgid, String proname) {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("token", token_s);
        ht.put("os", "Android");
        ht.put("otherdata", catgid);
        ht.put("productname", proname);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                String error = result.getString("error");
                if (error.equals("-9")) {
                    pushNewPage(R.array.page_122_preferproduct, null);
                    setupUI(playout);
                }else {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_product_list_add_to", ht));

    }
}

