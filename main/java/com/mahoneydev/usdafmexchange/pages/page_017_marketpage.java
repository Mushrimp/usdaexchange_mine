package com.mahoneydev.usdafmexchange.pages;

import android.view.Gravity;
import android.widget.LinearLayout;
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

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class  page_017_marketpage extends PageOperations{
    public static void showMarketpage() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("fmid", getRecentPage().params.get("marketid"));
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("marketpageScrollTable");
                tl.removeAllViews();
                JSONArray allmarketprofile = result.getJSONArray("marketprofile");
                JSONObject marketprofile = allmarketprofile.getJSONObject(0);
                TableRow lv = new TableRow(context);
                lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.VERTICAL);

                //Market Name
                TextView mn = new TextView(context);
                mn.setTextAppearance(context, R.style.Bold);
                mn.setTextSize(width / 40);
                mn.setText(marketprofile.getString("price_market_name"));
                mn.setGravity(Gravity.CENTER);
                ll.addView(mn);
                //
                TextView br1 = new TextView(context);
                br1.setText("");
                ll.addView(br1);

                //Address
                TextView address1 = new TextView(context);
                address1.setTextAppearance(context, R.style.Title);
                address1.setTextSize(width / 50);
                address1.setText("Address:");
                ll.addView(address1);
                TextView address = new TextView(context);
                address.setTextAppearance(context, R.style.Normal);
                address.setTextSize(width / 45);
                address.setText(marketprofile.getString("price_street") + ", " + marketprofile.getString("price_city") + ", "
                        + marketprofile.getString("price_state") + ", " + marketprofile.getString("price_zipcode"));
                ll.addView(address);

                //
                TextView br3 = new TextView(context);
                br3.setText("");
                ll.addView(br3);

                ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                lv.addView(ll);
                tl.addView(lv);

                setupUI(playout);

            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_marketprofile_bypost", ht));
    }
}
