package efm.gasolina.ui.client.sales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.clients.ClientSaleRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSaleViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<String> result = new MutableLiveData<>();

    public ClientSaleViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getResult() { return result; }

    public void realizarCompra(String email, String brand, String zone,
                               String fuelType, String vehicleType, Integer volume) {  if (volume <= 0) {
            result.setValue("ERROR:El volumen debe ser mayor a 0");
            return;
        }

        ClientSaleRequest request = new ClientSaleRequest(
                email, brand, zone, fuelType, vehicleType, volume);
        apiService.realizarCompra(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    result.setValue("OK:Compra realizada correctamente");
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
