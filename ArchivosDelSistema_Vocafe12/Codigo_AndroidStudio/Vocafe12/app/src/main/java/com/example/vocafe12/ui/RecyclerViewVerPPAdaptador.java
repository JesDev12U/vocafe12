package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewVerPPAdaptador extends RecyclerView.Adapter<RecyclerViewVerPPAdaptador.VerPPViewHolder> {
    private Context mCtx;
    private List<VerPP> listaPP;
    RecyclerViewVerPPAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(VerPP item);
    }
    public RecyclerViewVerPPAdaptador(Context mCtx,List<VerPP> listaPP, RecyclerViewVerPPAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.listaPP = listaPP;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewVerPPAdaptador.VerPPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_verpp,null);
        return new VerPPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewVerPPAdaptador.VerPPViewHolder holder, int position) {
        VerPP pp = listaPP.get(position);
        holder.txtCodigoPPP.setText(pp.getCodigoP());
        holder.txtIDCPP.setText(pp.getIDCliente());
        holder.txtFPP.setText(pp.getFecha());
        holder.txtHPP.setText(pp.getHora());
        holder.binDataVerPP(listaPP.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPP.size();
    }
    class VerPPViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodigoPPP,txtIDCPP,txtFPP,txtHPP;
        public VerPPViewHolder(View itemView){
            super(itemView);
            txtCodigoPPP = itemView.findViewById(R.id.txtCodigoPPP);
            txtIDCPP = itemView.findViewById(R.id.txtIDCPP);
            txtFPP = itemView.findViewById(R.id.txtFPP);
            txtHPP = itemView.findViewById(R.id.txtHPP);
        }
        void binDataVerPP(final VerPP item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { listener.onItemClick(item); }
            });
        }
    }
}
