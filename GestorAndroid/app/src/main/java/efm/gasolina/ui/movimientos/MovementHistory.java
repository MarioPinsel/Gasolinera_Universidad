package efm.gasolina.ui.movimientos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import efm.gasolina.model.sale.Movement;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovementHistory extends ViewModel {

    private final ApiService apiService;

    private final MutableLiveData<List<Movement>> movimientos = new MutableLiveData<>();
    private final MutableLiveData<String> actionResult = new MutableLiveData<>();

    public MovementHistory() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<Movement>> getMovimientos() {
        return movimientos;
    }

    public LiveData<String> getActionResult() {
        return actionResult;
    }

    // 🔍 CONSULTAR HISTORIAL
    public void cargarHistorial(String operatorEmail) {
        apiService.getHistorialMovimientos(operatorEmail)
                .enqueue(new Callback<List<Movement>>() {
                    @Override
                    public void onResponse(Call<List<Movement>> call,
                                           Response<List<Movement>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            movimientos.setValue(response.body());
                        } else {
                            actionResult.setValue("ERROR:Error " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movement>> call, Throwable t) {
                        actionResult.setValue("ERROR:Sin conexión");
                    }
                });
    }
}
