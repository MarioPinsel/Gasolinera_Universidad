package efm.gasolina.ui.movimientos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.DTO.HistorialDTO;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<HistorialDTO> data = new ArrayList<>();

    public void setData(List<HistorialDTO> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistorialDTO item = data.get(position);

        holder.tvTipo.setText(item.getTipo());
        holder.tvPlaca.setText("Placa: " + item.getPlaca());
        holder.tvVolumen.setText("Volumen: " + item.getVolumen() + " gal");
        holder.tvTotal.setText(item.getTotal() != null ? "Total: $" + item.getTotal() : "");
        holder.tvFecha.setText(item.getFecha());

        // Verde para ENTRADA, rojo para SALIDA
        int color = item.getTipo().equals("ENTRADA") ? 0xFF2E7D32 : 0xFFC62828;
        holder.tvTipo.setTextColor(color);
        holder.cardView.setCardBackgroundColor(
                item.getTipo().equals("ENTRADA") ? 0xFFE8F5E9 : 0xFFFFEBEE);
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvPlaca, tvVolumen, tvTotal, tvFecha;
        androidx.cardview.widget.CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView  = itemView.findViewById(R.id.card_historial);
            tvTipo    = itemView.findViewById(R.id.tv_tipo);
            tvPlaca   = itemView.findViewById(R.id.tv_placa);
            tvVolumen = itemView.findViewById(R.id.tv_volumen);
            tvTotal   = itemView.findViewById(R.id.tv_total);
            tvFecha   = itemView.findViewById(R.id.tv_fecha);
        }
    }
}