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
public class page_328_postschedule extends PageOperations {
    public static void showschedule() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ht.put("posttype", "scheduled");
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("postscheduleScrollTable");
                tl.removeAllViews();
                JSONArray allschedule = result.getJSONArray("results");
                for (int i = 0; i < allschedule.length(); i++) {
                    JSONObject schedule = allschedule.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Price Product Name
                    TextView nameprice = new TextView(context);
                    nameprice.setTextAppearance(context, R.style.Bold);
                    nameprice.setTextSize(width / 35);
                    String name = schedule.getString("price_product_name") + " $" + schedule.getString("price_price") + " per " + schedule.getString("price_productunit_name");
                    nameprice.setText(name);
                    ll.addView(nameprice);
                    //Market Date
                    LinearLayout lh1 = new LinearLayout(context);
                    lh1.setOrientation(LinearLayout.HORIZONTAL);
                    TextView marketdatet = new TextView(context);
                    marketdatet.setTextAppearance(context, R.style.Title);
                    marketdatet.setTextSize(width / 45);
                    String mdt = "Date:   ";
                    marketdatet.setText(mdt);
                    lh1.addView(marketdatet);
                    TextView marketdate = new TextView(context);
                    marketdate.setTextAppearance(context, R.style.Body);
                    marketdate.setTextSize(width / 45);
                    String md = schedule.getString("price_market_date");
                    marketdate.setText(md);
                    lh1.addView(marketdate);
                    ll.addView(lh1);
                    //Published Date
                    LinearLayout lh2 = new LinearLayout(context);
                    lh2.setOrientation(LinearLayout.HORIZONTAL);
                    TextView publisheddatet = new TextView(context);
                    publisheddatet.setTextAppearance(context, R.style.Title);
                    publisheddatet.setTextSize(width / 45);
                    String pdt = "Published On:   ";
                    publisheddatet.setText(pdt);
                    lh2.addView(publisheddatet);
                    TextView publisheddate = new TextView(context);
                    publisheddate.setTextAppearance(context, R.style.Body);
                    publisheddate.setTextSize(width / 45);
                    String pd = schedule.getString("price_publish_date");
                    publisheddate.setText(pd);
                    lh2.addView(publisheddate);
                    ll.addView(lh2);
                    //Market Name
                    TextView marketname = new TextView(context);
                    marketname.setTextAppearance(context, R.style.Body);
                    marketname.setTextSize(width / 45);
                    String mn = "@ " + schedule.getString("price_market_name");
                    marketname.setText(mn);
                    ll.addView(marketname);

                    //
                    TextView br = new TextView(context);
                    br.setText("");
                    ll.addView(br);

                    ll.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), TableRow.LayoutParams.WRAP_CONTENT));
                    lv.addView(ll);
                    lv.setBackgroundResource(R.drawable.tablerow_style);
                    tl.addView(lv);
                    lv.setOnLongClickListener(new removepostListener(context,"Delete a post","Do you want to remove this post?",lv,tl,schedule.getString("ID")));
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
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_byuser", ht));

    }
    public static class removepostListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String postid;
        public removepostListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String postidi)
        {
            super(contexti, titlei, messagei);
            deletetablerow=tr;
            fromtablelayout=tl;
            postid=postidi;
        }
        @Override
        public void deleteaction(){
            String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("post_id", postid);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    fromtablelayout.removeView(deletetablerow);
                }
            }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_remove", ht));

        }
    }
}
