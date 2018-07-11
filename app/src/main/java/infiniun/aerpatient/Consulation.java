package infiniun.aerpatient;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import data.model.Doctor;
import data.remote.APIService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consulation extends AppCompatActivity {
    Doctor doctor;
    TextView docName, contactNo, discription;
    Button startConsultaion;

    EditText illnessDescription;
    final Context context;
    private RequestQueue requestQueue;
    APIService mAPIService;

    public Consulation() {
        context = this;
    }

    String url = "http://50.23.0.202:31090/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        startConsultaion = (Button) findViewById(R.id.startConsultation);

        illnessDescription = (EditText) findViewById(R.id.messageInC);
        docName = (TextView) findViewById(R.id.consultatioID);
        contactNo = (TextView) findViewById(R.id.contactNo);
        discription = (TextView) findViewById(R.id.specilization);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle data = getIntent().getExtras();
        doctor = (Doctor) data.getParcelable("contact");
        docName.setText(doctor.getDocName());
        contactNo.setText(doctor.getContactNumber());
        discription.setText(doctor.getDescription());
        requestQueue = Volley.newRequestQueue(this);

        startConsultaion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                JSONObject consultation=new JSONObject();
                JSONObject jsonObject=new JSONObject();



                try {


                    jsonObject.put("cardid",LoginActivity.jsonObject.get("cardid"));
                    jsonObject.put("doctorID","1");
                    jsonObject.put("patientID",LoginActivity.jsonObject.get("patientid"));
                    //consultation.put("$class", "org.acme.Consultation");
                    jsonObject.put("consultationID", ""+new Random().nextInt(1000));
                    jsonObject.put("illnessDescription",illnessDescription.getText().toString());
                    jsonObject.put("message", illnessDescription.getText());
                    jsonObject.put("consultationCompleted", "false");

                    System.out.println("upe "+jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startConsultaiton(jsonObject);
            }
        });




    }
    public void startConsultaiton(JSONObject jsonObject){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SignupActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        RequestBody responseBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        apiService.beginConsultation(responseBody).enqueue(new Callback<RequestBody>() {
            Toast toast;
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                System.out.println("response code=" + response.code()+"response Message:"+response.message());

                if(response.isSuccessful()){
                    System.out.println("consutaltion submited successfuly");
                    toast = Toast.makeText(context, "consutaltion submited successfuly", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    toast = Toast.makeText(context, "response code=" + response.code()+"response Message:"+response.message(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                toast = Toast.makeText(context, "consutaltion submited successfuly", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println("response failure=" + t.getMessage());
                Consulation.super.onBackPressed();



            }
        });
    }
}
