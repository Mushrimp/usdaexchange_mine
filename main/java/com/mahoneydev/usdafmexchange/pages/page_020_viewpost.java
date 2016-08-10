package com.mahoneydev.usdafmexchange.pages;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_020_viewpost extends PageOperations {
    public static void showpostView() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("tablename", "usdafmexchange_publish.usda_price_post_public");
        ht.put("idname", "ID");
        ht.put("idvalue", getRecentPage().params.get("postid"));
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("viewpostScrollTable");
                tl.removeAllViews();
                JSONObject postView = result.getJSONObject("results");
                TableRow lv = new TableRow(context);
                lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.VERTICAL);

                //Product Name
                TextView pn = new TextView(context);
                pn.setTextAppearance(context, R.style.Bold);
                pn.setTextSize(width / 50);
                pn.setText(postView.getString("price_product_name"));
                ll.addView(pn);
                //Vendorname
                LinearLayout ll1 = new LinearLayout(context);
                ll1.setOrientation(TableLayout.HORIZONTAL);
                TextView vn1 = new TextView(context);
                vn1.setTextAppearance(context, R.style.Bold);
                vn1.setTextSize(width / 50);
                vn1.setText("By  ");
                ll1.addView(vn1);
                TextView vn = new TextView(context);
                vn.setTextAppearance(context, R.style.Normal);
                vn.setTextSize(width / 50);
                vn.setText(postView.getString("price_vendorname"));
                ll1.addView(vn);
                ll.addView(ll1);

                //
                TextView br = new TextView(context);
                br.setText("");
                ll.addView(br);

                //Price
                LinearLayout ll2 = new LinearLayout(context);
                ll2.setOrientation(TableLayout.HORIZONTAL);
                TextView price1 = new TextView(context);
                price1.setTextAppearance(context, R.style.Normal);
                price1.setTextSize(width / 45);
                price1.setText("Price:   ");
                ll2.addView(price1);
                TextView price = new TextView(context);
                price.setTextAppearance(context, R.style.Price);
                price.setTextSize(width / 40);
                price.setText("$" + postView.getString("price_price"));
                ll2.addView(price);
                ll.addView(ll2);

                //Unit
                LinearLayout ll3 = new LinearLayout(context);
                ll3.setOrientation(TableLayout.HORIZONTAL);
                TextView unit1 = new TextView(context);
                unit1.setTextAppearance(context, R.style.Normal);
                unit1.setTextSize(width / 45);
                unit1.setText("Unit:     ");
                ll3.addView(unit1);
                TextView unit = new TextView(context);
                unit.setTextAppearance(context, R.style.Normal);
                unit.setTextSize(width / 45);
                unit.setText(postView.getString("price_productunit_name"));
                ll3.addView(unit);
                ll.addView(ll3);

                //Market Date
                LinearLayout ll4 = new LinearLayout(context);
                ll4.setOrientation(TableLayout.HORIZONTAL);
                TextView date1 = new TextView(context);
                date1.setTextAppearance(context, R.style.Normal);
                date1.setTextSize(width / 50);
                date1.setText("Market Date:     ");
                ll4.addView(date1);
                TextView date = new TextView(context);
                date.setTextAppearance(context, R.style.Normal);
                date.setTextSize(width / 50);
                date.setText(postView.getString("price_market_date"));
                ll4.addView(date);
                ll.addView(ll4);

                //
                TextView br2 = new TextView(context);
                br2.setText("");
                ll.addView(br2);

                //Description
                TextView des = new TextView(context);
                des.setTextAppearance(context, R.style.Bold);
                des.setTextSize(width / 35);
                des.setText("Description");
                ll.addView(des);

                //TableLayout
                TableLayout tl_in = new TableLayout(context);
                //Category
                TableRow tr1 = new TableRow(context);
                TextView category1 = new TextView(context);
                //category1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                category1.setTextAppearance(context, R.style.Bold);
                category1.setTextSize(width / 45);
                category1.setText("Category:");
                tr1.addView(category1);
                TextView category = new TextView(context);
                category.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                category.setTextAppearance(context, R.style.Normal);
                category.setTextSize(width / 45);
                category.setText(postView.getString("price_Prd_Category1"));
                tr1.addView(category);
                tl_in.addView(tr1);
                //Organic
                TableRow tr2 = new TableRow(context);
                TextView org1 = new TextView(context);
                org1.setLayoutParams(new TableRow.LayoutParams((int) (width * 3 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                org1.setTextAppearance(context, R.style.Bold);
                org1.setTextSize(width / 45);
                org1.setText("Organic:");
                tr2.addView(org1);
                LinearLayout ll_in = new LinearLayout(context);
                ImageView org = new ImageView(context);
                org.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                String organic = postView.getString("price_usdaorganic");
                if (organic.equals("yes")) {
                    org.setImageResource(R.drawable.usda_organic);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(50, 50);
                    parms.gravity = Gravity.START;
                    org.setLayoutParams(parms);
                }
                ll_in.addView(org);
                tr2.addView(ll_in);
                tl_in.addView(tr2);
                //Description
                TableRow tr3 = new TableRow(context);
                TextView des1 = new TextView(context);
                //des1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                des1.setTextAppearance(context, R.style.Bold);
                des1.setTextSize(width / 45);
                des1.setText("Description:");
                tr3.addView(des1);
                TextView desc = new TextView(context);
                desc.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                desc.setTextSize(width / 45);
                desc.setTextAppearance(context, R.style.Normal);
                desc.setText(postView.getString("price_ad_desc"));
                tr3.addView(desc);
                tl_in.addView(tr3);
                //Vendor
                TableRow tr4 = new TableRow(context);
                TextView vendor1 = new TextView(context);
                //vendor1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                vendor1.setTextAppearance(context, R.style.Bold);
                vendor1.setTextSize(width / 45);
                vendor1.setText("Vendor:");
                tr4.addView(vendor1);
                TextView vendor = new TextView(context);
                vendor.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                vendor.setTextAppearance(context, R.style.Normal);
                vendor.setTextSize(width / 45);
                vendor.setText(postView.getString("price_vendorname"));
                tr4.addView(vendor);
                tl_in.addView(tr4);
                //Vendor detail
                TableRow tr5 = new TableRow(context);
                TableRow.LayoutParams params = new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT);
                TextView bl = new TextView(context);
                tr5.addView(bl);
                TextView vd = new TextView(context);
                vd.setText("More Details...");
                vd.setLayoutParams(params);
                vd.setTextAppearance(context, R.style.Date);
                vd.setTextSize(width / 50);
                vd.setGravity(Gravity.RIGHT);
                tr5.addView(vd);
                tl_in.addView(tr5);

                String username = postView.getString("price_vendorusername");
                vd.setOnClickListener(new showVendorListener(vd,username));

                //Market name
                TableRow tr6 = new TableRow(context);
                TextView mn1 = new TextView(context);
                mn1.setTextAppearance(context, R.style.Bold);
                mn1.setTextSize(width / 45);
                mn1.setText("Market:");
                tr6.addView(mn1);
                TableRow.LayoutParams tparams = new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT);
                TextView mn = new TextView(context);
                mn.setLayoutParams(tparams);
                mn.setTextAppearance(context, R.style.Normal);
                mn.setTextSize(width / 45);
                mn.setText(postView.getString("price_market_name"));
                tr6.addView(mn);
                tl_in.addView(tr6);
                //Market detail
                TableRow tr7 = new TableRow(context);
                TextView bl2 = new TextView(context);
                tr7.addView(bl2);
                TextView md = new TextView(context);
                md.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableLayout.LayoutParams.WRAP_CONTENT));
                md.setText("More Details...");
                md.setTextAppearance(context, R.style.Date);
                md.setTextSize(width / 50);
                md.setGravity(Gravity.RIGHT);
                tr7.addView(md);
                tl_in.addView(tr7);

                final String marketid = postView.getString("price_fmid");
                md.setOnClickListener(new showMarketListener(md,marketid));

                //Location
                TableRow tr8 = new TableRow(context);
                TextView location1 = new TextView(context);
                location1.setTextAppearance(context, R.style.Bold);
                location1.setTextSize(width / 45);
                location1.setText("Location:");
                tr8.addView(location1);
                TextView location = new TextView(context);
                location.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableLayout.LayoutParams.WRAP_CONTENT));
                location.setTextAppearance(context, R.style.Normal);
                location.setTextSize(width / 45);
                location.setText(postView.getString("price_street") + "," + postView.getString("price_city") + ","
                        + postView.getString("price_state") + "," + postView.getString("price_zipcode"));
                tr8.addView(location);
                tl_in.addView(tr8);

                ll.addView(tl_in);

                ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                lv.addView(ll);
                tl.addView(lv);
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_table_by_id", ht));
    }
    public static class showVendorListener extends ClickOnceListener {
        private String username;
        public showVendorListener(View button, String iusername) {
            super(button);
            username=iusername;
        }

        @Override
        public void action() {
            Hashtable<String, String> para = new Hashtable<String, String>();
            para.put("username", username);
            pushNewPage(R.array.page_016_vendorpage, para);
        }
    }

    public static class showMarketListener extends ClickOnceListener {
        private String marketid;
        public showMarketListener(View button, String imarketid) {
            super(button);
            marketid=imarketid;
        }

        @Override
        public void action() {
            Hashtable<String, String> para = new Hashtable<String, String>();
            para.put("marketid", marketid);
            pushNewPage(R.array.page_017_marketpage, para);
        }
    }
}
