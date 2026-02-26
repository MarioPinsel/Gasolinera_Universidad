package efm.gasolina.network;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}