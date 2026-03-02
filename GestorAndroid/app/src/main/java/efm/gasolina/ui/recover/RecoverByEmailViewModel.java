package efm.gasolina.ui.recover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import efm.gasolina.model.TokenResponse;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import efm.gasolina.util.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverByEmailViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final TokenManager tokenManager;
    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public RecoverByEmailViewModel(@NonNull Application app) {
        super(app);
        apiService = ApiClient.getClient().create(ApiService.class);
        tokenManager = new TokenManager(app.getApplicationContext());
    }

    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void sendMail(String correo) {
        System.out.printf("SEND EMAIL");

        if (correo.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa correo para enviar el codigo");
            return;
        }
        Map request = new HashMap<String, String>();
        request.put("email", correo);
        apiService.forgotPassword(request).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call,
                                   Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    tokenManager.saveToken(token);
                    requestRecoverResult.setValue("OK");
                } else if (response.code() == 404) {
                    requestRecoverResult.setValue("ERROR:Correo no encontrado");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}

