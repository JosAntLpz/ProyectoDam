package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.util.UtilFecha;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nota  implements Parcelable{

    public static final int NOTA_SIMPLE = 0;
    public static final int NOTA_LISTA = 1;

    private long id;
    private int tipo;
    private String titulo;
    private String nota;
    private boolean realizado;
    private String rutaImagen = "";
    private String rutaAudio = "";
    private Date fecha;
    private List<Tarea> tareas;
    private String recordatorio;

    public Nota(long id, int tipo, String titulo, String nota, boolean realizado, Date fecha, String recordatorio) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.nota = nota;
        this.realizado = realizado;
        this.rutaImagen = "";
        this.rutaAudio = "";
        this.tareas = new ArrayList<>();
        this.fecha = fecha;
        this.recordatorio = recordatorio;
    }

    public Nota() {
        this(0, 0, null, null, false, new Date(), null);
    }


    protected Nota(Parcel in) {
        id = in.readLong();
        tipo = in.readInt();
        titulo = in.readString();
        nota = in.readString();

        realizado = in.readByte() != 0;
        rutaImagen = in.readString();
        tareas = new ArrayList<Tarea>();
        fecha = new Date(UtilFecha.dateToLong(in.readString()));
        recordatorio = in.readString();
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            this.id = Long.parseLong(id);
        } catch(NumberFormatException e){
            this.id = 0;
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getRutaAudio() {
        return rutaAudio;
    }

    public void setRutaAudio(String rutaAudio) {
        if(rutaAudio!=null) {
            this.rutaAudio = rutaAudio;
        }
        else{
            this.rutaAudio = "";
        }
    }

    public ContentValues getContentValues(){
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId) {
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaNota._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaNota.TIPO, this.getTipo());
        valores.put(ContratoBaseDatos.TablaNota.TITULO, this.getTitulo());
        valores.put(ContratoBaseDatos.TablaNota.NOTA, this.getNota());
        valores.put(ContratoBaseDatos.TablaNota.REALIZADO, this.isRealizado());
        valores.put(ContratoBaseDatos.TablaNota.IMAGEN, this.getRutaImagen());
        valores.put(ContratoBaseDatos.TablaNota.AUDIO, this.getRutaAudio());
        valores.put(ContratoBaseDatos.TablaNota.FECHA, UtilFecha.formatDate(this.getFecha()));
        valores.put(ContratoBaseDatos.TablaNota.RECORDATORIO, this.getRecordatorio());
        return valores;
    }

    public static Nota getNota(Cursor c) {
        Nota objeto = new Nota();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaNota._ID)));
        objeto.setTipo(c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaNota.TIPO)));
        objeto.setTitulo(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
        objeto.setNota(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.NOTA)));
        objeto.setRutaImagen(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.IMAGEN)));
        objeto.setRutaAudio(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.AUDIO)));
        if (c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaNota.REALIZADO)) == 0)
            objeto.setRealizado(false);
        else
            objeto.setRealizado(true);
        String f = c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.FECHA));
        if (f != null)
            objeto.setFecha(new Date(UtilFecha.dateToLong(f)));
        String r = c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.RECORDATORIO));
        if (r != null)
            objeto.setRecordatorio(r);
        return objeto;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void setTareas(Cursor c){
        this.tareas = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                if(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaTareas.ID_NOTA)) == this.id)
                    tareas.add(Tarea.getTarea(c));
            }while(c.moveToNext());
        }
    }

    public boolean addTarea(Tarea tarea){
        tarea.setIdNota(this.getId());
        return this.tareas.add(tarea);
    }

    public Tarea removeTarea(int posicion){
        return this.tareas.remove(posicion);
    }

    public boolean removeTarea(Tarea tarea){
        return this.tareas.remove(tarea);
    }

    @Override
    public String toString() {
        String ts = "";
        if(tareas != null && !tareas.isEmpty()) {
            for (Tarea t : this.tareas) {
                ts += t.toString() + ", ";
            }
        }
        return "Nota{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", titulo='" + titulo + '\'' +
                ", nota='" + nota + '\'' +
                ", realizado=" + realizado +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", rutaAudio='" + rutaAudio + '\'' +
                ", fecha=" + fecha +
                ", recordatorio='" + recordatorio +
                ", tareas=" + ts +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(tipo);
        dest.writeString(titulo);
        dest.writeString(nota);
        dest.writeByte((byte) (realizado ? 1 : 0));
        dest.writeString(rutaImagen);
        dest.writeString(rutaAudio);
        dest.writeString(fecha.toString());
        dest.writeString(recordatorio);
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        if(rutaImagen!=null) {
            this.rutaImagen = rutaImagen;
        }
        else{
            this.rutaImagen = "";
        }
    }
}