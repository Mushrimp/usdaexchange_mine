package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivilegedActionException;
import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_016_vendorpage extends PageOperations{
    public static void showVendorpage(String name) {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("vendorname", name);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("vendorpageScrollTable");
                tl.removeAllViews();
                JSONObject vendorprofile = result.getJSONObject("vendorprofile");
                TableRow lv = new TableRow(context);
                lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.VERTICAL);

                //Business Name
                TextView pn = new TextView(context);
                pn.setTextAppearance(context, R.style.Bold);
                pn.setTextSize(width / 40);
                pn.setText(vendorprofile.getString("business_name"));
                pn.setGravity(Gravity.CENTER);
                ll.addView(pn);
                //
                TextView br1 = new TextView(context);
                br1.setText("");
                ll.addView(br1);

                //TableLayout
                TableLayout tl_in = new TableLayout(context);
                //Organic
                TableRow tr1 = new TableRow(context);
                TextView org1 = new TextView(context);
                org1.setLayoutParams(new TableRow.LayoutParams((int) (width * 3 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                org1.setTextAppearance(context, R.style.Bold);
                org1.setTextSize(width / 45);
                org1.setText("Organic:");
                tr1.addView(org1);
                LinearLayout ll_in = new LinearLayout(context);
                ImageView org = new ImageView(context);
                org.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                JSONArray organic = vendorprofile.getJSONArray("business_usdaorganic");
                if (organic.getString(0).equals("yes")) {
                    org.setImageResource(R.drawable.usda_organic);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(50, 50);
                    parms.gravity = Gravity.START;
                    org.setLayoutParams(parms);
                }
                ll_in.addView(org);
                tr1.addView(ll_in);
                tl_in.addView(tr1);
                //Phone
                TableRow tr2 = new TableRow(context);
                TextView phone1 = new TextView(context);
                phone1.setTextAppearance(context, R.style.Bold);
                phone1.setTextSize(width / 45);
                phone1.setText("Phone:");
                tr2.addView(phone1);
                TextView phone = new TextView(context);
                phone.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                phone.setTextAppearance(context, R.style.Normal);
                phone.setTextSize(width / 45);
                phone.setText(vendorprofile.getString("business_phone"));
                tr2.addView(phone);
                tl_in.addView(tr2);
                //Email
                TableRow tr3 = new TableRow(context);
                TextView email1 = new TextView(context);
                email1.setTextAppearance(context, R.style.Bold);
                email1.setTextSize(width / 45);
                email1.setText("Email:");
                tr3.addView(email1);
                TextView email = new TextView(context);
                email.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                email.setTextAppearance(context, R.style.Normal);
                email.setTextSize(width / 45);
                email.setText(vendorprofile.getString("business_email"));
                tr3.addView(email);
                tl_in.addView(tr3);
                //Address
                TableRow tr4 = new TableRow(context);
                TextView address1 = new TextView(context);
                address1.setTextAppearance(context, R.style.Bold);
                address1.setTextSize(width / 45);
                address1.setText("Address:");
                tr4.addView(address1);
                TextView address = new TextView(context);
                address.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                address.setTextAppearance(context, R.style.Normal);
                address.setTextSize(width / 45);
                address.setText(vendorprofile.getString("business_street") + vendorprofile.getString("business_street") + ", "
                        + vendorprofile.getString("business_city") + ", " + vendorprofile.getString("business_state") + ", " + vendorprofile.getString("business_zip"));
                tr4.addView(address);
                tl_in.addView(tr4);

                ll.addView(tl_in);

                //
                TextView br2 = new TextView(context);
                br2.setText("");
                ll.addView(br2);

                RelativeLayout ll_bt = new RelativeLayout(context);
                RelativeLayout.LayoutParams lparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (width / 10));
                ll_bt.setLayoutParams(lparam);
                //Request Friend
                Button rf = new Button(context);
                RelativeLayout.LayoutParams rfparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rfparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rf.setLayoutParams(rfparams);
                rf.setBackgroundColor(Color.parseColor("#A2D25A"));
                rf.setTextAppearance(context, R.style.Date);
                rf.setTextSize(width / 55);
                rf.setText("Request friend");
                ll_bt.addView(rf);
                //Add to list
                Button al = new Button(context);
                RelativeLayout.LayoutParams alparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                alparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                al.setLayoutParams(alparams);
                al.setBackgroundColor(Color.parseColor("#A2D25A"));
                al.setTextAppearance(context, R.style.Date);
                al.setTextSize(width / 55);
                al.setText("Add to list");
                ll_bt.addView(al);

                ll.addView(ll_bt);

                //
                TextView br3 = new TextView(context);
                br3.setText("");
                ll.addView(br3);

                ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                lv.addView(ll);
                tl.addView(lv);
                setupUI(playout);

            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_vendorprofile_bypost", ht));
    }
}
