package com.izv.dam.newquip.camara;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dam on 09/11/2016.
 */

public class Camara {
    private AppCompatActivity yo;
    private final int PERMISO_EXTERNO = 123;
    private final int PERMISO_CAMARA = 124;
    private final int ACTIVIDAD_FOTO = 200;
    private final int ACTIVIDAD_GALERIA = 300;

    public Camara(AppCompatActivity yo) {
        this.yo = yo;
    }

    public Camara() {
    }

    public void openCamara(Context c) {
        if(ActivityCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(yo,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AlertDialog.Builder adb = new AlertDialog.Builder(c);
                adb.setMessage("El permiso es para guardar la imagen");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(yo,
                                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISO_EXTERNO);
                    }
                });
            }
            else {
                ActivityCompat.requestPermissions(yo,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISO_EXTERNO);
            }

        }
        else {
            if(ActivityCompat.checkSelfPermission(c, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(yo,android.Manifest.permission.CAMERA)){
                    AlertDialog.Builder adb = new AlertDialog.Builder(c);
                    adb.setMessage("El permiso es para guardar la imagen");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(yo,
                                    new String[]{android.Manifest.permission.CAMERA},PERMISO_CAMARA);
                        }
                    });
                }
                else {
                    ActivityCompat.requestPermissions(yo,
                            new String[]{android.Manifest.permission.CAMERA},PERMISO_CAMARA);
                }
            }
            else{
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                yo.startActivityForResult(intent, ACTIVIDAD_FOTO);
            }
        }
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public Bitmap redimension(Bitmap mBitmap, float newWidth, float newHeigth) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
