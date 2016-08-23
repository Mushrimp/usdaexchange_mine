package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
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

import java.security.PrivilegedActionException;
import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_016_vendorpage extends PageOperations{
    public static void showVendorpage() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("vendorname", getRecentPage().params.get("username"));
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("vendorpageScrollTable");
                tl.removeAllViews();
                JSONObject vendorprofile = result.getJSONObject("vendorprofile");
                if (vendorprofile == null){
                    TextView non = new TextView(context);
                    non.setText("This vendor didn't set up profile.");
                }else {
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));
                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    //Business Name
                    TextView pn = new TextView(context);
                    pn.setTextAppearance(context, R.style.Green);
                    pn.setTextSize(width / 30);
                    pn.setText(vendorprofile.getString("business_name"));
                    pn.setGravity(Gravity.LEFT);
                    ll.addView(pn);

//                    //
//                    TextView br2 = new TextView(context);
//                    br2.setText("");
//                    ll.addView(br2);

                    //Buttons
                    LinearLayout ll_bt = new LinearLayout(context);
                    ll_bt.setPadding(0,20,0,0);
                    ll_bt.setOrientation(LinearLayout.HORIZONTAL);
                    Button rf = new Button(context);
                    rf.setLayoutParams(new LinearLayout.LayoutParams((int)(width / 3),(int)(height/18)));
                    rf.setBackgroundResource(R.drawable.button_style);
                    rf.setPadding(0,10,0,10);
                    //rf.setBackgroundColor(Color.parseColor("#1C8A3B"));
                    rf.setTextAppearance(context, R.style.White);
                    rf.setTextSize(width / 50);
                    rf.setText("Request friend");
                    rf.setTransformationMethod(null);
                    ll_bt.addView(rf);

                    rf.setOnClickListener(new requestFriendListener(rf));

                    //
                    TextView bl = new TextView(context);
                    bl.setPadding(20,0,20,0);
                    ll_bt.addView(bl);

                    //Add to list
                    Button al = new Button(context);
                    al.setLayoutParams(new LinearLayout.LayoutParams((int)(width / 3),(int)(height/18)));
                    al.setBackgroundResource(R.drawable.button_style);
                    al.setPadding(0,10,0,10);
                    al.setTextAppearance(context, R.style.White);
                    al.setTextSize(width / 50);
                    al.setText("Add to list");
                    al.setTransformationMethod(null);
                    ll_bt.addView(al);

                    ll.addView(ll_bt);

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
                    email1.setText("Email:");
                    tr1.addView(email1);
                    TextView email = new TextView(context);
                    email.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    email.setTextAppearance(context, R.style.Normal);
                    email.setTextSize(width / 40);
                    if (vendorprofile.has("business_email")){
                        email.setText(vendorprofile.getString("business_email"));
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
                    phone1.setText("Phone:");
                    tr2.addView(phone1);
                    TextView phone = new TextView(context);
                    phone.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    phone.setTextAppearance(context, R.style.Normal);
                    phone.setTextSize(width / 40);
                    if (vendorprofile.has("business_phone")){
                        phone.setText(vendorprofile.getString("business_phone"));
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
                    address1.setText("Address:");
                    tr3.addView(address1);
                    TextView address = new TextView(context);
                    address.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    address.setTextAppearance(context, R.style.Normal);
                    address.setTextSize(width / 40);
                    address.setText(vendorprofile.getString("business_street") + vendorprofile.getString("business_street") + ", "
                            + vendorprofile.getString("business_city") + ", " + vendorprofile.getString("business_state") + ", " + vendorprofile.getString("business_zip"));
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
                    web1.setText("Website:");
                    tr4.addView(web1);
                    TextView web = new TextView(context);
                    web.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    web.setTextAppearance(context, R.style.Normal);
                    web.setTextSize(width / 40);
                    web.setText(vendorprofile.getString("business_website"));
                    tr4.addView(web);
                    tl_in.addView(tr4);
                    //Social media
                    TableRow tr5 = new TableRow(context);
                    tr5.setPadding(0,10,0,0);
                    TextView media1 = new TextView(context);
                    media1.setGravity(Gravity.END);
                    media1.setPadding(0,0,50,0);
                    media1.setTextAppearance(context, R.style.Body);
                    media1.setTextSize(width / 40);
                    media1.setText("Facebook:");
                    tr5.addView(media1);
                    TextView media = new TextView(context);
                    media.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    media.setTextAppearance(context, R.style.Normal);
                    media.setTextSize(width / 40);
                    media.setText(vendorprofile.getString("business_facebook"));
                    tr5.addView(media);
                    tl_in.addView(tr5);
                    TableRow tr51 = new TableRow(context);
                    tr51.setPadding(0,10,0,0);
                    TextView media11 = new TextView(context);
                    media11.setGravity(Gravity.END);
                    media11.setPadding(0,0,50,0);
                    media11.setTextAppearance(context, R.style.Body);
                    media11.setTextSize(width / 40);
                    media11.setText("Twitter:");
                    tr51.addView(media11);
                    TextView media12 = new TextView(context);
                    media12.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    media12.setTextAppearance(context, R.style.Normal);
                    media12.setTextSize(width / 40);
                    media12.setText(vendorprofile.getString("business_twitter"));
                    tr51.addView(media12);
                    tl_in.addView(tr51);
                    //Organic
                    TableRow tr6 = new TableRow(context);
                    tr6.setPadding(0,10,0,10);
                    TextView org1 = new TextView(context);
                    org1.setGravity(Gravity.END);
                    org1.setPadding(0,0,50,0);
                    org1.setLayoutParams(new TableRow.LayoutParams((int) (width * 3 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    org1.setTextAppearance(context, R.style.Body);
                    org1.setTextSize(width / 40);
                    org1.setText("Organic:");
                    tr6.addView(org1);
                    LinearLayout ll_in = new LinearLayout(context);
                    ImageView org = new ImageView(context);
                    org.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    String data = "business_usdaorganic";
                    Object json = new JSONTokener(data).nextValue();
                    if(json instanceof JSONArray){
                        JSONArray organic = vendorprofile.getJSONArray("business_usdaorganic");
                        if (organic.getString(0).equals("yes")) {
                            org.setImageResource(R.drawable.usda_organic);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(50, 50);
                            parms.gravity = Gravity.START;
                            org.setLayoutParams(parms);
                        }
                        ll_in.addView(org);
                    }
                    tr6.addView(ll_in);
                    tl_in.addView(tr6);

                    ll.addView(tl_in);

                    //
                    TextView br3 = new TextView(context);
                    br3.setText("");
                    ll.addView(br3);

                    ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                    lv.addView(ll);
                    tl.addView(lv);

                    //Button
                    TableRow bt1 = new TableRow(context);
                    bt1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    bt1.setBackgroundResource(R.drawable.row_border);
                    RelativeLayout ll_arrow = new RelativeLayout(context);
                    ll_arrow.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    TextView pl=new TextView(context);
                    RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams((int)(width*0.8),TableRow.LayoutParams.WRAP_CONTENT);
                    parms1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    pl.setLayoutParams(parms1);
                    pl.setTextAppearance(context, R.style.DarkBule);
                    pl.setText(R.string.l_016_VendorMainpage_ProductsPost_Label_0);
                    pl.setTextSize(width / 40);
                    pl.setPadding(30,20,0,20);
                    ll_arrow.addView(pl);
                    ImageView i1= new ImageView(context);
                    i1.setImageResource(R.drawable.next_item);
                    i1.setPadding(0,0,10,0);
                    RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(50, 50);
                    parms2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    parms2.addRule(RelativeLayout.CENTER_VERTICAL);
                    i1.setLayoutParams(parms2);
                    ll_arrow.addView(i1);
                    bt1.addView(ll_arrow);
                    tl.addView(bt1);

                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Hashtable<String,String> ht=new Hashtable<String, String>();
                            ht.put("username",getRecentPage().params.get("username"));
                            pushNewPage(R.array.page_015_productlist_byvendor,ht);
                        }
                    });

                    TableRow bt2 = new TableRow(context);
                    bt2.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    bt2.setBackgroundResource(R.drawable.row_border);
                    RelativeLayout ll_arrow2 = new RelativeLayout(context);
                    ll_arrow2.setLayoutParams(new TableRow.LayoutParams((int)(width*0.9), ViewGroup.LayoutParams.WRAP_CONTENT));
                    TextView vm=new TextView(context);
                    RelativeLayout.LayoutParams parms3 = new RelativeLayout.LayoutParams((int)(width*0.8),TableRow.LayoutParams.WRAP_CONTENT);
                    parms3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    vm.setLayoutParams(parms3);
                    vm.setTextAppearance(context, R.style.DarkBule);
                    vm.setText(R.string.l_016_VendorMainpage_Market_Label_0);
                    vm.setTextSize(width / 40);
                    vm.setPadding(30,20,0,20);
                    ll_arrow2.addView(vm);
                    ImageView i2= new ImageView(context);
                    i2.setImageResource(R.drawable.next_item);
                    i2.setPadding(0,0,10,0);
                    RelativeLayout.LayoutParams parms4 = new RelativeLayout.LayoutParams(50, 50);
                    parms4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    parms4.addRule(RelativeLayout.CENTER_VERTICAL);
                    i2.setLayoutParams(parms4);
                    ll_arrow2.addView(i2);
                    bt2.addView(ll_arrow2);
                    tl.addView(bt2);

                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Hashtable<String,String> ht=new Hashtable<String, String>();
                            ht.put("username",getRecentPage().params.get("username"));
                            pushNewPage(R.array.page_010_marketlist_byvendor,ht);
                        }
                    });

                }
                setupUI(playout);

            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_vendorprofile_bypost", ht));
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
