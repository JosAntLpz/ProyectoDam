package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.Nota;

public interface ContratoMain {

    interface InterfaceModelo {

        void updateNota(int position, boolean value);

        long deleteNota(int position);

        Nota getNota(int position);

        Cursor loadCursorNotas(int tipo);

        Cursor loadCursorTareas();

        void setCursorNotas(Cursor cursorNotas);

        Cursor getCursorNotas();

        void setCursorTareas(Cursor cursorTareas);

        Cursor getCursorTareas();

        void close();
    }

    interface InterfacePresentador {

        void onResume();

        void onAddNota(int tipo);

        void onEditNota(int position);

        void onDeleteNota(int position);

        void onUpdateNota(int position, boolean value);

        void onLoadCursorNotas(int filter);

    }

    interface InterfaceVista {

        void showAddNota(int tipo);

        void showEditNota(Nota nota);

        void showConfirmDeleteNota(Nota n);

        void showNotas(Cursor cursorNotas, Cursor cursoTareas);

    }

}