package efm.gasolina.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.User;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();

    public LoginViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void login(String correo, String password) {

        if (correo.isEmpty() || password.isEmpty()) {
            loginResult.setValue("ERROR:Ingresa correo y contraseña");
            return;
        }

        LoginRequest request = new LoginRequest(correo, password);
        // primero creas el objeto LoginRequest
        // luego se lo mandas a la API

        apiService.login(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call,
                                   Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String rol = response.body().getRole();
                    loginResult.setValue("OK:" + rol);
                } else if (response.code() == 401) {
                    loginResult.setValue("ERROR:Credenciales incorrectas");
                } else if (response.code() == 403) {
                    loginResult.setValue("ERROR:Tu cuenta está pendiente de aprobación");
                } else {
                    loginResult.setValue("ERROR:Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loginResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}