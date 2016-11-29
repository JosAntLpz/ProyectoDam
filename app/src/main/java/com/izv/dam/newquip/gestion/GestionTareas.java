package com.izv.dam.newquip.gestion;

import com.izv.dam.newquip.pojo.Tarea;

/**
 * Created by dam on 19/10/2016.
 */

public class GestionTareas extends Gestion<Tarea> {

    /*public GestionTareas(Context c) {
        super(c);
    }

    public GestionTareas(Context c, boolean write) {
        super(c, write);
    }

    @Override
    public long insert(Tarea objeto) {
        return this.insert(objeto.getContentValues());
    }

    @Override
    public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDatos.TablaTareas.TABLA, objeto);
    }

    @Override
    public int deleteAll() {
        return this.deleteAll(ContratoBaseDatos.TablaTareas.TABLA);
    }

    @Override
    public int delete(Tarea objeto) {
        String condicion = ContratoBaseDatos.TablaTareas._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.delete(ContratoBaseDatos.TablaTareas.TABLA, condicion, argumentos);
    }

    public int delete(String condicion, String[] argumentos) {
        return this.delete(ContratoBaseDatos.TablaTareas.TABLA, condicion, argumentos);
    }

    @Override
    public int update(Tarea objeto) {
        ContentValues valores = objeto.getContentValues();
        String condicion = ContratoBaseDatos.TablaTareas._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.update(valores, condicion, argumentos);
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return this.update(ContratoBaseDatos.TablaTareas.TABLA, valores, condicion, argumentos);
    }

    @Override
    public Cursor getCursor() {
        return this.getCursor(ContratoBaseDatos.TablaTareas.TABLA, ContratoBaseDatos.TablaTareas.PROJECTION_ALL, ContratoBaseDatos.TablaTareas.SORT_ORDER_DEFAULT);
    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.getCursor(ContratoBaseDatos.TablaTareas.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Tarea get(long id) {
        String where = ContratoBaseDatos.TablaTareas._ID + " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDatos.TablaTareas.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDatos.TablaTareas.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
            c.moveToFirst();
            return Tarea.getTarea(c);
        }
        return null;
    }

    public List<Tarea> getByNota(long id){
        String where = ContratoBaseDatos.TablaTareas.ID_NOTA + " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDatos.TablaTareas.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDatos.TablaTareas.SORT_ORDER_DEFAULT);
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(Tarea.getTarea(c));
        while(c.moveToNext()){
            tareas.add(Tarea.getTarea(c));
        }
        return tareas;
    }*/
}
