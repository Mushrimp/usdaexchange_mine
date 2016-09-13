package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

                //Vendor name
                TextView vn = new TextView(context);
                vn.setTextAppearance(context, R.style.Green);
                vn.setTextSize(width / 50);
                vn.setText(postView.getString("price_vendorname"));
                vn.setPadding(0,20,0,0);
                ll.addView(vn);

                //Product Name
                TextView pn = new TextView(context);
                pn.setTextAppearance(context, R.style.Normal);
                pn.setTextSize(width / 33);
                pn.setText(postView.getString("price_product_name"));
                ll.addView(pn);

//                //Vendorname
//                LinearLayout ll1 = new LinearLayout(context);
//                ll1.setOrientation(TableLayout.HORIZONTAL);
//                TextView vn1 = new TextView(context);
//                vn1.setTextAppearance(context, R.style.ItalicBold);
//                vn1.setTextSize(width / 50);
//                vn1.setText("By  ");
//                ll1.addView(vn1);
//                TextView vn = new TextView(context);
//                vn.setTextAppearance(context, R.style.Normal);
//                vn.setTextSize(width / 50);
//                vn.setText(postView.getString("price_vendorname"));
//                ll1.setPadding(0,20,0,0);
//                ll1.addView(vn);
//                ll.addView(ll1);

                //
                TextView br = new TextView(context);
                br.setText("");
                ll.addView(br);

                TableLayout tl_up = new TableLayout(context);
                tl_up.setPadding(0,10,0,0);
                //Price
                TableRow up_tr1 = new TableRow(context);
                up_tr1.setPadding(0,0,0,10);
                TextView price1 = new TextView(context);
                price1.setGravity(Gravity.RIGHT);
                price1.setTextAppearance(context, R.style.Body);
                price1.setTextSize(width / 48);
                price1.setText(R.string.l_020_FullPost_Price_Label_0);
                up_tr1.addView(price1);
                TextView price = new TextView(context);
                price.setPadding(40,0,0,0);
                price.setTextAppearance(context, R.style.Price);
                price.setTextSize(width / 40);
                price.setText("$" + postView.getString("price_price"));
                up_tr1.addView(price);
                tl_up.addView(up_tr1);

                //Unit
                TableRow up_tr2 = new TableRow(context);
                up_tr2.setPadding(0,10,0,10);
                TextView unit1 = new TextView(context);
                unit1.setGravity(Gravity.RIGHT);
                unit1.setTextAppearance(context, R.style.Body);
                unit1.setTextSize(width / 48);
                unit1.setText(R.string.l_020_FullPost_Unit_Label_0);
                up_tr2.addView(unit1);
                TextView unit = new TextView(context);
                unit.setPadding(40,0,0,0);
                unit.setTextAppearance(context, R.style.Normal);
                unit.setTextSize(width / 40);
                unit.setText(postView.getString("price_productunit_name"));
                up_tr2.addView(unit);
                tl_up.addView(up_tr2);

                //Market Date
                TableRow up_tr3 = new TableRow(context);
                up_tr3.setPadding(0,10,0,10);
                TextView date1 = new TextView(context);
                date1.setGravity(Gravity.RIGHT);
                date1.setTextAppearance(context, R.style.Body);
                date1.setTextSize(width / 48);
                date1.setText(R.string.l_020_FullPost_Organic_Label_1);
                up_tr3.addView(date1);
                TextView date = new TextView(context);
                date.setPadding(40,0,0,0);
                date.setTextAppearance(context, R.style.Normal);
                date.setTextSize(width / 40);
                date.setText(postView.getString("price_market_date"));
                up_tr3.addView(date);
                tl_up.addView(up_tr3);

                //Location
                TableRow tr8 = new TableRow(context);
                tr8.setPadding(0,10,0,10);
                TextView location1 = new TextView(context);
                location1.setGravity(Gravity.RIGHT);
                location1.setTextAppearance(context, R.style.Body);
                location1.setTextSize(width / 48);
                location1.setText(R.string.l_020_FullPost_Location_Label_0);
                tr8.addView(location1);
                TextView location = new TextView(context);
                location.setPadding(40,0,0,0);
                location.setLayoutParams(new TableRow.LayoutParams((int) (width * 0.7), TableLayout.LayoutParams.WRAP_CONTENT));
                location.setTextAppearance(context, R.style.Normal);
                location.setTextSize(width / 40);
                location.setText(postView.getString("price_street") + "," + postView.getString("price_city") + ","
                        + postView.getString("price_state") + "," + postView.getString("price_zipcode"));
                tr8.addView(location);
                tl_up.addView(tr8);

                ll.addView(tl_up);

                //
                TextView br2 = new TextView(context);
                br2.setText("");
                //br2.setPadding(0,0,0,10);
                ll.addView(br2);

                //Description
                TextView des = new TextView(context);
                des.setTextAppearance(context, R.style.Green);
                des.setTextSize(width / 35);
                des.setText("About this item");
                ll.addView(des);

                //TableLayout
                TableLayout tl_in = new TableLayout(context);
                tl_in.setPadding(0,20,0,80);
                //Category
                TableRow tr1 = new TableRow(context);
                tr1.setPadding(30,10,0,10);
                TextView category1 = new TextView(context);
                category1.setGravity(Gravity.RIGHT);
                category1.setTextAppearance(context, R.style.Body);
                category1.setTextSize(width / 48);
                category1.setText("Category:");
                //category1.setLayoutParams(new TableRow.LayoutParams((int) (width * 0.3), TableRow.LayoutParams.WRAP_CONTENT));
                tr1.addView(category1);
                TextView category = new TextView(context);
                category.setPadding(40,0,0,0);
                category.setLayoutParams(new TableRow.LayoutParams((int) (width * 0.7), TableRow.LayoutParams.WRAP_CONTENT));
                category.setTextAppearance(context, R.style.Normal);
                category.setTextSize(width / 40);
                category.setText(postView.getString("price_Prd_Category1"));
                tr1.addView(category);
                tl_in.addView(tr1);
                //Organic
                TableRow tr2 = new TableRow(context);
                tr2.setPadding(30,10,0,10);
                TextView org1 = new TextView(context);
                org1.setGravity(Gravity.RIGHT);
                //org1.setLayoutParams(new TableRow.LayoutParams((int) (width * 3 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                org1.setTextAppearance(context, R.style.Body);
                org1.setTextSize(width / 48);
                org1.setText(R.string.l_020_FullPost_Organic_Label_0);
                tr2.addView(org1);
                LinearLayout ll_in = new LinearLayout(context);
                ImageView org = new ImageView(context);
                org.setPadding(40,0,0,10);
                //org.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                String organic = postView.getString("price_usdaorganic");
                if (organic.equals("yes")) {
                    org.setImageResource(R.drawable.usda_organic);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(80, 80);
                    parms.gravity = Gravity.START;
                    org.setLayoutParams(parms);
                }
                ll_in.addView(org);
                tr2.addView(ll_in);
                tl_in.addView(tr2);
                //Message
                TableRow tr3 = new TableRow(context);
                tr3.setPadding(30,10,0,30);
                TextView des1 = new TextView(context);
                des1.setGravity(Gravity.RIGHT);
                des1.setTextAppearance(context, R.style.Body);
                des1.setTextSize(width / 48);
                des1.setText(R.string.l_020_FullPost_Message_Label_0);
                tr3.addView(des1);
                TextView desc = new TextView(context);
                desc.setPadding(40,0,0,0);
                desc.setLayoutParams(new TableRow.LayoutParams((int) (width * 7 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                desc.setTextSize(width / 40);
                desc.setTextAppearance(context, R.style.Normal);
                desc.setText(postView.getString("price_ad_desc"));
                tr3.addView(desc);
                tl_in.addView(tr3);


                //Vendor
                TableRow tr4 = new TableRow(context);
                tr4.setBackgroundResource(R.drawable.row_border);
                tr4.setPadding(30,20,0,20);
                TextView vendor1 = new TextView(context);
                vendor1.setGravity(Gravity.RIGHT);
                //vendor1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                vendor1.setTextAppearance(context, R.style.Body);
                vendor1.setTextSize(width / 48);
                vendor1.setText(R.string.l_020_FullPost_Vendor_Label_0);
                tr4.addView(vendor1);
                RelativeLayout ll_arrow = new RelativeLayout(context);
                TextView vendor = new TextView(context);
                vendor.setMaxLines(1);
                vendor.setEllipsize(TextUtils.TruncateAt.END);
                vendor.setHorizontallyScrolling(true);
                vendor.setPadding(40,0,0,0);
                vendor.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                vendor.setTextAppearance(context, R.style.DarkBule);
                vendor.setTextSize(width / 40);
                vendor.setText(postView.getString("price_vendorname"));
                ll_arrow.addView(vendor);
                ImageView i1= new ImageView(context);
                i1.setImageResource(R.drawable.next_item);
                RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams(50, 50);
                parms1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                i1.setLayoutParams(parms1);
                ll_arrow.addView(i1);
                tr4.addView(ll_arrow);
                tl_in.addView(tr4);
                //Vendor detail
//                TableRow tr5 = new TableRow(context);
//                tr5.setPadding(0,10,0,20);
//                TableRow.LayoutParams params = new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT);
//                TextView bl = new TextView(context);
//                tr5.addView(bl);
//                TextView vd = new TextView(context);
//                vd.setText("More Details...");
//                vd.setLayoutParams(params);
//                vd.setTextAppearance(context, R.style.Date);
//                vd.setTextSize(width / 50);
//                vd.setGravity(Gravity.RIGHT);
//                tr5.addView(vd);
//                tl_in.addView(tr5);

                String username = postView.getString("price_vendorusername");
                tr4.setOnClickListener(new showVendorListener(tr4,username));

                //Market name
                TableRow tr6 = new TableRow(context);
                tr6.setBackgroundResource(R.drawable.row_border);
                tr6.setPadding(30,20,0,20);
                TextView mn1 = new TextView(context);
                mn1.setGravity(Gravity.RIGHT);
                mn1.setTextAppearance(context, R.style.Body);
                mn1.setTextSize(width / 48);
                mn1.setText(R.string.l_020_FullPost_Market_Label_0);
                tr6.addView(mn1);
                TableRow.LayoutParams tparams = new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT);
                RelativeLayout ll_arrow2 = new RelativeLayout(context);
                TextView mn = new TextView(context);
                mn.setMaxLines(1);
                mn.setEllipsize(TextUtils.TruncateAt.END);
                mn.setHorizontallyScrolling(true);
                mn.setPadding(40,0,0,0);
                mn.setLayoutParams(tparams);
                mn.setTextAppearance(context, R.style.DarkBule);
                mn.setTextSize(width / 40);
                mn.setText(postView.getString("price_market_name"));
                ll_arrow2.addView(mn);
                ImageView i2= new ImageView(context);
                i2.setImageResource(R.drawable.next_item);
                RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(50, 50);
                parms2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                i2.setLayoutParams(parms2);
                ll_arrow2.addView(i2);
                tr6.addView(ll_arrow2);
                tl_in.addView(tr6);
                //Market detail
//                TableRow tr7 = new TableRow(context);
//                tr7.setPadding(0,10,0,20);
//                TextView bl2 = new TextView(context);
//                tr7.addView(bl2);
//                TextView md = new TextView(context);
//                md.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableLayout.LayoutParams.WRAP_CONTENT));
//                md.setText("More Details...");
//                md.setTextAppearance(context, R.style.Date);
//                md.setTextSize(width / 50);
//                md.setGravity(Gravity.RIGHT);
//                tr7.addView(md);
//                tl_in.addView(tr7);

                final String marketid = postView.getString("price_fmid");
                tr6.setOnClickListener(new showMarketListener(tr6,marketid));

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
