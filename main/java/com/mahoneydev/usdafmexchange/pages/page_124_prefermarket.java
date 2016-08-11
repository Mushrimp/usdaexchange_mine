package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.Frontpage;
import com.mahoneydev.usdafmexchange.LongPressDeleteDialogListener;
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
public class page_124_prefermarket extends PageOperations {

    public static void showPreferMarket() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("prefermarketScrollTable");
                tl.removeAllViews();
                JSONArray allpremarket = result.getJSONArray("results");
                for (int i = 0; i < allpremarket.length(); i++) {
                    JSONObject premarket = allpremarket.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Display Name
                    TextView dn1 = new TextView(context);
                    dn1.setTextAppearance(context, R.style.Title);
                    dn1.setTextSize(width / 50);
                    String name1 = "Market name:";
                    dn1.setText(name1);
                    ll.addView(dn1);
                    TextView dn = new TextView(context);
                    dn.setTextAppearance(context, R.style.Bold);
                    dn.setTextSize(width / 45);
                    dn.setText(premarket.getString("displayname"));
                    ll.addView(dn);
                    //Address
                    TextView address1 = new TextView(context);
                    address1.setTextAppearance(context, R.style.Title);
                    address1.setTextSize(width / 50);
                    String an = "Address name:";
                    address1.setText(an);
                    ll.addView(address1);
                    TextView address = new TextView(context);
                    address.setTextAppearance(context, R.style.Body);
                    address.setTextSize(width / 45);
                    address.setText(premarket.getString("address"));
                    ll.addView(address);

                    //
                    TextView br = new TextView(context);
                    br.setText("");
                    ll.addView(br);

                    ll.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), TableLayout.LayoutParams.WRAP_CONTENT));
                    lv.addView(ll);
                    tl.addView(lv);
                    lv.setOnLongClickListener(new removeprefermarketListener(context,"Delete a market","Do you want to remove "+premarket.getString("displayname")+" from the list?",lv,tl,premarket.getString("ID")));

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
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_market_list_getlist", ht));
    }

    public static class removeprefermarketListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String marketid;
        public removeprefermarketListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String marketidi)
        {
            super(contexti, titlei, messagei);
            deletetablerow=tr;
            fromtablelayout=tl;
            marketid=marketidi;
        }
        @Override
        public void deleteaction(){
            String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("indexid", marketid);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    fromtablelayout.removeView(deletetablerow);
                }
            }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_market_list_remove_from", ht));

        }
    }





}
