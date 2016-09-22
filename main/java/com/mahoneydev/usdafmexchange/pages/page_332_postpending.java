package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by xianan on 9/14/16.
 */
public class page_332_postpending extends PageOperations {
    public static void showpending() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
//        ht.put("username",UserFileUtility.get_username());
        ht.put("posttype","pending");
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("postpendingScrollTable");
                tl.removeAllViews();
                JSONArray allpendings = result.getJSONArray("results");
                String count = result.getString("count");
                if (!count.equals("0")){
                    for (int i = 0; i < allpendings.length(); i++) {
                        JSONObject pending = allpendings.getJSONObject(i);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                        //Template content
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        //Product Name
                        TextView pn = new TextView(context);
                        pn.setPadding(0,0,0,5);
                        pn.setTextAppearance(context, R.style.Bold);
                        pn.setTextSize(width / 40);
                        pn.setText(pending.getString("price_product_name")+" $"+pending.getString("price_price")+" per "
                                + pending.getString("price_productunit_name"));
                        ll.addView(pn);

                        //Market date
                        LinearLayout ll_in1 = new LinearLayout(context);
                        ll_in1.setPadding(0,5,0,5);
                        ll_in1.setOrientation(LinearLayout.HORIZONTAL);
                        TextView md1 = new TextView(context);
                        md1.setTextAppearance(context, R.style.Title);
                        md1.setTextSize(width / 50);
                        String mdtitle = "Market Date:         ";
                        md1.setText(mdtitle);
                        ll_in1.addView(md1);
                        TextView md = new TextView(context);
                        md.setTextAppearance(context, R.style.Body);
                        md.setTextSize(width / 50);
                        md.setText(pending.getString("price_market_date"));
                        ll_in1.addView(md);
                        ll.addView(ll_in1);

                        //Publish date
                        LinearLayout ll_in2 = new LinearLayout(context);
                        ll_in2.setPadding(0,5,0,5);
                        ll_in2.setOrientation(LinearLayout.HORIZONTAL);
                        TextView pd1 = new TextView(context);
                        pd1.setTextAppearance(context, R.style.Title);
                        pd1.setTextSize(width / 50);
                        String pdtitle = "Published Date:    ";
                        pd1.setText(pdtitle);
                        ll_in2.addView(pd1);
                        TextView pd = new TextView(context);
                        pd.setTextAppearance(context, R.style.Body);
                        pd.setTextSize(width / 50);
                        pd.setText(pending.getString("price_publish_date"));
                        ll_in2.addView(pd);
                        ll.addView(ll_in2);

                        //Market Name
                        TextView mn = new TextView(context);
                        mn.setPadding(0,5,0,15);
                        mn.setTextAppearance(context, R.style.Body);
                        mn.setTextSize(width / 45);
                        mn.setText("@ "+pending.getString("price_market_name"));
                        ll.addView(mn);

//                        //
//                        TextView br = new TextView(context);
//                        br.setText("");
//                        ll.addView(br);

                        ll.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), TableRow.LayoutParams.WRAP_CONTENT));
                        lv.addView(ll);
                        lv.setBackgroundResource(R.drawable.tablerow_style);
                        tl.addView(lv);
//                        lv.setOnLongClickListener(new removependingListener(context,"Delete a template","Do you want to remove this post?",lv,tl,pending.getString("ID")));

//                    final String id = template.getString("ID");
//                    lv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            page_322_newpost.preparepostform(id);
//                        }
//                    });

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

                        final String id = pending.getString("ID");
                        lv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Hashtable<String, String> pam = new Hashtable<String, String>();
                                pam.put("id", id);
                                pushNewPage(R.array.page_322_newpost, pam);
                            }
                        });
                    }
                }else if (count.equals("0")){
                    Toast.makeText(context,"There are no new pending posts.",Toast.LENGTH_SHORT).show();

                }

                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_byuser", ht));
    }

    public static class removependingListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String postid;
        public removependingListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String postidi)
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
            }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_template_remove", ht));

        }
    }

}
