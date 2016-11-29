package com.izv.dam.newquip.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.izv.dam.newquip.basedatos.Ayudante;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by JoseAntonio on 29/10/2016.
 */

public class Proveedor extends ContentProvider {

    //private GestionNota gn;
    //private GestionTareas gt;

    private Ayudante ayudante;
    private SQLiteDatabase bd;

    private static final int NOTAS = 1;
    private static final int NOTAS_ID = 2;
    private static final int TAREAS = 3;
    private static final int TAREAS_ID = 4;
    //private static final int TAREAS_NOTA_ID = 5;
    //private static final int JOIN = 6;
    private static final UriMatcher URI_MATCHER;

    //Inicializamos el UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, ContratoBaseDatos.TablaNota.TABLA, NOTAS);
        URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, ContratoBaseDatos.TablaNota.TABLA + "/#", NOTAS_ID);
        URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, ContratoBaseDatos.TablaTareas.TABLA, TAREAS);
        URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, ContratoBaseDatos.TablaTareas.TABLA + "/#", TAREAS_ID);
        //URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, ContratoBaseDatos.TablaTareas.TABLA + "/" + ContratoBaseDatos.TablaNota.TABLA + "/#", TAREAS_NOTA_ID);
        //URI_MATCHER.addURI(ContratoBaseDatos.AUTHORITY, "JOIN", JOIN);
    }

    @Override
    public boolean onCreate() {
        //gn = new GestionNota(getContext());
        //gt = new GestionTareas(getContext());
        ayudante = new Ayudante(this.getContext());
        bd = ayudante.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        switch( URI_MATCHER.match(uri) ) {
            case NOTAS_ID : {
                where = ContratoBaseDatos.TablaNota._ID + " = " + uri.getLastPathSegment();
            }
            case NOTAS : {
                return bd.query(
                        ContratoBaseDatos.TablaNota.TABLA,
                        projection,
                        where,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                //return gn.getCursor(projection, where, selectionArgs, null, null, sortOrder);
            }
            case TAREAS_ID : {
                where = ContratoBaseDatos.TablaTareas._ID + " = " + uri.getLastPathSegment();
            }
            case TAREAS : {
                return bd.query(
                        ContratoBaseDatos.TablaTareas.TABLA,
                        projection,
                        where,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                //return gt.getCursor(projection, where, selectionArgs, null, null, sortOrder);
            }
            /*case JOIN : {
                String query = "SELECT * FROM " + ContratoBaseDatos.TablaNota.TABLA + " LEFT JOIN " + ContratoBaseDatos.TablaTareas.TABLA
                        + " ON " + ContratoBaseDatos.TablaNota.TABLA + "." + ContratoBaseDatos.TablaNota._ID + " = " + ContratoBaseDatos.TablaTareas.ID_NOTA;
                return gt.rawQuery(query);
            }*/
            default: {
                return null;
            }
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case NOTAS:
                return "vnd.android.cursor.dir/com.izv.dam.nota";
            case NOTAS_ID:
                return "vnd.android.cursor.item/com.izv.dam.nota";
            case TAREAS:
                return "vnd.android.cursor.item/com.izv.dam.tarea";
            case TAREAS_ID:
                return "vnd.android.cursor.item/com.izv.dam.tarea";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = -1;
        switch( URI_MATCHER.match(uri) ) {
            case NOTAS : {
                id = bd.insert(ContratoBaseDatos.TablaNota.TABLA, null, values);
                //id = gn.insert(values);
                Uri newUri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, id);
                return newUri;
            }
            case TAREAS : {
                id = bd.insert(ContratoBaseDatos.TablaTareas.TABLA, null, values);
                //id = gt.insert(values);
                Uri newUri = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, id);
                return newUri;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        switch( URI_MATCHER.match(uri) ) {
            case NOTAS_ID : {
                where = ContratoBaseDatos.TablaNota._ID + " = " + uri.getLastPathSegment();
            }
            case NOTAS : {
                return bd.delete(ContratoBaseDatos.TablaNota.TABLA, where, selectionArgs);
                //return gn.delete(where,selectionArgs);
            }
            case TAREAS_ID : {
                where = ContratoBaseDatos.TablaTareas._ID + " = " + uri.getLastPathSegment();
            }
            case TAREAS : {
                return bd.delete(ContratoBaseDatos.TablaTareas.TABLA, where, selectionArgs);
                //return gt.delete(where,selectionArgs);
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        switch( URI_MATCHER.match(uri) ) {
            case NOTAS_ID : {
                where = ContratoBaseDatos.TablaNota._ID + " = " + uri.getLastPathSegment();
            }
            case NOTAS : {
                return bd.update(ContratoBaseDatos.TablaNota.TABLA, values, where, selectionArgs);
                //return gn.update(values,where,selectionArgs);
            }
            case TAREAS_ID : {
                where = ContratoBaseDatos.TablaTareas._ID + " = " + uri.getLastPathSegment();
            }
            case TAREAS : {
                return bd.update(ContratoBaseDatos.TablaTareas.TABLA, values, where, selectionArgs);
                //return gt.update(values,where,selectionArgs);
            }
            default: {
                return 0;
            }
        }
    }
}
