package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
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
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_403_messageform  extends PageOperations {


    public static void showmessageform() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("thread_id",getRecentPage().params.get("id"));
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("messageformScrollTable");
                tl.removeAllViews();
                JSONObject result2=result.getJSONObject("results");
                JSONArray allmessageforms = result2.getJSONArray("results");
                for (int i = 0; i < allmessageforms.length(); i++) {
                    JSONObject messageform = allmessageforms.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    //From
                    RelativeLayout rl = new RelativeLayout(context);
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    TextView from = new TextView(context);
                    RelativeLayout.LayoutParams fromprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    fromprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    from.setLayoutParams(fromprams);
                    from.setTextAppearance(context, R.style.Title);
                    from.setTextSize(width / 50);
                    from.setText("From:");
                    rl.addView(from);
                    //Time
                    TextView time = new TextView(context);
                    RelativeLayout.LayoutParams timeprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    timeprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    time.setLayoutParams(timeprams);
                    time.setText(messageform.getString("time"));
                    time.setTextAppearance(context, R.style.Body);
                    time.setTextSize(width / 50);
                    rl.addView(time);
                    ll.addView(rl);

                    //name
                    TextView name = new TextView(context);
                    String name1 = messageform.getString("sender_name");
                    if (name1.contains("&")&& name1.contains(";")) {
                        String[] namesplit1, namesplit2;
                        namesplit1 = name1.split("&");
                        namesplit2 = name1.split(";");
                        name.setText(namesplit1[0] + namesplit2[1]);
                    }else {
                        name.setText(name1);
                    }
                    name.setTextAppearance(context, R.style.Bold);
                    name.setTextSize(width / 45);
                    ll.addView(name);

                    //Message
                    TextView message1 = new TextView(context);
                    message1.setText("Message:");
                    message1.setTextAppearance(context, R.style.Title);
                    message1.setTextSize(width / 50);
                    ll.addView(message1);
                    TextView message = new TextView(context);
                    message.setText(messageform.getString("message"));
                    message.setTextAppearance(context, R.style.Body);
                    message.setTextSize(width / 45);
                    ll.addView(message);

                    //
                    TextView bl = new TextView(context);
                    ll.addView(bl);

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
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "messages_check_and_get_thread_messages", ht));
    }
    public static class replymessageListener extends ClickOnceListener {
        public replymessageListener(View button) {
            super(button);
        }

        @Override
        public void action() {
            Hashtable<String,String> ht=new Hashtable<>();
            ht.put("threadid",getRecentPage().params.get("id"));
            ht.put("recipientsname",getRecentPage().params.get("displayname"));
            ht.put("subject",getRecentPage().params.get("subject"));
            pushNewPage(R.array.page_408_sendmessage,ht);
        }
    }
}
