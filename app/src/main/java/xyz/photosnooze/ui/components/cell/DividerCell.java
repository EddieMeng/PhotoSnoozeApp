package xyz.photosnooze.ui.components.cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by shine on 16/6/4.
 */
public class DividerCell extends View {
    private static Paint paint;
    private boolean isHorizontal;

    public DividerCell(Context context, boolean isHorizontal) {
       super(context);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(0xffd9d9d9);
            paint.setStrokeWidth(1);
        }
        this.isHorizontal = isHorizontal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isHorizontal) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(5, MeasureSpec.EXACTLY));
        }else {
            super.onMeasure(MeasureSpec.makeMeasureSpec(5, MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isHorizontal) {
            canvas.drawLine(getPaddingLeft(), 0, getWidth() - getPaddingRight(), 0, paint);
        } else {
            canvas.drawLine(0, getPaddingTop(), 0, getHeight() - getPaddingBottom(), paint);
        }
    }
}
