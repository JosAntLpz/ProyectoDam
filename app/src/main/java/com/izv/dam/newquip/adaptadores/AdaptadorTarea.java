package com.izv.dam.newquip.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Tarea;

import java.util.List;

/**
 * Created by Pablo on 17/10/2016.
 */

public class AdaptadorTarea extends RecyclerView.Adapter<AdaptadorTarea.ViewHolderTarea>{
    private List<Tarea> list;


    public static class ViewHolderTarea extends RecyclerView.ViewHolder {
        public CheckBox cbRealizado;
        public EditText etTarea;

        public ViewHolderTarea(View itemView) {
            super(itemView);
            cbRealizado = (CheckBox) itemView.findViewById(R.id.cbRealizado);
            etTarea = (EditText) itemView.findViewById(R.id.etTarea);
        }
    }

    public AdaptadorTarea(List<Tarea> list){
        this.list = list;
    }

    @Override
    public ViewHolderTarea onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarea_card, parent, false);
        return new ViewHolderTarea(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolderTarea holder, final int position) {
        Tarea tarea = list.get(position);
        holder.cbRealizado.setChecked(tarea.isRealizado());
        holder.cbRealizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.get(position).setRealizado(b);
            }
        });
        holder.etTarea.setText(tarea.getTarea());

        holder.etTarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (position <= list.size() - 1) {
                    list.get(position).setTarea(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }else{
            return 0;
        }
    }

}
