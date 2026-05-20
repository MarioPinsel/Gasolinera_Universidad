package efm.gasolina.ui.client.sales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.clients.ClientHistoryDTO;

public class ClientHistoryAdapter extends RecyclerView.Adapter<ClientHistoryAdapter.ViewHolder> {

    private final List<ClientHistoryDTO> data;

    public ClientHistoryAdapter(List<ClientHistoryDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClientHistoryDTO item = data.get(position);
        holder.tvBrand.setText(item.getBrand() + " - " + item.getZone());
        holder.tvFuel.setText(item.getFuelType() + " · " + item.getVehicleType());
        holder.tvVolume.setText("Volumen: " + item.getVolume() + " gal");
        holder.tvTotal.setText("Total: $" + item.getTotalPrice());
        holder.tvDate.setText(item.getDate());
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrand, tvFuel, tvVolume, tvTotal, tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrand  = itemView.findViewById(R.id.tv_brand);
            tvFuel   = itemView.findViewById(R.id.tv_fuel);
            tvVolume = itemView.findViewById(R.id.tv_volume);
            tvTotal  = itemView.findViewById(R.id.tv_total);
            tvDate   = itemView.findViewById(R.id.tv_date);
        }
    }
}