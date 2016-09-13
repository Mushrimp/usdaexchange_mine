package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.LoadImage;
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
public class page_412_request extends PageOperations {
    public static void showrequests() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("requestScrollTable");
                tl.removeAllViews();
                JSONArray allrequests = result.getJSONArray("results");
                int count = result.getInt("count");
                if (count == 0) {
                    Toast.makeText(context, "You have no requests.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < allrequests.length(); i++) {
                        JSONObject request = allrequests.getJSONObject(i);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                        //Logo
                        ImageView logo = new ImageView(context);
                        String vendorlogohtml = request.getString("avatar");
                        int urlstart = vendorlogohtml.indexOf("src=\"") + "src=\"".length();
                        int urlend = urlstart;
                        for (int j = urlstart; vendorlogohtml.charAt(j) != '\"'; j++) {
                            urlend = j;
                        }
                        String vendorlogourl = vendorlogohtml.substring(urlstart, urlend + 1);
                        LoadImage li = new LoadImage();
                        li.img = logo;
                        li.execute(vendorlogourl);
                        logo.setLayoutParams(new TableRow.LayoutParams(0, height / 5, 0.3f));
                        lv.addView(logo);

                        LinearLayout ll_bl = new LinearLayout(context);
                        ll_bl.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.04f));
                        lv.addView(ll_bl);

                        //Name
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        TextView busname = new TextView(context);
                        busname.setText(request.getString("businessname"));
                        busname.setTextAppearance(context, R.style.Normal);
                        busname.setTextSize(width / 45);
                        ll.addView(busname);

                        TextView disname = new TextView(context);
                        disname.setText("(" + request.getString("displayname") + ")");
                        disname.setTextAppearance(context, R.style.Normal);
                        disname.setTextSize(width / 50);
                        ll.addView(disname);

                        TextView bl = new TextView(context);
                        ll.addView(bl);
                        final String friendusername=request.getString("username");
                        //Button
                        RelativeLayout rl_in = new RelativeLayout(context);
                        rl_in.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 10));
                        final Button reject_bt = new Button(context);
                        RelativeLayout.LayoutParams btprams = new RelativeLayout.LayoutParams((int)(width*0.25), (int)(height*0.05));
                        btprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT|RelativeLayout.CENTER_VERTICAL);
                        reject_bt.setLayoutParams(btprams);
                        reject_bt.setText(res.getString(R.string.l_412_Requests_RejectButton_Label_0));
                        reject_bt.setTextAppearance(context, R.style.White);
                        reject_bt.setTextSize(width / 55);
                        reject_bt.setPadding(0, 0, 0, 1);
                        reject_bt.setBackgroundResource(R.drawable.button_style);
                        reject_bt.setStateListAnimator(null);
                        reject_bt.setTransformationMethod(null);
                        reject_bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                reject_bt.setClickable(false);
                                String token_s = UserFileUtility.get_token();
                                Hashtable<String, String> htt = new Hashtable<String, String>();
                                htt.put("os", "Android");
                                htt.put("token", token_s);
                                htt.put("fname",friendusername);
                                new FetchTask(){
                                    @Override
                                    protected void executeSuccess(JSONObject result) throws JSONException {
                                        showrequests();
                                    }
                                }.execute(AppCodeResources.postUrl("usdafriendship","friends_reject_friendship_request",htt));
                            }
                        });
                        rl_in.addView(reject_bt);
                        final Button accept_bt = new Button(context);
                        RelativeLayout.LayoutParams btprams1 = new RelativeLayout.LayoutParams((int)(width*0.25), (int)(height*0.05));
                        btprams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        btprams1.addRule(RelativeLayout.CENTER_VERTICAL);
                        accept_bt.setLayoutParams(btprams1);
                        accept_bt.setText(res.getString(R.string.l_412_Requests_AcceptButton_Label_0));
                        accept_bt.setTextAppearance(context, R.style.White);
                        accept_bt.setTextSize(width / 55);
                        accept_bt.setPadding(0, 0, 0, 1);
                        accept_bt.setBackgroundResource(R.drawable.button_style);
                        accept_bt.setStateListAnimator(null);
                        accept_bt.setTransformationMethod(null);

                        accept_bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                accept_bt.setClickable(false);
                                String token_s = UserFileUtility.get_token();
                                Hashtable<String, String> htt = new Hashtable<String, String>();
                                htt.put("os", "Android");
                                htt.put("token", token_s);
                                htt.put("fname", friendusername);
                                new FetchTask() {
                                    @Override
                                    protected void executeSuccess(JSONObject result) throws JSONException {
                                        showrequests();
                                    }
                                }.execute(AppCodeResources.postUrl("usdafriendship", "friends_accept_friendship_request", htt));
                            }
                        });

                        rl_in.addView(accept_bt);
                        ll.addView(rl_in);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
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
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "friends_list_allrequests_byuser", ht));
    }
}
