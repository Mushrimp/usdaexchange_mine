package com.mahoneydev.usdafmexchange;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mahoneydev on 5/11/2016.
 */
public class AppCodeResources {
    public static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public static final String web_url = "http://52.207.244.89/usdafmexchange/";
    public static final String api = "api/";
    public static final String userfile = "username.txt";
    public static final int IMAGE_UPLOAD= 55;
    public static final int SCAN_QR=56;
    public static final String FRIEND_URL_PRE="http://52.207.244.89/usdafmexchange/407-friendship-request-action/?fname=";
    public static final ArrayList<SpinnerElement> state_list=new ArrayList<SpinnerElement>(){{
        add(new SpinnerElement("select a state",""));
        add(new SpinnerElement("Alabama","01"));
        add(new SpinnerElement("Alaska","02"));
        add(new SpinnerElement("Arizona","04"));
        add(new SpinnerElement("Arkansas","05"));
        add(new SpinnerElement("California","06"));
        add(new SpinnerElement("Colorado","08"));
        add(new SpinnerElement("Connecticut","09"));
        add(new SpinnerElement("Delaware","10"));
        add(new SpinnerElement("Washington DC","11"));
        add(new SpinnerElement("Florida","12"));
        add(new SpinnerElement("Georgia","13"));
        add(new SpinnerElement("Hawaii","15"));
        add(new SpinnerElement("Idaho","16"));
        add(new SpinnerElement("Illinois","17"));
        add(new SpinnerElement("Indiana","18"));
        add(new SpinnerElement("Iowa","19"));
        add(new SpinnerElement("Kansas","20"));
        add(new SpinnerElement("Kentucky","21"));
        add(new SpinnerElement("Louisiana","22"));
        add(new SpinnerElement("Maine","23"));
        add(new SpinnerElement("Maryland","24"));
        add(new SpinnerElement("Massachusetts","25"));
        add(new SpinnerElement("Michigan","26"));
        add(new SpinnerElement("Minnesota","27"));
        add(new SpinnerElement("Mississippi","28"));
        add(new SpinnerElement("Missouri","29"));
        add(new SpinnerElement("Montana","30"));
        add(new SpinnerElement("Nebraska","31"));
        add(new SpinnerElement("Nevada","32"));
        add(new SpinnerElement("New Hampshire","33"));
        add(new SpinnerElement("New Jersey","34"));
        add(new SpinnerElement("New Mexico","35"));
        add(new SpinnerElement("New York","36"));
        add(new SpinnerElement("North Carolina","37"));
        add(new SpinnerElement("North Dakota","38"));
        add(new SpinnerElement("Northern Mariana Islands","69"));
        add(new SpinnerElement("Ohio","39"));
        add(new SpinnerElement("Oklahoma","40"));
        add(new SpinnerElement("Oregon","41"));
        add(new SpinnerElement("Pennsylvania","42"));
        add(new SpinnerElement("Puerto Rico","72"));
        add(new SpinnerElement("Rhode Island","44"));
        add(new SpinnerElement("South Carolina","45"));
        add(new SpinnerElement("South Dakota","46"));
        add(new SpinnerElement("Tennessee","47"));
        add(new SpinnerElement("Texas","48"));
        add(new SpinnerElement("Utah","49"));
        add(new SpinnerElement("Vermont","50"));
        add(new SpinnerElement("Virgin Islands","78"));
        add(new SpinnerElement("Virginia","51"));
        add(new SpinnerElement("Washington","53"));
        add(new SpinnerElement("West Virginia","54"));
        add(new SpinnerElement("Wisconsin","55"));
        add(new SpinnerElement("Wyoming","56"));

    }};

    public static String postUrl(String controller, String method, Hashtable<String, String> param) {
        String s = web_url + api;
        if ((controller != null) && (!controller.equals(""))) {
            s = s + controller + "/";
        }
        if ((method != null) && (!method.equals(""))) {
            s = s + method + "/";
        }
        boolean firstKey = true;
        for (String key : param.keySet()) {
            if (firstKey) {
                s = s + '?';
                firstKey = false;
            } else {
                s = s + '&';
            }
            try {
                s = s + URLEncoder.encode(key, "UTF-8") + '=' + URLEncoder.encode(param.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return s;
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static boolean isZipCodeValid(String zip) {
        boolean isValid = false;

        String expression = "^[0-9]{5}$";
        CharSequence inputStr = zip;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
