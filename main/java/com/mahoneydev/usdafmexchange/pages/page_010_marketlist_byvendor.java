package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
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
import java.util.StringTokenizer;

/**
 * Created by cuiyue on 8/16/2016.
 */
public class page_010_marketlist_byvendor extends PageOperations {
    public static void showmarkets() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("vendorname", getRecentPage().params.get("username"));
        ht.put("perpage","10");
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("marketpageScrollTable");
                tl.removeAllViews();
                JSONArray markets = result.getJSONArray("results");
                for (int i=0;i<markets.length();i++)
                {
                    JSONObject marketitem=markets.getJSONObject(i);
                    final TableRow lv=new TableRow(context);
                    LinearLayout tr=new LinearLayout(context);
                    LinearLayout ll1=new LinearLayout(context);
                    ll1.setOrientation(LinearLayout.HORIZONTAL);
                    TextView markettitle=new TextView(context);
                    markettitle.setTextAppearance(context, R.style.Bold);
                    markettitle.setTextSize(width / 45);
                    TextView marketname=new TextView(context);
                    marketname.setTextAppearance(context,R.style.Normal);
                    marketname.setTextSize(width / 45);
                    markettitle.setText(res.getString(R.string.l_010_MarketLocationList_Name_Label_0));
                    marketname.setText(marketitem.getString("price_market_name"));
                    ll1.addView(markettitle);
                    ll1.addView(marketname);

                    LinearLayout ll2=new LinearLayout(context);
                    ll2.setOrientation(LinearLayout.HORIZONTAL);
                    TextView addresstitle=new TextView(context);
                    addresstitle.setTextAppearance(context, R.style.Bold);
                    addresstitle.setTextSize(width / 45);
                    TextView addressname=new TextView(context);
                    addressname.setTextAppearance(context,R.style.Normal);
                    addressname.setTextSize(width / 45);
                    addresstitle.setText(res.getString(R.string.l_010_MarketLocationList_Address_Label_0));
                    addressname.setText(marketitem.getString("price_street")+", "+marketitem.getString("price_city")+", "+marketitem.getString("price_state"));
                    ll2.addView(addresstitle);
                    ll2.addView(addressname);

                    tr.setOrientation(LinearLayout.VERTICAL);
                    tr.addView(ll1);
                    tr.addView(ll2);
                    lv.addView(tr);
                    tl.addView(lv);
                    final String id=marketitem.getString("price_fmid");
                    lv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lv.setClickable(false);
                            Hashtable<String,String> ht=new Hashtable<String, String>();
                            ht.put("marketid",id);
                            pushNewPage(R.array.page_017_marketpage,ht);
                        }
                    });
                    TableRow lk=new TableRow(context);
                    View ldivider = new LinearLayout(context);
                    ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    ldivider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2,1));
                    lk.addView(ldivider);
                    tl.addView(lk);
                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_list_markets_byvendor", ht));
    }
}
