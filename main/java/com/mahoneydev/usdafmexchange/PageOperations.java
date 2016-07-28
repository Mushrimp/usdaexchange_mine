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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.mahoneydev.usdafmexchange.pages.*;

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
    protected static View playout = null;
    protected static Hashtable<String, View> hashelements;
    public static Hashtable<String, String> hashvalues;
    private static List<PageNode> pageHistory = new ArrayList<PageNode>();
    public static int height = 0;
    public static int width = 0;

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
            case (R.array.page_102_login): {
                TextView tv = new TextView(context);
                tv.setTextAppearance(context,R.style.Normal);
                tv.setTextColor(Color.WHITE);
                tv.setText("Sign In");
                toolbar.addView(tv);
                break;
            }
        }
        hideKey(context.findViewById(R.id.appbar));
    }

    public static void generateLayout(int code, LinearLayout layout, Hashtable<String, String> params) {
        if ((res == null) || (context == null))
            return;
        TypedArray pageArray = res.obtainTypedArray(code);
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
                        tv.setTextAppearance(context, R.style.Bold);
                        tv.setTextSize(width / 45);
                        tv.setText(jsonelements.getString("value"));
                        if (jsonelements.has("inputtype")) {
                        }
                        hashelements.put(jsonelements.getString("id"), tv);
                        tv.setVisibility(View.INVISIBLE);
                        if (jsonelements.getString("id").equals("forgotView")){
                            tv.setGravity(Gravity.RIGHT);
                            tv.setTextAppearance(context,R.style.Date);
                            tv.setTextSize(width/55);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pushNewPage(R.array.page_103_retrievepin, null);
                                }
                            });
                        }else if(jsonelements.getString("id").equals("newView")){
                            tv.setTextAppearance(context,R.style.Normal);
                            tv.setTextSize(width/55);
                        }else if(jsonelements.getString("id").equals("resetView")){
                            tv.setTextAppearance(context,R.style.Normal);
                        }
                        layout.addView(tv);

                    } else if (element.equals("EditText")) {
                        EditText et = new EditText(context);
                        et.setHint(jsonelements.getString("value"));
                        et.setTextSize(width / 45);
                        if (jsonelements.has("inputtype")) {
                            String type = jsonelements.getString("inputtype");
                            if (type.equals("textPassword"))
                                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            else if (type.equals("number"))
                                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                        } else if (jsonelements.has("enter")) {
                            final String action = jsonelements.getString("enter");
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
                                        executeAction(action);
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
                        et.setHint(jsonelements.getString("value"));
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
                        } else if (jsonelements.has("enter")) {
                            final String action = jsonelements.getString("enter");
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
                                        executeAction(action);
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
                        bt.setText(jsonelements.getString("value"));

                        hashelements.put(jsonelements.getString("id"), bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setBackgroundResource(R.drawable.button_600x50);
                        bt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        bt.setPadding(15, 0, 0, 0);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        bt.setTextSize(width / 50);
                        bt.setTextAppearance(context, R.style.QText);
                        bt.setTransformationMethod(null);
                        bt.setVisibility(View.INVISIBLE);
                        layout.addView(bt);
                    } else if (element.equals("ImageView")) {
                        ImageView iv = new ImageView(context);
                        iv.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), iv);
                        int width = 300;
                        int height = 300;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                        parms.gravity = Gravity.CENTER;
                        iv.setLayoutParams(parms);
                        iv.setImageResource(R.drawable.blank_profile);
                        layout.addView(iv);
                    } else if (element.equals("SearchView")) {
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        EditText et = new EditText(context);
                        et.setHint(jsonelements.getString("value"));
                        hashelements.put(jsonelements.getString("id") + "Input", et);
                        et.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));

                        ll.addView(et);
                        ImageButton bt = new ImageButton(context);
                        bt.setImageResource(R.drawable.ic_menu_manage);
                        hashelements.put(jsonelements.getString("id") + "Button", bt);
                        setButtonAction(jsonelements.getString("clickaction"), bt);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));
                        ll.addView(bt);
                        ll.setVisibility(View.INVISIBLE);
                        layout.addView(ll);
                    } else if (element.equals("ScrollView")) {
                        ScrollView sv = new ScrollView(context);
                        hashelements.put(jsonelements.getString("id"), sv);
                        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        sv.setVisibility(View.INVISIBLE);
                        TableLayout tl = new TableLayout(context);
                        hashelements.put(jsonelements.getString("id") + "Table", tl);
                        tl.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
                        tl.setVisibility(View.INVISIBLE);
                        sv.addView(tl);
                        layout.addView(sv);
                    } else if (element.equals("Spinner")) {
                        Spinner sp = new Spinner(context);
                        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        sp.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), sp);
                        layout.addView(sp);
                    } else if (element.equals("CheckBox")) {
                        CheckBox cb = new CheckBox(context);
                        cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        cb.setText(jsonelements.getString("value"));
                        cb.setVisibility(View.INVISIBLE);
                        hashelements.put(jsonelements.getString("id"), cb);
                        layout.addView(cb);
                    } else if (element.equals("DatePicker")) {
                        Log.e("DatePicker", "1");
                        TextView dp = new TextView(context);
                        dp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        dp.setVisibility(View.INVISIBLE);
                        dp.setText("Select the Date");
                        hashelements.put(jsonelements.getString("id"), dp);
                        layout.addView(dp);
                    }
                }
            }
            renderUI(code, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeAction(String action) {
        if (action.equals("loginSubmit")) {
            page_102_login.loginAction();
        } else if (action.equals("registration")) {
            page_100_registration.registrationAction();
        }
    }

    private static void setButtonAction(String action, ImageButton bt) {
        if (action.equals("searchPublicPost")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_001_front.showpublicposts((((EditText) hashelements.get("searchpublicpostsInput")).getText()).toString());
                }
            });
        } else if (action.equals("testSearch")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String k = (((EditText) hashelements.get("testSearchInput")).getText()).toString();
                    ((TextView) hashelements.get("testView")).setText(k);
                }
            });
        } else if (action.equals("searchfriend")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_406_searchfriend.showRequestfriends((((EditText) hashelements.get("searchfriendsInput")).getText()).toString());
                }
            });
        }
    }







    private static void setButtonAction(String action, Button bt) {
        if (action.equals("loginSubmit")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_102_login.loginAction();
                }
            });
        } else if (action.equals("registration")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_100_registration.registrationAction();
                }
            });
        } else if (action.equals("signUp")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(R.array.page_100_registration, null);
                }
            });
        } else if (action.equals("selectImage")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_106_uploadlogo.startUploadImage();
                }
            });
        }

        //customer service
        else if (action.equals("about")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_003_aboutus, null);
                }
            });
        } else if (action.equals("contact")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_005_contactus, null);
                }
            });
        } else if (action.equals("privacy")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_006_privacy, null);
                }
            });
        } else if (action.equals("help")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_130_helpcenter, null);
                }
            });
        } else if (action.equals("fmdir")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_fmdir, null);
                }
            });
        }

        //post price
        else if (action.equals("newpost")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_322_newpost, null);
                }
            });
        } else if (action.equals("posttemplate")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_324_posttemplate, null);
                }
            });
        } else if (action.equals("postschedule")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_328_postschedule, null);
                }
            });
        } else if (action.equals("postpublish")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_330_postpublish, null);
                }
            });
        }

        //vendor profile
        else if (action.equals("nameaddress")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_205_nameaddress, null);
                }
            });
        } else if (action.equals("phoneemail")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_206_phoneemail, null);
                }
            });
        } else if (action.equals("website")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_207_website, null);
                }
            });
        } else if (action.equals("certified")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_209_certified, null);
                }
            });
        } else if (action.equals("farmermarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_309_farmermarket, null);
                }
            });
        } else if (action.equals("productsell")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_305_productsell, null);
                }
            });
        } else if (action.equals("savenameaddress")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_205_nameaddress.saveNameAddressAction();
                }
            });
        } else if (action.equals("savephoneemail")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_206_phoneemail.saveContactAction();
                }
            });
        } else if (action.equals("savemedia")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_207_website.saveWebAction();
                }
            });
        }

        //social network
        else if (action.equals("friendship")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_401_friendship, null);
                }
            });
        } else if (action.equals("requestfriend")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_406_searchfriend, null);
                }
            });
        }else if (action.equals("request")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_412_request, null);
                }
            });
        } else if (action.equals("message")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_402_message, null);
                }
            });
        } else if (action.equals("notification")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_404_notification, null);
                }
            });
        }

        //account and setting
        else if (action.equals("accountinfo")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_105_accountinfo, null);
                }
            });
        } else if (action.equals("uploadlogo")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_106_uploadlogo, null);
                }
            });
        } else if (action.equals("qrcode")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_109_qrcode, null);
                }
            });
        } else if (action.equals("searchpreference")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_111_searchpreference, null);
                }
            });
        } else if (action.equals("setnotification")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_107_setnotification, null);
                }
            });
        } else if (action.equals("socialnetwork")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_108_socialnetwork, null);
                }
            });
        } else if (action.equals("deleteaccount")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_112_deleteaccount, null);
                }
            });
        } else if (action.equals("savenotificationsettings")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_107_setnotification.savenotificationsettings();
                }
            });
        } else if (action.equals("savesocialsettings")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_108_socialnetwork.savesocialsettings();
                }
            });
        }

        //Account and Settings
        else if (action.equals("searchdistance")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(R.array.page_121_searchdistance, null);
                }
            });
        } else if (action.equals("preferproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(R.array.page_122_preferproduct, null);
                }
            });
        } else if (action.equals("prefervendor")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(R.array.page_123_prefervendor, null);
                }
            });
        } else if (action.equals("prefermarket")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pushNewPage(R.array.page_124_prefermarket, null);
                }
            });
        }
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
        } else if (action.equals("addproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushNewPage(R.array.page_306_addproductform, null);
                }
            });
        } else if (action.equals("publishpost")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_322_newpost.publishPostAction();
                }
            });
        } else if (action.equals("saveproduct")) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page_306_addproductform.saveProductAction();
                }
            });
        }
    }

    private static void renderUI(int code, Hashtable<String, String> params) {
        if (code == R.array.page_001_front) {
            page_001_front.showpublicposts("");
        } else if (code == R.array.page_020_viewpost) {
            String idvalue = params.get("postid");
            page_020_viewpost.showpostView(idvalue);
            /*((TextView)hashelements.get("postView")).setText("This is post "+params.get("postid"));
            setupUI(playout);*/
        } else if (code == R.array.page_016_vendorpage) {
            String name = params.get("username");
            page_016_vendorpage.showVendorpage(name);
        } else if (code == R.array.page_017_marketpage) {
            String id = params.get("marketid");
            page_017_marketpage.showMarketpage(id);
        } else if (code == R.array.page_106_uploadlogo) {
            page_106_uploadlogo.loadavatar();
        } else if (code == R.array.page_105_accountinfo) {
            page_105_accountinfo.prepareaccountinfoform();
        } else if (code == R.array.page_107_setnotification) {
            page_107_setnotification.preparenotifications();
        } else if (code == R.array.page_108_socialnetwork) {
            page_108_socialnetwork.preparesocial();
        } else if (code == R.array.page_205_nameaddress) {
            page_205_nameaddress.preparevendorprofileform();
        } else if (code == R.array.page_206_phoneemail) {
            page_206_phoneemail.preparevendorcontactform();
        } else if (code == R.array.page_207_website) {
            page_207_website.preparevendorwebform();
        } else if (code == R.array.page_407_profile) {
            String name=params.get("friendname");
            page_407_profile.showprofile(name);
        } else if (code == R.array.page_322_newpost) {
            page_322_newpost.preparepostform();
        } else if (code == R.array.page_306_addproductform) {
            page_306_addproductform.prepareproductform();
        } else if (code == R.array.page_777_test) {
            page_777_test.preparetest();
        } else if (code == R.array.page_401_friendship) {
            page_401_friendship.showfriends();
        } else if (code == R.array.page_412_request) {
            page_412_request.showrequests();
        } else if (code == R.array.page_402_message) {
            page_402_message.showmessages();
        } else if (code == R.array.page_403_messageform) {
            String id = params.get("id");
            page_403_messageform.showmessageform(id);
        } else if (code == R.array.page_404_notification) {
            page_404_notification.shownotifications();
        } else if (code == R.array.page_309_farmermarket) {
            page_309_farmermarket.showmarkets();
        } else if (code == R.array.page_305_productsell) {
            page_305_productsell.showproducts();
        } else if (code == R.array.page_324_posttemplate) {
            page_324_posttemplate.showtemplate();
        } else if (code == R.array.page_328_postschedule) {
            page_328_postschedule.showschedule();
        } else if (code == R.array.page_330_postpublish) {
            page_330_postpublish.showpublished();
        } else if (code == R.array.page_122_preferproduct) {
            page_122_preferproduct.showPreferProduct();
        } else if (code == R.array.page_123_prefervendor) {
            page_123_prefervendor.showPreferVendor();
        } else if (code == R.array.page_124_prefermarket) {
            page_124_prefermarket.showPreferMarket();
        } else
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

    public static void squashNewPage(int pageId, Hashtable<String,String> params){
        cleanPageHistory();
        pushNewPage(pageId,params);
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
