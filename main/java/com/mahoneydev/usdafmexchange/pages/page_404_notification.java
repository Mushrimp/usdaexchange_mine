package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
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
public class page_404_notification extends PageOperations {

    public static void shownotifications() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("notificationScrollTable");
                tl.removeAllViews();
                JSONArray allnotifications = result.getJSONArray("results");
                int count = result.getInt("count");
                if (count == 0) {
                    Toast.makeText(context, "You have no notifications.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < allnotifications.length(); i++) {
                        JSONObject notification = allnotifications.getJSONObject(i);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        TextView name = new TextView(context);
                        name.setText(notification.getString("notificationfrom"));
                        name.setTextAppearance(context, R.style.Bold);
                        name.setTextSize(width/45);
                        ll.addView(name);

                        TextView subject = new TextView(context);
                        subject.setText(notification.getString("notificationsubject"));
                        subject.setTextAppearance(context,R.style.Normal);
                        subject.setTextSize(width/50);
                        ll.addView(subject);

                        TextView time = new TextView(context);
                        time.setText(notification.getString("notificationtime"));
                        time.setTextAppearance(context,R.style.Body);
                        time.setTextSize(width/50);
                        ll.addView(time);

                        TextView bl = new TextView(context);
                        ll.addView(bl);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        lv.setBackgroundResource(R.drawable.tablerow_style);
                        lv.setOnLongClickListener(new marknotificationListener(context,"Delete a notification","Do you want to remove this notification from the list?",lv,tl, notification.getString("ID")));
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
        }.execute(AppCodeResources.postUrl("usdafriendship", "notifications_list_all_byuser", ht));
    }
    public static class marknotificationListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String nid;
        public marknotificationListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String id)
        {
            super(contexti, titlei, messagei);
            deletetablerow=tr;
            fromtablelayout=tl;
            nid = id;
        }
        @Override
        public void deleteaction(){
            String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("nid",nid);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    fromtablelayout.removeView(deletetablerow);
                }
            }.execute(AppCodeResources.postUrl("usdafriendship", "notifications_mark_selectednotification", ht));

        }
    }
    public static void markAllNotifications(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht=new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                shownotifications();
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "notifications_mark_all_byuser", ht));

    }

}
