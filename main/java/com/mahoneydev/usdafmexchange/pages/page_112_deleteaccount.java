package com.mahoneydev.usdafmexchange.pages;

import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mahoneydev.usdafmexchange.PageOperations;
import com.mahoneydev.usdafmexchange.R;

/**
 * Created by xianan on 8/1/16.
 */
public class page_112_deleteaccount extends PageOperations {
    public static void showdeletepage() {
        String name=getRecentPage().params.get("username");
        LinearLayout ll = new LinearLayout(context);

        TextView title = new TextView(context);
        title.setText("Are You Sure?");
        title.setTextAppearance(context, R.style.Bold);
        title.setTextSize(width/40);
        ll.addView(title);

        TextView warn = new TextView(context);
        warn.setText("WARING");
        warn.setTextAppearance(context, R.style.Normal);
        warn.setTextSize(width/40);
        warn.setTextColor(Color.RED);
        ll.addView(warn);

        TextView content = new TextView(context);
        String con = "If you delete your account, your posts and other correspondence will also be permanently deleted. " +
                "The information you have contributed to this website (e.g., price posts) will not be deleted, " +
                "however, it won’t be linked to you (even if you recreate your account in the future).";
        content.setText(con);
        content.setTextAppearance(context, R.style.Normal);
        content.setTextSize(width/50);
        ll.addView(content);

        TextView un = new TextView(context);
        un.setText("Username:  " + name);
        un.setTextAppearance(context, R.style.Normal);
        un.setTextSize(width/50);
        ll.addView(un);

        Button del = new Button(context);
        del.setText("I Want To Delete This Account");

        setupUI(playout);
    }
}
