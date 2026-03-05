package efm.gasolina.network;
import java.util.List;
import java.util.Map;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.PasswordRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.TokenResponse;
import efm.gasolina.model.User;
import efm.gasolina.model.consults.PricesRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);
    @POST("/sesion/forgotPassword")
    Call<TokenResponse> forgotPassword(@Body Map<String,String> request);

    @POST("/sesion/codeVerifier")
    Call<Void> codeVerifier(@Body Map<String,String> request);

    @PATCH("/sesion/changePassword")
    Call<Void> changePassword(@Body PasswordRequest request);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("developer/pending")
    Call<List<User>> getPendingUsers();

    @PUT("developer/approve/{id}")
    Call<Void> approveUser(@Path("id") Long id);

    @PUT("developer/reject/{id}")
    Call<Void> rejectUser(@Path("id") Long id);

    @GET("/consult/prices/{zone}/{type}")
    Call<List<PricesRequest>> getPrices(@Path("zone") String zone, @Path("type") String type);

}