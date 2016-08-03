package com.mahoneydev.usdafmexchange;

import android.view.View;
import android.widget.Button;

/**
 * Created by bichongg on 8/2/2016.
 */
public abstract class ClickOnceListener implements View.OnClickListener {
    private View clickbutton;
    public ClickOnceListener(View button)
    {
        clickbutton=button;
    }
    public void onClick(View v)
    {
        clickbutton.setClickable(false);
        action();
    }
    public void enableButton()
    {
        clickbutton.setClickable(true);
    }
    public abstract void action();
}
