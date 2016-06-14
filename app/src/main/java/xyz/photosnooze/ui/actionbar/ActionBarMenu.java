package xyz.photosnooze.ui.actionbar;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.photosnooze.ui.components.cell.DividerCell;

/**
 * Created by shine on 16/6/14.
 */
public class ActionBarMenu extends LinearLayout{
    private TextView canCelTextView, saveTextView;
    private DividerCell dividerLine;


    public ActionBarMenu(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }





    public TextView addCancelMenuItem(Context context) {
        canCelTextView = new TextView(context);
        canCelTextView.setText("CanCel");
        canCelTextView.setGravity(Gravity.LEFT);
        canCelTextView.setTextSize(15);
        canCelTextView.setLines(1);
        canCelTextView.setMaxLines(1);
        canCelTextView.setSingleLine(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(canCelTextView,params);
        return canCelTextView;
    }

    public TextView addSaveMenuItem(Context context) {
        saveTextView = new TextView(context);
        saveTextView.setText("Save");
        saveTextView.setGravity(Gravity.LEFT);
        saveTextView.setTextSize(15);
        saveTextView.setLines(1);
        saveTextView.setMaxLines(1);
        saveTextView.setSingleLine(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
//        params.leftMargin = 5;
        addView(saveTextView,params);
        return saveTextView;
    }

    public void addDividerLine(Context context) {
        dividerLine = new DividerCell(context, false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.leftMargin = 5;
        addView(dividerLine, params);
    }






}
