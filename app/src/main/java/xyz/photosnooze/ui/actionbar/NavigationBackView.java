package xyz.photosnooze.ui.actionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.ImageView;

/**
 * Created by shine on 16/6/6.
 */
public class NavigationBackView extends ImageView {
    Paint paint;

    public NavigationBackView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(53, 10);
        path.lineTo(20, 35);
        path.lineTo(53, 60);
        canvas.drawPath(path, paint);

    }
}
