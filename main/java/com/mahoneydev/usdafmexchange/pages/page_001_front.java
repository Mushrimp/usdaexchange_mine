package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.Frontpage;
import com.mahoneydev.usdafmexchange.LoadImage;
import com.mahoneydev.usdafmexchange.PageNode;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/21/2016.
 */
public class page_001_front extends PageOperations {
    public static int page_position=0;
    public static void showpublicposts(final String search) {
        //SEARCH AND SHOW FUNTION FOR FRONT PAGE
        //final ScrollView sv = (ScrollView) hashelements.get("postsScroll");

        sc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    Log.e("y=",""+sc.getScrollY()+","+playout.getHeight()+","+sc.getHeight());

                    if ((sc.getScrollY()+sc.getHeight()+50)>playout.getHeight())
                    {
                        final String token_s = UserFileUtility.get_token();
                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        ht.put("list_start", ""+page_position);
                        ht.put("perpage", "3");
                        ht.put("os", "Android");
                        ht.put("search", search);
                        ht.put("token", token_s);
                        Log.e("",""+page_position);
                        new FetchTask() {
                            @Override
                            protected void executeSuccess(JSONObject result) throws JSONException {
                                JSONArray resultposts = result.getJSONArray("results");
                                Log.d("resultposts", resultposts.toString());
                                drawposttable(resultposts);
                                setupUI(playout);

                            }

                        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_posts", ht));
                    }
                }
                return false;
            }
        });
        final String token_s = UserFileUtility.get_token();
        page_position=0;
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("list_start", ""+page_position);
        ht.put("perpage", "3");
        ht.put("os", "Android");
        ht.put("search", search);
        ht.put("token", token_s);

        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("postsScrollTable");
                tl.removeAllViews();
                JSONArray resultposts = result.getJSONArray("results");
                Log.d("resultposts", resultposts.toString());
                drawposttable(resultposts);
                setupUI(playout);

            }

        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_posts", ht));
    }

    private static void drawposttable(JSONArray resultposts) throws JSONException{
        TableLayout tl = (TableLayout) hashelements.get("postsScrollTable");
        for (int i = 0; i < resultposts.length(); i++) {
            page_position++;
            JSONObject jpost = resultposts.getJSONObject(i);
            Log.d("jpost", jpost.toString());
            TableRow lv = new TableRow(context);
            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            ImageView logo = new ImageView(context);
            String vendorlogohtml = jpost.getString("vendorlogo");
            int urlstart = vendorlogohtml.indexOf("src=\"") + "src=\"".length();
            int urlend = urlstart;
            for (int j = urlstart; vendorlogohtml.charAt(j) != '\"'; j++) {
                urlend = j;
            }

            String vendorlogourl = vendorlogohtml.substring(urlstart, urlend + 1);
            Log.d("LOGOURL", vendorlogourl);
            LoadImage li = new LoadImage();
            li.img = logo;
            li.execute(vendorlogourl);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            logo.setLayoutParams(layoutParams);
            lv.addView(logo);

            LinearLayout ll_bl = new LinearLayout(context);
            ll_bl.setOrientation(LinearLayout.VERTICAL);
            ll_bl.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.04f));
            TextView bl = new TextView(context);
            bl.setText("");
            ll_bl.addView(bl);
            lv.addView(ll_bl);

            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            //Product Name
            TextView pn = new TextView(context);
            pn.setText(jpost.getString("productname"));
            pn.setTextAppearance(context, R.style.Normal);
            pn.setTextSize(width / 45);
            ll.addView(pn);
            //Vendor
            TextView vendor = new TextView(context);
            String tv = "By: " + jpost.getString("vendorbusinessname");
            vendor.setText(tv);
            vendor.setTextAppearance(context, R.style.Title);
            vendor.setTextSize(width / 55);
            ll.addView(vendor);
            //Date
            TextView date = new TextView(context);
            String td = "Date: " + jpost.getString("marketday");
            date.setText(td);
            date.setTextAppearance(context, R.style.Date);
            date.setTextSize(width / 55);
            ll.addView(date);
            //Price Unit
            LinearLayout ll_in = new LinearLayout(context);
            ll_in.setOrientation(LinearLayout.HORIZONTAL);
            ll_in.setGravity(Gravity.RIGHT);
            String price = jpost.getString("price");/*+ " / " + jpost.getString("unit")*/
            String[] pricesplit;
            pricesplit = price.split("\\.");
            TextView priceint = new TextView(context);
            priceint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            priceint.setGravity(Gravity.RIGHT);
            priceint.setText("$" + pricesplit[0] + ".");
            priceint.setTextAppearance(context, R.style.Price);
            priceint.setTextSize(width / 30);
            ll_in.addView(priceint);
            TextView pricedec = new TextView(context);
            pricedec.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            pricedec.setGravity(Gravity.RIGHT | Gravity.TOP);
            pricedec.setText(pricesplit[1]);
            pricedec.setTextAppearance(context, R.style.Price);
            pricedec.setTextSize(width / 45);
            ll_in.addView(pricedec);
            ll.addView(ll_in);
            //Market
            TextView tv2 = new TextView(context);
            tv2.setTextAppearance(context, R.style.Body);
            tv2.setTextSize(width / 60);
            tv2.setText("@ " + jpost.getString("farmersmarketname"));
            ll.addView(tv2);
            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.66f));

            lv.addView(ll);
            lv.setBackgroundResource(R.drawable.tablerow_style);

            final String postid = jpost.getString("postid");
            lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hashtable<String, String> pam = new Hashtable<String, String>();
                    pam.put("postid", postid);
                    pushNewPage(R.array.page_020_viewpost, pam);
                }
            });

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
    }
    public static class searchPostsListener extends ClickOnceListener
    {
        public searchPostsListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            final String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("liststart", "0");
            ht.put("perpage", "5");
            ht.put("os", "Android");
            ht.put("search", (((EditText) hashelements.get("searchpublicpostsInput")).getText()).toString());
            ht.put("token", token_s);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    TableLayout tl = (TableLayout) hashelements.get("postsScrollTable");
                    tl.removeAllViews();
                    JSONArray resultposts = result.getJSONArray("results");
                    Log.d("resultposts", resultposts.toString());
                    for (int i = 0; i < resultposts.length(); i++) {
                        JSONObject jpost = resultposts.getJSONObject(i);
                        Log.d("jpost", jpost.toString());
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        ImageView logo = new ImageView(context);
                        String vendorlogohtml = jpost.getString("vendorlogo");
                        int urlstart = vendorlogohtml.indexOf("src=\"") + "src=\"".length();
                        int urlend = urlstart;
                        for (int j = urlstart; vendorlogohtml.charAt(j) != '\"'; j++) {
                            urlend = j;
                        }

                        String vendorlogourl = vendorlogohtml.substring(urlstart, urlend + 1);
                        Log.d("LOGOURL", vendorlogourl);
                        LoadImage li = new LoadImage();
                        li.img = logo;
                        li.execute(vendorlogourl);

                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
                        layoutParams.gravity = Gravity.CENTER_VERTICAL;
                        logo.setLayoutParams(layoutParams);
                        lv.addView(logo);

                        LinearLayout ll_bl = new LinearLayout(context);
                        ll_bl.setOrientation(LinearLayout.VERTICAL);
                        ll_bl.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.04f));
                        TextView bl = new TextView(context);
                        bl.setText("");
                        ll_bl.addView(bl);
                        lv.addView(ll_bl);

                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        //Product Name
                        TextView pn = new TextView(context);
                        pn.setText(jpost.getString("productname"));
                        pn.setTextAppearance(context, R.style.Normal);
                        pn.setTextSize(width / 45);
                        ll.addView(pn);
                        //Vendor
                        TextView vendor = new TextView(context);
                        String tv = "By: " + jpost.getString("vendorbusinessname");
                        vendor.setText(tv);
                        vendor.setTextAppearance(context, R.style.Title);
                        vendor.setTextSize(width / 55);
                        ll.addView(vendor);
                        //Date
                        TextView date = new TextView(context);
                        String td = "Date: " + jpost.getString("marketday");
                        date.setText(td);
                        date.setTextAppearance(context, R.style.Date);
                        date.setTextSize(width / 55);
                        ll.addView(date);
                        //Price Unit
                        LinearLayout ll_in = new LinearLayout(context);
                        ll_in.setOrientation(LinearLayout.HORIZONTAL);
                        ll_in.setGravity(Gravity.RIGHT);
                        String price = jpost.getString("price");/*+ " / " + jpost.getString("unit")*/
                        String[] pricesplit;
                        pricesplit = price.split("\\.");
                        TextView priceint = new TextView(context);
                        priceint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        priceint.setGravity(Gravity.RIGHT);
                        priceint.setText("$" + pricesplit[0] + ".");
                        priceint.setTextAppearance(context, R.style.Price);
                        priceint.setTextSize(width / 30);
                        ll_in.addView(priceint);
                        TextView pricedec = new TextView(context);
                        pricedec.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        pricedec.setGravity(Gravity.RIGHT | Gravity.TOP);
                        pricedec.setText(pricesplit[1]);
                        pricedec.setTextAppearance(context, R.style.Price);
                        pricedec.setTextSize(width / 45);
                        ll_in.addView(pricedec);
                        ll.addView(ll_in);
                        //Market
                        TextView tv2 = new TextView(context);
                        tv2.setTextAppearance(context, R.style.Body);
                        tv2.setTextSize(width / 60);
                        tv2.setText("@ " + jpost.getString("farmersmarketname"));
                        ll.addView(tv2);
                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.66f));

                        lv.addView(ll);

                        final String postid = jpost.getString("postid");
                        lv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Hashtable<String, String> pam = new Hashtable<String, String>();
                                pam.put("postid", postid);
                                pushNewPage(R.array.page_020_viewpost, pam);
                            }
                        });

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
                    enableButton();
                }

            }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_posts", ht));
        }
    }
}
