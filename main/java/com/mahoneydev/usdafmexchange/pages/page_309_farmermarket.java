package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
 * Created by bichongg on 7/25/2016.
 */
public class page_309_farmermarket extends PageOperations {


    public static void showmarkets() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("farmermarketScrollTable");
                tl.removeAllViews();
                JSONArray allmarkets = result.getJSONArray("results");
//                //Add
//                LinearLayout ll_in = new LinearLayout(context);
//                ll_in.setGravity(Gravity.END);
//                ImageButton bt1 = new ImageButton(context);
//                bt1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt1.setImageResource(R.drawable.add_white);
//                bt1.setBackgroundResource(R.drawable.button_style);
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pushNewPage(R.array.page_311_addmarketform, new Hashtable<String, String>());
//                    }
//                });
//                bt1.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//                ll_in.addView(bt1);
//                tl.addView(ll_in);

                String count = result.getString("count");
                if (!count.equals("0")){
                    for (int i = 0; i < allmarkets.length(); i++) {
                        JSONObject market = allmarkets.getJSONObject(i);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        //Market Name
                        TextView marketnamet = new TextView(context);
                        marketnamet.setTextAppearance(context, R.style.Title);
                        marketnamet.setTextSize(width / 50);
                        marketnamet.setText(res.getString(R.string.l_309_MarketLocation_MarketName_Label_0));
                        ll.addView(marketnamet);
                        TextView marketname = new TextView(context);
                        marketname.setTextAppearance(context, R.style.Bold);
                        marketname.setTextSize(width / 45);
                        marketname.setText(market.getString("MarketName"));
                        ll.addView(marketname);
                        //Market Location
                        TextView marketlocationt = new TextView(context);
                        marketlocationt.setTextAppearance(context, R.style.Title);
                        marketlocationt.setTextSize(width / 50);
                        marketlocationt.setText(res.getString(R.string.l_309_MarketLocation_Address_Label_0));
                        ll.addView(marketlocationt);
                        TextView marketlocation = new TextView(context);
                        marketlocation.setTextAppearance(context, R.style.Body);
                        marketlocation.setTextSize(width / 50);
                        marketlocation.setText(market.getString("Market_location"));
                        ll.addView(marketlocation);

                        //
                        TextView br = new TextView(context);
                        br.setText("");
                        ll.addView(br);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        lv.setBackgroundResource(R.drawable.tablerow_style);
                        tl.addView(lv);
                        lv.setOnLongClickListener(new removemarketListener(context,"Delete a product","Do you want to remove "+market.getString("MarketName")+" from the list?",lv,tl,market.getString("id")));

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
                }else if (count.equals("0")){
                    Toast.makeText(context,"No markets found...",Toast.LENGTH_SHORT).show();

                }


                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_markets_listall_byuser", ht));
    }

    public static class removemarketListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String marketid;
        public removemarketListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String marketidi)
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
            }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_remove_selectedmarket", ht));

        }
    }
}
