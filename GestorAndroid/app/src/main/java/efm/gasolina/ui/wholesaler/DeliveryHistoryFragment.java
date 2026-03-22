package efm.gasolina.ui.wholesaler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import efm.gasolina.R;

public class DeliveryHistoryFragment extends Fragment {

    private RecyclerView recyclerDeliveries;
    private DeliveryAdapter adapter;
    private WholesalerViewModel viewModel;
    private String distributorEmail;


    public static DeliveryHistoryFragment newInstance(String email) {
        DeliveryHistoryFragment fragment = new DeliveryHistoryFragment();
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
        return inflater.inflate(R.layout.fragment_delivery_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            distributorEmail = getArguments().getString("email");
        }

        recyclerDeliveries = view.findViewById(R.id.recyclerDeliveries);
        recyclerDeliveries.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new DeliveryAdapter(new ArrayList<>());
        recyclerDeliveries.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity())
                .get(WholesalerViewModel.class);

        viewModel.getDeliveries().observe(getViewLifecycleOwner(), deliveries -> {
            adapter.updateList(deliveries);
        });

        viewModel.loadHistory(distributorEmail);

        Button btnRefresh = view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(v -> viewModel.loadHistory(distributorEmail));
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadHistory(distributorEmail);
    }
}