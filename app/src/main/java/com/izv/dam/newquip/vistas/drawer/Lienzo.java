package com.izv.dam.newquip.vistas.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dam on 11/11/2016.
 */

public class Lienzo extends View {

    private Path drawPath;
    private static Paint drawPaint;
    private Paint canvasPaint;
    private int paintColor = Color.parseColor("#000000");
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private String colorString;

    public Lienzo(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        /*Log.v("Lienzo", "onSizeChanged, Width: " + w);
        Log.v("Lienzo", "onSizeChanged, Height: " + h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                canvasBitmap.setPixel(i, j, Color.rgb(255, 255, 255));
            }
        }*/
        drawCanvas = new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX,touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;

    }

    public void setColor(String newColor){
        invalidate();
        colorString=newColor;
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public static void setTamanyoPunto(float nuevoTamanyo){
        drawPaint.setStrokeWidth(nuevoTamanyo);
    }

    public void NuevoDibujo(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public  float getTamanyoPunto() {
        return drawPaint.getStrokeWidth();
    }
    public String getColor(){
        return colorString;
    }
}
