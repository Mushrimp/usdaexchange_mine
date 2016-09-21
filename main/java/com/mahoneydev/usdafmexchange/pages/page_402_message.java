package com.mahoneydev.usdafmexchange.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class page_402_message extends PageOperations {


    public static void showmessages() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("messageScrollTable");
                tl.removeAllViews();
                JSONArray allmessages = result.getJSONArray("results");
                int count = result.getInt("count");
                if (count == 0) {
                    Toast.makeText(context, "You have no messages.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < allmessages.length(); i++) {
                        JSONObject message = allmessages.getJSONObject(i);
                        final TableRow lv = new TableRow(context);
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
                        //count
                        TextView count_in = new TextView(context);
                        RelativeLayout.LayoutParams countprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        countprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        count_in.setLayoutParams(countprams);
                        count_in.setText("(" + message.getString("count") + ")");
                        count_in.setTextAppearance(context, R.style.Normal);
                        count_in.setTextSize(width / 45);
                        rl.addView(count_in);
                        ll.addView(rl);

                        //name
                        TextView name = new TextView(context);
                        String name1 = message.getString("from");
                        final String displayname;
                        if (name1.contains("&")&& name1.contains(";")) {
                            String[] namesplit1, namesplit2;
                            namesplit1 = name1.split("&");
                            namesplit2 = name1.split(";");
                            name.setText(namesplit1[0] + namesplit2[1]);
                            displayname=namesplit1[0];
                        }else{
                            name.setText(name1);
                            displayname=name1;
                        }
                        name.setTextAppearance(context, R.style.Bold);
                        name.setTextSize(width / 45);
                        ll.addView(name);

                        //Subject
                        TextView subject1 = new TextView(context);
                        subject1.setText("Subject:");
                        subject1.setTextAppearance(context, R.style.Title);
                        subject1.setTextSize(width / 50);
                        ll.addView(subject1);
                        TextView subject = new TextView(context);
                        subject.setText(message.getString("subject"));
                        subject.setTextAppearance(context, R.style.Body);
                        subject.setTextSize(width / 45);
                        ll.addView(subject);

                        //Time
                        TextView time = new TextView(context);
                        time.setText(message.getString("time"));
                        time.setTextAppearance(context, R.style.Body);
                        time.setTextSize(width / 50);
                        ll.addView(time);

                        //
                        TextView bl = new TextView(context);
                        ll.addView(bl);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        lv.setBackgroundResource(R.drawable.tablerow_style);

                        final String id = message.getString("ID");
                        final String subjectstring = message.getString("subject");
                        lv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lv.setClickable(false);
                                Hashtable<String, String> pam = new Hashtable<String, String>();
                                pam.put("id", id);
                                pam.put("subject",subjectstring);
                                pam.put("displayname",displayname);
                                pushNewPage(R.array.page_403_messageform, pam);
                            }
                        });
                        lv.setOnLongClickListener(new removemessageListener(context,"Delete a message","Do you want to remove this message from the list?",lv,tl, message.getString("ID")));


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
        }.execute(AppCodeResources.postUrl("usdafriendship", "messages_list_all_byuser", ht));
    }
    public static class removemessageListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String user_name;
        private String threadid;
        public removemessageListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String id)
        {
            super(contexti, titlei, messagei);
            deletetablerow=tr;
            fromtablelayout=tl;
           // user_name=username;
            threadid = id;
        }
        @Override
        public void deleteaction(){
            String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("os", "Android");
            ht.put("token", token_s);
            //ht.put("username", user_name);
            ht.put("threadid",threadid);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    fromtablelayout.removeView(deletetablerow);
                }
            }.execute(AppCodeResources.postUrl("usdafriendship", "messages_remove_thread", ht));

        }
    }
    public static void removeAllmessage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete all the messages");
        alert.setMessage("DO you want to DELETE all the messages?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                String token_s = UserFileUtility.get_token();
                Hashtable<String, String> ht = new Hashtable<String, String>();
                ht.put("os", "Android");
                ht.put("token", token_s);
                //ht.put("username", user_name);
                ht.put("threadid","-1");
                new FetchTask() {
                    @Override
                    protected void executeSuccess(JSONObject result) throws JSONException {
                        showmessages();
                    }
                }.execute(AppCodeResources.postUrl("usdafriendship", "messages_remove_thread", ht));
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }
}
