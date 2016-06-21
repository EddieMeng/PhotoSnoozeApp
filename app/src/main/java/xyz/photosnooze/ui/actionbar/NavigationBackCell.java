package xyz.photosnooze.ui.actionbar;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.photosnooze.R;


/**
 * Created by shine on 16/6/6.
 */
public class NavigationBackCell extends LinearLayout{
    private ImageView iconImage;
    private TextView navigationTextView;
    private NavigationBackView navigationBackView;

    public NavigationBackCell(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setClickable(true);
        setBackgroundResource(R.drawable.back_selector);
    }


    public NavigationBackCell addNavigationImage(Context context) {
        navigationBackView = new NavigationBackView(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(66, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(navigationBackView, params);
        return this;
    }

    public NavigationBackCell addImageIcon(Context context, int resId) {
        iconImage = new ImageView(context);
        iconImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iconImage.setImageResource(resId);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(iconImage, params);
        return this;
    }

    public NavigationBackCell addNavigatinText(Context context, CharSequence text) {
        navigationTextView = new TextView(context);
        navigationTextView.setText(text);
        navigationTextView.setGravity(Gravity.LEFT);
        navigationTextView.setTextSize(20);
        navigationTextView.setTextColor(getResources().getColor(android.R.color.white));
        navigationTextView.setLines(1);
        navigationTextView.setMaxLines(1);
        navigationTextView.setSingleLine(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(navigationTextView, params);
        return this;
    }



}
