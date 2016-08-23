package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

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
                ll.setPadding(0,0,0,80);

                //Market Name
                TextView mn = new TextView(context);
                mn.setTextAppearance(context, R.style.Green);
                mn.setTextSize(width / 30);
                mn.setText(marketprofile.getString("price_market_name"));
                mn.setGravity(Gravity.START);
                ll.addView(mn);
                //
                TextView br1 = new TextView(context);
                br1.setText("");
                ll.addView(br1);

                //Button
                Button al = new Button(context);
                al.setLayoutParams(new LinearLayout.LayoutParams((int)(width / 3), (int)(height/18)));
                al.setBackgroundResource(R.drawable.button_style);
                al.setTextAppearance(context, R.style.White);
                al.setPadding(0,10,0,10);
                al.setTextSize(width / 50);
                al.setGravity(Gravity.CENTER);
                al.setText("Add to list");
                al.setTransformationMethod(null);

                al.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        String token_s = UserFileUtility.get_token();
                        ht.put("token", token_s);
                        ht.put("os", "Android");
                        ht.put("fmid", getRecentPage().params.get("marketid"));
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                removeRecentPage();
                                Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_market_list_add_to", ht));
                    }
                });

                ll.addView(al);

                //Address
                TextView address1 = new TextView(context);
                address1.setPadding(0,30,0,0);
                address1.setTextAppearance(context, R.style.Body);
                address1.setTextSize(width / 40);
                address1.setText(res.getString(R.string.l_017_MarketMainpage_Address_Label_0));
                ll.addView(address1);
                TextView address = new TextView(context);
                address.setPadding(40,10,0,0);
                address.setTextAppearance(context, R.style.Normal);
                address.setTextSize(width / 40);
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
                product1.setTextAppearance(context, R.style.Body);
                product1.setTextSize(width / 40);
                product1.setText(res.getString(R.string.l_017_MarketMainpage_Products_Label_0));
                ll.addView(product1);
                TextView product = new TextView(context);
                product.setPadding(40,10,0,0);
                product.setTextAppearance(context, R.style.Normal);
                product.setTextSize(width / 40);
                product.setText(result.getString("productlist"));
                ll.addView(product);

                TextView br4 = new TextView(context);
                br4.setText("");
                ll.addView(br4);

                //Vendors
                TextView vendor1 = new TextView(context);
                vendor1.setTextAppearance(context, R.style.Green);
                vendor1.setTextSize(width / 35);
                vendor1.setText(res.getString(R.string.l_017_MarketMainpage_VendorPostPrice_Label_0));
                vendor1.setPadding(0,10,0,10);
                ll.addView(vendor1);

                ScrollView vendorscroll=new ScrollView(context);
                TableLayout vendorlinear=new TableLayout(context);
                JSONArray vendorarray=result.getJSONArray("vendorlist");
                for (int i=0;i<vendorarray.length();i++)
                {
                    TableRow tr=new TableRow(context);
                    tr.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    tr.setBackgroundResource(R.drawable.row_border);
                    RelativeLayout ll_arrow = new RelativeLayout(context);
                    ll_arrow.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    TextView vendorinfo=new TextView(context);
                    RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams((int)(width*0.8),TableRow.LayoutParams.WRAP_CONTENT);
                    parms2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    vendorinfo.setLayoutParams(parms2);
                    vendorinfo.setTextAppearance(context, R.style.DarkBule);
                    vendorinfo.setTextSize(width / 40);
                    vendorinfo.setPadding(30,20,0,20);
                    JSONObject vendor=vendorarray.getJSONObject(i);
                    vendorinfo.setMaxLines(1);
                    vendorinfo.setEllipsize(TextUtils.TruncateAt.END);
                    vendorinfo.setText(vendor.getString("vendorbusinessname")+" ("+vendor.getString("vendorcity")+", "+vendor.getString("vendorstate")+")");
                    ll_arrow.addView(vendorinfo);
                    ImageView i1= new ImageView(context);
                    i1.setImageResource(R.drawable.next_item);
                    i1.setPadding(0,0,10,0);
                    RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams(50, 50);
                    parms1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    parms1.addRule(RelativeLayout.CENTER_VERTICAL);
                    i1.setLayoutParams(parms1);
                    ll_arrow.addView(i1);

                    tr.addView(ll_arrow);

                    tr.setOnClickListener(new checkvendorListener(tr,vendor.getString("vendorusername")));
                    vendorlinear.addView(tr);

//                    TableRow tr_line = new TableRow(context);
//                    View line = new View(context);
//                    line.setLayoutParams(new TableRow.LayoutParams(0,2,1f));
//                    line.setBackgroundColor(Color.parseColor("#C2C2C2"));
//                    tr_line.addView(line);
//                    vendorlinear.addView(tr_line);
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
