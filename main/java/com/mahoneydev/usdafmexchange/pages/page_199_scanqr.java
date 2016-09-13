package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_199_scanqr extends PageOperations{
    public static void showQRcode() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        final String username = UserFileUtility.get_username();
        ht.put("vendorname", username);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("scanqrScrollTable");
                tl.removeAllViews();
                JSONObject vendorprofile = result.getJSONObject("vendorprofile");
                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setGravity(Gravity.CENTER_HORIZONTAL);
                if (vendorprofile == null){
                    TextView non = new TextView(context);
                    non.setText("This vendor didn't set up profile.");
                    ll.addView(non);
                }else {
                    //User Name
                    TextView un = new TextView(context);
                    un.setTextAppearance(context, R.style.Green);
                    un.setTypeface(null,Typeface.BOLD);
                    un.setTextSize(width / 30);
                    un.setPadding(0,0,0,10);
                    un.setGravity(Gravity.CENTER);
                    un.setText(username);
                    ll.addView(un);

                    //Business Name
                    TextView pn = new TextView(context);
                    pn.setTextAppearance(context, R.style.Green);
                    pn.setTextSize(width / 40);
                    pn.setGravity(Gravity.CENTER);
                    pn.setPadding(0,0,0,10);
                    pn.setText(vendorprofile.getString("business_name"));
                    ll.addView(pn);

                    //Phone
                    TextView phone = new TextView(context);
                    phone.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    phone.setTextAppearance(context, R.style.Body);
                    phone.setTextSize(width / 40);
                    phone.setPadding(0,0,0,10);
                    phone.setGravity(Gravity.CENTER);
                    if (vendorprofile.has("business_phone")){
                        phone.setText(vendorprofile.getString("business_phone"));
                    }
                    ll.addView(phone);

                    //email
                    TextView email = new TextView(context);
                    email.setLayoutParams(new TableRow.LayoutParams((int) (width * 6 / 10), TableRow.LayoutParams.WRAP_CONTENT));
                    email.setTextAppearance(context, R.style.Body);
                    email.setTextSize(width / 40);
                    email.setPadding(0,0,0,10);
                    email.setGravity(Gravity.CENTER);
                    if (vendorprofile.has("business_email")){
                        email.setText(vendorprofile.getString("business_email"));
                    }
                    ll.addView(email);

                    //Address
                    TextView addressname=new TextView(context);
                    addressname.setTextAppearance(context,R.style.Body);
                    addressname.setTextSize(width / 40);
                    addressname.setGravity(Gravity.CENTER);
                    addressname.setText(vendorprofile.getString("business_street")+",\n "+vendorprofile.getString("business_city")
                            +", "+vendorprofile.getString("business_state")+", "+vendorprofile.getString("business_zip"));
                    ll.addView(addressname);

                    //QR code
                    String url = AppCodeResources.web_url + "407-friendship-request-action/?fname="+username;
                    getQR(url);

                    tl.addView(ll);

                }

                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_display_vendorprofile_bypost", ht));
    }

    public static void getQR(String url) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ImageView iv=new ImageView(context);
            ((LinearLayout)playout).addView(iv);
            iv.setImageBitmap(bmp);
            setupUI(playout);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static void startScanQR(){
        IntentIntegrator qrscanner = new IntentIntegrator(context);
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
}
