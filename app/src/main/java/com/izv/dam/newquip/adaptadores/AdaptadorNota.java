package com.izv.dam.newquip.adaptadores;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;
import com.izv.dam.newquip.util.UtilFecha;

import java.util.StringTokenizer;

/**
 * Created by Pablo on 13/10/2016.
 */

public class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.ViewHolderNota> {
    private Cursor cursor;
    private Cursor cursorTareas;
    private OnItemClickListener clickListener;
    private OnCheckBoxClickListener checkBoxListener;

    public AdaptadorNota(Cursor cursor) {
        this.cursor = cursor;
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int i, boolean value);
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener listener){
        this.checkBoxListener = listener;
    }

    public static class ViewHolderNota extends RecyclerView.ViewHolder{

        public CheckBox cbRealizado;
        public TextView tvNota;
        public ImageView ivImage;
        public ImageView ivAudio;
        public TextView tvFecha;
        public TextView tvText;

        public ViewHolderNota(View itemView) {
            super(itemView);
            cbRealizado = (CheckBox) itemView.findViewById(R.id.cbRealizado);
            tvNota = (TextView) itemView.findViewById(R.id.tvNota);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            ivAudio = (ImageView) itemView.findViewById(R.id.ivAudio);
        }
    }

    @Override
    public int getItemViewType(int position) {
        cursor.moveToPosition(position);
        return Nota.getNota(cursor).getTipo();
    }

    @Override
    public ViewHolderNota onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == Nota.NOTA_SIMPLE)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota_simple_card, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota_lista_card, parent, false);
        return new ViewHolderNota(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderNota holder, final int position) {
        cursor.moveToPosition(position);
        final Nota nota = Nota.getNota(cursor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(position);
            }
        });
        holder.cbRealizado.setChecked(nota.isRealizado());
        holder.cbRealizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxListener.onCheckBoxClick(position, b);
            }
        });
        String txtNota;
        if(!nota.getTitulo().equals("")){
            txtNota=nota.getTitulo();
            StringTokenizer str = new StringTokenizer(txtNota, "\n");
            txtNota = str.nextToken();
            if (txtNota.length() > 18) {
                txtNota = txtNota.substring(0, 18) + "...";
            }
            holder.tvText.setText(txtNota);
        }else{
            txtNota=nota.getNota();
            StringTokenizer str = new StringTokenizer(txtNota, "\n");
            txtNota = str.nextToken();
            if (txtNota.length() > 18) {
                txtNota = txtNota.substring(0, 18) + "...";
            }
            holder.tvText.setText(txtNota);
        }
        if(nota.getFecha() != null)
            holder.tvFecha.setText(UtilFecha.europeanFormatDate(nota.getFecha()));
        if(nota.getTipo() == Nota.NOTA_SIMPLE) {
            if(nota.getRutaImagen() != null && !nota.getRutaImagen().equals("")) {
                //holder.ivImage.setImageURI(Uri.fromFile(new File(nota.getRutaImagen())));
                holder.ivImage.setVisibility(View.VISIBLE);
            }
            if(nota.getRutaAudio() != null && !nota.getRutaAudio().equals("")){
                holder.ivAudio.setVisibility(View.VISIBLE);
            }
        }else if(nota.getTipo() == Nota.NOTA_LISTA){
            nota.setTareas(cursorTareas);
            if(!nota.getTareas().isEmpty()) {
                int totalElementos = nota.getTareas().size();
                int elementosRealizados = 0;
                for (Tarea tarea : nota.getTareas()) {
                    if (tarea.isRealizado())
                        elementosRealizados++;
                }
                holder.tvNota.setText("Tareas realizadas " + elementosRealizados + "/" + totalElementos);
            }else{
                holder.tvNota.setText("No hay tareas...");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public void changeCursorNotas(Cursor cursor) {
        if (cursor != null) {
            this.cursor = cursor;
            try {
                notifyDataSetChanged();
            } catch (Exception e) {
                Log.v("AdaptadorNota", e.toString());
            }
        }
    }

    public void changeCursorTareas(Cursor cursorTareas) {
        this.cursorTareas = cursorTareas;
    }

    public Cursor getCursorTareas(){
        return cursorTareas;
    }
}