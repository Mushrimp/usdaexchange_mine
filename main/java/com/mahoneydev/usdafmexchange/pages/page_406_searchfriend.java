package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by May on 7/26/2016.
 */
public class page_406_searchfriend extends PageOperations {
    public static void showRequestfriends(String name){
        final String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("friendname", name);
        ht.put("token", token_s);
        ht.put("os","Android");
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("searchuserScrollTable");
                tl.removeAllViews();
                JSONArray resultfriends = result.getJSONArray("results");
                for (int i = 0; i < resultfriends.length(); i++) {
                    JSONObject friends = resultfriends.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    LinearLayout ll_bl = new LinearLayout(context);
                    TextView bl = new TextView(context);
                    bl.setText("");
                    ll_bl.addView(bl);
                    lv.addView(ll_bl);

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Name
                    TextView un = new TextView(context);
                    un.setText(friends.getString("username"));
                    un.setTextAppearance(context, R.style.Bold);
                    un.setTextSize(width / 40);
                    ll.addView(un);
                    //business name
                    TextView bn = new TextView(context);
                    bn.setText(friends.getString("business_name"));
                    bn.setTextAppearance(context, R.style.Body);
                    bn.setTextSize(width / 45);
                    ll.addView(bn);

                    //
                    TextView br = new TextView(context);
                    ll.addView(br);

                    lv.addView(ll);

                    final String username = friends.getString("username");
                    lv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Hashtable<String, String> pam = new Hashtable<String, String>();
                            pam.put("username", username);
                            pushNewPage(R.array.page_016_vendorpage, pam);
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

                setupUI(playout);

            }

        }.execute(AppCodeResources.postUrl("usdafriendship", "friends_search_nonefriend_user", ht));
    }
}
