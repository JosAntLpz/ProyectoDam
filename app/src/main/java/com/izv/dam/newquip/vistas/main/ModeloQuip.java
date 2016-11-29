package com.izv.dam.newquip.vistas.main;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloQuip implements ContratoMain.InterfaceModelo {

    private ContentResolver cr;
    private Cursor cursorNotas;
    private Cursor cursorTareas;
    private int tipo;

    public ModeloQuip(Context c) {
        cr = c.getContentResolver();
    }

    @Override
    public void close() {
        cursorNotas.close();
        cursorTareas.close();
    }

    @Override
    public void updateNota(int position, boolean value) {
        cursorNotas.moveToPosition(position);
        Nota n = Nota.getNota(cursorNotas);
        n.setRealizado(value);
        updateNota(n);
        this.loadCursorNotas(tipo);
    }

    private void updateNota(Nota n){
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        cr.update(
                uri,
                n.getContentValues(true),
                "",
                new String[]{}
        );
    }

    @Override
    public long deleteNota(int position) {
        cursorNotas.moveToPosition(position);
        Nota n = Nota.getNota(cursorNotas);
        long id = this.deleteNota(n);
        this.loadCursorNotas(tipo);
        this.loadCursorTareas();
        return id;
    }

    private long deleteNota(Nota n) {
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,n.getId());
        if (n.getTipo() == Nota.NOTA_LISTA) {
            String where = ContratoBaseDatos.TablaTareas.ID_NOTA + " =? ";
            String[] argumentos = new String[]{String.valueOf(n.getId())};
            cr.delete(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, where, argumentos);
        }
        return cr.delete(uri,"",null);
    }

    @Override
    public Nota getNota(int position) {
        cursorNotas.moveToPosition(position);
        Nota n = Nota.getNota(cursorNotas);
        n.setId(cursorNotas.getLong(0));
        if(n.getTipo() == Nota.NOTA_LISTA){
            n.setTareas(cursorTareas);
        }
        Log.v("ModeloQuip", "getNota(" + position + ") : " + n);
        return n;
    }

    @Override
    public Cursor loadCursorNotas(int tipo) {
        this.tipo = tipo;
        if(tipo == 0){//Todas las notas
            cursorNotas = cr.query(
                    ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                    null,
                    null,
                    null ,
                    ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
            );
        }else if(tipo == 1){//Notas
            cursorNotas = cr.query(
                    ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                    null,
                    ContratoBaseDatos.TablaNota.TIPO + " = " + Nota.NOTA_SIMPLE,
                    null ,
                    ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
            );
        }else if(tipo == 2){//Listas
            cursorNotas = cr.query(
                    ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                    null,
                    ContratoBaseDatos.TablaNota.TIPO + " = " + Nota.NOTA_LISTA,
                    null ,
                    ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
            );
        }else if(tipo == 3){//Recordatorios
            //TODO implementar recordatorios
            return null;
        }else if(tipo == 4){//Completadas
            cursorNotas = cr.query(
                    ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                    null,
                    ContratoBaseDatos.TablaNota.REALIZADO + " = " + 1,
                    null ,
                    ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
            );
        }else if(tipo == 5){//No completadas
            cursorNotas = cr.query(
                    ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                    null,
                    ContratoBaseDatos.TablaNota.REALIZADO + " = " + 0,
                    null ,
                    ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
            );
        }
        return cursorNotas;
    }

    @Override
    public Cursor loadCursorTareas() {
        cursorTareas = cr.query(
                ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                null,
                null,
                null ,
                ContratoBaseDatos.TablaTareas.SORT_ORDER_DEFAULT
        );
        return cursorTareas;
    }

    @Override
    public void setCursorNotas(Cursor cursorNotas) {
        this.cursorNotas = cursorNotas;
    }

    @Override
    public Cursor getCursorNotas() {
        return cursorNotas;
    }

    @Override
    public void setCursorTareas(Cursor cursorTareas) {
        this.cursorTareas = cursorTareas;
    }

    @Override
    public Cursor getCursorTareas() {
        return cursorTareas;
    }

}