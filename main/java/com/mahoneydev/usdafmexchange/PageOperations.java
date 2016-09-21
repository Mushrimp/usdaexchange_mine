package com.mahoneydev.usdafmexchange;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.pages.*;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by mahoneydev on 5/23/2016.
 */
public class PageOperations {
    public static Frontpage context = null;
    public static Resources res = null;
    public static ScrollView sc=null;
    public static String packagename=null;
    protected static View playout = null;
    protected static Hashtable<String, View> hashelements;
    public static Hashtable<String, String> hashvalues;
    private static List<PageNode> pageHistory = new ArrayList<PageNode>();
    public static int height = 0;
    public static int width = 0;
    public static boolean loading=false;

    public static void setPage(int page_id, Hashtable<String, String> ht) {
        if (context != null)
            context.switchContent(page_id, ht);
    }

    public static void setMenu(int menu_id) {
        if (context != null)
            context.switchMenu(menu_id);
    }

    public static void setMenuChecked(int checked_id) {
        if (context != null)
            context.switchMenuChecked(checked_id);
    }

    public static void setAvatar(boolean exist) {
        if (context != null)
            context.switchAvatar(exist);
    }

    public static void generateTitle(int code, RelativeLayout toolbar) {

        toolbar.removeAllViewsInLayout();
        toolbar.setGravity(Gravity.CENTER);
        switch (code) {
            case (R.array.page_001_front): {
                ImageView iv = new ImageView(context);
                iv.setImageResource(R.drawable.fme_header_white);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                toolbar.addView(iv);
                Log.e("Toolbar width", "" + toolbar.getWidth());
                Log.e("Toolbar Height", "" + toolbar.getHeight());
                Log.e("iv width", "" + iv.getWidth());
                Log.e("iv height", "" + iv.getHeight());
                break;
            }
            case (R.array.page_403_messageform): {
                TextView tv = new TextView(context);
                tv.setText(getRecentPage().params.get("subject"));
                tv.setTextAppearance(context,R.style.Normal);
                tv.setTextColor(Color.WHITE);
                tv.setGravity(Gravity.CENTER);
                tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                toolbar.addView(tv);
                break;
            }
            default:{
                ImageView iv = new ImageView(context);
                iv.setImageResource(R.drawable.fme_header_white);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                toolbar.addView(iv);
                break;
            }
        }
        hideKey(context.findViewById(R.id.appbar));
    }
    public static void setupperrightbutton(int code, final ImageButton qbutton, final ImageButton qbutton2)
    {
        switch (code) {
            case (R.array.page_001_front):{
                qbutton.setImageResource(R.drawable.qr);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setupperrightbuttonaction();
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            case (R.array.page_199_scanqr):{
                qbutton.setImageResource(R.drawable.camera_white);
                //qbutton.setImageResource(android.R.drawable.ic_menu_camera);
                //qbutton.setImageResource(android.R.drawable.ic_menu_camera);
//                qbutton.setColorFilter(Color.WHITE);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page_199_scanqr.startScanQR();
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            case (R.array.page_309_farmermarket):{
                qbutton.setImageResource(R.drawable.add_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_311_addmarketform, new Hashtable<String, String>());
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
//            case (R.array.page_311_addmarketform):{
//                TextView tx = new TextView(context);
//                qbutton.set(tx);
//                qbutton.setVisibility(View.VISIBLE);
//                qbutton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        pushNewPage(R.array.page_311_addmarketform, new Hashtable<String, String>());
//                    }
//                });
//                break;
//            }
            case (R.array.page_305_productsell):{
                qbutton.setImageResource(R.drawable.add_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_306_addproductform, new Hashtable<String, String>());
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            case (R.array.page_401_friendship):{
                qbutton.setImageResource(R.drawable.add_friend_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_406_searchfriend, new Hashtable<String, String>());
                    }
                });

                qbutton2.setVisibility(View.VISIBLE);
                qbutton2.setImageResource(R.drawable.send_all_white);
                qbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Hashtable<String,String> ht=new Hashtable<String, String>();
                        ht.put("recipientsname","All Friends");
                        ht.put("recipientsid","-1");
                        pushNewPage(R.array.page_408_sendmessage,ht);
                    }
                });
                break;
            }
            case (R.array.page_402_message):{
                qbutton.setImageResource(R.drawable.compose_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_401_friendship, new Hashtable<String, String>());
                    }
                });

                qbutton2.setVisibility(View.VISIBLE);
                qbutton2.setImageResource(R.drawable.delete_all_white);
                qbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qbutton2.setClickable(false);
                        page_402_message.removeAllmessage();
                        qbutton2.setClickable(true);
                    }
                });
                break;
            }
            case (R.array.page_404_notification):{
                qbutton.setImageResource(R.drawable.delete_all_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page_404_notification.markAllNotifications();
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            case (R.array.page_122_preferproduct):{
                qbutton.setImageResource(R.drawable.add_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_122_preferproduct_addnew, null);
                    }
                });
                break;
            }
            case (R.array.page_123_prefervendor):{
                qbutton.setImageResource(R.drawable.add_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_123_prefervendor_addnew, null);
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            case (R.array.page_124_prefermarket):{
                qbutton.setImageResource(R.drawable.add_white);
                qbutton.setVisibility(View.VISIBLE);
                qbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewPage(R.array.page_124_prefermarket_addnew, null);
                    }
                });
                qbutton2.setVisibility(View.GONE);
                break;
            }
            default:{
                qbutton.setVisibility(View.INVISIBLE);
                qbutton2.setVisibility(View.GONE);
                qbutton.setOnClickListener(null);
                qbutton2.setOnClickListener(null);
                break;
            }
        }
    }
    public static void setupperrightbuttonaction(){
        String role = UserFileUtility.get_role();
        Log.e("role222",role);
        if (role.equals("vendor")){
            pushNewPage(R.array.page_199_scanqr,new Hashtable<String, String>());
        } else if (role.equals("customer")){
            page_199_scanqr.startScanQR();
        } else {
            Toast.makeText(context,"You need to login first.",Toast.LENGTH_SHORT).show();
        }
    }

