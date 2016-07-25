package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bichongg on 7/25/2016.
 */
public class page_122_preferproduct extends PageOperations {
    public static void showPreferProduct() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("preferproductScrollTable");
                tl.removeAllViews();
                JSONArray allpreproduct = result.getJSONArray("results");
                for (int i = 0; i < allpreproduct.length(); i++) {
                    JSONObject preproduct = allpreproduct.getJSONObject(i);
                    TableRow lv = new TableRow(context);
                    lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                    //Preferred Products content
                    LinearLayout ll = new LinearLayout(context);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    //Category
                    TextView category1 = new TextView(context);
                    category1.setTextAppearance(context, R.style.Title);
                    category1.setTextSize(width / 50);
                    String ctitle = "Category:";
                    category1.setText(ctitle);
                    ll.addView(category1);
                    TextView category = new TextView(context);
                    category.setTextAppearance(context, R.style.Bold);
                    category.setTextSize(width / 45);
                    category.setText(preproduct.getString("Prd_Category1"));
                    ll.addView(category);
                    //Product Name
                    TextView pn1 = new TextView(context);
                    pn1.setTextAppearance(context, R.style.Title);
                    pn1.setTextSize(width / 50);
                    String mntitle = "Product:";
                    pn1.setText(mntitle);
                    ll.addView(pn1);
                    TextView pn = new TextView(context);
                    pn.setTextAppearance(context, R.style.Body);
                    pn.setTextSize(width / 45);
                    pn.setText(preproduct.getString("product_name"));
                    ll.addView(pn);

                    //
                    TextView br = new TextView(context);
                    br.setText("");
                    ll.addView(br);

                    ll.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    lv.addView(ll);
                    tl.addView(lv);

                    TableRow lk = new TableRow(context);
                    lk.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    View ldivider = new LinearLayout(context);
                    ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    ldivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.3f));
                    View rdivider = new LinearLayout(context);
                    rdivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    rdivider.setLayoutParams(new TableRow.LayoutParams(0, 2, 0.7f));
                    lk.addView(ldivider);
                    lk.addView(rdivider);
                    tl.addView(lk);
                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "userpreference_product_list_getlist", ht));
    }
}
