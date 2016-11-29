package com.izv.dam.newquip.contrato;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContratoBaseDatos {

    public final static String BASEDATOS = "quiip.sqlite";
    public static final String AUTHORITY ="com.izv.dam.newquip.notas";

    private ContratoBaseDatos(){
    }

    public static abstract class TablaNota implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id

        //TABLA NOTA
        public static final String TABLA = "nota"; // Nombre de la tabla
        public static final String TIPO = "tipo"; // Tipo de nota , nota simple / nota lista
        public static final String TITULO = "titulo"; // titulo de la nota
        public static final String NOTA = "nota"; // texto de la nota
        public static final String REALIZADO = "realizado"; // Booleano checkbox
        public static final String IMAGEN = "imagen"; // URL de la imagen o imagen en si(?) cuando el tipo es lista este campo es null
        public static final String FECHA = "fecha"; // Fecha cuando se escribio la nota o lista
        public static final String RECORDATORIO = "recordatorio";
        public static final String AUDIO = "audio";//Audio de cada nota, puede ser null, solo en nota simples
        public static final String[] PROJECTION_ALL = {_ID, TIPO, TITULO, NOTA, REALIZADO, IMAGEN, FECHA/*, RECORDATORIO*/, AUDIO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        public static final String URI_NOTA ="content://"+AUTHORITY+"/"+ContratoBaseDatos.TablaNota.TABLA;
        public static final Uri CONTENT_URI_NOTA = Uri.parse(URI_NOTA);
    }

    public static abstract class TablaTareas implements BaseColumns{
        //TABLA TAREAS
        public static final String TABLA = "tareas"; // Nombre de la tabla
        public static final String ID_NOTA = "id_nota"; // Si la nota es del tipo lista, relaciona el _id de la tabla nota con este
        public static final String TAREA = "tarea"; // Tareas que hay en la nota lista
        public static final String REALIZADA = "realizada"; // Boolean checkbox
        public static final String[] PROJECTION_ALL = {_ID, ID_NOTA, TAREA, REALIZADA};
        public static final String SORT_ORDER_DEFAULT = _ID + " asc";

        public static final String URI_TAREA ="content://"+AUTHORITY+"/"+ContratoBaseDatos.TablaTareas.TABLA;
        public static final Uri CONTENT_URI_TAREA = Uri.parse(URI_TAREA);
    }
}