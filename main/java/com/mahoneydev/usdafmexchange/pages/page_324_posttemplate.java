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
public class page_324_posttemplate extends PageOperations {
    public static void showtemplate() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("posttemplateScrollTable");
                tl.removeAllViews();
                JSONArray alltemplates = result.getJSONArray("results");
                for (int i = 0; i < alltemplates.length(); i++) {
                    JSONObject template = alltemplates.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    //Template content
                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Product Name
                    TextView pn1 = new TextView(context);
                    pn1.setTextAppearance(context, R.style.Title);
                    pn1.setTextSize(width / 50);
                    String pntitle = "Product:";
                    pn1.setText(pntitle);
                    ll.addView(pn1);
                    TextView pn = new TextView(context);
                    pn.setTextAppearance(context, R.style.Bold);
                    pn.setTextSize(width / 45);
                    pn.setText(template.getString("price_product_name"));
                    ll.addView(pn);
                    //Market Name
                    TextView mn1 = new TextView(context);
                    mn1.setTextAppearance(context, R.style.Title);
                    mn1.setTextSize(width / 50);
                    String mntitle = "Market:";
                    mn1.setText(mntitle);
                    ll.addView(mn1);
                    TextView mn = new TextView(context);
                    mn.setTextAppearance(context, R.style.Body);
                    mn.setTextSize(width / 45);
                    mn.setText(template.getString("price_market_name"));
                    ll.addView(mn);
                    //Description
                    TextView desc1 = new TextView(context);
                    desc1.setTextAppearance(context, R.style.Title);
                    desc1.setTextSize(width / 50);
                    String desct = "Promotional Message:";
                    desc1.setText(desct);
                    ll.addView(desc1);
                    TextView desc = new TextView(context);
                    desc.setTextAppearance(context, R.style.Body);
                    desc.setTextSize(width / 45);
                    desc.setText(template.getString("price_ad_desc"));
                    ll.addView(desc);

                    //
                    TextView br = new TextView(context);
                    br.setText("");
                    ll.addView(br);

                    ll.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), TableRow.LayoutParams.WRAP_CONTENT));
                    lv.addView(ll);
                    lv.setBackgroundResource(R.drawable.tablerow_style);
                    tl.addView(lv);
                    lv.setOnLongClickListener(new removetemplateListener(context,"Delete a template","Do you want to remove this template?",lv,tl,template.getString("ID")));

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
                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_template_list_byuser", ht));
    }
    public static class removetemplateListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String postid;
        public removetemplateListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String postidi)
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
