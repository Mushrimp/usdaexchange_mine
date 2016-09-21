package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.Frontpage;
import com.mahoneydev.usdafmexchange.LongPressDeleteDialogListener;
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
public class page_305_productsell extends PageOperations {
    public static void showproducts() {
        String token_s = UserFileUtility.get_token();
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("os", "Android");
        ht.put("token", token_s);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                TableLayout tl = (TableLayout) hashelements.get("productsellScrollTable");
                tl.removeAllViews();
                JSONArray allproducts = result.getJSONArray("results");

//                //Add
//                LinearLayout ll_in = new LinearLayout(context);
//                ll_in.setGravity(Gravity.END);
//                ImageButton bt1 = new ImageButton(context);
//                bt1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                bt1.setImageResource(R.drawable.add_white);
//                bt1.setBackgroundResource(R.drawable.button_style);
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pushNewPage(R.array.page_306_addproductform, new Hashtable<String, String>());
//                    }
//                });
//                bt1.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.13), (int)(height*0.08)));
//                ll_in.addView(bt1);
//                tl.addView(ll_in);

                String count = result.getString("count");
                if (!count.equals("0")){
                    for (int i = 0; i < allproducts.length(); i++) {
                        JSONObject product = allproducts.getJSONObject(i);
                        TableRow lv = new TableRow(context);
                        lv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height / 5));

                        //Product content
                        LinearLayout ll = new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        //Category
                        TextView category1 = new TextView(context);
                        category1.setTextAppearance(context, R.style.Title);
                        category1.setTextSize(width / 50);
                        String categoryt = res.getString(R.string.l_305_ProductList_Category_Label_0);
                        category1.setText(categoryt);
                        ll.addView(category1);
                        TextView category = new TextView(context);
                        String categoryline = product.getString("Prd_Category1");
                        category.setTextAppearance(context, R.style.Bold);
                        category.setTextSize(width / 45);
                        category.setText(categoryline);
                        ll.addView(category);
                        //Product Name
                        TextView pn1 = new TextView(context);
                        pn1.setTextAppearance(context, R.style.Title);
                        pn1.setTextSize(width / 50);
                        String productt = res.getString(R.string.l_305_ProductList_Product_Label_0);
                        pn1.setText(productt);
                        ll.addView(pn1);
                        TextView pn = new TextView(context);
                        String productline = product.getString("product_name");
                        pn.setTextAppearance(context, R.style.Normal);
                        pn.setTextSize(width / 45);
                        pn.setText(productline);
                        ll.addView(pn);

                        //Unit
                        LinearLayout ll_in1 = new LinearLayout(context);
                        ll_in1.setOrientation(LinearLayout.HORIZONTAL);
                        TextView unit1 = new TextView(context);
                        unit1.setTextAppearance(context, R.style.Title);
                        unit1.setTextSize(width / 50);
                        String unitt = res.getString(R.string.l_305_ProductList_Unit_Label_0)+"          ";
                        unit1.setText(unitt);
                        ll_in1.addView(unit1);
                        TextView unit = new TextView(context);
                        String unitline = product.getString("productunit_name");
                        unit.setTextAppearance(context, R.style.Body);
                        unit.setTextSize(width / 45);
                        unit.setText(unitline);
                        ll_in1.addView(unit);
                        ll.addView(ll_in1);

                        //Organic
                        LinearLayout ll_in2 = new LinearLayout(context);
                        ll_in2.setOrientation(LinearLayout.HORIZONTAL);
                        TextView organic1 = new TextView(context);
                        organic1.setTextAppearance(context, R.style.Title);
                        organic1.setTextSize(width / 50);
                        String organict = res.getString(R.string.l_305_ProductList_Organic_Label_0)+"    ";
                        organic1.setText(organict);
                        ll_in2.addView(organic1);
                        ImageView organic = new ImageView(context);
                        String organicline = product.getString("organic_usda");
                        if (organicline.equals("yes")) {
                            organic.setImageResource(R.drawable.usda_organic);
                            int width = 50;
                            int height = 50;
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                            parms.gravity = Gravity.START;
                            organic.setLayoutParams(parms);
                        }
                        ll_in2.addView(organic);
                        ll.addView(ll_in2);

                        //
                        TextView br = new TextView(context);
                        br.setText("");
                        ll.addView(br);

                        ll.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                        lv.addView(ll);
                        lv.setBackgroundResource(R.drawable.tablerow_style);
                        tl.addView(lv);
                        lv.setOnLongClickListener(new removeproductListener(context,"Delete a product","Do you want to remove "+product.getString("product_name")+" from the list?",lv,tl,product.getString("id")));

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
                }else if (count.equals("0")){
                    Toast.makeText(context,"No products found...",Toast.LENGTH_SHORT).show();

                }
                setupUI(playout);
            }
        }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_product_listall_byuser", ht));
    }

    public static class removeproductListener extends LongPressDeleteDialogListener {
        private TableRow deletetablerow;
        private TableLayout fromtablelayout;
        private String productid;
        public removeproductListener(Frontpage contexti, String titlei, String messagei, TableRow tr, TableLayout tl, String productidi)
        {
            super(contexti, titlei, messagei);
            deletetablerow=tr;
            fromtablelayout=tl;
            productid=productidi;
        }
        @Override
        public void deleteaction(){
            String token_s = UserFileUtility.get_token();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("os", "Android");
            ht.put("token", token_s);
            ht.put("indexid", productid);
            new FetchTask() {
                @Override
                protected void executeSuccess(JSONObject result) throws JSONException {
                    fromtablelayout.removeView(deletetablerow);
                }
            }.execute(AppCodeResources.postUrl("usdatestyue", "vendorprofile_product_remove_selecteditem", ht));

        }
    }
}
