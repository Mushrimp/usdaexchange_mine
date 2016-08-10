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
public class page_401_friendship extends PageOperations {
    public static void showfriends() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("friendshipScrollTable");
                tl.removeAllViews();
                JSONArray allfriends = result.getJSONArray("results");
                for (int i = 0; i < allfriends.length(); i++) {
                    JSONObject friend = allfriends.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                    //Logo
                    ImageView logo = new ImageView(context);
                    String vendorlogohtml = friend.getString("avatar");
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

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Name & message
                    RelativeLayout rl_in = new RelativeLayout(context);
                    rl_in.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 10));
                    TextView name = new TextView(context);
                    RelativeLayout.LayoutParams nameprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    nameprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
                    name.setLayoutParams(nameprams);
                    name.setText(friend.getString("displayname"));
                    name.setTextAppearance(context, R.style.Bold);
                    name.setTextSize(width / 45);
                    rl_in.addView(name);
                    Button message_bt = new Button(context);
                    RelativeLayout.LayoutParams btprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    btprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    message_bt.setLayoutParams(btprams);
                    message_bt.setText(res.getString(R.string.l_401_Friends_MessageButton_Label_0));
                    message_bt.setTextAppearance(context, R.style.Normal);
                    message_bt.setTextSize(width / 55);
                    message_bt.setBackgroundColor(Color.parseColor("#A2D25A"));
                    rl_in.addView(message_bt);
                    final String id=friend.getString("ID");
                    final String displayname=friend.getString("displayname");
                    message_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Hashtable<String,String> params=new Hashtable<String, String>();
                            params.put("recipientsid",id);
                            params.put("recipientsname",displayname);
                            pushNewPage(R.array.page_408_sendmessage,params);
                        }
                    });
                    ll.addView(rl_in);

                    //Business Name
                    final TextView bn = new TextView(context);
                    bn.setLayoutParams(new LinearLayout.LayoutParams((int) (width * 0.5), LinearLayout.LayoutParams.WRAP_CONTENT));
                    bn.setText(friend.getString("businessname"));
                    ll.addView(bn);


                    ll.setLayoutParams(new TableRow.LayoutParams(0, height / 5, 0.7f));
                    lv.addView(ll);
                    tl.addView(lv);

                    TableRow lk = new TableRow(context);
                    lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    View ldivider = new LinearLayout(context);
                    ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                    View rdivider = new LinearLayout(context);
                    ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                    lk.addView(ldivider);
                    lk.addView(rdivider);
                    tl.addView(lk);
                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "friends_list_all_byuser", ht));
    }
}
