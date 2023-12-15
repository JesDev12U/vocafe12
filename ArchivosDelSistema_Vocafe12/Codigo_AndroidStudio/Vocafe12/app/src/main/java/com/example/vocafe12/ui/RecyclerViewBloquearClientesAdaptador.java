package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocafe12.BloquearClientes;
import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewBloquearClientesAdaptador extends RecyclerView.Adapter<RecyclerViewBloquearClientesAdaptador.BloquearClientesViewHolder>{
    private Context mCtx;
    private List<BloquearClientesClass> bloquearClientesClassList;
    RecyclerViewBloquearClientesAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(BloquearClientesClass item);
    }
    public RecyclerViewBloquearClientesAdaptador(Context mCtx,List<BloquearClientesClass>bloquearClientesClassList,RecyclerViewBloquearClientesAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.bloquearClientesClassList = bloquearClientesClassList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewBloquearClientesAdaptador.BloquearClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_bloquearclientes,null);
        return new BloquearClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBloquearClientesAdaptador.BloquearClientesViewHolder holder, int position) {
        BloquearClientesClass bloquearClientesClass = bloquearClientesClassList.get(position);
        holder.txtIDBC.setText(bloquearClientesClass.getIDBC());
        holder.txtNomCompletoBC.setText(bloquearClientesClass.getNombreCompletoBC());
        holder.txtCorreoBC.setText(bloquearClientesClass.getCorreoBC());
        holder.txtPasswordBC.setText(bloquearClientesClass.getPasswordBC());
        holder.binDataBloquearClientes(bloquearClientesClassList.get(position));
    }

    @Override
    public int getItemCount() {
        return bloquearClientesClassList.size();
    }
    class BloquearClientesViewHolder extends RecyclerView.ViewHolder{
        TextView txtIDBC,txtNomCompletoBC,txtCorreoBC,txtPasswordBC;
        public BloquearClientesViewHolder(View itemView){
            super(itemView);
            txtIDBC = itemView.findViewById(R.id.txtIDBC);
            txtNomCompletoBC = itemView.findViewById(R.id.txtNomCompletoBC);
            txtCorreoBC = itemView.findViewById(R.id.txtCorreoBC);
            txtPasswordBC = itemView.findViewById(R.id.txtPasswordBC);
        }
        void binDataBloquearClientes(final BloquearClientesClass item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
