package com.izv.dam.newquip.vistas.notas;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloNota implements ContratoNota.InterfaceModelo {

    private ContentResolver cr;

    public ModeloNota(Context c) {
        cr = c.getContentResolver();
    }

    @Override
    public void close() {
    }

    @Override
    public Nota getNota(long id) {
        Log.v("ModeloNota", "getNota(), id: " + id);
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, id);
        Cursor c = cr.query(
                uri,
                ContratoBaseDatos.TablaNota.PROJECTION_ALL,
                "",
                new String[]{},
                ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
        );
        return Nota.getNota(c);
    }

    @Override
    public long saveNota(Nota n) {
        Log.v("ModeloNota", "saveNota(), id: " + n.getId() + ", Titulo: " + n.getTitulo() + ", nota: " + n.getNota());
        long r = 0;
        if(n.getId()==0) {
             r = this.insertNota(n);
        } else {
            this.updateNota(n);
        }
        return r;
    }

    private long deleteNota(Nota n) {
        Log.v("ModeloNota", "deleteNota(), id: " + n.getId() + ", Titulo: " + n.getTitulo() + ", nota: " + n.getNota());
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        return cr.delete(
                uri,
                "",
                new String[]{}
        );
    }

    private long insertNota(Nota n) {
        Log.v("ModeloNota", "insertNota(), id: " + n.getId() + ", Titulo: " + n.getTitulo() + ", nota: " + n.getNota());
        if(n.getNota().trim().compareTo("")==0 && n.getTitulo().trim().compareTo("")==0) {
            this.deleteNota(n);
            return 0;
        }
        return ContentUris.parseId(cr.insert(
                ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                n.getContentValues(false)
        ));
    }

    private int updateNota(Nota n) {
        Log.v("ModeloNota", "updateNota(), id: " + n.getId() + ", Titulo: " + n.getTitulo() + ", nota: " + n.getNota());
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        if(n.getNota().trim().compareTo("")==0 && n.getTitulo().trim().compareTo("")==0) {
            this.deleteNota(n);
            return 0;
        }
        return cr.update(
                uri,
                n.getContentValues(true),
                "",
                new String[]{}
        );
    }
}