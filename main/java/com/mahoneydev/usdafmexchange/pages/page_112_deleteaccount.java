package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mahoneydev.usdafmexchange.AppCodeResources;
import com.mahoneydev.usdafmexchange.FetchTask;
import com.mahoneydev.usdafmexchange.Frontpage;
import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;
import com.mahoneydev.usdafmexchange.UserFileUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by xianan on 8/1/16.
 */
public class page_112_deleteaccount extends PageOperations {
    public static void showdeletepage(final String name) {
        TableLayout tl = (TableLayout) hashelements.get("deleteScrollTable");
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(context);
        title.setText("Are You Sure?");
        title.setTextAppearance(context, R.style.Bold);
        title.setTextSize(width / 35);
        title.setPadding(0,0,0,10);
        ll.addView(title);

        TextView warn = new TextView(context);
        warn.setText("WARING");
        warn.setTextAppearance(context, R.style.Normal);
        warn.setTextSize(width / 40);
        warn.setTextColor(Color.RED);
        warn.setPadding(0,10,0,10);
        ll.addView(warn);

        TextView content = new TextView(context);
        String con = "If you delete your account, your posts and other correspondence will also be permanently deleted. " +
                "The information you have contributed to this website (e.g., price posts) will not be deleted, " +
                "however, it won’t be linked to you (even if you recreate your account in the future).";
        content.setText(con);
        content.setTextAppearance(context, R.style.Normal);
        content.setTextSize(width / 45);
        content.setPadding(0,10,0,10);
        ll.addView(content);

        TextView un = new TextView(context);
        un.setText("Username:  " + name);
        un.setTextAppearance(context, R.style.Normal);
        un.setTextSize(width / 40);
        un.setPadding(0,10,0,10);
        ll.addView(un);

        TextView bl = new TextView(context);
        ll.addView(bl);

        LinearLayout ll_in = new LinearLayout(context);
        ll_in.setGravity(Gravity.CENTER);
        Button del = new Button(context);
        del.setBackgroundResource(R.drawable.button_delete);
        del.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.85), (int)(height/15)));
        del.setPadding(0, 0, 0, 1);
        del.setText(R.string.l_112_DeleteAccount_DeleteButton_Label_0);
        del.setTransformationMethod(null);
        del.setTextAppearance(context, R.style.White);
        del.setTextSize(width / 40);
        ll_in.addView(del);
        ll.addView(ll_in);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteaccount(name);
            }
        });

        tl.addView(ll);

        setupUI(playout);
    }

    private static void deleteaccount(String name) {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("username", name);
        new FetchTask() {
            @Override
            protected void executeSuccess(JSONObject result) throws JSONException {
                PageOperations.squashNewPage(R.array.page_001_front,null);
                UserFileUtility.clean_userinfo();
                UserFileUtility.save_userinfo();
                NavigationView navigationView = (NavigationView) context.findViewById(R.id.nav_view);
                navigationView.getMenu().setGroupVisible(R.id.nologin, true);
                navigationView.getMenu().setGroupVisible(R.id.login_vendor, false);
                navigationView.getMenu().setGroupVisible(R.id.login_customer, false);

                ImageView logo=(ImageView) context.findViewById(R.id.uavatar);
                if (logo != null) {
                    logo.setImageResource(R.drawable.blank_profile);
                    logo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PageOperations.squashNewPage(R.array.page_102_login, null);
                            DrawerLayout drawer = (DrawerLayout) context.findViewById(R.id.drawer_layout);
                            if (drawer!=null)
                                drawer.closeDrawer(GravityCompat.START);
                        }
                    });
                }
                TextView text = (TextView)context.findViewById(R.id.username_menu_display);
                String hi = "Welcome,\nPlease Sign In.";
                final SpannableString ss = new SpannableString(hi);
                ss.setSpan(new RelativeSizeSpan(0.9f),0,9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new StyleSpan(Typeface.NORMAL),0,9,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ss.setSpan(new RelativeSizeSpan(1f),9,24,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),9,24,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                text.setText(ss);
            }
        }.execute(AppCodeResources.postUrl("usdalogin", "deleteuser", ht));

    }
}

