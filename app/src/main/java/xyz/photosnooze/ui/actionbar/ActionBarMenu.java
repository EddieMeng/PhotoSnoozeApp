package xyz.photosnooze.ui.actionbar;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.photosnooze.R;
import xyz.photosnooze.ui.components.cell.DividerCell;

/**
 * Created by shine on 16/6/14.
 */
public class ActionBarMenu extends LinearLayout{
    private TextView canCelTextView, saveTextView;
    private ImageView canCelImgaeView, saveImgaeView;
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

    public ImageView addCancelImageView(Context context) {
        canCelImgaeView = new ImageView(context);
        canCelImgaeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        addView(canCelImgaeView, params);
        canCelImgaeView.setImageResource(R.mipmap.img_cancel);
        return canCelImgaeView;
    }


    public ImageView addSaveImageView(Context context) {
        saveImgaeView = new ImageView(context);
        saveImgaeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.leftMargin = 10;
        addView(saveImgaeView, params);
        saveImgaeView.setImageResource(R.mipmap.img_save_alarm);
        return saveImgaeView;
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
