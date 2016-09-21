package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.SpinnerElement;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by xianan on 8/3/16.
 */
public class page_123_prefervendor_addnew extends PageOperations {
//    public static void searchprevendor() {
//
//        Hashtable<String, String> ht = new Hashtable<String, String>();
//        String token_s = UserFileUtility.get_token();
//        ht.put("os", "Android");
//        ht.put("token", token_s);
//        ht.put("search", "");
//        ht.put("lstate", "");
//
//                int length= AppCodeResources.state_list.size();
//                SpinnerElement[] arraySpinner = new SpinnerElement[length];
//                arraySpinner[0] = new SpinnerElement("Select a State", "0");
//                for(int i=0;i<length;i++)
//                {
//                    arraySpinner[i] = new SpinnerElement(AppCodeResources.state_list.get(i).getName(),AppCodeResources.state_list.get(i).getValue());
//                }
//                ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
//                        R.layout.simple_spinner_item, arraySpinner);
//                ((Spinner) hashelements.get("vendorstateSpinner")).setAdapter(adapter);
//
//                setupUI(playout);
//
//
//
//
//        ((Spinner) hashelements.get("vendorstateSpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i != 0) {
//                    searchvendor("");
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//
//
//    }


    public static void searchvendor(final String name) {
        final String vn = name;
//        String selecteditem = ((SpinnerElement) (((Spinner) hashelements.get("vendorstateSpinner")).getSelectedItem())).getName();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("search", vn);
//        ht.put("lstate", selecteditem);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {

                JSONArray allvendors = result.getJSONArray("results");
                TableLayout tl = (TableLayout) hashelements.get("vendorScrollTable");
                tl.removeAllViews();
                for (int i = 0; i < allvendors.length(); i++) {
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    JSONObject vendors = allvendors.getJSONObject(i);

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), TableRow.LayoutParams.WRAP_CONTENT));

                    //Name
                    TextView name1 = new TextView(context);
                    name1.setText("Vendor name:");
                    name1.setTextAppearance(context, R.style.Title);
                    name1.setTextSize(width / 50);
                    ll.addView(name1);

                    TextView name = new TextView(context);
                    name.setText(vendors.getString("displayname"));
                    name.setTextAppearance(context, R.style.Bold);
                    name.setTextSize(width / 40);
                    ll.addView(name);

                    //Address
                    TextView address1 = new TextView(context);
                    address1.setText("Address:");
                    address1.setTextAppearance(context, R.style.Title);
                    address1.setTextSize(width / 50);
                    ll.addView(address1);

                    TextView address = new TextView(context);
                    address.setText(vendors.getString("address"));
                    address.setTextAppearance(context, R.style.Body);
                    address.setTextSize(width / 45);
                    ll.addView(address);

                    //
                    TextView br = new TextView(context);
                    ll.addView(br);

                    lv.addView(ll);
                    lv.setBackgroundResource(R.drawable.tablerow_style);
                    final String vendorusername=vendors.getString("vendorusername");
                    lv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addtovendor(vendorusername);
                        }
                    });
//
//                                final String proname = products.getString("label");
//                                final String catg = selecteditem.getValue();
//                                lv.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        addpreproduct(catg,proname);
//                                    }
//                                });

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

        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_vendor_list_search_allvendor", ht));
    }

    public static void addtovendor(String vendorusername)
    {
        Hashtable<String,String> ht=new Hashtable<>();
        String token_s = UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("vendorusername",vendorusername);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                removeRecentPage();
                Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_vendor_list_add_to", ht));
    }
}