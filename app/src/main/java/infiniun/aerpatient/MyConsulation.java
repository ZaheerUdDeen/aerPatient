package infiniun.aerpatient;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import data.model.MyConsultation;
import data.remote.APIService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyConsulation extends AppCompatActivity {
    MyConsultation myConsultation;
    Button orderDrugs;

    TextView illnessDescription,consultationID,message,mdName,mdMG,mdDosage,procedure,drugDetails;
    final Context context;
    private RequestQueue requestQueue;
    APIService mAPIService;

    public MyConsulation() {
        context = this;
    }

    String url = "http://50.23.0.202:31090/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consulation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderDrugs = (Button) findViewById(R.id.shareDrugs);
        drugDetails = (TextView) findViewById(R.id.drugDetails);

        illnessDescription = (TextView) findViewById(R.id.IllnesDecription);
        consultationID = (TextView) findViewById(R.id.consultationIDMC);
        message = (TextView) findViewById(R.id.messageInC);
        procedure = (TextView) findViewById(R.id.procedure);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle data = getIntent().getExtras();
        myConsultation = (MyConsultation) data.getParcelable("consultation");

        System.out.println("Here hiiii"+myConsultation.getConsultationID());
        illnessDescription.setText(myConsultation.getIllnessDescription());
        consultationID.setText(myConsultation.getConsultationID());
        procedure.setText(myConsultation.getProcedure());
        message.setText(myConsultation.getMessage());
        System.out.println("hello11 :"+myConsultation.getMedName());

        drugDetails.setText("Medicine :"+myConsultation.getMedName()+"\nMg :"+myConsultation.getMedMg()+"\nUsage :"+myConsultation.getMedName());

        requestQueue = Volley.newRequestQueue(this);

        orderDrugs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                JSONObject consultation=new JSONObject();
                JSONObject jsonObject=new JSONObject();

                try {

                    jsonObject.put("patientID",LoginActivity.jsonObject.get("patientID"));

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
                MyConsulation.super.onBackPressed();



            }
        });
    }
}
