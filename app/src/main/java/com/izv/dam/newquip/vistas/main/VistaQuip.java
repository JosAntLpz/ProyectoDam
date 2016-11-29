package com.izv.dam.newquip.vistas.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.notas.VistaNota;
import com.izv.dam.newquip.vistas.notas.VistaNotaLista;

import java.util.ArrayList;


public class VistaQuip extends AppCompatActivity implements ContratoMain.InterfaceVista , OnBorrarDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recycler;
    private AdaptadorNota adaptador;
    private PresentadorQuip presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presentador = new PresentadorQuip(this);

        recycler = (RecyclerView) findViewById(R.id.rvNotas);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorNota(null);
        recycler.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorNota.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presentador.onEditNota(position);
            }
        });

        adaptador.setOnCheckBoxClickListener(new AdaptadorNota.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(int position, boolean value) {
                presentador.onUpdateNota(position, value);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                presentador.onDeleteNota(swipedPosition);
                adaptador.notifyItemRemoved(swipedPosition);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_nota);
        fab.setColorNormal(getResources().getColor(R.color.colorAccent));
        fab.setColorPressed(getResources().getColor(R.color.colorAccent));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.onAddNota(Nota.NOTA_SIMPLE);
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.add_lista);
        fab2.setColorNormal(getResources().getColor(R.color.colorAccent));
        fab2.setColorPressed(getResources().getColor(R.color.colorAccent));
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.onAddNota(Nota.NOTA_LISTA);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {

    }

    @Override
    public void onBorrarNegativeButtonClick() {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            presentador.onLoadCursorNotas(0);
        }else if (id == R.id.nav_notas) {
            presentador.onLoadCursorNotas(1);
        } else if (id == R.id.nav_listas) {
            presentador.onLoadCursorNotas(2);
        } else if (id == R.id.nav_recordatorios) {
            presentador.onLoadCursorNotas(3);
        } else if (id == R.id.nav_completadas) {
            presentador.onLoadCursorNotas(4);
        } else if (id == R.id.nav_no_completadas) {
            presentador.onLoadCursorNotas(5);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showAddNota(int tipo) {
        if(tipo == Nota.NOTA_SIMPLE || tipo == Nota.NOTA_LISTA) {
            Nota nota = new Nota();
            nota.setTipo(tipo);
            Intent intent = null;
            if(tipo == Nota.NOTA_SIMPLE){
                intent = new Intent(this, VistaNota.class);
            }else if(tipo == Nota.NOTA_LISTA){
                intent = new Intent(this, VistaNotaLista.class);
                intent.putParcelableArrayListExtra("tareas", (ArrayList<? extends Parcelable>) nota.getTareas());
            }
            if(intent != null) {
                intent.putExtra("nota", nota);
                startActivity(intent);
            }
        }
    }

    @Override
    public void showEditNota(Nota nota) {
        Intent intent = null;
        if(nota.getTipo() == Nota.NOTA_SIMPLE){
            intent = new Intent(this, VistaNota.class);
        }else if(nota.getTipo() == Nota.NOTA_LISTA){
            intent = new Intent(this, VistaNotaLista.class);
            intent.putParcelableArrayListExtra("tareas", (ArrayList<? extends Parcelable>) nota.getTareas());
        }
        if(intent != null) {
            intent.putExtra("nota", nota);
            startActivity(intent);
        }
    }

    @Override
    public void showConfirmDeleteNota(Nota n) {

    }

    @Override
    public void showNotas(Cursor cursorNotas, Cursor cursoTareas) {
        adaptador.changeCursorTareas(cursoTareas);
        adaptador.changeCursorNotas(cursorNotas);
    }
}