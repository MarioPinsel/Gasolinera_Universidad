package efm.gasolina.network;
import java.util.Map;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.PasswordRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.TokenResponse;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);
    @POST("/sesion/forgotPassword")
    Call<TokenResponse> forgotPassword(@Body Map<String,String> request);

    @POST("/sesion/codeVerifier")
    Call<Void> codeVerifier(@Body Map<String,String> request);

    @POST("/sesion/changePassword")
    Call<Void> changePassword(@Body PasswordRequest request);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}