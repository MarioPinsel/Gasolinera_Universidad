package efm.gasolina.ui.recover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeVerifierViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public CodeVerifierViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void sendCode(String codigo) {

        if (codigo.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa codigo para cambiar contraseña");
            return;
        }

        String request = codigo;
        // primero creas el objeto LoginRequest
        // luego se lo mandas a la API

        apiService.codeVerifier(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful() ) {
                    requestRecoverResult.setValue("OK");
                } else if (response.code() == 404) {
                    requestRecoverResult.setValue("ERROR:Correo no encontrado");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}
