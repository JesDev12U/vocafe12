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

public class RecyclerViewDetallesPPSEmpAdaptador extends RecyclerView.Adapter<RecyclerViewDetallesPPSEmpAdaptador.DetallesPPSEmpViewHolder> {
    private Context mCtx;
    private List<DetallesPPSEmp> detallesPPSEmpList;
    public RecyclerViewDetallesPPSEmpAdaptador(Context mCtx, List<DetallesPPSEmp> detallesPPSEmpList){
        this.mCtx = mCtx;
        this.detallesPPSEmpList = detallesPPSEmpList;
    }
    @NonNull
    @Override
    public RecyclerViewDetallesPPSEmpAdaptador.DetallesPPSEmpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_detallespp,null);
        return new DetallesPPSEmpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDetallesPPSEmpAdaptador.DetallesPPSEmpViewHolder holder, int position) {
        DetallesPPSEmp detallesPPSEmp = detallesPPSEmpList.get(position);
        holder.textViewNombreProductoPP.setText(detallesPPSEmp.getNombreProductoPP());
        holder.textViewCantidadPP.setText(detallesPPSEmp.getCantidadPP());
        holder.textViewImportePP.setText(detallesPPSEmp.getImportePP());
        holder.textViewDetallesPP.setText(detallesPPSEmp.getDetallesPP());
        Glide.with(mCtx)
                .load(detallesPPSEmp.getFotoPP())
                .into(holder.imageViewProductoPP);
    }

    @Override
    public int getItemCount() {
        return detallesPPSEmpList.size();
    }
    class DetallesPPSEmpViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombreProductoPP,textViewCantidadPP,textViewImportePP,textViewDetallesPP;
        ImageView imageViewProductoPP;
        public DetallesPPSEmpViewHolder(View itemView){
            super(itemView);
            textViewNombreProductoPP = itemView.findViewById(R.id.textViewNombreProductoPP);
            textViewCantidadPP = itemView.findViewById(R.id.textViewCantidadPP);
            textViewImportePP = itemView.findViewById(R.id.textViewImportePP);
            textViewDetallesPP = itemView.findViewById(R.id.textViewDetallesPP);
            imageViewProductoPP = itemView.findViewById(R.id.imageViewProductoPP);
        }
    }
}
