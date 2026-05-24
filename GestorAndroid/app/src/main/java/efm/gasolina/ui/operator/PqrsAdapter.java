package efm.gasolina.ui.operator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.pqrs.Pqrs;

public class PqrsAdapter extends RecyclerView.Adapter<PqrsAdapter.ViewHolder> {

    public interface OnRespond {
        void onRespond(Pqrs pqrs);
    }

    private final List<Pqrs> pqrsList;
    private final OnRespond listener;

    public PqrsAdapter(List<Pqrs> pqrsList, OnRespond listener) {
        this.pqrsList = pqrsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pqrs_pending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pqrs pqrs = pqrsList.get(position);

        holder.tvTipo.setText(pqrs.getTipo());
        holder.tvEmail.setText("Cliente: " + pqrs.getEmail());
        holder.tvMensaje.setText(pqrs.getMensaje());
        holder.tvFecha.setText(pqrs.getFecha() != null ? "Fecha: " + pqrs.getFecha() : "Fecha no disponible");
        holder.btnResponder.setOnClickListener(v -> listener.onRespond(pqrs));
    }

    @Override
    public int getItemCount() {
        return pqrsList.size();
    }

    public void removeItem(Pqrs pqrs) {
        int index = pqrsList.indexOf(pqrs);
        if (index != -1) {
            pqrsList.remove(index);
            notifyItemRemoved(index);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvEmail, tvMensaje, tvFecha;
        MaterialButton btnResponder;

        ViewHolder(View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tv_tipo);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvMensaje = itemView.findViewById(R.id.tv_mensaje);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            btnResponder = itemView.findViewById(R.id.btn_responder);
        }
    }
}
