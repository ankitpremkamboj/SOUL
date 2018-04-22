package com.soul.app.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ashishkumar on 5/8/16.
 */
public class RoundedImageViewForEditProfile extends ImageView {

    public static float radius = 8.0f;
     /*public static float radius = 25.0f;*/

    public RoundedImageViewForEditProfile(Context context) {
        super(context);
    }

    public RoundedImageViewForEditProfile(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageViewForEditProfile(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
