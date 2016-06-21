package xyz.photosnooze.ui.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by shine on 16/6/6.
 */
public class PhotoSnoozeActionBar extends FrameLayout{


    private NavigationBackCell navigationBackCell;
    private TextView titleTextView;
    private ActionBarMenu menu;

    private LayoutParams layoutParams;



    public PhotoSnoozeActionBar(Context context) {
        this(context, null);
    }

    public PhotoSnoozeActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoSnoozeActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int actionbarHeight = 70;

        setMeasuredDimension(width, actionbarHeight);

        if (navigationBackCell != null && navigationBackCell.getVisibility() != GONE) {

            navigationBackCell.measure(MeasureSpec.makeMeasureSpec(125, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(actionbarHeight, MeasureSpec.EXACTLY));
        }

        if (titleTextView != null) {
            int availableWidth = width - (navigationBackCell != null ? navigationBackCell.getMeasuredWidth() : 0) - getPaddingLeft();
            titleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        }

        if (menu != null) {
            int availableWidth = width - (navigationBackCell != null ? navigationBackCell.getMeasuredWidth() : 0) - (titleTextView != null ? titleTextView.getMeasuredWidth() : 0)- getPaddingLeft();
            menu.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(actionbarHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (navigationBackCell != null) {
            navigationBackCell.layout(getPaddingLeft(), 0, getPaddingLeft() + navigationBackCell.getMeasuredWidth(), navigationBackCell.getMeasuredHeight());
        }

        if (titleTextView != null) {
            int leftPosition = getMeasuredWidth() / 2 - titleTextView.getMeasuredWidth() / 2;
            int topPosition = getMeasuredHeight() / 2 - titleTextView.getMeasuredHeight() / 2;
            int rightPosition = leftPosition + titleTextView.getMeasuredWidth();
            int bottomPosition = topPosition + titleTextView.getMeasuredHeight();

            titleTextView.layout(leftPosition, topPosition, rightPosition, bottomPosition);
        }

        if (menu != null) {
            int topPosition = 0;
            int rightPosition = getMeasuredWidth();
            int leftPosition = rightPosition - menu.getMeasuredWidth();
            int bottomPosition = topPosition + menu.getMeasuredHeight();
            menu.layout(leftPosition, topPosition, rightPosition, bottomPosition);
        }
    }

    public void setActionBarTitle(CharSequence title) {
        if (title != null && titleTextView == null) {
            createActionBarTitle(title);
        }
    }



    private void createActionBarTitle(CharSequence title) {
        if (titleTextView != null) {
            return;
        }

        titleTextView = new TextView(getContext());
        titleTextView.setText(title);
        titleTextView.setTextSize(18);
        titleTextView.setGravity(Gravity.LEFT);
        titleTextView.setLines(1);
        titleTextView.setTextColor(getResources().getColor(android.R.color.white));
        titleTextView.setMaxLines(1);
        titleTextView.setSingleLine(true);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(titleTextView, 0, layoutParams);
    }

    public NavigationBackCell createNavigationCell() {
        if (navigationBackCell != null) {
            return navigationBackCell;
        }
        navigationBackCell = new NavigationBackCell(getContext());
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.LEFT);
        addView(navigationBackCell, layoutParams);
        return navigationBackCell;
    }


    public ActionBarMenu createMenu(Context context) {
        if (menu != null) {
            return menu;
        }

        menu = new ActionBarMenu(context);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
        addView(menu, layoutParams);
        return menu;
    }



}
