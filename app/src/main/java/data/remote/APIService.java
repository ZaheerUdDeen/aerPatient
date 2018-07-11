package data.remote;

import data.model.postDoctor;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Zephyr on 5/30/2018.
 */

public interface APIService {

        @Headers("Content-Type: application/json")
        @POST("Doctor/")
        Call<RequestBody> addDoctors(@Body RequestBody requestBody);

        @Headers("Content-Type: application/json")
        @GET("Doctor/")
        Call<postDoctor> getDoctors(@Body postDoctor body);

        @Headers("Content-Type: application/json")
        @POST("Consultation/")
        Call<ResponseBody> startConsultaion(@Body RequestBody requestBody);

        @Headers("Content-Type: application/json")
        @POST("registerPatient/")
        Call<ResponseBody> addPatient(@Body RequestBody requestBody);
        @Headers("Content-Type: application/json")
        @POST("patientData/")
        Call<ResponseBody> getPatient(@Body RequestBody requestBody);

        @Headers("Content-Type: application/json")
        @POST("beginConsultation/")
        Call<RequestBody> beginConsultation(@Body RequestBody requestBody);

        @Headers("Content-Type: application/json")
        @POST("patientConsultation/")
        Call<ResponseBody> patientConsultation(@Body RequestBody requestBody);
}
