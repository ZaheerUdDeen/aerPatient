package infiniun.aerpatient;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import data.model.MyConsultation;
import data.remote.APIService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static infiniun.aerpatient.MainActivity.listView1;

/**
 * Created by Zephyr on 5/27/2018.
 */

public class MyConsultationController extends Fragment {

    TextView docName,docContact;
    ArrayList<MyConsultation> consultations_List=new ArrayList<>();
    View rootView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView= inflater.inflate(R.layout.my_consultation_fragment, container, false);
        context=this.getActivity();

            loadConsultationForPatient(LoginActivity.jsonObject);

        ListAdapter listAdapter=new MyConsultationAdapter(context,consultations_List);

        listView1=(ListView) rootView.findViewById(R.id.doctorList);

        listView1.setAdapter(listAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent consultaion=new Intent(context,MyConsulation.class);
                consultaion.putExtra("consultation", consultations_List.get(position));
                startActivity(consultaion);
           }
           });



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultations_List.clear();
                loadConsultationForPatient(LoginActivity.jsonObject);
                Snackbar.make(view, "Loading available doctors...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return  rootView;
    }



    MyConsultation consultation;
    public void getConsultation(JSONObject json){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SignupActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        RequestBody responseBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
        apiService.patientConsultation(responseBody).enqueue(new Callback<ResponseBody>() {
            Toast toast;
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                System.out.println("response code=" + response.code()+"response :"+response);

                if(response.isSuccessful()){
                    try {
                        JSONArray consultationList=new JSONArray( response.body().string());
                        System.out.println("Consultation :"+consultationList.toString());

                        for (int i=0;i<consultationList.length();i++){
                            JSONObject jsonObject=(JSONObject)consultationList.get(i);
                            JSONArray medicine=jsonObject.getJSONArray("medicine");
                            JSONObject medObject=(JSONObject)medicine.get(i);



                            consultation=new MyConsultation(jsonObject.getString("consultationID"),jsonObject.getString("consultationCompleted"),jsonObject.getString("message"),jsonObject.getString("illnessDescription"),jsonObject.getString("procedure"),medObject.getString("name"),medObject.getString("mg"),medObject.getString("amount"));
                            System.out.println("hello :"+consultation.getProcedure());

                            consultations_List.add(consultation);

                            listView1.invalidateViews();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    toast = Toast.makeText(context, "Patient LogIn successfuly", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    toast = Toast.makeText(context, "response code=" + response.code()+"response Message: "+response.message(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toast = Toast.makeText(context, "doctor added failure", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println("response failure=" + t.getMessage());

            }
        });
    }





    private void loadConsultationForPatient(final JSONObject json) {
        final Context context=this.getActivity();
        final ProgressBar view = new ProgressBar(context);
        view.setBackgroundColor(0x7f000000);
        final WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.CENTER;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;

        new AsyncTask<Integer, Integer, Integer>() {
            public void onPreExecute() {
                // init your dialog here;
                getActivity().getWindowManager().addView(view, windowParams);
                System.out.println("load Doctor");
            }

            public void onPostExecute(Integer result) {
                getActivity().getWindowManager().removeView(view);
                // process result;
            }

            @Override
            protected Integer doInBackground(Integer... arg0) {
                // do your things here
                getConsultation(json);

                return null;
            }
        }.execute();
    }
}
