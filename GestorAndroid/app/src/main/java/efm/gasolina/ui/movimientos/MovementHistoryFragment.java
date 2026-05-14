package efm.gasolina.ui.movimientos;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import efm.gasolina.R;
import efm.gasolina.model.sale.Movement;

public class MovementHistoryFragment extends Fragment {

    private MovementHistory viewModel;
    private RecyclerView rvHistorial;
    private TextView tvEmpty;
    private String operatorEmail;
    private Long stationId;

    public static MovementHistoryFragment newInstance(String email, Long stationId) {
        MovementHistoryFragment fragment = new MovementHistoryFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        args.putLong("stationId", stationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movements_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            operatorEmail = getArguments().getString("email");
            stationId     = getArguments().getLong("stationId", -1L);
        }

        rvHistorial = view.findViewById(R.id.rv_historial);
        tvEmpty     = view.findViewById(R.id.tv_empty);

        viewModel = new ViewModelProvider(requireActivity())
                .get(MovementHistory.class);

        HistorialAdapter adapter = new HistorialAdapter();
        rvHistorial.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHistorial.setAdapter(adapter);

        viewModel.getMovimientos().observe(getViewLifecycleOwner(), lista -> {
            adapter.setData(lista);
            tvEmpty.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
        });

        viewModel.getActionResult().observe(getViewLifecycleOwner(), result ->
                Toast.makeText(requireContext(), result.substring(7), Toast.LENGTH_SHORT).show());

        viewModel.cargarHistorial(operatorEmail, stationId);
    }
}