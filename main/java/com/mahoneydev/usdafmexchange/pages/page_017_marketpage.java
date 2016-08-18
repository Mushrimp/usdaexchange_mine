package com.mahoneydev.usdafmexchange.pages;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
                mn.setTextSize(width / 33);
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
                address1.setTextSize(width / 48);
                address1.setText(res.getString(R.string.l_017_MarketMainpage_Address_Label_0));
                ll.addView(address1);
                TextView address = new TextView(context);
                address.setPadding(0,10,0,0);
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

                //Products
                TextView product1 = new TextView(context);
                product1.setTextAppearance(context, R.style.Title);
                product1.setTextSize(width / 48);
                product1.setText(res.getString(R.string.l_017_MarketMainpage_Products_Label_0));
                ll.addView(product1);
                TextView product = new TextView(context);
                product.setPadding(0,10,0,0);
                product.setTextAppearance(context, R.style.Normal);
                product.setTextSize(width / 45);
                product.setText(result.getString("productlist"));
                ll.addView(product);

                TextView br4 = new TextView(context);
                br4.setText("");
                ll.addView(br4);

                //Vendors
                TextView vendor1 = new TextView(context);
                vendor1.setTextAppearance(context, R.style.Title);
                vendor1.setTextSize(width / 48);
                vendor1.setText(res.getString(R.string.l_017_MarketMainpage_VendorPostPrice_Label_0));
                ll.addView(vendor1);

                ScrollView vendorscroll=new ScrollView(context);
                TableLayout vendorlinear=new TableLayout(context);
                JSONArray vendorarray=result.getJSONArray("vendorlist");
                for (int i=0;i<vendorarray.length();i++)
                {
                    TableRow tr=new TableRow(context);
                    TextView vendorinfo=new TextView(context);
                    vendorinfo.setTextAppearance(context, R.style.Normal);
                    vendorinfo.setTextSize(width / 45);
                    JSONObject vendor=vendorarray.getJSONObject(i);
                    vendorinfo.setText(vendor.getString("vendorbusinessname")+" ("+vendor.getString("vendorcity")+", "+vendor.getString("vendorstate")+")");
                    tr.addView(vendorinfo);
                    tr.setOnClickListener(new checkvendorListener(tr,vendor.getString("vendorusername")));
                    vendorlinear.addView(tr);
                }
                vendorscroll.addView(vendorlinear);

                ll.addView(vendorscroll);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_marketprofile_bypost", ht));
    }

    public static class  checkvendorListener extends ClickOnceListener {
        public String vendorname;
        public checkvendorListener(View button, String vendornamei) {
            super(button);
            vendorname=vendornamei;
        }
        @Override
        public void action() {
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("username",vendorname);
            pushNewPage(R.array.page_016_vendorpage,ht);
        }
    }
}