//    public static void addImageButton(int code, final ImageButton bt1, TextView bl, final ImageButton bt2)
//    {
//        switch (code) {
//            case (R.array.page_401_friendship):{
//                bt1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt1.setImageResource(R.drawable.send_all_white);
//                bt1.setBackgroundResource(R.drawable.button_style);
////                hashelements.put(jsonelements.getString("id") + "Button", bt1);
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Hashtable<String,String> ht=new Hashtable<String, String>();
//                        ht.put("recipientsname","All Friends");
//                        ht.put("recipientsid","-1");
//                        pushNewPage(R.array.page_408_sendmessage,ht);
//                    }
//                });
//                bt1.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//
//                bl.setPadding(20,0,20,0);
//
//                bt2.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt2.setImageResource(R.drawable.add_friend_white);
//                bt2.setBackgroundResource(R.drawable.button_style);
////                hashelements.put(jsonelements.getString("id") + "Button", bt2);
//                bt2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pushNewPage(R.array.page_406_searchfriend, new Hashtable<String, String>());
//                    }
//                });
//                bt2.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//                break;
//            }
//
//            case (R.array.page_402_message):{
//                bt1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt1.setImageResource(R.drawable.delete_all_white);
//                bt1.setBackgroundResource(R.drawable.button_style);
////                hashelements.put(jsonelements.getString("id") + "Button", bt1);
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        bt1.setClickable(false);
//                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                        alert.setTitle("Delete all the messages");
//                        alert.setMessage("DO you want to DELETE all the messages?");
//                        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //do your work here
//                                page_402_message.removeAllmessage();
//                                dialog.dismiss();
//
//                            }
//                        });
//                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                            }
//                        });
//
//                        alert.show();
//                        bt1.setClickable(true);
//                    }
//                });
//
//                bt1.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//
//                bl.setPadding(20,0,20,0);
//
//                bt2.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt2.setImageResource(R.drawable.compose_white);
//                bt2.setBackgroundResource(R.drawable.button_style);
////                hashelements.put(jsonelements.getString("id") + "Button", bt2);
//                bt2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pushNewPage(R.array.page_401_friendship, new Hashtable<String, String>());
//                    }
//                });
//                bt2.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//                break;
//            }
//            default:{
//                bt1.setVisibility(View.INVISIBLE);
//                bt2.setVisibility(View.INVISIBLE);
//                bt1.setOnClickListener(null);
//                bt2.setOnClickListener(null);
//                break;
//            }
//        }
//    }

    public static void generateLayout(int code, LinearLayout layout,RelativeLayout toolbar, Hashtable<String, String> params) {
        if ((res == null) || (context == null)||(toolbar==null))
            return;
        sc=(ScrollView)context.findViewById(R.id.scroll);
        sc.setOnTouchListener(null);
        loading=true;
        packagename=context.getPackageName();
        TypedArray pageArray = res.obtainTypedArray(code);
        //layout.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,height));
        layout.removeAllViewsInLayout();
        Log.e("remove", "21134124");
        ProgressBar pb = new ProgressBar(context);
        pb.setIndeterminate(true);
        layout.addView(pb);
        hashelements = new Hashtable<>();
        hashelements.put("Loading", pb);
        playout = layout;
        try {

            for (int i = 0; i < pageArray.length(); i++) {
                String elements = pageArray.getString(i);
                JSONObject jsonelements = new JSONObject(elements);
                if (jsonelements.has("element")) {
                    String element = jsonelements.getString("element");
                    if (element.equals("TextView")) {
                        TextView tv = new TextView(context);
                        tv.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), tv);
                        tv.setVisibility(View.INVISIBLE);
                        if(jsonelements.getString("id").equals("newView")){
                            tv.setTextAppearance(context,R.style.Normal);
                            tv.setTextSize(width/45);
                            tv.setPadding(0,0,0,20);
                        } else if(jsonelements.getString("id").equals("resetView")){
                            tv.setTextAppearance(context,R.style.Normal);
                        } else if(jsonelements.getString("id").equals("recipientname")){
                            tv.setTextAppearance(context,R.style.Normal);
                            tv.setTextSize(width/35);
                            tv.setPadding(0,20,0,0);
                        } else if (jsonelements.getString("id").equals("nameView")){
                            tv.setTextAppearance(context,R.style.EditText);
                            tv.setTextSize(width/40);
                        } else if (jsonelements.getString("id").equals("instructionView")){
                            tv.setTextAppearance(context,R.style.EditText);
                            tv.setTextSize(width/45);
                            tv.setPadding(0,20,0,30);
                        } else if (jsonelements.getString("id").equals("addmarketinstrView")){
                            tv.setTextAppearance(context,R.style.EditText);
                            tv.setTextSize(width/45);
                            tv.setPadding(0,10,0,10);
                        } else {
                            tv.setTextAppearance(context, R.style.Bold);
                            tv.setTextSize(width / 45);
                            tv.setPadding(0,30,0,0);
                        }
                        layout.addView(tv);

                    }
                    else if (element.equals("TextRightView")){
                        LinearLayout rl = new LinearLayout(context);
                        rl.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.9), LinearLayout.LayoutParams.WRAP_CONTENT));
                        rl.setGravity(Gravity.RIGHT);
                        TextView tv = new TextView(context);
                        tv.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), tv);
                        tv.setVisibility(View.INVISIBLE);
                            tv.setGravity(Gravity.RIGHT);
                            tv.setTextAppearance(context,R.style.Date);
                            tv.setTextSize(width/45);
                            tv.setPadding(0,0,0,20);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pushNewPage(R.array.page_103_retrievepin, new Hashtable<String, String>());
                                }
                            });
                        rl.addView(tv);
                        layout.addView(rl);
                    }
                    else if (element.equals("TextButtonView")){
                        RelativeLayout rl = new RelativeLayout(context);
                        TextView tv = new TextView(context);
                        RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        parms1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        parms1.addRule(RelativeLayout.CENTER_VERTICAL);
                        tv.setLayoutParams(parms1);
                        tv.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), tv);
                        tv.setVisibility(View.INVISIBLE);
                        tv.setTextAppearance(context, R.style.Bold);
                        tv.setTextSize(width / 45);
                        tv.setPadding(0,30,0,0);
                        rl.addView(tv);
                        if (jsonelements.getString("id").equals("marketView1")) {
                            Button addpro = new Button(context);
                            addpro.setText(R.string.l_322_NewPost_Product_AddButton_0);
                            hashelements.put("addproduct322", addpro);
                            setButtonAction("productsell", addpro);
                            addpro.setBackgroundResource(R.drawable.button_style);
                            addpro.setStateListAnimator(null);
                            addpro.setPadding(0, 0, 0, 2);
                            addpro.setTextAppearance(context, R.style.White);
                            addpro.setTextSize(width / 50);
                            RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams((int)(width*0.3), (int)(height*0.04));
                            parms2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            parms2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            addpro.setLayoutParams(parms2);
                            addpro.setTransformationMethod(null);
                            addpro.setVisibility(View.INVISIBLE);
                            rl.addView(addpro);
                        } else if (jsonelements.getString("id").equals("marketView3")){
                            Button addmar = new Button(context);
                            addmar.setText(R.string.l_322_NewPost_FarmersMarket_AddButton_0);
                            hashelements.put("addmarket322", addmar);
                            setButtonAction("farmermarket", addmar);
                            addmar.setBackgroundResource(R.drawable.button_style);
                            addmar.setStateListAnimator(null);
                            addmar.setPadding(0, 0, 0, 2);
                            addmar.setTextAppearance(context, R.style.White);
                            addmar.setTextSize(width / 50);
                            RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams((int)(width*0.3), (int)(height*0.04));
                            parms2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            parms2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            addmar.setLayoutParams(parms2);
                            addmar.setTransformationMethod(null);
                            addmar.setVisibility(View.INVISIBLE);
                            rl.addView(addmar);
                        }
                        layout.addView(rl);
                    }
                    else if (element.equals("Title")){
                        ImageButton qbutton =(ImageButton)context.findViewById(R.id.camerabutton);
                        ImageButton qbutton2 =(ImageButton)context.findViewById(R.id.camerabutton2);
                        toolbar.removeAllViewsInLayout();
                        toolbar.setGravity(Gravity.CENTER);
                        TextView tb = new TextView(context);
                        tb.setTextAppearance(context,R.style.Normal);
                        tb.setTextColor(Color.WHITE);
                        String value=jsonelements.getString("value");
                        setupperrightbutton(code,qbutton,qbutton2);
                        if (value.equals("image")||value.equals("special"))
                        {
                            generateTitle(code, toolbar) ;
                        }
                        else {
                            tb.setText(AppCodeResources.getStringbyName(res,packagename,value));
                            toolbar.addView(tb);
                            hideKey(context.findViewById(R.id.toolbarLayout));
                        }

                    }
                    else if (element.equals("EditText")) {
                        EditText et = new EditText(context);
                        et.setHint(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        et.setPadding(0,20,0,8);
                        et.setTextAppearance(context,R.style.EditText);
                        et.setHintTextColor(context.getResources().getColor(R.color.colorGray));
                        et.setTextSize(width / 35);
                        et.setSingleLine();
                        boolean multiple=false;
                        if (jsonelements.has("inputtype")) {
                            String type = jsonelements.getString("inputtype");
                            if (type.equals("textPassword")) {
                               et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                et.setTypeface(Typeface.DEFAULT);
                                et.setTransformationMethod(new PasswordTransformationMethod());
                            }
                            else if (type.equals("number"))
                                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            else if (type.equals("send")) {
                                et.setImeOptions(EditorInfo.IME_ACTION_SEND);
                            }
                            else if (type.equals("email"))
                                et.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            else if (type.equals("multiple")) {
                                et.setSingleLine(false);
                                multiple=true;
                            }
                        }
                        if (jsonelements.has("next")) {
                            final String thisid = jsonelements.getString("id");
                            final String nextid = jsonelements.getString("next");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m = s.toString();
                                    if (m.indexOf("\n") != -1) {
                                        ((EditText) hashelements.get(thisid)).setText(m.replace("\n", ""));
                                        ((EditText) hashelements.get(thisid)).clearFocus();
                                        (hashelements.get(nextid)).requestFocus();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        } else if (multiple==false){
                            final String thisid = jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m = s.toString();
                                    if (m.indexOf("\n") != -1) {
                                        ((EditText) hashelements.get(thisid)).setText(m.replace("\n", ""));
                                        ((EditText) hashelements.get(thisid)).clearFocus();
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
                    } else if (element.equals("AutoCompleteTextView")) {
                        AutoCompleteTextView et = new AutoCompleteTextView(context);
                        et.setHint(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        et.setPadding(0,20,0,8);
                        et.setTextAppearance(context,R.style.EditText);
                        et.setHintTextColor(context.getResources().getColor(R.color.colorGray));
                        et.setTextSize(width / 35);
                        if (jsonelements.has("inputtype")) {
                            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                        if (jsonelements.has("next")) {
                            final String thisid = jsonelements.getString("id");
                            final String nextid = jsonelements.getString("next");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m = s.toString();
                                    if (m.indexOf("\n") != -1) {
                                        ((AutoCompleteTextView) hashelements.get(thisid)).setText(m.replace("\n", ""));
                                        ((AutoCompleteTextView) hashelements.get(thisid)).clearFocus();
                                        (hashelements.get(nextid)).requestFocus();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        } else {
                            final String thisid = jsonelements.getString("id");
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String m = s.toString();
                                    if (m.indexOf("\n") != -1) {
                                        ((AutoCompleteTextView) hashelements.get(thisid)).setText(m.replace("\n", ""));
                                        ((AutoCompleteTextView) hashelements.get(thisid)).clearFocus();
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
                    } else if (element.equals("Button")) {
                        Button bt = new Button(context);
                        bt.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setBackgroundResource(R.drawable.button_menu);
                        Drawable img = context.getResources().getDrawable(R.drawable.next_item);
                        img.setBounds( 0, 0, 40, 40);
                        bt.setCompoundDrawables(null, null, img, null );
                        bt.setStateListAnimator(null);
                        bt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(height/10)));
                        bt.setPadding(20,0,10,0);
                        bt.setTextAppearance(context, R.style.Normal);
                        bt.setTextSize(width / 40);
                        bt.setTransformationMethod(null);
                        bt.setVisibility(View.INVISIBLE);
                        layout.addView(bt);
                    } else if (element.equals("InsideButton")){
                        LinearLayout ll = new LinearLayout(context);
                        ll.setGravity(Gravity.CENTER);
                        Button inbt = new Button(context);
                        inbt.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), inbt);
                        setButtonAction(jsonelements.getString("clickaction"), inbt);
                        inbt.setBackgroundResource(R.drawable.button_style);
                        inbt.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.7), (int)(height/15)));
                        inbt.setStateListAnimator(null);
                        inbt.setPadding(0, 0, 0, 1);
                        inbt.setTextAppearance(context, R.style.White);
                        inbt.setTextSize(width / 40);
                        inbt.setTransformationMethod(null);
                        inbt.setVisibility(View.INVISIBLE);
                        ll.addView(inbt);
                        layout.addView(ll);
                    } else if (element.equals("NextButton")){
                        Button bt = new Button(context);
                        bt.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id"), bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setBackgroundResource(R.drawable.button_next);
                        Drawable img = context.getResources().getDrawable(R.drawable.next_item);
                        img.setBounds( 0, 0, 40, 40);
                        bt.setCompoundDrawables(null, null, img, null );
                        bt.setStateListAnimator(null);
                        bt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(height/10)));
                        bt.setPadding(20,0,10,0);
                        bt.setTextAppearance(context, R.style.Normal);
                        bt.setTextSize(width / 40);
                        bt.setTransformationMethod(null);
                        bt.setVisibility(View.INVISIBLE);
                        layout.addView(bt);
                    }
