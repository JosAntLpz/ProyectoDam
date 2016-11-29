package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by Pablo on 13/10/2016.
 */

public class Tarea implements Parcelable{
    private long id;
    private long idNota;
    private boolean realizado;
    private String tarea;

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaTareas._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaTareas.TAREA, this.getTarea());
        valores.put(ContratoBaseDatos.TablaTareas.REALIZADA, this.isRealizado());
        valores.put(ContratoBaseDatos.TablaTareas.ID_NOTA, this.getIdNota());
        return valores;
    }

    public ContentValues getContentValues(){
        return this.getContentValues(false);
    }

    public Tarea(long id, long idNota, boolean realizado, String tarea){
        this.id = id;
        this.idNota = idNota;
        this.realizado = realizado;
        this.tarea = tarea;
    }

    public Tarea(long id, long idNota){
        this(id, idNota, false, "");
    }

    public Tarea(long idNota, boolean realizado, String tarea){
        this(0, idNota, realizado, tarea);
    }

    public Tarea(boolean realizado, String tarea){
        this(0, 0, realizado, tarea);
    }

    public Tarea(String tarea){
        this(0, 0, false, tarea);
    }

    public Tarea(){
        this(0, 0, false, "");
    }

    protected Tarea(Parcel in) {
        id = in.readLong();
        idNota = in.readLong();
        if(in.readInt() == 0)
            realizado = false;
        else
            realizado = true;
        tarea = in.readString();
    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdNota() {
        return idNota;
    }

    public void setIdNota(long idNota) {
        this.idNota = idNota;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", idNota=" + idNota +
                ", realizado=" + realizado +
                ", tarea='" + tarea + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(id);
        dest.writeLong(idNota);
        if(isRealizado())
            dest.writeInt(1);
        else
            dest.writeInt(0);
        dest.writeString(tarea);
    }

    public static Tarea getTarea(Cursor c) {
        Tarea objeto = new Tarea(false, "");
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaTareas._ID)));
        objeto.setIdNota(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaTareas.ID_NOTA)));
        if(c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaTareas.REALIZADA)) == 0)
            objeto.setRealizado(false);
        else
            objeto.setRealizado(true);
        objeto.setTarea(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaTareas.TAREA)));
        return objeto;
    }
}
