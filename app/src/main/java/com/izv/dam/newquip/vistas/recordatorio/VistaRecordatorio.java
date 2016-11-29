package com.izv.dam.newquip.vistas.recordatorio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Nota;

import java.util.Date;

/**
 * Created by Sergio on 18/11/2016.
 */

public class VistaRecordatorio extends AppCompatActivity{
    private CalendarView calendar;
    private TextView tv1;
    Nota nota;
    private String fecha;
    private ImageView guardar;
    Date d = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecordatorio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null) {
            nota = savedInstanceState.getParcelable("nota");
        } else {
            Bundle b = getIntent().getExtras();
            if(b != null ) {
                nota = b.getParcelable("nota");
            }
        }
        calendar = (CalendarView)findViewById(R.id.calendar);
        tv1 = (TextView)findViewById(R.id.tv1);
        guardar = (ImageView)findViewById(R.id.guardar);
        tv1.setText("Recordatorio: ");

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2){
                i1++;
                tv1.setText("Recordatorio: "+ i2 +" / "+ i1 +" / "+ i );
                fecha = i +"-"+ i1 +"-"+ i2;
                nota.setRecordatorio(fecha);
            }

        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nota.setRecordatorio(UtilFecha.stringToDate(fecha));
                //Log.v("recordatorioNOTA",nota.getRecordatorio().toString());
                //Intent intent = new Intent(VistaRecordatorio.this, VistaNota.class);
                //Bundle b = new Bundle();
                //b.putParcelable("nota", nota);
                //intent.putExtras(b);
                //startActivity(intent);
                cierraYguarda();
            }
        });
    }
    private void cierraYguarda(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("fechaModificada", fecha);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("nota", nota);
    }
}
