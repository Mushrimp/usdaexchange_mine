package com.mahoneydev.usdafmexchange.pages;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.ClickOnceListener;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Hashtable;

/**
 * Created by xianan on 9/2/16.
 */
public class page_407_userprofile extends PageOperations {
    public static void showUserpage() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        Log.d("username",getRecentPage().params.get("username"));
        ht.put("username", getRecentPage().params.get("username"));
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("userprofileScrollTable");
                tl.removeAllViews();
                JSONObject vendorinfo = result.getJSONObject("results");
                JSONObject userprofile = vendorinfo.getJSONObject("vendorinfo");
                if (userprofile == null){
                    TextView non = new TextView(context);
                    non.setText("This vendor didn't set up profile.");
                }else {
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    //User Name
                    TextView un = new TextView(context);
                    un.setTextAppearance(context, R.style.Green);
                    un.setTextSize(width / 30);
                    un.setText(userprofile.getString("business_name"));
                    un.setGravity(Gravity.CENTER);
                    ll.addView(un);

                    //
                    TextView br1 = new TextView(context);
                    br1.setText("");
                    ll.addView(br1);

                    //TableLayout
                    TableLayout tl_in = new TableLayout(context);
                    //Email
                    TableRow tr1 = new TableRow(context);
                    tr1.setPadding(0,10,0,0);
                    TextView email1 = new TextView(context);
                    email1.setGravity(Gravity.END);
                    email1.setPadding(0,0,50,0);
                    email1.setTextAppearance(context, R.style.Body);
                    email1.setTextSize(width / 40);
                    email1.setText(R.string.l_407_FriendshipProfile_Email_Label_0);
                    tr1.addView(email1);
                    TextView email = new TextView(context);
                    email.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    email.setTextAppearance(context, R.style.Normal);
                    email.setTextSize(width / 40);
                    if (userprofile.has("business_email")){
                        email.setText(userprofile.getString("business_email"));
                    }

                    tr1.addView(email);
                    tl_in.addView(tr1);
                    //Phone
                    TableRow tr2 = new TableRow(context);
                    tr2.setPadding(0,10,0,0);
                    TextView phone1 = new TextView(context);
                    phone1.setGravity(Gravity.END);
                    phone1.setPadding(0,0,50,0);
                    phone1.setTextAppearance(context, R.style.Body);
                    phone1.setTextSize(width / 40);
                    phone1.setText(R.string.l_407_FriendshipProfile_Phone_Label_0);
                    tr2.addView(phone1);
                    TextView phone = new TextView(context);
                    phone.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    phone.setTextAppearance(context, R.style.Normal);
                    phone.setTextSize(width / 40);
                    if (userprofile.has("business_phone")){
                        phone.setText(userprofile.getString("business_phone"));
                    }
                    tr2.addView(phone);
                    tl_in.addView(tr2);
                    //Address
                    TableRow tr3 = new TableRow(context);
                    tr3.setPadding(0,10,0,0);
                    TextView address1 = new TextView(context);
                    address1.setGravity(Gravity.END);
                    address1.setPadding(0,0,50,0);
                    address1.setTextAppearance(context, R.style.Body);
                    address1.setTextSize(width / 40);
                    address1.setText(R.string.l_407_FriendshipProfile_Address_Label_0);
                    tr3.addView(address1);
                    TextView address = new TextView(context);
                    address.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    address.setTextAppearance(context, R.style.Normal);
                    address.setTextSize(width / 40);
                    address.setText(userprofile.getString("business_street") + ", " + userprofile.getString("business_city")
                            + ", " + userprofile.getString("business_state") + ", " + userprofile.getString("business_zip"));
                    tr3.addView(address);
                    tl_in.addView(tr3);
                    //Website
                    TableRow tr4 = new TableRow(context);
                    tr4.setPadding(0,10,0,0);
                    TextView web1 = new TextView(context);
                    web1.setGravity(Gravity.END);
                    web1.setPadding(0,0,50,0);
                    web1.setTextAppearance(context, R.style.Body);
                    web1.setTextSize(width / 40);
                    web1.setText(R.string.l_407_FriendshipProfile_Website_Label_0);
                    tr4.addView(web1);
                    TextView web = new TextView(context);
                    web.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    web.setTextAppearance(context, R.style.Normal);
                    web.setTextSize(width / 40);
                    web.setText(userprofile.getString("business_website"));
                    tr4.addView(web);
                    tl_in.addView(tr4);

                    ll.addView(tl_in);

                    //
                    TextView br3 = new TextView(context);
                    br3.setText("\n");
                    ll.addView(br3);

                    //Request friend
                    LinearLayout ll_in = new LinearLayout(context);
                    ll_in.setGravity(Gravity.CENTER);
                    Button inbt = new Button(context);
                    inbt.setBackgroundResource(R.drawable.button_style);
                    inbt.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.7), (int)(height/15)));
                    inbt.setStateListAnimator(null);
                    inbt.setPadding(0, 0, 0, 1);
                    inbt.setTextAppearance(context, R.style.White);
                    inbt.setText(R.string.l_407_FriendshipProfile_RequestButton_Label_0);
                    inbt.setTextSize(width / 40);
                    inbt.setTransformationMethod(null);
                    inbt.setVisibility(View.INVISIBLE);
                    inbt.setOnClickListener(new requestFriendListener(inbt));
                    ll_in.addView(inbt);
                    ll.addView(ll_in);


                    ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                    lv.addView(ll);
                    tl.addView(lv);


                }
                setupUI(playout);

            }
        }.execute(AppCodeResources.postUrl("usdalogin", "get_userinfo_byusername", ht));
    }

    public static class requestFriendListener extends ClickOnceListener {
        public requestFriendListener(View button) {
            super(button);
        }
        public void action(){
            Hashtable<String,String> ht=new Hashtable<>();
            String token_s = UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("fname", getRecentPage().params.get("username"));
            new FetchTask(){
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    Toast toast = Toast.makeText(context, result.getString("results"), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }.execute(AppCodeResources.postUrl("usdafriendship", "friends_request_friendship", ht));
        }
    }

}
