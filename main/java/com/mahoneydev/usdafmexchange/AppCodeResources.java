package com.mahoneydev.usdafmexchange;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

}
