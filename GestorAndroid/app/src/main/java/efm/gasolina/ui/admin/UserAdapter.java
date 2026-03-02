package efm.gasolina.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> usuarios;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onAceptar(User user);
        void onRechazar(User user);
    }

    public UserAdapter(List<User> usuarios, OnUserActionListener listener) {
        this.usuarios = usuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = usuarios.get(position);

        holder.tvNombre.setText(user.getName());
        holder.tvCorreo.setText(user.getEmail());
        holder.tvRol.setText("Rol: " + user.getRole());

        holder.btnAceptar.setOnClickListener(v -> listener.onAceptar(user));
        holder.btnRechazar.setOnClickListener(v -> listener.onRechazar(user));
    }

    @Override
    public int getItemCount() { return usuarios.size(); }

    public void updateList(List<User> nuevaLista) {
        this.usuarios = nuevaLista;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCorreo, tvRol;
        Button btnAceptar, btnRechazar;

        UserViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            tvRol = itemView.findViewById(R.id.tvRol);
            btnAceptar = itemView.findViewById(R.id.btnAceptar);
            btnRechazar = itemView.findViewById(R.id.btnRechazar);
        }
    }
}