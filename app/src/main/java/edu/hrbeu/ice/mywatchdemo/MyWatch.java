package edu.hrbeu.ice.mywatchdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ice on 2016/8/1.
 */
public class MyWatch extends View {

    private Paint paint;
    private Paint textPaint;

    private int mWidth;
    private int mHeight;

    int speed = 0;
    float radius;
    //    private Bitmap back;
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
        textPaint = new Paint();

        radius = a.getDimension(R.styleable.MyWatch_radius, 200);

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

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            float needWidth = radius * 2+8;
            int desired = (int) (getPaddingLeft() + needWidth + getPaddingRight());
            mWidth = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            float needWidth = radius+24;
            int desired = (int) (getPaddingTop() + needWidth + getPaddingBottom());
            mHeight = desired;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStrokeWidth(6);
        paint.setColor(getResources().getColor(R.color.watchBlue));
        paint.setStyle(Paint.Style.STROKE);
        //抗锯齿
        paint.setAntiAlias(true);

        //绘制表盘
        RectF oval1 = new RectF(4, 4, radius * 2, radius * 2);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval1, -180, 180, false, paint);

        //绘制指针
        paint.setColor(Color.WHITE);
        float arc = (float) (speed % 140) * 180 / 140;

        canvas.save(); //记录画布状态
        canvas.rotate(-90+arc, mWidth / 2, radius);
        canvas.drawLine(mWidth / 2, 20, mWidth / 2, radius, paint);//画指针
        canvas.restore();

        //绘制圆心
        // 计算左边位置
        left = mWidth / 2 - center.getWidth() / 2;
        // 计算上边位置
        top = mHeight - center.getHeight();
        canvas.drawBitmap(center, left, top, paint);

        //按照百分比绘制刻度
        //绘制小刻度
        canvas.save(); //记录画布状态
        canvas.rotate(-90, mWidth / 2, radius);
        float rAngle1 = (float) 180.0 / 70;
        canvas.drawLine(mWidth / 2, 3, mWidth / 2, 40, paint);//画刻度线

        for (int i = 0; i < 70; i++) {
            canvas.rotate(rAngle1, mWidth / 2, radius);
            canvas.drawLine(mWidth / 2, 10, mWidth / 2, 30, paint);//画刻度线
            //            canvas.drawText("" + i * 10, getWidth() / 2 - mArcWidth * 2, 40, paintouter_Num);//画刻度

        }
        canvas.restore();

        //绘制大刻度和度数
        paint.setColor(getResources().getColor(R.color.watchBlue));

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //抗锯齿
        textPaint.setAntiAlias(true);

        canvas.save(); //记录画布状态
        canvas.rotate(-90, mWidth / 2, radius);
        float rAngle = (float) 180 / 14;
        canvas.drawLine(mWidth / 2, 3, mWidth / 2, 40, paint);//画刻度线
        canvas.drawText("" + 0, mWidth / 2, 75, textPaint);//画刻度
        for (int i = 0; i < 14; i++) {
            canvas.rotate(rAngle, mWidth / 2, radius);
            canvas.drawLine(mWidth / 2, 4, mWidth / 2, 40, paint);//画刻度线
            canvas.drawText("" + (i+1)* 10, mWidth / 2, 75, textPaint);//画刻度

        }
        canvas.restore();

        //中心数字
        textPaint.setStrokeWidth(4);
        textPaint.setTextSize(90);

        canvas.drawText(speed % 140 + "", mWidth / 2, mHeight * 2 / 3, textPaint);

    }

    public void setSpeed(int speed) {
        this.speed = speed;
        invalidate();
    }
}
