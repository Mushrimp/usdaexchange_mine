package com.mahoneydev.usdafmexchange;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by mahoneydev on 5/23/2016.
 */
public class PageOperations {
    public static Frontpage context = null;
    public static Resources res = null;
    private static View playout=null;
    private static Hashtable<String, View> hashelements;
    private static List<PageNode> pageHistory=new ArrayList<PageNode>();
    public static int height=0;
    public static int width=0;
    public static void setPage(int page_id, Hashtable<String,String> ht){
        if (context!=null)
            context.switchContent(page_id, ht);
    }
    public static void setMenu(int menu_id){
        if (context!=null)
            context.switchMenu(menu_id);
    }
    public static void generateTitle(int code, LinearLayout toolbar){
        toolbar.removeAllViewsInLayout();
        switch (code){
            case (R.array.page_001_front):
            {
                ImageView iv=new ImageView(context);
                iv.setImageResource(R.drawable.fme_header);
                toolbar.addView(iv);
                break;
            }
            case (R.array.page_102_login):
            {
                TextView tv = new TextView(context);
                tv.setText("Login");
                toolbar.addView(tv);
                break;
            }
        }
    }
    public static void generateLayout(int code, LinearLayout layout, Hashtable<String,String> params) {
        if ((res == null) || (context == null))
            return;
        TypedArray pageArray = res.obtainTypedArray(code);
        layout.removeAllViewsInLayout();
        playout=layout;
        try {
            hashelements = new Hashtable<>();
            for (int i = 0; i < pageArray.length(); i++) {
                String elements = pageArray.getString(i);
                JSONObject jsonelements = new JSONObject(elements);
                if (jsonelements.has("element")) {
                    String element = jsonelements.getString("element");
                    if (element.equals("TextView")) {
                        TextView tv = new TextView(context);
                        tv.setText(jsonelements.getString("value"));
                        if (jsonelements.has("inputtype")) {
                        }
                        hashelements.put(jsonelements.getString("id"), tv);
                        tv.setVisibility(View.INVISIBLE);
                        layout.addView(tv);

                    } else if (element.equals("EditText")) {
                        EditText et = new EditText(context);
                        et.setHint(jsonelements.getString("value"));
                        if (jsonelements.has("inputtype")) {
                            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        hashelements.put(jsonelements.getString("id"), et);
                        et.setVisibility(View.INVISIBLE);
                        et.setBackgroundResource(R.drawable.rounded_text);
                        layout.addView(et);
                    }else if (element.equals("AutoCompleteTextView")) {
                        AutoCompleteTextView et = new AutoCompleteTextView(context);
                        et.setHint(jsonelements.getString("value"));
                        if (jsonelements.has("inputtype")) {
                            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        hashelements.put(jsonelements.getString("id"), et);
                        et.setVisibility(View.INVISIBLE);
                        et.setBackgroundResource(R.drawable.rounded_text);
                        layout.addView(et);
                    } else if (element.equals("Button"))
                    {
                        Button bt = new Button(context);
                        bt.setText(jsonelements.getString("value"));

                        hashelements.put(jsonelements.getString("id"), bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setBackgroundResource(R.drawable.button_600x50);
                        bt.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                        bt.setPadding(15,0,0,0);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        bt.setTextSize(width/50);
                        bt.setTextAppearance(context,R.style.QText);
                        bt.setTransformationMethod(null);
                        bt.setVisibility(View.INVISIBLE);
                        layout.addView(bt);
                    }
                    else if (element.equals("ImageView"))
                    {
                        ImageView iv=new ImageView(context);
                        iv.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"),iv);
                        int width = 300;
                        int height = 300;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                        parms.gravity=Gravity.CENTER;
                        iv.setLayoutParams(parms);
                        iv.setImageResource(R.drawable.blank_profile);
                        layout.addView(iv);
                    }
                    else if (element.equals("SearchView"))
                    {
                        LinearLayout ll=new LinearLayout(context);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        EditText et = new EditText(context);
                        et.setHint(jsonelements.getString("value"));
                        hashelements.put(jsonelements.getString("id") + "Input", et);
                        et.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));

                        ll.addView(et);
                        ImageButton bt = new ImageButton(context);
                        bt.setImageResource(R.drawable.ic_menu_manage);
                        hashelements.put(jsonelements.getString("id") + "Button", bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                        ll.addView(bt);
                        ll.setVisibility(View.INVISIBLE);
                        layout.addView(ll);
                    }else if (element.equals("ScrollView"))
                    {
                        ScrollView sv=new ScrollView(context);
                        hashelements.put(jsonelements.getString("id"),sv);
                        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        sv.setVisibility(View.INVISIBLE);
                        TableLayout tl=new TableLayout(context);
                        hashelements.put(jsonelements.getString("id")+"Table",tl);
                        tl.setLayoutParams(new ScrollView.LayoutParams( ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
                        tl.setVisibility(View.INVISIBLE);
                        sv.addView(tl);
                        layout.addView(sv);
                    }else if (element.equals("Spinner"))
                    {
                        Spinner sp=new Spinner(context);
                        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        sp.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"),sp);
                        layout.addView(sp);
                    }else if (element.equals("CheckBox"))
                    {
                        CheckBox cb=new CheckBox(context);
                        cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        cb.setText(jsonelements.getString("value"));
                        cb.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), cb);
                        layout.addView(cb);
                    }
                }
            }
            renderUI(code,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setButtonAction(String action, ImageButton bt) {
        if (action.equals("searchPublicPost"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showpublicposts((((EditText) hashelements.get("searchpublicpostsInput")).getText()).toString());
                }
            });
        }else if (action.equals("testSearch"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String k=(((EditText) hashelements.get("testSearchInput")).getText()).toString();
                    ((TextView)hashelements.get("testView")).setText(k);
                }
            });
        }
    }
    private static void setButtonAction(String action, Button bt){
        if (action.equals("loginSubmit")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username_s=(((EditText)hashelements.get("usernameInput")).getText()).toString();
                    Log.d("username", username_s);
                    final String password_s=(((EditText)hashelements.get("passwordInput")).getText()).toString();
                    Log.d("password",password_s);
                    String token_s=UserFileUtility.get_token();
                    if (token_s.equals(""))
                    {
                        ((TextView)hashelements.get("loginErrorView")).setText("Network Error!");
                        return;
                    }
                    UserFileUtility.set_userlogininfo(username_s,password_s);
                    Hashtable<String,String> ht=new Hashtable<String, String>();
                    ht.put("username",username_s);
                    ht.put("password", password_s);
                    ht.put("os", "Android");
                    ht.put("token",token_s);
                    new FetchTask(){
                        @Override
                        protected void onPostExecute(JSONObject result)
                        {
                            try {
                                Log.d("Error", result.getString("error"));
                                String error=result.getString("error");
                                if (error.equals("-9"))
                                {
                                    UserFileUtility.save_userinfo();
                                    setPage(R.array.page_001_front,null);
                                    ((TextView)context.findViewById(R.id.username_menu_display)).setText(UserFileUtility.get_username());
                                    setMenu(R.id.login_vendor);

                                }
                                else
                                {
                                    ((TextView)hashelements.get("loginErrorView")).setText("Password Error!");
                                    UserFileUtility.clean_userinfo();
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }.execute(AppCodeResources.postUrl("usdamobile", "mobile_login", ht));
                }
            });
        }
        else if (action.equals("selectImage")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    context.startActivityForResult(intent, AppCodeResources.IMAGE_UPLOAD);
                }
            });
        }

        //post price
        else if(action.equals("newpost"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_322_newpost, null));
                    setPage(R.array.page_322_newpost, null);
                }
            });
        }
        else if(action.equals("posttemplate"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_324_posttemplate, null));
                    setPage(R.array.page_324_posttemplate, null);
                }
            });
        }
        else if(action.equals("postschedule"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_328_postschedule, null));
                    setPage(R.array.page_328_postschedule, null);
                }
            });
        }
        else if(action.equals("postpublish"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_330_postpublish, null));
                    setPage(R.array.page_330_postpublish, null);
                }
            });
        }

        //vendor profile
        else if(action.equals("nameaddress"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_205_nameaddress, null));
                    setPage(R.array.page_205_nameaddress, null);
                }
            });
        }
        else if(action.equals("phoneemail"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_206_phoneemail, null));
                    setPage(R.array.page_206_phoneemail, null);
                }
            });
        }
        else if(action.equals("website"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_207_website, null));
                    setPage(R.array.page_207_website, null);
                }
            });
        }
        else if(action.equals("certified"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_209_certified, null));
                    setPage(R.array.page_209_certified, null);
                }
            });
        }
        else if(action.equals("farmermarket"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_309_farmermarket, null));
                    setPage(R.array.page_309_farmermarket, null);
                }
            });
        }
        else if(action.equals("productsell"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_305_productsell, null));
                    setPage(R.array.page_305_productsell, null);
                }
            });
        }

        //social network
        else if(action.equals("friendship"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_401_friendship, null));
                    setPage(R.array.page_401_friendship, null);
                }
            });
        }
        else if(action.equals("request"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_412_request, null));
                    setPage(R.array.page_412_request, null);
                }
            });
        }
        else if(action.equals("message"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_402_message, null));
                    setPage(R.array.page_402_message, null);
                }
            });
        }
        else if(action.equals("notification"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_404_notification, null));
                    setPage(R.array.page_404_notification, null);
                }
            });
        }

        //account and setting
        else if(action.equals("accountinfo"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_105_accountinfo, null));
                    setPage(R.array.page_105_accountinfo, null);
                }
            });
        }
        else if(action.equals("uploadlogo"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_106_uploadlogo, null));
                    setPage(R.array.page_106_uploadlogo, null);
                }
            });
        }
        else if(action.equals("qrcode"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_109_qrcode, null));
                    setPage(R.array.page_109_qrcode, null);
                }
            });
        }
        else if(action.equals("searchpreference"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_111_searchpreference, null));
                    setPage(R.array.page_111_searchpreference, null);
                }
            });
        }
        else if(action.equals("setnotification"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_107_setnotification, null));
                    setPage(R.array.page_107_setnotification, null);
                }
            });
        }
        else if(action.equals("socialnetwork"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_108_socialnetwork, null));
                    setPage(R.array.page_108_socialnetwork, null);
                }
            });
        }
        else if(action.equals("deleteaccount"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_112_deleteaccount, null));
                    setPage(R.array.page_112_deleteaccount, null);
                }
            });
        }
        //Account and Settings
        else if(action.equals("searchdistance")){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(new PageNode(R.array.page_121_searchdistance,null));
                    setPage(R.array.page_121_searchdistance,null);
                }
            });
        }
        else if(action.equals("preferproduct")){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(new PageNode(R.array.page_122_preferproduct,null));
                    setPage(R.array.page_122_preferproduct,null);
                }
            });
        }
        else if(action.equals("prefervendor")){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(new PageNode(R.array.page_123_prefervendor,null));
                    setPage(R.array.page_123_prefervendor,null);
                }
            });
        }
        else if(action.equals("prefermarket")){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(new PageNode(R.array.page_124_prefermarket,null));
                    setPage(R.array.page_124_prefermarket,null);
                }
            });
        }

        else if (action.equals("scanQR"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator qrscanner=new IntentIntegrator(context);
                    qrscanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    qrscanner.initiateScan();
                    context.startActivityForResult(qrscanner.createScanIntent(), AppCodeResources.SCAN_QR);
//                    try {
//                        //start the scanning activity from the com.google.zxing.client.android.SCAN intent
//                        Intent intent = new Intent(AppCodeResources.ACTION_SCAN);
//                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//                        context.startActivityForResult(intent, AppCodeResources.SCAN_QR);
//                    } catch (ActivityNotFoundException e) {
//                        //on catch, show the download dialog
//                        context.showDialog(context, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
//                    }
                }
            });
        }
        else if (action.equals("TestBegin"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean check=((CheckBox)hashelements.get("testCheckbox")).isChecked();
                    ((CheckBox)hashelements.get("testCheckbox")).setChecked(!check);
                    String input1=((EditText)hashelements.get("testInput")).getText().toString();
                    ((EditText)hashelements.get("testInput")).setText(input1 + " " + input1);
                    SpinnerElement spinner1=(SpinnerElement) (((Spinner)hashelements.get("testSpinner")).getSelectedItem());
                    ((TextView)hashelements.get("testView")).setText("Selected: "+spinner1.getName()+", Postion: "+spinner1.getValue());
                }
            });
        }
        else if(action.equals("addproduct"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(new PageNode(R.array.page_306_addproductform, null));
                    setPage(R.array.page_306_addproductform, null);
                }
            });
        }
        else if(action.equals("saveproduct"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String productcategoryid=((SpinnerElement)(((Spinner)hashelements.get("categorySpinner")).getSelectedItem())).getValue();
                    String productname=((AutoCompleteTextView)hashelements.get("productnameInput")).getText().toString();
                    String unit=((AutoCompleteTextView)hashelements.get("unitInput")).getText().toString();
                    String organic="";
                    if (((CheckBox)hashelements.get("organicCheckbox")).isChecked())
                        organic="yes";
                    String other="";
                    if (((CheckBox)hashelements.get("otherCheckbox")).isChecked())
                        other="yes";
                    String otherdesc=((EditText)hashelements.get("otherorganicInput")).getText().toString();
                    boolean flag=true;
                    TextView errortv=((TextView)hashelements.get("producterrorView"));
                    if (productcategoryid.equals("0"))
                    {
                        errortv.setText("Please Select a Product Category");
                        flag=false;
                    }
                    else if (productname==null||productname.equals(""))
                    {
                        errortv.setText("Please Input Product Name");
                        flag=false;
                    }
                    else if (unit==null||unit.equals(""))
                    {
                        errortv.setText("Please Input Unit");
                        flag=false;
                    }
                    if (flag)
                    {
                        //Build Post Data
                        Hashtable<String,String> postdataht=new Hashtable<String, String>();
                        postdataht.put("Prd_Category1",productcategoryid);
                        postdataht.put("Prd_Name",productname);
                        postdataht.put("Prd_Unit",unit);
                        postdataht.put("Prd_organic_usda",organic);
                        postdataht.put("Prd_organic_other",other);
                        postdataht.put("Prd_organic_other_desc",otherdesc);
                        String jsonpostdata=(new JSONObject(postdataht)).toString();

                        Hashtable<String,String> ht=new Hashtable<String, String>();
                        String token_s=UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token",token_s);
                        ht.put("postdata",jsonpostdata);
                        ht.put("formargs","2");
                        new FetchTask(){
                            @Override
                            protected void onPostExecute(JSONObject result)
                            {
                                try {
                                    Log.d("Error", result.getString("error"));
                                    String error=result.getString("error");
                                    if (error.equals("-9"))
                                    {
                                        ((TextView)hashelements.get("producterrorView")).setText("Success!");
                                        removeRecentPage();
                                        PageNode k= getRecentPage();
                                        setPage(k.pageId,k.params);
                                    }
                                    else
                                    {
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_product_addto_list", ht));
                    }
                }
            });
        }
    }

    private static void renderUI(int code, Hashtable<String,String> params){
        if (code== R.array.page_001_front)
        {
            showpublicposts("");
        }else if (code== R.array.page_020_viewpost)
        {
            ((TextView)hashelements.get("postView")).setText("This is post "+params.get("postid"));
            setupUI(playout);
        }
        else if (code== R.array.page_106_uploadlogo)
        {
            Hashtable<String,String> ht=new Hashtable<String, String>();
            String token_s=UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token",token_s);
            new FetchTask(){
                @Override
                protected void onPostExecute(JSONObject result)
                {
                    try {
                        Log.d("Error", result.getString("error"));
                        String error=result.getString("error");
                        if (error.equals("-9"))
                        {
                            String imageurl=result.getString("avatar_url");
                            imageurl=imageurl.replace("\\","");
                            LoadImage li=new LoadImage();
                            li.img=(ImageView) hashelements.get("logoView");
                            li.execute(imageurl);
                        }
                        else
                        {
                        }
                        setupUI(playout);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

            }.execute(AppCodeResources.postUrl("usdamobile", "get_avatarurl", ht));
        }else if (code== R.array.page_407_profile) {
            ((TextView) hashelements.get("nameView")).setText("Name: " + params.get("friendname"));
            setupUI(playout);
        }else if (code==R.array.page_306_addproductform){
            Hashtable<String,String> ht=new Hashtable<String, String>();
            String token_s=UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ((Spinner)hashelements.get("categorySpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i!=0) {
                        SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                        Hashtable<String,String> ht=new Hashtable<String, String>();
                        String token_s=UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token", token_s);
                        ht.put("catetoryid",selecteditem.getValue());
                        new FetchTask(){
                            @Override
                            protected void onPostExecute(JSONObject result)
                            {
                                try {
                                    Log.d("Error", result.getString("error"));
                                    String error=result.getString("error");
                                    if (error.equals("-9"))
                                    {
                                        JSONArray ja=result.getJSONArray("results");
                                        String[] arrayString = new String[ja.length()];
                                        for (int i=0;i<ja.length();i++)
                                        {
                                            JSONObject jsonobject=ja.getJSONObject(i);
                                            arrayString[i]=jsonobject.getString("label");
                                        }
                                        MatchAdapter adapter = new MatchAdapter(context,
                                                android.R.layout.simple_spinner_item, arrayString);
                                        AutoCompleteTextView actv=((AutoCompleteTextView)hashelements.get("productnameInput"));
                                        actv.setAdapter(adapter);
                                        actv.setThreshold(2);
                                    }
                                    else
                                    {
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductbycatetory", ht));
                    }
                    else
                    {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, new String[0]);
                        AutoCompleteTextView actv=((AutoCompleteTextView)hashelements.get("productnameInput"));
                        actv.setAdapter(adapter);
                        actv.setThreshold(2);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            new FetchTask(){
                @Override
                protected void onPostExecute(JSONObject result)
                {
                    try {
                        Log.d("Error", result.getString("error"));
                        String error=result.getString("error");
                        if (error.equals("-9"))
                        {
                            JSONArray ja=result.getJSONArray("results");
                            SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()+1];
                            arraySpinner[0]=new SpinnerElement("Select a Category","0");
                            for (int i=0;i<ja.length();i++)
                            {
                                JSONObject jsonobject=ja.getJSONObject(i);
                                arraySpinner[i+1]=new SpinnerElement(jsonobject.getString("Prd_Category1"),jsonobject.getString("Prd_Cat_ID1"));
                            }

                            ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                                    android.R.layout.simple_spinner_item, arraySpinner);
                            ((Spinner)hashelements.get("categorySpinner")).setAdapter(adapter);
                        }
                        else
                        {
                        }
                        Hashtable<String,String> ht=new Hashtable<String, String>();
                        String token_s=UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token", token_s);
                        new FetchTask(){
                            @Override
                            protected void onPostExecute(JSONObject result)
                            {
                                try {
                                    Log.d("Error", result.getString("error"));
                                    String error=result.getString("error");
                                    if (error.equals("-9"))
                                    {
                                        JSONArray ja=result.getJSONArray("results");
                                        String[] arrayString = new String[ja.length()];
                                        for (int i=0;i<ja.length();i++)
                                        {
                                            JSONObject jsonobject=ja.getJSONObject(i);
                                            arrayString[i]=jsonobject.getString("label");
                                        }
                                        MatchAdapter adapter = new MatchAdapter(context,
                                                android.R.layout.simple_spinner_item, arrayString);
                                        AutoCompleteTextView actv=((AutoCompleteTextView)hashelements.get("unitInput"));
                                        actv.setAdapter(adapter);
                                        actv.setThreshold(2);
                                    }
                                    else
                                    {
                                    }
                                    setupUI(playout);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }.execute(AppCodeResources.postUrl("usdatestyue", "autocomplete_getproductunit", ht));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

            }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));
        }else if (code== R.array.page_777_test) {
            Hashtable<String,String> ht=new Hashtable<String, String>();
            String token_s=UserFileUtility.get_token();
            ht.put("os", "Android");
            ht.put("token", token_s);
            new FetchTask(){
                @Override
                protected void onPostExecute(JSONObject result)
                {
                    try {
                        Log.d("Error", result.getString("error"));
                        String error=result.getString("error");



                        if (error.equals("-9"))
                        {
                            JSONArray ja=result.getJSONArray("results");
                            SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                            for (int i=0;i<ja.length();i++)
                            {
                                JSONObject jsonobject=ja.getJSONObject(i);
                                arraySpinner[i]=new SpinnerElement(jsonobject.getString("Prd_Category1"),jsonobject.getString("Prd_Cat_ID1"));
                            }

                            ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                                    android.R.layout.simple_spinner_item, arraySpinner);
                            ((Spinner)hashelements.get("testSpinner")).setAdapter(adapter);
                        }
                        else
                        {
                        }
                        setupUI(playout);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }.execute(AppCodeResources.postUrl("usdatestyue", "get_product_category", ht));
        }else  if (code == R.array.page_401_friendship){
            showfriends();
        }
        else if (code == R.array.page_309_farmermarket){
            showmarkets();
        }
        else if(code == R.array.page_305_productsell){
            showproducts();
            //setupUI(playout);
        }
        else if (code == R.array.page_324_posttemplate){
            showtemplate();
        }
        else if (code == R.array.page_328_postschedule){
            showschedule();
        }
        else if (code == R.array.page_330_postpublish){
            showpublished();
        }
        else if (code == R.array.page_122_preferproduct){
            showPreferProduct();
        }
        else if (code == R.array.page_123_prefervendor){
            showPreferVendor();
        }
        else if (code == R.array.page_124_prefermarket){
            showPreferMarket();
        }
        else
            setupUI(playout);
    }
    private static void showfriends(){
        String token_s=UserFileUtility.get_token();
        Hashtable<String,String> ht=new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result) {
                try
                {
                    Log.d("Error", result.getString("error"));
                    String error=result.getString("error");
                    if (error.equals("-9")) {
                        TableLayout tl = (TableLayout) hashelements.get("friendshipScrollTable");
                        tl.removeAllViews();
                        JSONArray allfriends=result.getJSONArray("results");
                        for (int i=0;i<allfriends.length();i++)
                        {
                            JSONObject friend=allfriends.getJSONObject(i);
                            TableRow lv=new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));
                            //Logo
                            ImageView logo=new ImageView(context);
                            String vendorlogohtml=friend.getString("avatar");
                            int urlstart=vendorlogohtml.indexOf("src=\"")+"src=\"".length();
                            int urlend=urlstart;
                            for (int j=urlstart;vendorlogohtml.charAt(j)!='\"';j++)
                            {
                                urlend=j;
                            }
                            String vendorlogourl=vendorlogohtml.substring(urlstart,urlend+1);
                            Log.d("LOGOURL",vendorlogourl);
                            LoadImage li=new LoadImage();
                            li.img=logo;
                            li.execute(vendorlogourl);
                            logo.setLayoutParams(new TableRow.LayoutParams(0, height/5, 0.3f));
                            lv.addView(logo);

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Name
                            TextView name=new TextView(context);
                            name.setText(friend.getString("displayname"));
                            name.setTextAppearance(context,R.style.Large);
                            name.setTextSize(width/50);
                            ll.addView(name);
                            //Business Name
                            final TextView bn=new TextView(context);
                            bn.setText(friend.getString("businessname"));
                            ll.addView(bn);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, height/5, 0.7f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.BLACK);
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.BLACK);
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else
                    {
                    }
                    setupUI(playout);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdafriendship", "friends_list_all_byuser", ht));
    }


    private static void showmarkets(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("farmermarketScrollTable");
                        tl.removeAllViews();
                        JSONArray allmarkets = result.getJSONArray("results");
                        for (int i=0; i<allmarkets.length();i++){
                            JSONObject market = allmarkets.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Market Name
                            TextView marketnamet=new TextView(context);
                            marketnamet.setTextAppearance(context,R.style.Title);
                            marketnamet.setTextSize(width/50);
                            marketnamet.setText("Market Name:");
                            ll.addView(marketnamet);
                            TextView marketname=new TextView(context);
                            marketname.setTextAppearance(context,R.style.Bold);
                            marketname.setTextSize(width/45);
                            marketname.setText(market.getString("MarketName"));
                            ll.addView(marketname);
                            //Market Location
                            TextView marketlocationt=new TextView(context);
                            marketlocationt.setTextAppearance(context,R.style.Title);
                            marketlocationt.setTextSize(width/50);
                            marketlocationt.setText("Address:");
                            ll.addView(marketlocationt);
                            TextView marketlocation=new TextView(context);
                            marketlocation.setTextAppearance(context,R.style.Body);
                            marketlocation.setTextSize(width/50);
                            marketlocation.setText(market.getString("Market_location"));
                            ll.addView(marketlocation);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_markets_listall_byuser", ht));
    }


    private static void showproducts(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("productsellScrollTable");
                        tl.removeAllViews();
                        JSONArray allproducts = result.getJSONArray("results");
                        for (int i=0; i<allproducts.length();i++){
                            JSONObject product = allproducts.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            //Product content
                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Category
                            TextView category1=new TextView(context);
                            category1.setTextAppearance(context,R.style.Title);
                            category1.setTextSize(width/50);
                            String categoryt = "Category:";
                            category1.setText(categoryt);
                            ll.addView(category1);
                            TextView category=new TextView(context);
                            String categoryline = product.getString("Prd_Category1");
                            category.setTextAppearance(context,R.style.Bold);
                            category.setTextSize(width/45);
                            category.setText(categoryline);
                            ll.addView(category);
                            //Product Name
                            TextView pn1=new TextView(context);
                            pn1.setTextAppearance(context,R.style.Title);
                            pn1.setTextSize(width/50);
                            String productt = "Product:";
                            pn1.setText(productt);
                            ll.addView(pn1);
                            TextView pn=new TextView(context);
                            String productline = product.getString("product_name");
                            pn.setTextAppearance(context,R.style.Normal);
                            pn.setTextSize(width/45);
                            pn.setText(productline);
                            ll.addView(pn);

                            //Unit
                            LinearLayout ll_in1 = new LinearLayout(context);
                            ll_in1.setOrientation(LinearLayout.HORIZONTAL);
                            TextView unit1=new TextView(context);
                            unit1.setTextAppearance(context,R.style.Title);
                            unit1.setTextSize(width/50);
                            String unitt = "Unit:          ";
                            unit1.setText(unitt);
                            ll_in1.addView(unit1);
                            TextView unit=new TextView(context);
                            String unitline = product.getString("productunit_name");
                            unit.setTextAppearance(context,R.style.Body);
                            unit.setTextSize(width/45);
                            unit.setText(unitline);
                            ll_in1.addView(unit);
                            ll.addView(ll_in1);

                            //Organic
                            LinearLayout ll_in2 = new LinearLayout(context);
                            ll_in2.setOrientation(LinearLayout.HORIZONTAL);
                            TextView organic1=new TextView(context);
                            organic1.setTextAppearance(context,R.style.Title);
                            organic1.setTextSize(width/50);
                            String organict = "Organic:    ";
                            organic1.setText(organict);
                            ll_in2.addView(organic1);
                            ImageView organic=new ImageView(context);
                            String organicline = product.getString("organic_usda");
                            if(organicline.equals("yes")){
                                organic.setImageResource(R.drawable.usda_organic);
                                int width = 50;
                                int height = 50;
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                                parms.gravity=Gravity.START;
                                organic.setLayoutParams(parms);
                            }
                            ll_in2.addView(organic);
                            ll.addView(ll_in2);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_product_listall_byuser", ht));
    }

    private static void showtemplate(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("posttemplateScrollTable");
                        tl.removeAllViews();
                        JSONArray alltemplates = result.getJSONArray("results");
                        for (int i=0; i<alltemplates.length();i++){
                            JSONObject template = alltemplates.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                            //Template content
                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Product Name
                            TextView pn1=new TextView(context);
                            pn1.setTextAppearance(context,R.style.Title);
                            pn1.setTextSize(width/50);
                            String pntitle = "Product:";
                            pn1.setText(pntitle);
                            ll.addView(pn1);
                            TextView pn=new TextView(context);
                            pn.setTextAppearance(context,R.style.Bold);
                            pn.setTextSize(width/45);
                            pn.setText(template.getString("price_product_name"));
                            ll.addView(pn);
                            //Market Name
                            TextView mn1=new TextView(context);
                            mn1.setTextAppearance(context,R.style.Title);
                            mn1.setTextSize(width/50);
                            String mntitle = "Market:";
                            mn1.setText(mntitle);
                            ll.addView(mn1);
                            TextView mn=new TextView(context);
                            mn.setTextAppearance(context,R.style.Body);
                            mn.setTextSize(width/45);
                            mn.setText(template.getString("price_market_name"));
                            ll.addView(mn);
                            //Description
                            TextView desc1=new TextView(context);
                            desc1.setTextAppearance(context,R.style.Title);
                            desc1.setTextSize(width/50);
                            String desct = "Description:";
                            desc1.setText(desct);
                            ll.addView(desc1);
                            TextView desc=new TextView(context);
                            desc.setTextAppearance(context,R.style.Body);
                            desc.setTextSize(width/45);
                            desc.setText(template.getString("price_ad_desc"));
                            ll.addView(desc);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_template_list_byuser", ht));
    }

    private static void showschedule(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        ht.put("posttype","scheduled");
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("postscheduleScrollTable");
                        tl.removeAllViews();
                        JSONArray allschedule = result.getJSONArray("results");
                        for (int i=0; i<allschedule.length();i++){
                            JSONObject schedule = allschedule.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Price Product Name
                            TextView nameprice=new TextView(context);
                            nameprice.setTextAppearance(context,R.style.Bold);
                            nameprice.setTextSize(width/35);
                            String name = schedule.getString("price_product_name") + " $" + schedule.getString("price_price") + " per " + schedule.getString("price_productunit_name");
                            nameprice.setText(name);
                            ll.addView(nameprice);
                            //Market Date
                            LinearLayout lh1=new LinearLayout(context);
                            lh1.setOrientation(LinearLayout.HORIZONTAL);
                            TextView marketdatet=new TextView(context);
                            marketdatet.setTextAppearance(context,R.style.Title);
                            marketdatet.setTextSize(width/45);
                            String mdt = "Date:   ";
                            marketdatet.setText(mdt);
                            lh1.addView(marketdatet);
                            TextView marketdate=new TextView(context);
                            marketdate.setTextAppearance(context,R.style.Body);
                            marketdate.setTextSize(width/45);
                            String md = schedule.getString("price_market_date");
                            marketdate.setText(md);
                            lh1.addView(marketdate);
                            ll.addView(lh1);
                            //Published Date
                            LinearLayout lh2=new LinearLayout(context);
                            lh2.setOrientation(LinearLayout.HORIZONTAL);
                            TextView publisheddatet=new TextView(context);
                            publisheddatet.setTextAppearance(context,R.style.Title);
                            publisheddatet.setTextSize(width/45);
                            String pdt = "Published On:   ";
                            publisheddatet.setText(pdt);
                            lh2.addView(publisheddatet);
                            TextView publisheddate=new TextView(context);
                            publisheddate.setTextAppearance(context,R.style.Body);
                            publisheddate.setTextSize(width/45);
                            String pd = schedule.getString("price_publish_date");
                            publisheddate.setText(pd);
                            lh2.addView(publisheddate);
                            ll.addView(lh2);
                            //Market Name
                            TextView marketname=new TextView(context);
                            marketname.setTextAppearance(context,R.style.Body);
                            marketname.setTextSize(width/45);
                            String mn = "@ " + schedule.getString("price_market_name");
                            marketname.setText(mn);
                            ll.addView(marketname);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_byuser", ht));

    }


    private static void showpublished(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        ht.put("posttype","published");
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("postpublishScrollTable");
                        tl.removeAllViews();
                        JSONArray allpublished = result.getJSONArray("results");
                        for (int i=0; i<allpublished.length();i++){
                            JSONObject published = allpublished.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Price Product Name
                            TextView nameprice=new TextView(context);
                            nameprice.setTextAppearance(context,R.style.Bold);
                            nameprice.setTextSize(width/40);
                            String name = published.getString("price_product_name") + " $" + published.getString("price_price") + " per " + published.getString("price_productunit_name");
                            nameprice.setText(name);
                            ll.addView(nameprice);
                            //Market Date
                            LinearLayout lh1=new LinearLayout(context);
                            lh1.setOrientation(LinearLayout.HORIZONTAL);
                            TextView marketdatet=new TextView(context);
                            marketdatet.setTextAppearance(context,R.style.Title);
                            marketdatet.setTextSize(width/45);
                            String mdt = "Market Date:   ";
                            marketdatet.setText(mdt);
                            lh1.addView(marketdatet);
                            TextView marketdate=new TextView(context);
                            marketdate.setTextAppearance(context,R.style.Body);
                            marketdate.setTextSize(width/45);
                            String md = published.getString("price_market_date");
                            marketdate.setText(md);
                            lh1.addView(marketdate);
                            ll.addView(lh1);
                            //Published Date
                            LinearLayout lh2=new LinearLayout(context);
                            lh2.setOrientation(LinearLayout.HORIZONTAL);
                            TextView publisheddatet=new TextView(context);
                            publisheddatet.setTextAppearance(context,R.style.Title);
                            publisheddatet.setTextSize(width/45);
                            String pdt = "Published Date:   ";
                            publisheddatet.setText(pdt);
                            lh2.addView(publisheddatet);
                            TextView publisheddate=new TextView(context);
                            publisheddate.setTextAppearance(context,R.style.Body);
                            publisheddate.setTextSize(width/45);
                            String pd = published.getString("price_publish_date");
                            publisheddate.setText(pd);
                            lh2.addView(publisheddate);
                            ll.addView(lh2);
                            //Market Name
                            TextView marketname=new TextView(context);
                            marketname.setTextAppearance(context,R.style.Body);
                            marketname.setTextSize(width/45);
                            String mn = "@ " + published.getString("price_market_name");
                            marketname.setText(mn);
                            ll.addView(marketname);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_byuser", ht));

    }

    private static void showPreferProduct(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("preferproductScrollTable");
                        tl.removeAllViews();
                        JSONArray allpreproduct = result.getJSONArray("results");
                        for (int i=0; i<allpreproduct.length();i++){
                            JSONObject preproduct = allpreproduct.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            /*//Preferred Products Title
                            LinearLayout lltitle=new LinearLayout(context);
                            lltitle.setOrientation(LinearLayout.VERTICAL);
                            //Category
                            TextView category1=new TextView(context);
                            category1.setTextAppearance(context,R.style.Bold);
                            category1.setTextSize(width/45 );
                            String ctitle = "Category:";
                            category1.setText(ctitle);
                            lltitle.addView(category1);
                            //Product Name
                            TextView pn1=new TextView(context);
                            pn1.setTextAppearance(context,R.style.Bold);
                            pn1.setTextSize(width/45);
                            String mntitle = "Product:";
                            pn1.setText(mntitle);
                            lltitle.addView(pn1);

                            lltitle.setLayoutParams(new TableRow.LayoutParams(0, height/5, 0.3f));
                            lv.addView(lltitle);*/

                            //Preferred Products content
                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Category
                            TextView category1=new TextView(context);
                            category1.setTextAppearance(context,R.style.Title);
                            category1.setTextSize(width/50);
                            String ctitle = "Category:";
                            category1.setText(ctitle);
                            ll.addView(category1);
                            TextView category=new TextView(context);
                            category.setTextAppearance(context,R.style.Normal);
                            category.setTextSize(width/45);
                            category.setText(preproduct.getString("Prd_Category1"));
                            ll.addView(category);
                            //Product Name
                            TextView pn1=new TextView(context);
                            pn1.setTextAppearance(context,R.style.Title);
                            pn1.setTextSize(width/50);
                            String mntitle = "Product:";
                            pn1.setText(mntitle);
                            ll.addView(pn1);
                            TextView pn=new TextView(context);
                            pn.setTextAppearance(context,R.style.Normal);
                            pn.setTextSize(width/45);
                            pn.setText(preproduct.getString("product_name"));
                            ll.addView(pn);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.7f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_product_list_getlist", ht));
    }

    private static void showPreferVendor(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("prefervendorScrollTable");
                        tl.removeAllViews();
                        JSONArray allprevendor = result.getJSONArray("results");
                        for (int i=0; i<allprevendor.length();i++){
                            JSONObject prevendor = allprevendor.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Display Name
                            TextView dn=new TextView(context);
                            dn.setTextAppearance(context,R.style.Bold);
                            dn.setTextSize(width/45);
                            String name = prevendor.getString("displayname");
                            String namereplace = name.replace("<br>","\n");
                            dn.setText(namereplace);
                            ll.addView(dn);
                            //Address
                            TextView address=new TextView(context);
                            address.setTextAppearance(context,R.style.Normal);
                            address.setTextSize(width/50);
                            address.setText(prevendor.getString("address"));
                            ll.addView(address);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_vendor_list_getlist", ht));
    }

    private static void showPreferMarket(){
        String token_s = UserFileUtility.get_token();
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("os","Android");
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("prefermarketScrollTable");
                        tl.removeAllViews();
                        JSONArray allpremarket = result.getJSONArray("results");
                        for (int i=0; i<allpremarket.length();i++){
                            JSONObject premarket = allpremarket.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Display Name
                            TextView dn=new TextView(context);
                            dn.setTextAppearance(context,R.style.Bold);
                            dn.setTextSize(width/45);
                            dn.setText(premarket.getString("displayname"));
                            ll.addView(dn);
                            //Address
                            TextView address=new TextView(context);
                            address.setTextAppearance(context,R.style.Normal);
                            address.setTextSize(width/50);
                            address.setText(premarket.getString("address"));
                            ll.addView(address);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }
                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_market_list_getlist", ht));
    }

    private static void showpublicposts(String search){
        //SEARCH AND SHOW FUNTION FOR FRONT PAGE
        String token_s=UserFileUtility.get_token();
        Hashtable<String,String> ht=new Hashtable<String, String>();
        ht.put("liststart","0");
        ht.put("perpage","5");
        ht.put("os", "Android");
        ht.put("search",search);
        ht.put("token",token_s);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result)
            {
                try {
                    Log.d("Error", result.getString("error"));
                    String error=result.getString("error");
                    if (error.equals("-9"))
                    {
                        TableLayout tl=(TableLayout)hashelements.get("postsScrollTable");
                        tl.removeAllViews();
                        JSONArray resultposts=result.getJSONArray("results");
                        Log.d("resultposts", resultposts.toString());
                        for (int i=0;i<resultposts.length();i++)
                        {
                            JSONObject jpost=resultposts.getJSONObject(i);
                            Log.d("jpost", jpost.toString());
                            TableRow lv=new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                            //Logo
                            ImageView logo=new ImageView(context);
                            String vendorlogohtml=jpost.getString("vendorlogo");
                            int urlstart=vendorlogohtml.indexOf("src=\"")+"src=\"".length();
                            int urlend=urlstart;
                            for (int j=urlstart;vendorlogohtml.charAt(j)!='\"';j++)
                            {
                                urlend=j;
                            }
                            String vendorlogourl=vendorlogohtml.substring(urlstart,urlend+1);
                            Log.d("LOGOURL",vendorlogourl);
                            LoadImage li=new LoadImage();
                            li.img=logo;
                            li.execute(vendorlogourl);
                            logo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
                            lv.addView(logo);

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Product Name and Day
                            TextView tv=new TextView(context);
                            tv.setText(jpost.getString("productname") + "\n" + "Day:" + jpost.getString("marketday"));
                            ll.addView(tv);
                            //Price Unit
                            TextView tv4=new TextView(context);
                            tv4.setText(jpost.getString("price") + " / " + jpost.getString("unit"));
                            ll.addView(tv4);
                            //Vendor
                            TextView tv3=new TextView(context);
                            tv3.setText(jpost.getString("vendorbusinessname"));
                            ll.addView(tv3);
                            //Market
                            TextView tv2=new TextView(context);
                            tv2.setText(jpost.getString("farmersmarketname"));
                            ll.addView(tv2);
                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.7f));
                            lv.addView(ll);




                            final String postid=jpost.getString("postid");
                            lv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Hashtable<String, String> pam = new Hashtable<String, String>();
                                    pam.put("postid", postid);
                                    pushNewPage(new PageNode(R.array.page_020_viewpost, pam));
                                    setPage(R.array.page_020_viewpost, pam);
                                }
                            });

                            tl.addView(lv);
                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.BLACK);
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.BLACK);
                            rdivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.7f));
                            lk.addView(ldivider);
                            lk.addView(rdivider);
                            tl.addView(lk);
                        }

                    }
                    else
                    {
                    }
                    setupUI(playout);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_posts", ht));
    }
    private static void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(context);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);
                innerView.setVisibility(View.VISIBLE);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }



    public static PageNode getRecentPage(){
        if (pageHistory==null)
            pageHistory=new ArrayList<>();
        if (pageHistory.isEmpty())
            return null;
        return pageHistory.get(0);
    }
    public static void removeRecentPage(){
        if (pageHistory==null)
            pageHistory=new ArrayList<>();
        if (!pageHistory.isEmpty())
            pageHistory.remove(0);
    }
    public static void cleanPageHistory(){
        if (pageHistory==null)
            pageHistory=new ArrayList<>();
        pageHistory.clear();
    }
    public static void pushNewPage(PageNode pn){
        if (pageHistory==null)
            pageHistory=new ArrayList<>();
        pageHistory.add(0,pn);
    }
    public static boolean historyEmpty(){
        if (pageHistory==null)
            pageHistory=new ArrayList<>();
        if (pageHistory.isEmpty())
            return true;
        else
            return false;
    }

}
