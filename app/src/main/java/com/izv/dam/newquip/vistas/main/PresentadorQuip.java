package com.izv.dam.newquip.vistas.main;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoMain;

public class PresentadorQuip implements ContratoMain.InterfacePresentador{

    private ContratoMain.InterfaceModelo modelo;
    private ContratoMain.InterfaceVista vista;

    public PresentadorQuip(ContratoMain.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new ModeloQuip((Context)vista);
    }

    @Override
    public void onResume() {
        if(this.modelo.getCursorNotas() == null && this.modelo.getCursorTareas() == null) {
            this.vista.showNotas(
                    this.modelo.loadCursorNotas(0),
                    this.modelo.loadCursorTareas()
            );
        } else {
            this.vista.showNotas(this.modelo.getCursorNotas(), this.modelo.getCursorTareas());
        }
    }

    @Override
    public void onAddNota(int tipo) {
        this.vista.showAddNota(tipo);
    }

    @Override
    public void onEditNota(int position) {
        this.vista.showEditNota(this.modelo.getNota(position));
    }

    @Override
    public void onDeleteNota(int position) {
        this.modelo.deleteNota(position);
        this.vista.showNotas(this.modelo.getCursorNotas(), this.modelo.getCursorTareas());
    }

    @Override
    public void onUpdateNota(int position, boolean value) {
        this.modelo.updateNota(position, value);
        this.vista.showNotas(this.modelo.getCursorNotas(), this.modelo.getCursorTareas());
    }

    @Override
    public void onLoadCursorNotas(int filter) {
        this.vista.showNotas(this.modelo.loadCursorNotas(filter), this.modelo.getCursorTareas());
    }
}