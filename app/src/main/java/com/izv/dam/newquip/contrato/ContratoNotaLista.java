package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;

public interface ContratoNotaLista {

    interface InterfaceModelo {
        long saveNota(Nota n);

        long saveTarea(Tarea t);

        void removeTarea(long id);
    }

    interface InterfacePresentador {
        void onResume(Nota n);

        void onPause(Nota n);

        long onAddTarea(Tarea t);

        void onRemoveTarea(long id);
    }

    interface InterfaceVista {
        void showNota(Nota n);
    }

}