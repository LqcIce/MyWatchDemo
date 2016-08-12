package edu.hrbeu.ice.mywatchdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ice on 2016/8/1.
 */
public class MyWatch extends View {

    private Paint paint;

    int speed = 0;
    private Bitmap back;
    private Bitmap center;
    private Bitmap arrow;
    private int left;
    private int top;

    public MyWatch(Context context) {
        this(context, null, 0);
    }

    public MyWatch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyWatch, defStyleAttr, 0);

        speed = a.getInt(R.styleable.MyWatch_speed, 0);

        paint = new Paint();

        back = BitmapFactory.decodeResource(getResources(), R.drawable.back_watch);
        center = BitmapFactory.decodeResource(getResources(), R.drawable.icon_watch_center);
        arrow = BitmapFactory.decodeResource(getResources(), R.drawable.icon_watch_arrow);


        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            float needWidth = back.getWidth();
            int desired = (int) (getPaddingLeft() + needWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float needWidth = back.getHeight();
            int desired = (int) (getPaddingTop() + needWidth + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int backWidth = back.getWidth();
        int backHeight = back.getHeight();
        //绘制表盘
        canvas.drawBitmap(back, 0, 0, paint);


        //绘制指针
        float arc = (speed % 240 - 120) * 180 / 240;
        Matrix matrix = new Matrix();
        matrix.postRotate(arc);


        Bitmap dstbmp = Bitmap.createBitmap(arrow, 0, 0, arrow.getWidth(),
                arrow.getHeight(), matrix, true);

        if (arc >= 0)
            canvas.drawBitmap(dstbmp, backWidth / 2 - arrow.getWidth() / 2 + (int) (arrow.getWidth() * Math.sin(arc * Math.PI / 180)), (int) (backHeight - center.getWidth() / 2 - arrow.getWidth() / 4 - arrow.getHeight() * Math.cos(arc * Math.PI / 180)), null);
        else {
            canvas.drawBitmap(dstbmp, backWidth / 2 - arrow.getWidth() / 2 + (int) (arrow.getHeight() * Math.sin(arc * Math.PI / 180)), (int) (backHeight - center.getWidth() / 2 - arrow.getWidth() / 4 - arrow.getHeight() * Math.cos(arc * Math.PI / 180)), null);

        }


        //绘制圆心
        // 计算左边位置
        left = backWidth / 2 - center.getWidth() / 2;
        // 计算上边位置
        top = backHeight - center.getHeight();
        canvas.drawBitmap(center, left, top, paint);


        //中心数字
        paint.setStrokeWidth(4);
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        //抗锯齿
        paint.setAntiAlias(true);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(speed % 240 + "", backWidth / 2, backHeight * 2 / 3, paint);


    }

    public void setSpeed(int speed) {
        this.speed = speed;
        invalidate();
    }
}
