package efm.gasolina.ui.movimientos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import efm.gasolina.model.sale.Movimiento;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialMovimientos extends ViewModel {

    private final ApiService apiService;

    private final MutableLiveData<List<Movimiento>> movimientos = new MutableLiveData<>();
    private final MutableLiveData<String> actionResult = new MutableLiveData<>();

    public HistorialMovimientos() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<Movimiento>> getMovimientos() {
        return movimientos;
    }

    public LiveData<String> getActionResult() {
        return actionResult;
    }

    // 🔍 CONSULTAR HISTORIAL
    public void cargarHistorial(String operatorEmail) {
        apiService.getHistorialMovimientos(operatorEmail)
                .enqueue(new Callback<List<Movimiento>>() {
                    @Override
                    public void onResponse(Call<List<Movimiento>> call,
                                           Response<List<Movimiento>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            movimientos.setValue(response.body());
                        } else {
                            actionResult.setValue("ERROR:Error " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                        actionResult.setValue("ERROR:Sin conexión");
                    }
                });
    }
}
