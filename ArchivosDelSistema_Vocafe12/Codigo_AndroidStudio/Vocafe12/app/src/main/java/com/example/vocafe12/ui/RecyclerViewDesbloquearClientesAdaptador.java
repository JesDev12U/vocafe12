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

public class RecyclerViewDesbloquearClientesAdaptador extends RecyclerView.Adapter<RecyclerViewDesbloquearClientesAdaptador.DesbloquearClientesViewHolder>{
    private Context mCtx;
    private List<DesbloquearClientesClass> desbloquearClientesClassList;
    RecyclerViewDesbloquearClientesAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(DesbloquearClientesClass item);
    }
    public RecyclerViewDesbloquearClientesAdaptador(Context mCtx,List<DesbloquearClientesClass>desbloquearClientesClassList,RecyclerViewDesbloquearClientesAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.desbloquearClientesClassList = desbloquearClientesClassList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewDesbloquearClientesAdaptador.DesbloquearClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_desbloquearclientes,null);
        return new DesbloquearClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDesbloquearClientesAdaptador.DesbloquearClientesViewHolder holder, int position) {
        DesbloquearClientesClass desbloquearClientesClass = desbloquearClientesClassList.get(position);
        holder.txtIDDC.setText(desbloquearClientesClass.getIDDC());
        holder.txtNomCompletoDC.setText(desbloquearClientesClass.getNombreCompletoDC());
        holder.txtCorreoDC.setText(desbloquearClientesClass.getCorreoDC());
        holder.txtPasswordDC.setText(desbloquearClientesClass.getPasswordDC());
        holder.binDataDesbloquearClientes(desbloquearClientesClassList.get(position));
    }

    @Override
    public int getItemCount() {
        return desbloquearClientesClassList.size();
    }
    class DesbloquearClientesViewHolder extends RecyclerView.ViewHolder{
        TextView txtIDDC,txtNomCompletoDC,txtCorreoDC,txtPasswordDC;
        public DesbloquearClientesViewHolder(View itemView){
            super(itemView);
            txtIDDC = itemView.findViewById(R.id.txtIDDC);
            txtNomCompletoDC = itemView.findViewById(R.id.txtNomCompletoDC);
            txtCorreoDC = itemView.findViewById(R.id.txtCorreoDC);
            txtPasswordDC = itemView.findViewById(R.id.txtPasswordDC);
        }
        void binDataDesbloquearClientes(final DesbloquearClientesClass item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
