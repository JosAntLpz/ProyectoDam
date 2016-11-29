package com.izv.dam.newquip.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

public class Ayudante extends SQLiteOpenHelper {

    //sqlite
    //tipos de datos https://www.sqlite.org/datatype3.html
    //fechas https://www.sqlite.org/lang_datefunc.html
    //trigger https://www.sqlite.org/lang_createtrigger.html

    private static final int VERSION = 4;

    public Ayudante(Context context) {
        super(context, ContratoBaseDatos.BASEDATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREAR TABLA NOTA
        StringBuilder queryNota = new StringBuilder();
        queryNota.append("create table if not exists ").append(ContratoBaseDatos.TablaNota.TABLA).append("(");
        queryNota.append(ContratoBaseDatos.TablaNota._ID).append(" integer primary key autoincrement, ");
        queryNota.append(ContratoBaseDatos.TablaNota.TIPO).append(" integer, ");
        queryNota.append(ContratoBaseDatos.TablaNota.TITULO).append(" text, ");
        queryNota.append(ContratoBaseDatos.TablaNota.NOTA).append(" text, ");
        queryNota.append(ContratoBaseDatos.TablaNota.REALIZADO).append(" integer, ");
        queryNota.append(ContratoBaseDatos.TablaNota.IMAGEN).append(" text, ");
        queryNota.append(ContratoBaseDatos.TablaNota.FECHA).append(" text, ");
        queryNota.append(ContratoBaseDatos.TablaNota.RECORDATORIO).append(" text, ");
        queryNota.append(ContratoBaseDatos.TablaNota.AUDIO).append(" text");



        queryNota.append(")");
        Log.v("sqlTablaNota",queryNota.toString());
        db.execSQL(queryNota.toString());

        //CREAR TABLA TAREAS
        StringBuilder queryTareas = new StringBuilder();
        queryTareas.append("create table if not exists ").append(ContratoBaseDatos.TablaTareas.TABLA).append("(");
        queryTareas.append(ContratoBaseDatos.TablaTareas._ID).append(" integer primary key autoincrement, ");
        queryTareas.append(ContratoBaseDatos.TablaTareas.ID_NOTA).append(" integer, ");
        queryTareas.append(ContratoBaseDatos.TablaTareas.REALIZADA).append(" integer, ");
        queryTareas.append(ContratoBaseDatos.TablaTareas.TAREA).append(" text");
        queryTareas.append(")");
        Log.v("sqlTablaTareas",queryTareas.toString());
        db.execSQL(queryTareas.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String delete1="drop table if exists " + ContratoBaseDatos.TablaNota.TABLA;
        String delete2="drop table if exists " + ContratoBaseDatos.TablaTareas.TABLA;
        db.execSQL(delete1);
        db.execSQL(delete2);
        onCreate(db);
        //Se crea la tabla temporal de Nota
        /*StringBuilder tablaTemporalNota = new StringBuilder();
        tablaTemporalNota.append("create table if not exists ").append(ContratoBaseDatos.TablaNota.TABLA + "tmp").append("(");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota._ID).append(" integer primary key autoincrement, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.TIPO).append(" integer, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.TITULO).append(" text, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.NOTA).append(" text, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.REALIZADO).append(" integer, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.IMAGEN).append(" text, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.FECHA).append(" text, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.RECORDATORIO).append(" text, ");
        tablaTemporalNota.append(ContratoBaseDatos.TablaNota.AUDIO).append(" text");


        tablaTemporalNota.append(")");
        db.execSQL(tablaTemporalNota.toString());

        //Se crea la tabla temporal de tareas
        StringBuilder tablaTemporalTareas = new StringBuilder();
        tablaTemporalTareas.append("create table if not exists ").append(ContratoBaseDatos.TablaTareas.TABLA + "tmp").append("(");
        tablaTemporalTareas.append(ContratoBaseDatos.TablaTareas._ID).append(" integer primary key autoincrement, ");
        tablaTemporalTareas.append(ContratoBaseDatos.TablaTareas.ID_NOTA).append(" integer, ");
        tablaTemporalTareas.append(ContratoBaseDatos.TablaTareas.REALIZADA).append(" integer, ");
        tablaTemporalTareas.append(ContratoBaseDatos.TablaTareas.TAREA).append(" text");
        tablaTemporalTareas.append(")");
        db.execSQL(tablaTemporalTareas.toString());

        //Insertamos los datos en ambas tablas
        String insert1 = "Insert into " + ContratoBaseDatos.TablaNota.TABLA + "tmp Select * from " + ContratoBaseDatos.TablaNota.TABLA;
        String insert2 = "Insert into " + ContratoBaseDatos.TablaTareas.TABLA + "tmp Select * from " + ContratoBaseDatos.TablaTareas.TABLA;
        db.execSQL(insert1);
        db.execSQL(insert2);

        //Borramos las tablas antiguas
        String delete1="drop table if exists " + ContratoBaseDatos.TablaNota.TABLA;
        String delete2="drop table if exists " + ContratoBaseDatos.TablaTareas.TABLA;
        db.execSQL(delete1);
        db.execSQL(delete2);

        //Reinsertamos los datos en las tablas y creamos las tablas
        onCreate(db);
        String insert3 = "Insert into " + ContratoBaseDatos.TablaNota.TABLA + " Select * from " + ContratoBaseDatos.TablaNota.TABLA + "tmp";
        String insert4 = "Insert into " + ContratoBaseDatos.TablaTareas.TABLA + " Select * from " + ContratoBaseDatos.TablaTareas.TABLA + "tmp";
        db.execSQL(insert3);
        db.execSQL(insert4);

        //Borramos las tablas temporales
        String delete3="drop table if exists " + ContratoBaseDatos.TablaNota.TABLA + "tmp";
        String delete4="drop table if exists " + ContratoBaseDatos.TablaTareas.TABLA + "tmp";
        db.execSQL(delete3);
        db.execSQL(delete4);*/
    }
}