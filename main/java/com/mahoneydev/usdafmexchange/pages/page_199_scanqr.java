package com.mahoneydev.usdafmexchange.pages;

import com.google.zxing.integration.android.IntentIntegrator;
import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.PageOperations;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_199_scanqr extends PageOperations{
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
