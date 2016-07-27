package com.mahoneydev.usdafmexchange.pages;

import android.widget.TextView;

import com.mahoneydev.usdafmexchange.PageOperations;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_407_profile extends PageOperations {
    public static void showprofile(String name){
        ((TextView) hashelements.get("nameView")).setText("Name: " + name);
        setupUI(playout);
    }
}