//                    else if (element.equals("ImageButton")){
//                        LinearLayout ll = new LinearLayout(context);
//                        ll.setOrientation(LinearLayout.HORIZONTAL);
//                        ll.setGravity(Gravity.END);
//                        ImageButton bt1 = new ImageButton(context);
//                        ImageButton bt2 = new ImageButton(context);
//                        TextView bl = new TextView(context);
//                        addImageButton(code,bt1,bl,bt2);
//                        ll.addView(bt1);
//                        ll.addView(bl);
//                        ll.addView(bt2);
//                        layout.addView(ll);
//
//
//
//                    }
                    else if (element.equals("LineView")) {
                        View line = new View(context);
                        line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                        line.setBackgroundColor(Color.parseColor("#A2D25A"));
                        layout.addView(line);
                    }
                    else if (element.equals("ImageView")) {
                        ImageView iv = new ImageView(context);
                        iv.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), iv);
                        int width = 300;
                        int height = 300;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                        parms.gravity = Gravity.CENTER;
                        iv.setLayoutParams(parms);
                        if (jsonelements.getString("id").equals("postpriceView")){
                            iv.setImageResource(R.drawable.post_price);
                        }else if (jsonelements.getString("id").equals("custserviceView")){
                            iv.setImageResource(R.drawable.customer_service);
                        }else if (jsonelements.getString("id").equals("profilevendorView")){
                            iv.setImageResource(R.drawable.vendor_circle);
                        }else if (jsonelements.getString("id").equals("socialnetworkView")){
                            iv.setImageResource(R.drawable.social_network);
                        }else if (jsonelements.getString("id").equals("accountsettingView")){
                            iv.setImageResource(R.drawable.setting_circle);
                        }else if (jsonelements.getString("id").equals("searchprefView")){
                            iv.setImageResource(R.drawable.search_pref);
                        }else {
                            iv.setImageResource(R.drawable.blank_profile);
                        }
                        layout.addView(iv);
                    } else if (element.equals("SearchView")) {
//                        SearchView sv = new SearchView(context);
//                        sv.setBackgroundResource((R.drawable.searchview_style));
//                        layout.addView(sv);

                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        EditText et = new EditText(context);
                        if(jsonelements.getString("id").equals("searchpublicposts")){
                            et.setTextSize(width/60);
                        }else if(jsonelements.getString("id").equals("searchproducts")){
                            et.setTextSize(width/50);
                        }else if(jsonelements.getString("id").equals("searchfriends")){
                            et.setTextSize(width/50);
                        }else if(jsonelements.getString("id").equals("searchmarket")){
                            et.setTextSize(width/50);
                        }
                        et.setHint(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        hashelements.put(jsonelements.getString("id") + "Input", et);
                        et.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));

                        ll.addView(et);
                        ImageButton bt = new ImageButton(context);
                        bt.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        bt.setPadding(10,5,10,5);
                        bt.setImageResource(R.drawable.search_white);
                       // bt.setImageResource(R.drawable.ic_menu_search);
                       // bt.setColorFilter(Color.WHITE);
                        bt.setBackgroundResource(R.drawable.button_style);
                        hashelements.put(jsonelements.getString("id") + "Button", bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.15), (int)(height*0.05)));
                        ll.addView(bt);
                        ll.setVisibility(View.INVISIBLE);
                        layout.addView(ll);
                    } else if (element.equals("ScrollView")) {
                        ScrollView sv = new ScrollView(context);
                        sv.setPadding(0,20,0,40);
                        hashelements.put(jsonelements.getString("id"), sv);
                        LinearLayout.LayoutParams layoutparams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        sv.setLayoutParams(layoutparams);
                        sv.setBackgroundResource(R.color.colorWhite);
                        sv.setVisibility(View.INVISIBLE);
                        TableLayout tl = new TableLayout(context);
                        hashelements.put(jsonelements.getString("id") + "Table", tl);
                        tl.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
                        tl.setVisibility(View.INVISIBLE);
                        sv.addView(tl);
                        layout.addView(sv);
                    } else if (element.equals("Spinner")) {
                        Spinner sp = new Spinner(context);
                        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(height*0.08)));
                       // sp.setPadding(0,15,0,0);
                        sp.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), sp);
                        layout.addView(sp);
                    } else if (element.equals("CheckBox")) {
                        LinearLayout ll_cb = new LinearLayout(context);
                        ll_cb.setPadding(0,30,0,0);
                        CheckBox cb = new CheckBox(context);
                        cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        cb.setTextAppearance(context, R.style.Normal);
                        cb.setTextSize(width/40);
                        cb.setGravity(Gravity.TOP);
                        cb.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        cb.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), cb);
                        ll_cb.addView(cb);
                        layout.addView(ll_cb);
                    } else if (element.equals("DatePicker")) {
                        Log.e("DatePicker", "1");
                        TextView dp = new TextView(context);
                        dp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        dp.setVisibility(View.INVISIBLE);
                        dp.setPadding(0,20,0,8);
                        dp.setText(AppCodeResources.getStringbyName(res,packagename,jsonelements.getString("value")));
                        dp.setTextAppearance(context, R.style.EditText);
                        dp.setBackgroundResource(R.drawable.spinner_style);
                        dp.setTextSize(width/35);
                        hashelements.put(jsonelements.getString("id"), dp);
                        layout.addView(dp);
                    }
                }
            }
            renderUI(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void setButtonAction(String action, ImageButton bt) {
        if (action.equals("searchPublicPost")) {
            bt.setOnClickListener(new page_001_front.searchPostsListener(bt));
        } else if (action.equals("testSearch")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String k = (((EditText) hashelements.get("testSearchInput")).getText()).toString();
                    ((TextView) hashelements.get("testView")).setText(k);
                }
            });
        }else if (action.equals("searchfriend")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_406_searchfriend.showRequestfriends((((EditText) hashelements.get("searchfriendsInput")).getText()).toString());
                }
            });
        }
        else if (action.equals("searchproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_122_preferproduct_addnew.searchpreproduct((((EditText) hashelements.get("searchproductsInput")).getText()).toString());
                }
            });
        }
        else if (action.equals("searchvendor")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_123_prefervendor_addnew.searchvendor((((EditText) hashelements.get("searchvendorInput")).getText()).toString());
                }
            });
        }
        else if (action.equals("searchpremarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_124_prefermarket_addnew.searchpremarket((((EditText) hashelements.get("searchpremarketInput")).getText()).toString());
                }
            });
        }
        else if (action.equals("searchmarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_311_addmarketform.searchmarket((((EditText) hashelements.get("searchmarketInput")).getText()).toString());
                }
            });
        }
    }

    private static void setButtonAction(String action, final Button bt) {
        if (action.equals("loginSubmit")) {
            bt.setOnClickListener(new page_102_login.loginListener(bt));
        } else if (action.equals("registration")) {
            bt.setOnClickListener(new page_100_registration.registrationListener(bt));
        } else if (action.equals("signUp")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_100_registration, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("selectImage")) {
            bt.setOnClickListener(new page_106_uploadlogo.startUploadImageListener(bt));
        } else if (action.equals("sendemail")) {
            bt.setOnClickListener(new page_103_retrievepin.retrievepinListener(bt));
        }
        //View Post
        else if (action.equals("productlist")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    Hashtable<String,String> ht=new Hashtable<String, String>();
                    ht.put("username",getRecentPage().params.get("username"));
                    pushNewPage(R.array.page_015_productlist_byvendor,ht);
                }
            });
        }else if (action.equals("marketsell")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    Hashtable<String,String> ht=new Hashtable<String, String>();
                    ht.put("username",getRecentPage().params.get("username"));
                    pushNewPage(R.array.page_010_marketlist_byvendor,ht);
                }
            });
        }
        //customer service
        else if (action.equals("about")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_003_aboutus, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("contact")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_005_contactus, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("privacy")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_006_privacy, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("help")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_130_helpcenter, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("fmdir")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_fmdir, new Hashtable<String, String>());
                }
            });
        }

        //post price
        else if (action.equals("newpost")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_322_newpost, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("posttemplate")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_324_posttemplate, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("postschedule")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_328_postschedule, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("postpending")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    Hashtable<String,String> ht=new Hashtable<String, String>();
                    pushNewPage(R.array.page_332_postpending,ht);
                }
            });
        }
        else if (action.equals("postpublish")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_330_postpublish, new Hashtable<String, String>());
                }
            });
        }

        //vendor profile
        else if (action.equals("nameaddress")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_205_nameaddress, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("phoneemail")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_206_phoneemail, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("website")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_207_website, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("certified")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_209_certified, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("farmermarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_309_farmermarket, new Hashtable<String, String>());
                }
            });
        }else if (action.equals("addmarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_311_addmarketform, new Hashtable<String, String>());
                }
            });
        }else if (action.equals("cantfindmarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_310_addnewmarket, new Hashtable<String, String>());
                }
            });
        }else if (action.equals("productsell")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_305_productsell, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("addproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_306_addproductform, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("savenameaddress")) {
            bt.setOnClickListener(new page_205_nameaddress.saveNameAddressListener(bt));
        } else if (action.equals("savephoneemail")) {
            bt.setOnClickListener(new page_206_phoneemail.savecontactListener(bt));
        } else if (action.equals("savemedia")) {
            bt.setOnClickListener(new page_207_website.savewebListener(bt));
        } else if (action.equals("saveproduct")) {
            bt.setOnClickListener(new page_306_addproductform.saveproductListener(bt));
        } else if (action.equals("savenewmarket")) {
            bt.setOnClickListener(new page_310_addnewmarket.savenewmarketListener(bt));
        }

        //social network
        else if (action.equals("friendship")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_401_friendship, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("sendmessageall")) {
            bt.setOnClickListener(new page_401_friendship.sendmessageallListener(bt));
        }else if (action.equals("requestfriend")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_406_searchfriend, new Hashtable<String, String>());
                }
            });
        }else if (action.equals("request")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_412_request, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("message")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_402_message, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("notification")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_404_notification, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("sendmessageAction")) {
            bt.setOnClickListener(new page_408_sendmessage.sendmessageListener(bt));
        }  else if (action.equals("replymessageaction")) {
            bt.setOnClickListener(new page_403_messageform.replymessageListener(bt));
        }

        //account and setting
        else if (action.equals("accountinfo")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_105_accountinfo, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("uploadlogo")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_106_uploadlogo, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("qrcode")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_199_scanqr,new Hashtable<String, String>());
                }
            });
        } else if (action.equals("searchpreference")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_111_searchpreference, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("setnotification")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_107_setnotification, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("socialnetwork")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_108_socialnetwork, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("deleteaccount")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bt.setClickable(false);
                    if (UserFileUtility.get_role().equals("vendor")) {
                        pushNewPage(R.array.page_112_deleteaccount, new Hashtable<String, String>());
                    } else if (UserFileUtility.get_role().equals("customer")){
                        pushNewPage(R.array.page_112_deleteaccount_customer, new Hashtable<String, String>());
                    }
                }
            });
        } else if (action.equals("savenotificationsettings")) {
            bt.setOnClickListener(new page_107_setnotification.savenotificationsettingsListener(bt));
        } else if (action.equals("savesocialsettings")) {
            bt.setOnClickListener(new page_108_socialnetwork.savesocialListener(bt));
        }

        //Account and Settings
        else if (action.equals("searchdistance")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_121_searchdistance, new Hashtable<String, String>());
                }
            });
        } else if (action.equals("preferproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_122_preferproduct, new Hashtable<String, String>());
                }
            });
        }
