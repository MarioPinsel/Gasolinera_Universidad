package efm.gasolina.ui.client.pqrs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.pqrs.PqrsRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PqrsViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<String> result = new MutableLiveData<>();

    public PqrsViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getResult() { return result; }

    public void enviarPqrs(String email, String brand, String tipo, String mensaje) {
        if (mensaje.trim().isEmpty()) {
            result.setValue("ERROR:El mensaje no puede estar vacío");
            return;
        }

        PqrsRequest request = new PqrsRequest(email, brand, tipo, mensaje);

        apiService.enviarPqrs(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    result.setValue("OK:PQRS enviada correctamente");
                } else {
                    result.setValue("ERROR:Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.setValue("ERROR:Sin conexión");
            }
        });
    }
}