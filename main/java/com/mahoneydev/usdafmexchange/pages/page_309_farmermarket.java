package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_309_farmermarket extends PageOperations {


    public static void showmarkets() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("farmermarketScrollTable");
                tl.removeAllViews();
                JSONArray allmarkets = result.getJSONArray("results");
                for (int i = 0; i < allmarkets.length(); i++) {
                    JSONObject market = allmarkets.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Market Name
                    TextView marketnamet = new TextView(context);
                    marketnamet.setTextAppearance(context, R.style.Title);
                    marketnamet.setTextSize(width / 50);
                    marketnamet.setText("Market Name:");
                    ll.addView(marketnamet);
                    TextView marketname = new TextView(context);
                    marketname.setTextAppearance(context, R.style.Bold);
                    marketname.setTextSize(width / 45);
                    marketname.setText(market.getString("MarketName"));
                    ll.addView(marketname);
                    //Market Location
                    TextView marketlocationt = new TextView(context);
                    marketlocationt.setTextAppearance(context, R.style.Title);
                    marketlocationt.setTextSize(width / 50);
                    marketlocationt.setText("Address:");
                    ll.addView(marketlocationt);
                    TextView marketlocation = new TextView(context);
                    marketlocation.setTextAppearance(context, R.style.Body);
                    marketlocation.setTextSize(width / 50);
                    marketlocation.setText(market.getString("Market_location"));
                    ll.addView(marketlocation);

                    //
                    TextView br = new TextView(context);
                    br.setText("");
                    ll.addView(br);

                    ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                    lv.addView(ll);
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
        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_markets_listall_byuser", ht));
    }
}
