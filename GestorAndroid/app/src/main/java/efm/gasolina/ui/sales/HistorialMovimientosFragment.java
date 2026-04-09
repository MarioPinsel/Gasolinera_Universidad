package efm.gasolina.ui.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;
import efm.gasolina.model.sale.Movimiento;

public class HistorialMovimientosFragment extends Fragment {

    private HistorialMovimientos viewModel;
    private TextView tvHistorial;
    private String operatorEmail;

    public static HistorialMovimientosFragment newInstance(String email) {
        HistorialMovimientosFragment fragment = new HistorialMovimientosFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial_movimientos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            operatorEmail = getArguments().getString("email");
        }

        tvHistorial = view.findViewById(R.id.tvHistorial);

        viewModel = new ViewModelProvider(requireActivity())
                .get(HistorialMovimientos.class);

        // 👀 Observar datos
        viewModel.getMovimientos().observe(getViewLifecycleOwner(), lista -> {
            StringBuilder texto = new StringBuilder();

            for (Movimiento m : lista) {
                texto.append("Tipo: ").append(m.getTipo()).append("\n")
                        .append("Placa: ").append(m.getPlaca()).append("\n")
                        .append("Volumen: ").append(m.getVolumen()).append("\n")
                        .append("Total: $").append(m.getTotal()).append("\n")
                        .append("Fecha: ").append(m.getFecha()).append("\n")
                        .append("----------------------\n");
            }

            tvHistorial.setText(texto.toString());
        });

        viewModel.getActionResult().observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(requireContext(),
                    result.substring(7), Toast.LENGTH_SHORT).show();
        });

        // 🚀 Cargar historial
        viewModel.cargarHistorial(operatorEmail);
    }
}