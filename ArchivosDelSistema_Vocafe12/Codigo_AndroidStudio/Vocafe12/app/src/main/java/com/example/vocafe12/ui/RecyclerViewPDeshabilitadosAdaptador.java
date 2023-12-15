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

public class RecyclerViewPDeshabilitadosAdaptador extends RecyclerView.Adapter<RecyclerViewPDeshabilitadosAdaptador.PDeshabilitadosViewHolder>{
    private Context mCtx;
    private List<PDeshabilitados> pDeshabilitadosList;
    RecyclerViewPDeshabilitadosAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(PDeshabilitados item);
    }
    public RecyclerViewPDeshabilitadosAdaptador(Context mCtx,List<PDeshabilitados> pDeshabilitadosList, RecyclerViewPDeshabilitadosAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.pDeshabilitadosList = pDeshabilitadosList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewPDeshabilitadosAdaptador.PDeshabilitadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_productosdeshabilitados,null);
        return new PDeshabilitadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPDeshabilitadosAdaptador.PDeshabilitadosViewHolder holder, int position) {
        PDeshabilitados pDeshabilitados = pDeshabilitadosList.get(position);
        holder.textViewPDeshabilitados.setText(pDeshabilitados.getNombrePDeshabilitado());
        Glide.with(mCtx)
                .load(pDeshabilitados.getFotoPDeshabilitado())
                .into(holder.imgPDeshabilitados);
        holder.binDataPDeshabilitados(pDeshabilitadosList.get(position));
    }

    @Override
    public int getItemCount() {
        return pDeshabilitadosList.size();
    }
    class PDeshabilitadosViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPDeshabilitados;
        ImageView imgPDeshabilitados;
        public PDeshabilitadosViewHolder(View itemView){
            super(itemView);
            textViewPDeshabilitados = itemView.findViewById(R.id.textViewPDeshabilitado);
            imgPDeshabilitados = itemView.findViewById(R.id.imgPDeshabilitado);
        }
        void binDataPDeshabilitados(final PDeshabilitados item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
