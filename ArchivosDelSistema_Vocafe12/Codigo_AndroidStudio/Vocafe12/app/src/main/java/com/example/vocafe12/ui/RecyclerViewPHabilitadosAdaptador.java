package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewPHabilitadosAdaptador extends RecyclerView.Adapter<RecyclerViewPHabilitadosAdaptador.PHabilitadosViewHolder>{
    private Context mCtx;
    private List<PHabilitados> pHabilitadosList;
    RecyclerViewPHabilitadosAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(PHabilitados item);
    }
    public RecyclerViewPHabilitadosAdaptador(Context mCtx,List<PHabilitados> pHabilitadosList, RecyclerViewPHabilitadosAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.pHabilitadosList = pHabilitadosList;
        this.listener=listener;
    }
    @NonNull
    @Override
    public RecyclerViewPHabilitadosAdaptador.PHabilitadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_productoshabilitados,null);
        return new PHabilitadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPHabilitadosAdaptador.PHabilitadosViewHolder holder, int position) {
        PHabilitados pHabilitados = pHabilitadosList.get(position);
        holder.textViewPHabilitado.setText(pHabilitados.getNombrePHabilitado());
        Glide.with(mCtx)
                .load(pHabilitados.getFotoPHabilitado())
                .into(holder.imgPHabilitado);
        holder.binDataPHabilitados(pHabilitadosList.get(position));
    }

    @Override
    public int getItemCount() {
        return pHabilitadosList.size();
    }
    class PHabilitadosViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPHabilitado;
        ImageView imgPHabilitado;
        public PHabilitadosViewHolder(View itemView){
            super(itemView);
            textViewPHabilitado = itemView.findViewById(R.id.textViewPHabilitado);
            imgPHabilitado = itemView.findViewById(R.id.imgPHabilitado);
        }
        void binDataPHabilitados(final PHabilitados item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        listener.onItemClick(item);
                }
            });
        }
    }
}
