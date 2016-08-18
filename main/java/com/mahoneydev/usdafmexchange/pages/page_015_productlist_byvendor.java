package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by cuiyue on 8/16/2016.
 */
public class page_015_productlist_byvendor extends PageOperations{
    public static void showproducts() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("vendorname", getRecentPage().params.get("username"));
        ht.put("perpage","10");
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("productpageScrollTable");
                tl.removeAllViews();
                JSONArray products = result.getJSONArray("results");
                for (int i=0;i<products.length();i++)
                {
                    JSONObject productitem=products.getJSONObject(i);
                    TableRow lv=new TableRow(context);
                    LinearLayout tr=new LinearLayout(context);
                    LinearLayout ll1=new LinearLayout(context);
                    ll1.setOrientation(LinearLayout.HORIZONTAL);
                    TextView categorytitle=new TextView(context);
                    categorytitle.setTextAppearance(context, R.style.Bold);
                    categorytitle.setTextSize(width / 45);
                    TextView categoryname=new TextView(context);
                    categoryname.setTextAppearance(context,R.style.Normal);
                    categoryname.setTextSize(width / 45);
                    categorytitle.setText(res.getString(R.string.l_015_ProductList_Title_1));
                    categoryname.setText(productitem.getString("price_Prd_Category1"));
                    ll1.addView(categorytitle);
                    ll1.addView(categoryname);
                    if (productitem.getString("price_usdaorganic").equals("yes"))
                    {
                        ImageView org = new ImageView(context);
                        org.setLayoutParams(new LinearLayout.LayoutParams(50,50));
                        org.setImageResource(R.drawable.usda_organic);
                        ll1.addView(org);
                    }

                    LinearLayout ll2=new LinearLayout(context);
                    ll2.setOrientation(LinearLayout.HORIZONTAL);
                    TextView producttitle=new TextView(context);
                    producttitle.setTextAppearance(context, R.style.Bold);
                    producttitle.setTextSize(width / 45);
                    TextView productname=new TextView(context);
                    productname.setTextAppearance(context,R.style.Normal);
                    productname.setTextSize(width / 45);
                    producttitle.setText(res.getString(R.string.l_015_ProductList_Title_2));
                    productname.setText(productitem.getString("price_product_name"));
                    ll2.addView(producttitle);
                    ll2.addView(productname);

                    tr.setOrientation(LinearLayout.VERTICAL);
                    tr.addView(ll1);
                    tr.addView(ll2);
                    lv.addView(tr);
                    tl.addView(lv);
                    TableRow lk=new TableRow(context);
                    View ldivider = new LinearLayout(context);
                    ldivider.setBackgroundColor(Color.parseColor("#A2D25A"));
                    ldivider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2,1));
                    lk.addView(ldivider);
                    tl.addView(lk);
                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestchongguang", "public_list_products_byvendor", ht));
    }
}