//        else if (action.equals("addpreproduct")) {
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pushNewPage(R.array.page_122_preferproduct_addnew, null);
//                }
//            });
//        }
        else if (action.equals("prefervendor")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_123_prefervendor, new Hashtable<String, String>());
                }
            });
        }
//        else if (action.equals("addprevendor")) {
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pushNewPage(R.array.page_123_prefervendor_addnew, null);
//                }
//            });
//        }
        else if (action.equals("prefermarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt.setClickable(false);
                    pushNewPage(R.array.page_124_prefermarket, new Hashtable<String, String>());
                }
            });
        }
//        else if (action.equals("addpremarket")) {
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pushNewPage(R.array.page_124_prefermarket_addnew, null);
//                }
//            });
//        }
        //Account
        else if (action.equals("saveinfo")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_105_accountinfo.saveUserInfoAction();
                }
            });
        } else if (action.equals("scanQR")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_199_scanqr.startScanQR();
                }
            });
        } else if (action.equals("TestBegin")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_777_test.testAction();
                }
            });
        } else if (action.equals("publishpost")) {
            bt.setOnClickListener(new page_322_newpost.savepostListener(bt));
        }
    }

    private static void renderUI(int code) {
        if (code == R.array.page_001_front) {
            page_001_front.showpublicposts("");
        } else if (code == R.array.page_020_viewpost) {
            page_020_viewpost.showpostView();
//            ((TextView)hashelements.get("postView")).setText("This is post "+params.get("postid"));
//            setupUI(playout);
 }else if (code==R.array.page_010_marketlist_byvendor) {
           page_010_marketlist_byvendor.showmarkets();
        }else if (code == R.array.page_015_productlist_byvendor) {
            page_015_productlist_byvendor.showproducts();
        }else if (code == R.array.page_016_vendorpage) {
            page_016_vendorpage.showVendorpage();
        } else if (code == R.array.page_017_marketpage) {
            page_017_marketpage.showMarketpage();
        } else if (code == R.array.page_106_uploadlogo) {
            page_106_uploadlogo.loadavatar();
        } else if (code == R.array.page_105_accountinfo) {
            page_105_accountinfo.prepareaccountinfoform();
        } else if (code == R.array.page_107_setnotification) {
            page_107_setnotification.preparenotifications();
        } else if (code == R.array.page_108_socialnetwork) {
            page_108_socialnetwork.preparesocial();
        }else if (code==R.array.page_199_scanqr){
            page_199_scanqr.showQRcode();
        } else if (code == R.array.page_205_nameaddress) {
            page_205_nameaddress.preparevendorprofileform();
        } else if (code == R.array.page_206_phoneemail) {
            page_206_phoneemail.preparevendorcontactform();
        } else if (code == R.array.page_207_website) {
            page_207_website.preparevendorwebform();
        } else if (code == R.array.page_322_newpost) {
            String id = "";
            page_322_newpost.preparepostform(id);
        } else if (code == R.array.page_306_addproductform) {
            page_306_addproductform.prepareproductform();
        } else if (code == R.array.page_310_addnewmarket) {
            page_310_addnewmarket.preparemarketform();
        } else if (code == R.array.page_777_test) {
            page_777_test.preparetest();
        } else if (code == R.array.page_401_friendship) {
            page_401_friendship.showfriends();
        } else if (code == R.array.page_412_request) {
            page_412_request.showrequests();
        } else if (code == R.array.page_402_message) {
            page_402_message.showmessages();
        } else if (code == R.array.page_403_messageform) {
            page_403_messageform.showmessageform();
        } else if (code == R.array.page_408_sendmessage) {
            page_408_sendmessage.preparemessage();
        } else if (code == R.array.page_407_userprofile) {
            page_407_userprofile.showUserpage();
        }else if (code == R.array.page_404_notification) {
            page_404_notification.shownotifications();
        } else if (code == R.array.page_309_farmermarket) {
            page_309_farmermarket.showmarkets();
        }
//        else if (code == R.array.page_311_addmarketform) {
//            page_311_addmarketform.searchmarket();
//        }
        else if (code == R.array.page_305_productsell) {
            page_305_productsell.showproducts();
        } else if (code == R.array.page_324_posttemplate) {
            page_324_posttemplate.showtemplate();
        } else if (code == R.array.page_328_postschedule) {
            page_328_postschedule.showschedule();
        } else if (code == R.array.page_332_postpending) {
            page_332_postpending.showpending();
        } else if (code == R.array.page_330_postpublish) {
            page_330_postpublish.showpublished();
        } else if (code == R.array.page_122_preferproduct) {
            page_122_preferproduct.showPreferProduct();
        }
//        else if (code == R.array.page_122_preferproduct_addnew) {
//            page_122_preferproduct_addnew.searchpreproduct();
//        }
        else if (code == R.array.page_123_prefervendor) {
            page_123_prefervendor.showPreferVendor();
        }
//      else if (code == R.array.page_123_prefervendor_addnew) {
//            page_123_prefervendor_addnew.searchprevendor();
//        }
           else if (code == R.array.page_124_prefermarket) {
            page_124_prefermarket.showPreferMarket();
        }
//        else if (code == R.array.page_124_prefermarket_addnew) {
//            page_124_prefermarket_addnew.searchpremarket();
//        }
        else if (code == R.array.page_112_deleteaccount) {
            String name = UserFileUtility.get_username();
//            String name = getRecentPage().params.get("username");
            page_112_deleteaccount.showdeletepage(name);
        }else if (code == R.array.page_112_deleteaccount_customer) {
            String name = UserFileUtility.get_username();
//            String name = getRecentPage().params.get("username");
            page_112_deleteaccount_customer.showdeletepage_customer(name);
        }
        else
            setupUI(playout);
    }

    protected static void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

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

                setupUIinner(innerView);
            }
        }
        ((ProgressBar) hashelements.get("Loading")).setVisibility(View.GONE);
        loading=false;
    }

    private static void setupUIinner(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

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

                setupUIinner(innerView);
            }
        }
    }
    private static void hideKey(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

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

                hideKey(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static PageNode getRecentPage() {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        if (pageHistory.isEmpty())
            return null;
        return pageHistory.get(0);
    }
    public static void showRecentPage() {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        if (pageHistory.isEmpty())
            return;
        else {
            PageNode pn = pageHistory.get(0);
            setPage(pn.pageId, pn.params);
        }
    }
    public static void removeRecentPage() {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        if (!pageHistory.isEmpty())
            pageHistory.remove(0);
        if (PageOperations.historyEmpty())
        {
            pushNewPage(R.array.page_001_front,null);
        }
        else {
            PageNode pn = getRecentPage();
            setPage(pn.pageId, pn.params);
        }
    }

    private static void cleanPageHistory() {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        pageHistory.clear();
    }

    public static void pushNewPage(int pageId, Hashtable<String,String> params) {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        pageHistory.add(0, new PageNode(pageId,params));
        setPage(pageId,params);
    }
    public static void pushNewPageHold(int pageId, Hashtable<String,String> params)
    {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        pageHistory.add(0, new PageNode(pageId,params));
    }
    public static void squashNewPage(int pageId, Hashtable<String,String> params){
        cleanPageHistory();
        pushNewPage(pageId,params);
    }
    public static void squashNewPageHold(int pageId, Hashtable<String,String> params){
        cleanPageHistory();
        pushNewPageHold(pageId,params);
    }
    public static boolean historyEmpty() {
        if (pageHistory == null)
            pageHistory = new ArrayList<>();
        if (pageHistory.isEmpty())
            return true;
        else
            return false;
    }

}
