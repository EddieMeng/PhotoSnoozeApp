package xyz.photosnooze.ui.components;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xyz.photosnooze.R;


public class SlideToStopBar extends RelativeLayout
{
    private OnUnlockListener listener;

    private TextView text_label = null;
    private ImageView img_thumb = null;
    private ImageView unlock_img;

    private int thumbWidth = 0;
    boolean sliding = false;
    private int sliderPosition = 0;
    int initialSliderPosition = 0;
    float initialSlidingX = 0;

    public SlideToStopBar(Context context)
    {
        super(context);
        init(context, null);
    }

    public SlideToStopBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public SlideToStopBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setOnUnlockListener(OnUnlockListener listener)
    {
        this.listener = listener;
    }

    public void reset()
    {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img_thumb.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.rightMargin, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                params.rightMargin = (Integer) valueAnimator.getAnimatedValue();
                img_thumb.requestLayout();
            }
        });
        animator.setDuration(300);
        animator.start();
        text_label.setAlpha(1f);
    }

    private void init(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slidetostop_bar, this, true);

        // Retrieve layout elements
        text_label = (TextView) findViewById(R.id.text_label);
        img_thumb = (ImageView) findViewById(R.id.img_thumb);
        unlock_img = (ImageView) findViewById(R.id.stopClock);

        unlock_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onStopClick();
                }
            }
        });

        // Get padding
        thumbWidth = dpToPx(80); // 60dp + 2*10dp
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            sliderPosition = getMeasuredWidth();
            if (event.getX() < sliderPosition && event.getX() > (sliderPosition - thumbWidth))
            {
                sliding = true;
                initialSlidingX = event.getX();
                initialSliderPosition = sliderPosition;
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE)
        {
            if (sliderPosition > (getMeasuredWidth() - thumbWidth - unlock_img.getMeasuredWidth() - dpToPx(10)))
            {
                if (listener != null) listener.onUnlock();
            }
            else
            {
                sliding = false;
                sliderPosition = 0;
                reset();
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE && sliding)
        {
            sliderPosition = (int) (0 + (initialSlidingX - event.getX()));
            if (sliderPosition <= 0) sliderPosition = 0;

            if (sliderPosition >= (getMeasuredWidth() - thumbWidth))
            {
                sliderPosition = (int) (getMeasuredWidth()  - thumbWidth);
            }
            else
            {
                int max = (int) (getMeasuredWidth() - thumbWidth);
                int progress = (int) (sliderPosition * 100 / (max * 1.0f));
                text_label.setAlpha(1f - progress * 0.02f);
            }
            setMarginRight(sliderPosition);
        }

        return true;
    }

    private void setMarginRight(int margin)
    {
        if (img_thumb == null) return;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img_thumb.getLayoutParams();
        params.setMargins(0, 0, margin, 0);
        img_thumb.setLayoutParams(params);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public interface OnUnlockListener {
        void onUnlock();

        void onStopClick();
    }
}
