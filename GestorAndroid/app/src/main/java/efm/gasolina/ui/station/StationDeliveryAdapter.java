package efm.gasolina.ui.station;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.delivery.Delivery;

public class StationDeliveryAdapter extends RecyclerView.Adapter<StationDeliveryAdapter.ViewHolder> {

    public interface OnAction {
        void onAccept(Delivery delivery);
        void onReject(Delivery delivery);
    }

    private List<Delivery> deliveries;
    private OnAction listener;

    public StationDeliveryAdapter(List<Delivery> deliveries, OnAction listener) {
        this.deliveries = deliveries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery_action, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery d = deliveries.get(position);

        holder.tvVehicle.setText("🚛 " + d.getVehicle());
        holder.tvConductor.setText("Conductor: " + d.getConductor());
        holder.tvVolume.setText("Volumen: " + d.getVolume() + " gal");
        holder.tvFuelType.setText("Combustible: " + d.getFuelType());
        holder.tvDate.setText("Fecha: " + d.getDate());
        holder.tvDistributor.setText("📦 " + (d.getDistributor() != null
                ? d.getDistributor().getName() : "—"));


        if (d.getPrice() != null) {
            holder.tvPrice.setText("💰 Precio: $" + d.getPrice());
        } else {
            holder.tvPrice.setText("💰 Precio: No especificado");
        }

        holder.btnAccept.setOnClickListener(v -> listener.onAccept(d));
        holder.btnReject.setOnClickListener(v -> listener.onReject(d));
    }

    @Override
    public int getItemCount() { return deliveries.size(); }

    public void removeItem(Delivery delivery) {
        int index = deliveries.indexOf(delivery);
        if (index != -1) {
            deliveries.remove(index);
            notifyItemRemoved(index);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicle, tvConductor, tvVolume,
                tvFuelType, tvDistributor, tvDate, tvPrice;
        MaterialButton btnAccept, btnReject;

        ViewHolder(View itemView) {
            super(itemView);
            tvVehicle     = itemView.findViewById(R.id.tv_vehicle);
            tvConductor   = itemView.findViewById(R.id.tv_conductor);
            tvVolume      = itemView.findViewById(R.id.tv_volume);
            tvFuelType    = itemView.findViewById(R.id.tv_fuel_type);
            tvDistributor = itemView.findViewById(R.id.tv_distributor);
            tvDate        = itemView.findViewById(R.id.tv_date);
            tvPrice       = itemView.findViewById(R.id.tv_price);
            btnAccept     = itemView.findViewById(R.id.btn_accept);
            btnReject     = itemView.findViewById(R.id.btn_reject);
        }
    }
}