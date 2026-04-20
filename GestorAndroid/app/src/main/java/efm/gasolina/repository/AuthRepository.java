package efm.gasolina.repository;

import efm.gasolina.model.auth.LoginRequest;
import efm.gasolina.model.auth.LoginResponse;
import efm.gasolina.model.auth.User;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;

public class AuthRepository {

    private final ApiService apiService;

    public AuthRepository() {
        apiService =
                ApiClient.getClient()
                        .create(ApiService.class);
    }

    public Call<Void> register(User user) {
        return apiService.registerUser(user);
    }

    public Call<LoginResponse> login(LoginRequest request) {
        return apiService.login(request);
    }
}