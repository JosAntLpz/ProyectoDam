package com.izv.dam.newquip.vistas.notas;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNotaLista;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;

import java.util.Date;

/**
 * Created by dam on 25/10/2016.
 */

public class ModeloNotaLista implements ContratoNotaLista.InterfaceModelo  {

    private ContentResolver cr;

    public ModeloNotaLista(Context c){
        cr = c.getContentResolver();
    }

    @Override
    public long saveNota(Nota n) {
        n.setFecha(new Date());
        if(n.getId() == 0){
            return insertNota(n);
        }else{
            updateNota(n);
            return n.getId();
        }
    }

    @Override
    public long saveTarea(Tarea t) {
        if(t.getId() == 0){
            return insertTarea(t);
        }else{
            updateTarea(t);
            return t.getId();
        }
    }

    @Override
    public void removeTarea(long id) {
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, id);
        cr.delete(
                uri,
                "",
                new String[]{}
        );
    }

    private long insertNota(Nota n){
        long id = ContentUris.parseId(
            cr.insert(
                ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                n.getContentValues(false)
            )
        );
        if(n.getTareas() != null) {
            for (Tarea t : n.getTareas()) {
                t.setIdNota(id);
                t.setId(insertTarea(t));
            }
        }
        return id;
    }

    private void updateNota(Nota n){
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        cr.update(
            uri,
            n.getContentValues(true),
            "",
            new String[]{}
        );
        for (Tarea t : n.getTareas()) {
            if(t.getId() == 0){
                t.setId(insertTarea(t));
            }else{
                updateTarea(t);
            }
        }
    }

    private long insertTarea(Tarea t){
        return ContentUris.parseId(
            cr.insert(
                ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                t.getContentValues(false)
            )
        );
    }

    private void updateTarea(Tarea t){
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, t.getId());
        cr.update(
            uri,
            t.getContentValues(true),
            "",
            new String[]{}
        );
    }
}
