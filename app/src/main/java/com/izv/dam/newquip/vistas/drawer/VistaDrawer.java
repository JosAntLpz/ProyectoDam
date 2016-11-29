package com.izv.dam.newquip.vistas.drawer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.notas.VistaNota;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by dam on 11/11/2016.
 */

public class VistaDrawer extends AppCompatActivity implements View.OnClickListener{
    ImageButton negro;
    ImageButton verde;
    ImageButton azul;
    ImageButton rojo;
    ImageButton amarillo;
    private Lienzo lienzo;
    float ppequenyo;
    float pmediano;
    float pgrande;
    float pdefecto;
    int GUARDAR_DIBUJO = 200;
    Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        negro = (ImageButton)findViewById(R.id.colornegro);
        verde = (ImageButton)findViewById(R.id.colorverde);
        azul = (ImageButton)findViewById(R.id.colorazul);
        rojo = (ImageButton)findViewById(R.id.colorrojo);
        amarillo = (ImageButton)findViewById(R.id.coloramarillo);

        negro.setOnClickListener(this);
        verde.setOnClickListener(this);
        azul.setOnClickListener(this);
        rojo.setOnClickListener(this);
        amarillo.setOnClickListener(this);

        lienzo = (Lienzo)findViewById(R.id.lienzo);


        ppequenyo = 10;
        pmediano = 20;
        pgrande = 30;

        pdefecto = pmediano;

        if (savedInstanceState != null) {
            nota = savedInstanceState.getParcelable("nota");
            String color = savedInstanceState.getString("color");
            float grosor = savedInstanceState.getFloat("grosor");
            lienzo.setColor(color);
            lienzo.setTamanyoPunto(grosor);
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                nota = b.getParcelable("nota");
            }
            else {
                nota= new Nota();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nuevo:
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Nuevo Dibujo");
                newDialog.setMessage("¿Comenzar nuevo dibujo(perderás el dibujo actual)?");
                newDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                        lienzo.NuevoDibujo();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialog.show();
                return true;


            case R.id.grande:
                lienzo.setTamanyoPunto(pgrande);
                Toast.makeText(getApplicationContext(),"ha seleccionado el tamaño: grande",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.mediano:
                lienzo.setTamanyoPunto(pmediano);
                Toast.makeText(getApplicationContext(),"ha seleccionado el tamaño: mediano",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.pequeno:
                lienzo.setTamanyoPunto(ppequenyo);
                Toast.makeText(getApplicationContext(),"ha seleccionado el tamaño: pequeño",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.goma:
                lienzo.setColor("#ffffff");//BLANCO
                return true;

            case R.id.guardar:
                PermisosDibujo();

                AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
                salvarDibujo.setTitle("Salvar dibujo");
                salvarDibujo.setMessage("¿Salvar Dibujo a la galeria?");
                salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        lienzo.setDrawingCacheEnabled(true);
                        Bitmap data1 = lienzo.getDrawingCache();
                        String path2 = Environment.getExternalStorageDirectory().toString();
                        OutputStream fOut = null;
                        File file = new File(path2, "Quit"+Math.random()+".jpg");
                        if(!file.exists()){
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            fOut = new FileOutputStream(file);
                            data1.compress(Bitmap.CompressFormat.JPEG, 0, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                            try {
                                fOut.flush(); // Not really required
                                fOut.close(); // do not forget to close the stream
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                            Toast.makeText(getApplicationContext(),"¡Dibujo salvado en la galeria!", Toast.LENGTH_SHORT).show();
                            nota.setRutaImagen(file.getAbsolutePath());
                            Intent i = new Intent(VistaDrawer.this, VistaNota.class);
                            Bundle b = new Bundle();
                            b.putParcelable("nota", nota);
                            i.putExtras(b);
                            startActivity(i);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                salvarDibujo.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        String color;

        switch (v.getId()){
            case R.id.colornegro:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorverde:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorazul:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.colorrojo:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            case R.id.coloramarillo:
                color = v.getTag().toString();
                lienzo.setColor(color);
                break;
            default:

                break;
        }
    }

    public void PermisosDibujo(){

        //guardar = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /**
         * ¿Tengo el permiso para hacer la accion?
         */                                                                             ///PERMISO CONCENDIDO
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // startActivity(guardar);
            Toast.makeText(this, "1 Permiso Concedido", Toast.LENGTH_SHORT).show();
        } else {
            solicitarPermisoHacerLlamada();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("nota", nota);
        outState.putFloat("grosor",lienzo.getTamanyoPunto());
        outState.putString("color",lienzo.getColor());
    }

    private void solicitarPermisoHacerLlamada() {
        //Pedimos el permiso o los permisos con un cuadro de dialogo del sistema
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GUARDAR_DIBUJO);


        Toast.makeText(this, "2.2 Pedimos el permiso con un cuadro de dialogo del sistema", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * Si tubieramos diferentes permisos solicitando permisos de la aplicacion, aqui habria varios IF
         */
        if (requestCode == GUARDAR_DIBUJO) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Realizamos la accion
                //  startActivity(guardar);
                Toast.makeText(this, "3.1 Permiso Concedido", Toast.LENGTH_SHORT).show();
            } else {
                //1-Seguimos el proceso de ejecucion sin esta accion: Esto lo recomienda Google
                //2-Cancelamos el proceso actual
                //3-Salimos de la aplicacion
                Toast.makeText(this, "3.2 Permiso No Concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
