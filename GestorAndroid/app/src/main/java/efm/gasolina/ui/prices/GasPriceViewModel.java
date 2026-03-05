package efm.gasolina.ui.prices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import efm.gasolina.model.consults.PricesRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasPriceViewModel extends ViewModel {
    private final ApiService apiService;
    private final MutableLiveData<String> result = new MutableLiveData<>();
    private final MutableLiveData<String> gasPricesResult = new MutableLiveData<>();

    public GasPriceViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getResult() {
        return result;
    }

    public LiveData<String> getGasPricesResult() {
        return gasPricesResult;
    }

    public void  getPricesByZoneAndType(String zone, String type) {

        if (zone.isEmpty() && type.isEmpty()) {
            gasPricesResult.setValue("Todos los campos son necesarios");
            return;
        }

        if (zone.isEmpty()) {
            gasPricesResult.setValue("La zona es requerida");
            return;
        }

        if (type.isEmpty()) {
            gasPricesResult.setValue("El tipo de combustible es requerido");
            return;
        }


        apiService.getPrices(zone, type).enqueue(new Callback<List<PricesRequest>>() {
            @Override
            public void onResponse(Call<List<PricesRequest>> call, Response<List<PricesRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String results = response.body().stream().map(body ->
                            body.getFranchise() + " ---> " + body.getPrice() + "\n"
                    ).collect(Collectors.joining());
                    gasPricesResult.setValue("OK");
                    result.setValue(results);
                } else {
                    gasPricesResult.setValue("ERROR:No se pudo buscar la informacion");
                }
            }

            @Override
            public void onFailure(Call<List<PricesRequest>> call, Throwable t) {
                gasPricesResult.setValue("No internet connection");
            }
        });
    }
}
