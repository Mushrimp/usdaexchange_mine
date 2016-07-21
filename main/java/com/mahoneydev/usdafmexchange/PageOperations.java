package com.mahoneydev.usdafmexchange;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahoneydev on 5/23/2016.
 */
public class PageOperations {
    public static Frontpage context = null;
    public static Resources res = null;
    private static View playout=null;
    private static Hashtable<String, View> hashelements;
    private static Hashtable<String, String> hashvalues;
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
        setupUI(context.findViewById(R.id.appbar));
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
                            String type=jsonelements.getString("inputtype");
                            if (type.equals("textPassword"))
                                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            else if (type.equals("number"))
                                et.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        }
                        if (jsonelements.has("next"))
                        {
                            final String thisid=jsonelements.getString("id");
                            final String nextid=jsonelements.getString("next");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((EditText)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((EditText)hashelements.get(thisid)).clearFocus();
                                        (hashelements.get(nextid)).requestFocus();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        else if (jsonelements.has("enter"))
                        {
                            final String action=jsonelements.getString("enter");
                            final String thisid=jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((EditText)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((EditText)hashelements.get(thisid)).clearFocus();
                                        hideSoftKeyboard(context);
                                        executeAction(action);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        else
                        {
                            final String thisid=jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((EditText)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((EditText)hashelements.get(thisid)).clearFocus();
                                        hideSoftKeyboard(context);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
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
                        if (jsonelements.has("next"))
                        {
                            final String thisid=jsonelements.getString("id");
                            final String nextid=jsonelements.getString("next");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((AutoCompleteTextView)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((AutoCompleteTextView)hashelements.get(thisid)).clearFocus();
                                        (hashelements.get(nextid)).requestFocus();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        else if (jsonelements.has("enter"))
                        {
                            final String action=jsonelements.getString("enter");
                            final String thisid=jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((AutoCompleteTextView)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((AutoCompleteTextView)hashelements.get(thisid)).clearFocus();
                                        hideSoftKeyboard(context);
                                        executeAction(action);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        else
                        {
                            final String thisid=jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m=s.toString();
                                    if (m.indexOf("\n")!=-1)
                                    {
                                        ((AutoCompleteTextView)hashelements.get(thisid)).setText(m.replace("\n",""));
                                        ((AutoCompleteTextView)hashelements.get(thisid)).clearFocus();
                                        hideSoftKeyboard(context);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
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
                    }else if (element.equals("DatePicker"))
                    {
                        Log.e("DatePicker","1");
                        TextView dp=new TextView(context);
                        dp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        dp.setVisibility(View.INVISIBLE);
                        dp.setText("Select the Date");
                        hashelements.put(jsonelements.getString("id"), dp);
                        layout.addView(dp);
                    }
                }
            }
            renderUI(code,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void executeAction(String action){
        if (action.equals("loginSubmit"))
        {
            loginAction();
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
    private static void loginAction(){
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
    private static void setButtonAction(String action, Button bt){
        if (action.equals("loginSubmit")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginAction();
                }
            });
        }
        else if (action.equals("signUp")){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(new PageNode(R.array.page_100_registration,null));
                    setPage(R.array.page_100_registration,null);
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
        else if (action.equals("publishpost"))
        {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String price_market_userindex_id = ((SpinnerElement) (((Spinner) hashelements.get("marketSpinner")).getSelectedItem())).getValue();
                    String price_product_userindex_id = ((SpinnerElement) (((Spinner) hashelements.get("productSpinner")).getSelectedItem())).getValue();
                    String price_productunit_name = ((AutoCompleteTextView) hashelements.get("unitInput")).getText().toString();
                    String price_price =  ((EditText) hashelements.get("priceInput")).getText().toString();
                    String template="";
                    final String regExp = "[1-9]+([.][0-9]{1,2})?";
                    if (((CheckBox) hashelements.get("postCheckbox")).isChecked())
                        template = "yes";
                    boolean flag = true;
                    TextView errortv = ((TextView) hashelements.get("newposterrorView"));
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                    if (price_market_userindex_id.equals("")) {
                        errortv.setText("Please Select a Market");
                        flag = false;
                    }else if (price_product_userindex_id.equals("")) {
                        errortv.setText("Please Select a Product");
                        flag = false;
                    }else if (price_price == null || price_price.equals("")) {
                        errortv.setText("Please Input Price");
                        flag = false;
                    } else if (price_productunit_name == null || price_productunit_name.equals("")) {
                        errortv.setText("Please Input Unit");
                        flag = false;
                    }
                    try {
                        float pricefloat = Float.parseFloat(price_price);
                        price_price=String.format("%.2f", pricefloat);
                    } catch (NumberFormatException e)
                    {
                        errortv.setText("Please Input Price");
                        flag = false;
                    }

                    try {
                        Date date = dateFormatter.parse(((TextView)hashelements.get("marketDay")).getText().toString());

                    } catch (ParseException e) {
                        errortv.setText("Please Select a Market Day");
                        flag = false;
                    }
                    try {
                        Date date = dateFormatter.parse(((TextView)hashelements.get("publishDay")).getText().toString());

                    } catch (ParseException e) {
                        errortv.setText("Please Select a Publish Day");
                        flag = false;
                    }

                    if (flag) {
                        //Build Post Data
                        Hashtable<String, String> postdataht = new Hashtable<String, String>();
                        postdataht.put("price_market_userindex_id", price_market_userindex_id);
                        postdataht.put("price_product_userindex_id", price_product_userindex_id);
                        postdataht.put("price_productunit_name", price_productunit_name);
                        postdataht.put("price_price", price_price);
                        postdataht.put("task", "add");
                        postdataht.put("price_template", template);
                        postdataht.put("price_market_date", ((TextView)hashelements.get("marketDay")).getText().toString());
                        postdataht.put("price_publish_date", ((TextView)hashelements.get("publishDay")).getText().toString());
                        postdataht.put("price_ad_desc", ((EditText)hashelements.get("descInput")).getText().toString());
                        postdataht.put("price_publish_date", ((TextView)hashelements.get("publishDay")).getText().toString());
                        String jsonpostdata = (new JSONObject(postdataht)).toString();

                        Hashtable<String, String> ht = new Hashtable<String, String>();
                        String token_s = UserFileUtility.get_token();
                        ht.put("os", "Android");
                        ht.put("token", token_s);
                        ht.put("postdata", jsonpostdata);
                        ht.put("formargs", "2");
                        new FetchTask() {
                            @Override
                            protected void onPostExecute(JSONObject result) {
                                try {
                                    Log.d("Error", result.getString("error"));
                                    String error = result.getString("error");
                                    if (error.equals("-9")) {
                                        ((TextView) hashelements.get("newposterrorView")).setText("Success!");

                                        removeRecentPage();
                                        PageNode k = getRecentPage();
                                        setPage(k.pageId, k.params);
                                        Toast toast = Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_save_postform", ht));
                    }
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
            String idvalue = params.get("postid");
            showpostView(idvalue);
            /*((TextView)hashelements.get("postView")).setText("This is post "+params.get("postid"));
            setupUI(playout);*/
        }
        else if (code== R.array.page_016_vendorpage)
        {
            String name = params.get("username");
            showVendorpage(name);
        }
        else if (code== R.array.page_017_marketpage)
        {
            String id = params.get("marketid");
            showMarketpage(id);
        }
        else if (code== R.array.page_102_login)
        {
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
        }else if (code == R.array.page_322_newpost) {
            preparepostform();
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
                        TableLayout tl = (TableLayout)hashelements.get("friendshipScrollTable");
                        tl.removeAllViews();
                        JSONArray allfriends = result.getJSONArray("results");
                        for (int i=0; i<allfriends.length();i++){
                            JSONObject friend = allfriends.getJSONObject(i);
                            TableRow lv = new TableRow(context);
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));
                            //Logo
                            ImageView logo=new ImageView(context);
                            String vendorlogohtml=friend.getString("avatar");
                            int urlstart=vendorlogohtml.indexOf("src=\"")+"src=\"".length();
                            int urlend=urlstart;
                            for (int j=urlstart; vendorlogohtml.charAt(j)!='\"';j++){
                                urlend = j;
                            }
                            String vendorlogourl=vendorlogohtml.substring(urlstart,urlend+1);
                            LoadImage li=new LoadImage();
                            li.img=logo;
                            li.execute(vendorlogourl);
                            logo.setLayoutParams(new TableRow.LayoutParams(0, height/5, 0.3f));
                            lv.addView(logo);

                            LinearLayout ll_bl = new LinearLayout(context);
                            ll_bl.setLayoutParams(new  TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,0.04f));
                            lv.addView(ll_bl);

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Name & message
                            RelativeLayout rl_in = new RelativeLayout(context);
                            rl_in.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,width/10));
                            TextView name=new TextView(context);
                            RelativeLayout.LayoutParams nameprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            nameprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT|RelativeLayout.CENTER_VERTICAL);
                            name.setLayoutParams(nameprams);
                            name.setText(friend.getString("displayname"));
                            name.setTextAppearance(context,R.style.Bold);
                            name.setTextSize(width/45);
                            rl_in.addView(name);
                            Button message_bt = new Button(context);
                            RelativeLayout.LayoutParams btprams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            btprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            message_bt.setLayoutParams(btprams);
                            message_bt.setText("Message");
                            message_bt.setTextAppearance(context,R.style.Normal);
                            message_bt.setTextSize(width/55);
                            message_bt.setBackgroundColor(Color.parseColor("#A2D25A"));
                            rl_in.addView(message_bt);
                            ll.addView(rl_in);

                            //Business Name
                            final TextView bn=new TextView(context);
                            bn.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.5), LinearLayout.LayoutParams.WRAP_CONTENT));
                            bn.setText(friend.getString("businessname"));
                            ll.addView(bn);


                            ll.setLayoutParams(new TableRow.LayoutParams(0, height/5, 0.7f));
                            lv.addView(ll);
                            tl.addView(lv);

                            TableRow lk=new TableRow(context);
                            lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            View ldivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0,2,0.3f));
                            View rdivider=new LinearLayout(context);
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
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
        }.execute(AppCodeResources.postUrl("usdafriendship", "friends_list_all_byuser", ht));
    }

    private static void preparepostform(){
        Hashtable<String,String> ht=new Hashtable<String, String>();
        String token_s=UserFileUtility.get_token();
        ht.put("os", "Android");
        ht.put("token", token_s);
        ((Spinner)hashelements.get("productSpinner")).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    SpinnerElement selecteditem = (SpinnerElement) adapterView.getItemAtPosition(i);
                    String id=selecteditem.getValue();
                    ((AutoCompleteTextView)hashelements.get("unitInput")).setText(hashvalues.get(id));
                }
                else
                {
                    ((AutoCompleteTextView)hashelements.get("unitInput")).setText("");
                }
                ((AutoCompleteTextView)hashelements.get("unitInput")).clearFocus();
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
                        JSONObject ja=result.getJSONObject("results");
                        SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                        Iterator<?> keys = ja.keys();
                        int i=0;
                        while( keys.hasNext() ) {
                            String key = (String)keys.next();
                            arraySpinner[i]=new SpinnerElement(ja.getString(key),key);
                            i++;
                        }

                        ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                                android.R.layout.simple_spinner_item, arraySpinner);
                        ((Spinner)hashelements.get("productSpinner")).setAdapter(adapter);
                        hashvalues=new Hashtable<String, String>();
                        JSONArray results=result.getJSONArray("result");
                        for (i=0;i<results.length();i++)
                        {
                            JSONObject unititem=results.getJSONObject(i);
                            hashvalues.put(unititem.getString("id"),unititem.getString("productunit_name"));
                        }
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
                                    JSONObject ja=result.getJSONObject("results");
                                    SpinnerElement[] arraySpinner = new SpinnerElement[ja.length()];
                                    Iterator<?> keys = ja.keys();
                                    int i=0;
                                    while( keys.hasNext() ) {
                                        String key = (String)keys.next();
                                        arraySpinner[i]=new SpinnerElement(ja.getString(key),key);
                                        i++;
                                    }

                                    ArrayAdapter<SpinnerElement> adapter = new ArrayAdapter<SpinnerElement>(context,
                                            android.R.layout.simple_spinner_item, arraySpinner);
                                    ((Spinner)hashelements.get("marketSpinner")).setAdapter(adapter);
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

                    }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_user_marketlist", ht));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }.execute(AppCodeResources.postUrl("usdatestyue", "usda_pricepost_list_user_productlist", ht));
        ((TextView)hashelements.get("marketDay")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date;
                Calendar newCalendar = Calendar.getInstance();
                try {
                    date = dateFormatter.parse(((TextView)hashelements.get("marketDay")).getText().toString());
                    newCalendar.setTime(date);
                } catch (ParseException e) {

                }
                Calendar currentday=Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        ((TextView)hashelements.get("marketDay")).setText(dateFormatter.format(newDate.getTime()));
                        Calendar currentday=Calendar.getInstance();
                        Calendar publishday=Calendar.getInstance();
                        publishday.set(year,monthOfYear,dayOfMonth-2);
                        if (currentday.getTimeInMillis()>publishday.getTimeInMillis())
                            ((TextView)hashelements.get("publishDay")).setText(dateFormatter.format(currentday.getTime()));
                        else
                            ((TextView)hashelements.get("publishDay")).setText(dateFormatter.format(publishday.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(currentday.getTimeInMillis());
                newCalendar.set(currentday.get(Calendar.YEAR), currentday.get(Calendar.MONTH), currentday.get(Calendar.DAY_OF_MONTH)+42);
                fromDatePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
                fromDatePickerDialog.show();
            }
        });
        ((TextView)hashelements.get("publishDay")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date;
                try {
                    date = dateFormatter.parse(((TextView)hashelements.get("publishDay")).getText().toString());
                } catch (ParseException e) {
                    return;
                }
                newCalendar.setTime(date);
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        ((TextView)hashelements.get("publishDay")).setText(dateFormatter.format(newDate.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                Calendar currentday=Calendar.getInstance();
                Calendar marketday=Calendar.getInstance();
                try {
                    date = dateFormatter.parse(((TextView)hashelements.get("marketDay")).getText().toString());
                } catch (ParseException e) {
                    return;
                }
                marketday.setTime(date);
                fromDatePickerDialog.getDatePicker().setMaxDate(marketday.getTimeInMillis());
                Calendar publishday=Calendar.getInstance();
                publishday.set(newCalendar.get(marketday.YEAR), marketday.get(Calendar.MONTH), marketday.get(Calendar.DAY_OF_MONTH)-2);
                if (currentday.getTimeInMillis()>publishday.getTimeInMillis())
                    fromDatePickerDialog.getDatePicker().setMinDate(currentday.getTimeInMillis());
                else
                    fromDatePickerDialog.getDatePicker().setMinDate(publishday.getTimeInMillis());

                fromDatePickerDialog.show();
            }
        });
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
                            lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));

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

                            ll.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
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

                            ll.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
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

                            ll.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
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
                            category.setTextAppearance(context,R.style.Bold);
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
                            pn.setTextAppearance(context,R.style.Body);
                            pn.setTextSize(width/45);
                            pn.setText(preproduct.getString("product_name"));
                            ll.addView(pn);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
                            TextView dn1=new TextView(context);
                            dn1.setTextAppearance(context,R.style.Title);
                            dn1.setTextSize(width/50);
                            String name1 = "Vendor name:";
                            dn1.setText(name1);
                            ll.addView(dn1);
                            TextView dn=new TextView(context);
                            dn.setTextAppearance(context,R.style.Bold);
                            dn.setTextSize(width/45);
                            String name = prevendor.getString("displayname");
                            String namereplace = name.replace("<br>","\n");
                            dn.setText(namereplace);
                            ll.addView(dn);
                            //Address
                            TextView address1=new TextView(context);
                            address1.setTextAppearance(context,R.style.Title);
                            address1.setTextSize(width/50);
                            String an = "Address:";
                            address1.setText(an);
                            ll.addView(address1);
                            TextView address=new TextView(context);
                            address.setTextAppearance(context,R.style.Body);
                            address.setTextSize(width/50);
                            address.setText(prevendor.getString("address"));
                            ll.addView(address);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
                            TextView dn1=new TextView(context);
                            dn1.setTextAppearance(context,R.style.Title);
                            dn1.setTextSize(width/50);
                            String name1 = "Market name:";
                            dn1.setText(name1);
                            ll.addView(dn1);
                            TextView dn=new TextView(context);
                            dn.setTextAppearance(context,R.style.Bold);
                            dn.setTextSize(width/45);
                            dn.setText(premarket.getString("displayname"));
                            ll.addView(dn);
                            //Address
                            TextView address1=new TextView(context);
                            address1.setTextAppearance(context,R.style.Title);
                            address1.setTextSize(width/50);
                            String an = "Address name:";
                            address1.setText(an);
                            ll.addView(address1);
                            TextView address=new TextView(context);
                            address.setTextAppearance(context,R.style.Body);
                            address.setTextSize(width/45);
                            address.setText(premarket.getString("address"));
                            ll.addView(address);

                            //
                            TextView br = new TextView(context);
                            br.setText("");
                            ll.addView(br);

                            ll.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
        final String token_s=UserFileUtility.get_token();
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

                            LinearLayout ll_bl = new LinearLayout(context);
                            ll_bl.setOrientation(LinearLayout.VERTICAL);
                            ll_bl.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,0.04f));
                            TextView bl = new TextView(context);
                            bl.setText("");
                            ll_bl.addView(bl);
                            lv.addView(ll_bl);

                            LinearLayout ll=new LinearLayout(context);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            //Product Name
                            TextView pn=new TextView(context);
                            pn.setText(jpost.getString("productname"));
                            pn.setTextAppearance(context,R.style.Normal);
                            pn.setTextSize(width/40);
                            ll.addView(pn);
                            //Vendor
                            TextView vendor=new TextView(context);
                            String tv = "By: " + jpost.getString("vendorbusinessname");
                            vendor.setText(tv);
                            vendor.setTextAppearance(context,R.style.Title);
                            vendor.setTextSize(width/55);
                            ll.addView(vendor);
                            //Date
                            TextView date=new TextView(context);
                            String td = "Date: " + jpost.getString("marketday");
                            date.setText(td);
                            date.setTextAppearance(context,R.style.Date);
                            date.setTextSize(width/55);
                            ll.addView(date);
                            //Price Unit
                            LinearLayout ll_in = new LinearLayout(context);
                            ll_in.setOrientation(LinearLayout.HORIZONTAL);
                            ll_in.setGravity(Gravity.RIGHT);
                            String price = jpost.getString("price");/*+ " / " + jpost.getString("unit")*/
                            String[] pricesplit;
                            pricesplit = price.split("\\.");
                            TextView priceint=new TextView(context);
                            priceint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            priceint.setGravity(Gravity.RIGHT);
                            priceint.setText("$"+pricesplit[0]+".");
                            priceint.setTextAppearance(context,R.style.Price);
                            priceint.setTextSize(width/30);
                            ll_in.addView(priceint);
                            TextView pricedec=new TextView(context);
                            pricedec.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            pricedec.setGravity(Gravity.RIGHT|Gravity.TOP);
                            pricedec.setText(pricesplit[1]);
                            pricedec.setTextAppearance(context,R.style.Price);
                            pricedec.setTextSize(width/45);
                            ll_in.addView(pricedec);
                            ll.addView(ll_in);
                            //Market
                            TextView tv2=new TextView(context);
                            tv2.setTextAppearance(context,R.style.Body);
                            tv2.setTextSize(width/60);
                            tv2.setText("@ " + jpost.getString("farmersmarketname"));
                            ll.addView(tv2);
                            ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.66f));

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
                            ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                            ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2,0.3f));
                            View rdivider=new LinearLayout(context);
                            rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
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

    private static void showpostView(String idvalue){
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("tablename","usdafmexchange_publish.usda_price_post_public");
        ht.put("idname","ID");
        ht.put("idvalue",idvalue);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("viewpostScrollTable");
                        tl.removeAllViews();
                        JSONObject postView = result.getJSONObject("results");
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        //Product Name
                        TextView pn=new TextView(context);
                        pn.setTextAppearance(context,R.style.Bold);
                        pn.setTextSize(width/50);
                        pn.setText(postView.getString("price_product_name"));
                        ll.addView(pn);
                        //Vendorname
                        LinearLayout ll1 = new LinearLayout(context);
                        ll1.setOrientation(TableLayout.HORIZONTAL);
                        TextView vn1 = new TextView(context);
                        vn1.setTextAppearance(context,R.style.Bold);
                        vn1.setTextSize(width/50);
                        vn1.setText("By  ");
                        ll1.addView(vn1);
                        TextView vn = new TextView(context);
                        vn.setTextAppearance(context,R.style.Normal);
                        vn.setTextSize(width/50);
                        vn.setText(postView.getString("price_vendorname"));
                        ll1.addView(vn);
                        ll.addView(ll1);

                        //
                        TextView br = new TextView(context);
                        br.setText("");
                        ll.addView(br);

                        //Price
                        LinearLayout ll2 = new LinearLayout(context);
                        ll2.setOrientation(TableLayout.HORIZONTAL);
                        TextView price1 = new TextView(context);
                        price1.setTextAppearance(context,R.style.Normal);
                        price1.setTextSize(width/45);
                        price1.setText("Price:   ");
                        ll2.addView(price1);
                        TextView price = new TextView(context);
                        price.setTextAppearance(context,R.style.Price);
                        price.setTextSize(width/40);
                        price.setText("$"+postView.getString("price_price"));
                        ll2.addView(price);
                        ll.addView(ll2);

                        //Unit
                        LinearLayout ll3 = new LinearLayout(context);
                        ll3.setOrientation(TableLayout.HORIZONTAL);
                        TextView unit1 = new TextView(context);
                        unit1.setTextAppearance(context,R.style.Normal);
                        unit1.setTextSize(width/45);
                        unit1.setText("Unit:     ");
                        ll3.addView(unit1);
                        TextView unit = new TextView(context);
                        unit.setTextAppearance(context,R.style.Normal);
                        unit.setTextSize(width/45);
                        unit.setText(postView.getString("price_productunit_name"));
                        ll3.addView(unit);
                        ll.addView(ll3);

                        //Market Date
                        LinearLayout ll4 = new LinearLayout(context);
                        ll4.setOrientation(TableLayout.HORIZONTAL);
                        TextView date1 = new TextView(context);
                        date1.setTextAppearance(context,R.style.Normal);
                        date1.setTextSize(width/50);
                        date1.setText("Market Date:     ");
                        ll4.addView(date1);
                        TextView date = new TextView(context);
                        date.setTextAppearance(context,R.style.Normal);
                        date.setTextSize(width/50);
                        date.setText(postView.getString("price_market_date"));
                        ll4.addView(date);
                        ll.addView(ll4);

                        //
                        TextView br2 = new TextView(context);
                        br2.setText("");
                        ll.addView(br2);

                        //Description
                        TextView des = new TextView(context);
                        des.setTextAppearance(context,R.style.Bold);
                        des.setTextSize(width/35);
                        des.setText("Description");
                        ll.addView(des);

                        //TableLayout
                        TableLayout tl_in = new TableLayout(context);
                        //Category
                        TableRow tr1 = new TableRow(context);
                        TextView category1 = new TextView(context);
                        //category1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                        category1.setTextAppearance(context,R.style.Bold);
                        category1.setTextSize(width/45);
                        category1.setText("Category:");
                        tr1.addView(category1);
                        TextView category = new TextView(context);
                        category.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        category.setTextAppearance(context,R.style.Normal);
                        category.setTextSize(width/45);
                        category.setText(postView.getString("price_Prd_Category1"));
                        tr1.addView(category);
                        tl_in.addView(tr1);
                        //Organic
                        TableRow tr2 = new TableRow(context);
                        TextView org1 = new TextView(context);
                        org1.setLayoutParams(new TableRow.LayoutParams((int)(width*3/10), TableRow.LayoutParams.WRAP_CONTENT));
                        org1.setTextAppearance(context,R.style.Bold);
                        org1.setTextSize(width/45);
                        org1.setText("Organic:");
                        tr2.addView(org1);
                        LinearLayout ll_in = new LinearLayout(context);
                        ImageView org=new ImageView(context);
                        org.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        String organic = postView.getString("price_usdaorganic");
                        if(organic.equals("yes")){
                            org.setImageResource(R.drawable.usda_organic);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(50,50);
                            parms.gravity=Gravity.START;
                            org.setLayoutParams(parms);
                        }
                        ll_in.addView(org);
                        tr2.addView(ll_in);
                        tl_in.addView(tr2);
                        //Description
                        TableRow tr3 = new TableRow(context);
                        TextView des1 = new TextView(context);
                        //des1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                        des1.setTextAppearance(context,R.style.Bold);
                        des1.setTextSize(width/45);
                        des1.setText("Description:");
                        tr3.addView(des1);
                        TextView desc = new TextView(context);
                        desc.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        desc.setTextSize(width/45);
                        desc.setTextAppearance(context,R.style.Normal);
                        desc.setText(postView.getString("price_ad_desc"));
                        tr3.addView(desc);
                        tl_in.addView(tr3);
                        //Vendor
                        TableRow tr4 = new TableRow(context);
                        TextView vendor1 = new TextView(context);
                        //vendor1.setLayoutParams(new TableRow.LayoutParams((int)(width*0.35), TableRow.LayoutParams.WRAP_CONTENT));
                        vendor1.setTextAppearance(context,R.style.Bold);
                        vendor1.setTextSize(width/45);
                        vendor1.setText("Vendor:");
                        tr4.addView(vendor1);
                        TextView vendor = new TextView(context);
                        vendor.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        vendor.setTextAppearance(context,R.style.Normal);
                        vendor.setTextSize(width/45);
                        vendor.setText(postView.getString("price_vendorname"));
                        tr4.addView(vendor);
                        tl_in.addView(tr4);
                        //Vendor detail
                        TableRow tr5 = new TableRow(context);
                        TableRow.LayoutParams params = new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT);
                        TextView bl = new TextView(context);
                        tr5.addView(bl);
                        TextView vd = new TextView(context);
                        vd.setText("More Details...");
                        vd.setLayoutParams(params);
                        vd.setTextAppearance(context,R.style.Date);
                        vd.setTextSize(width/50);
                        vd.setGravity(Gravity.RIGHT);
                        tr5.addView(vd);
                        tl_in.addView(tr5);

                        final String username=postView.getString("price_vendorusername");
                        vd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Hashtable<String, String> para = new Hashtable<String, String>();
                                para.put("username", username);
                                pushNewPage(new PageNode(R.array.page_016_vendorpage, para));
                                setPage(R.array.page_016_vendorpage, para);
                            }
                        });

                        //Market name
                        TableRow tr6 = new TableRow(context);
                        TextView mn1 = new TextView(context);
                        mn1.setTextAppearance(context,R.style.Bold);
                        mn1.setTextSize(width/45);
                        mn1.setText("Market:");
                        tr6.addView(mn1);
                        TableRow.LayoutParams tparams = new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT);
                        TextView mn = new TextView(context);
                        mn.setLayoutParams(tparams);
                        mn.setTextAppearance(context,R.style.Normal);
                        mn.setTextSize(width/45);
                        mn.setText(postView.getString("price_market_name"));
                        tr6.addView(mn);
                        tl_in.addView(tr6);
                        //Market detail
                        TableRow tr7 = new TableRow(context);
                        TextView bl2 = new TextView(context);
                        tr7.addView(bl2);
                        TextView md = new TextView(context);
                        md.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableLayout.LayoutParams.WRAP_CONTENT));
                        md.setText("More Details...");
                        md.setTextAppearance(context,R.style.Date);
                        md.setTextSize(width/50);
                        md.setGravity(Gravity.RIGHT);
                        tr7.addView(md);
                        tl_in.addView(tr7);

                        final String marketid=postView.getString("price_fmid");
                        md.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Hashtable<String, String> para = new Hashtable<String, String>();
                                para.put("marketid", marketid);
                                pushNewPage(new PageNode(R.array.page_017_marketpage, para));
                                setPage(R.array.page_017_marketpage, para);
                            }
                        });

                        //Location
                        TableRow tr8 = new TableRow(context);
                        TextView location1 = new TextView(context);
                        location1.setTextAppearance(context,R.style.Bold);
                        location1.setTextSize(width/45);
                        location1.setText("Location:");
                        tr8.addView(location1);
                        TextView location = new TextView(context);
                        location.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableLayout.LayoutParams.WRAP_CONTENT));
                        location.setTextAppearance(context,R.style.Normal);
                        location.setTextSize(width/45);
                        location.setText(postView.getString("price_street")+","+postView.getString("price_city")+","
                                +postView.getString("price_state")+","+postView.getString("price_zipcode"));
                        tr8.addView(location);
                        tl_in.addView(tr8);

                        ll.addView(tl_in);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        tl.addView(lv);

                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_search_table_by_id", ht));
    }

    private static void showVendorpage(String name){
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("vendorname",name);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("vendorpageScrollTable");
                        tl.removeAllViews();
                        JSONObject vendorprofile = result.getJSONObject("vendorprofile");
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        //Business Name
                        TextView pn=new TextView(context);
                        pn.setTextAppearance(context,R.style.Bold);
                        pn.setTextSize(width/40);
                        pn.setText(vendorprofile.getString("business_name"));
                        pn.setGravity(Gravity.CENTER);
                        ll.addView(pn);
                        //
                        TextView br1 = new TextView(context);
                        br1.setText("");
                        ll.addView(br1);

                        //TableLayout
                        TableLayout tl_in = new TableLayout(context);
                        //Organic
                        TableRow tr1 = new TableRow(context);
                        TextView org1 = new TextView(context);
                        org1.setLayoutParams(new TableRow.LayoutParams((int)(width*3/10), TableRow.LayoutParams.WRAP_CONTENT));
                        org1.setTextAppearance(context,R.style.Bold);
                        org1.setTextSize(width/45);
                        org1.setText("Organic:");
                        tr1.addView(org1);
                        LinearLayout ll_in = new LinearLayout(context);
                        ImageView org=new ImageView(context);
                        org.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        JSONArray organic=vendorprofile.getJSONArray("business_usdaorganic");
                        if(organic.getString(0).equals("yes")){
                            org.setImageResource(R.drawable.usda_organic);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(50,50);
                            parms.gravity=Gravity.START;
                            org.setLayoutParams(parms);
                        }
                        ll_in.addView(org);
                        tr1.addView(ll_in);
                        tl_in.addView(tr1);
                        //Phone
                        TableRow tr2 = new TableRow(context);
                        TextView phone1 = new TextView(context);
                        phone1.setTextAppearance(context,R.style.Bold);
                        phone1.setTextSize(width/45);
                        phone1.setText("Phone:");
                        tr2.addView(phone1);
                        TextView phone = new TextView(context);
                        phone.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        phone.setTextAppearance(context,R.style.Normal);
                        phone.setTextSize(width/45);
                        phone.setText(vendorprofile.getString("business_phone"));
                        tr2.addView(phone);
                        tl_in.addView(tr2);
                        //Email
                        TableRow tr3 = new TableRow(context);
                        TextView email1 = new TextView(context);
                        email1.setTextAppearance(context,R.style.Bold);
                        email1.setTextSize(width/45);
                        email1.setText("Email:");
                        tr3.addView(email1);
                        TextView email = new TextView(context);
                        email.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        email.setTextAppearance(context,R.style.Normal);
                        email.setTextSize(width/45);
                        email.setText(vendorprofile.getString("business_email"));
                        tr3.addView(email);
                        tl_in.addView(tr3);
                        //Address
                        TableRow tr4 = new TableRow(context);
                        TextView address1 = new TextView(context);
                        address1.setTextAppearance(context,R.style.Bold);
                        address1.setTextSize(width/45);
                        address1.setText("Address:");
                        tr4.addView(address1);
                        TextView address = new TextView(context);
                        address.setLayoutParams(new TableRow.LayoutParams((int)(width*6/10), TableRow.LayoutParams.WRAP_CONTENT));
                        address.setTextAppearance(context,R.style.Normal);
                        address.setTextSize(width/45);
                        address.setText(vendorprofile.getString("business_street")+vendorprofile.getString("business_street")+", "
                                +vendorprofile.getString("business_city")+", "+vendorprofile.getString("business_state")+", "+vendorprofile.getString("business_zip"));
                        tr4.addView(address);
                        tl_in.addView(tr4);

                        ll.addView(tl_in);

                        //
                        TextView br2 = new TextView(context);
                        br2.setText("");
                        ll.addView(br2);

                        RelativeLayout ll_bt = new RelativeLayout(context);
                        RelativeLayout.LayoutParams lparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)(width/10));
                        ll_bt.setLayoutParams(lparam);
                        //Request Friend
                        Button rf = new Button(context);
                        RelativeLayout.LayoutParams rfparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        rfparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        rf.setLayoutParams(rfparams);
                        rf.setBackgroundColor(Color.parseColor("#A2D25A"));
                        rf.setTextAppearance(context,R.style.Date);
                        rf.setTextSize(width/55);
                        rf.setText("Request friend");
                        ll_bt.addView(rf);
                        //Add to list
                        Button al = new Button(context);
                        RelativeLayout.LayoutParams alparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        alparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        al.setLayoutParams(alparams);
                        al.setBackgroundColor(Color.parseColor("#A2D25A"));
                        al.setTextAppearance(context,R.style.Date);
                        al.setTextSize(width/55);
                        al.setText("Add to list");
                        ll_bt.addView(al);

                        ll.addView(ll_bt);

                        //
                        TextView br3 = new TextView(context);
                        br3.setText("");
                        ll.addView(br3);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        tl.addView(lv);

                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_vendorprofile_bypost", ht));
    }

    private static void showMarketpage(String id){
        Hashtable<String,String> ht = new Hashtable<String, String>();
        ht.put("fmid",id);
        new FetchTask(){
            @Override
            protected void onPostExecute(JSONObject result){
                try {
                    String error = result.getString("error");
                    if (error.equals("-9")){
                        TableLayout tl = (TableLayout)hashelements.get("marketpageScrollTable");
                        tl.removeAllViews();
                        JSONArray allmarketprofile=result.getJSONArray("marketprofile");
                        JSONObject marketprofile = allmarketprofile.getJSONObject(0);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height/5));
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        //Market Name
                        TextView mn=new TextView(context);
                        mn.setTextAppearance(context,R.style.Bold);
                        mn.setTextSize(width/40);
                        mn.setText(marketprofile.getString("price_market_name"));
                        mn.setGravity(Gravity.CENTER);
                        ll.addView(mn);
                        //
                        TextView br1 = new TextView(context);
                        br1.setText("");
                        ll.addView(br1);

                        //Address
                        TextView address1 = new TextView(context);
                        address1.setTextAppearance(context,R.style.Title);
                        address1.setTextSize(width/50);
                        address1.setText("Address:");
                        ll.addView(address1);
                        TextView address = new TextView(context);
                        address.setTextAppearance(context,R.style.Normal);
                        address.setTextSize(width/45);
                        address.setText(marketprofile.getString("price_street")+", "+marketprofile.getString("price_city")+", "
                                +marketprofile.getString("price_state")+", "+marketprofile.getString("price_zipcode"));
                        ll.addView(address);

                        //
                        TextView br3 = new TextView(context);
                        br3.setText("");
                        ll.addView(br3);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        tl.addView(lv);

                    }
                    else {

                    }
                    setupUI(playout);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_marketprofile_bypost", ht));
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
