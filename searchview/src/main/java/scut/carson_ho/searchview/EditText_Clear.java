package scut.carson_ho.searchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by Carson_Ho on 17/8/10.
 */

public class EditText_Clear extends AppCompatEditText {


    /**
     * 步骤1：定义左侧搜索图标 & 一键删除图标
     */
    private Drawable clearDrawable;

    public EditText_Clear(Context context) {
        super(context);
        init();
        // 初始化该组件时，对EditText_Clear进行初始化 ->>步骤2
    }

    public EditText_Clear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Clear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 步骤2：初始化 图标资源
     */
    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.clear);


    }


    /**
     * 步骤3：通过监听复写EditText本身的方法来确定是否显示删除图标
     * 监听方法：onTextChanged（） & onFocusChanged（）
     * 调用时刻：当输入框内容变化时 & 焦点发生变化时
     */

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
        // hasFocus()返回是否获得EditTEXT的焦点，即是否选中
        // setClearIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标->>关注1
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
        // focused = 是否获得焦点
        // 同样根据setClearIconVisible（）判断是否要显示删除图标
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标
     */
    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(null, null,
                visible ? clearDrawable : null, null);
    }

    /**
     * 步骤4：对删除图标区域设置点击事件，即"点击 = 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
            case MotionEvent.ACTION_UP:
                Drawable drawable = clearDrawable;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                // 判断条件说明
                // event.getX() ：抬起时的位置坐标
                // getWidth()：控件的宽度
                // getPaddingRight():删除图标图标右边缘至EditText控件右边缘的距离
                // 即：getWidth() - getPaddingRight() = 删除图标的右边缘坐标 = X1
                // getWidth() - getPaddingRight() - drawable.getBounds().width() = 删除图标左边缘的坐标 = X2
                // 所以X1与X2之间的区域 = 删除图标的区域
                // 当手指抬起的位置在删除图标的区域（X2=<event.getX() <=X1），即视为点击了删除图标 = 清空搜索框内容
                break;
        }
        return super.onTouchEvent(event);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(TextUtils.isEmpty(getText().toString())){
            drawText(canvas);
        }

    }



    Paint   paint=new Paint();

    {
        //绘制画笔
        paint=new Paint();
        paint.setColor(Color.parseColor("#C6C6C6")); //设置画笔的颜色,即图形的边框(包含字体)
        paint.setStrokeWidth(1); //设置画笔的大小，相当于图形的边框宽度
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);//不抗锯齿
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(45);
    }

    private void drawText(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int halfCanvasWidth = canvasWidth / 2;
        int canvasHeight = canvas.getHeight();
        float translateY = canvasHeight/2;
        float textHeight = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
        float  baseY = translateY+textHeight/2;

        //设置居中对齐
        paint.setTextAlign(Paint.Align.CENTER);//设置居中对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, baseY);
        canvas.drawText("搜索", 0, 0, paint);
        canvas.restore();

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.search );
        canvas.drawBitmap(bmp,halfCanvasWidth-30,30,paint);


    }



}

