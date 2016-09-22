package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by xianan on 9/22/16.
 */
public class page_121_searchdistance extends PageOperations {
    public static void savedistance() {
        Hashtable<String, String> ht = new Hashtable<String, String>();

        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {


                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_searchdistance_save", ht));
    }

}