package com.mahoneydev.usdafmexchange;

import android.os.SystemClock;
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
    private long mLastClickTime = 0;
    public void onClick(View v)
    {
        //mis-clicking prevention, using threshold of 3000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        //clickbutton.setClickable(false);
        action();
    }
    public void enableButton()
    {
        clickbutton.setClickable(true);
    }
    public abstract void action();

}
