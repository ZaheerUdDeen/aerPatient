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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.model.Doctor;

import static infiniun.aerpatient.MainActivity.listView1;

/**
 * Created by Zephyr on 5/27/2018.
 */

public class DoctorController extends Fragment {

    TextView docName,docContact;
    ArrayList<Doctor> doctor_List=new ArrayList<>();
    View rootView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView= inflater.inflate(R.layout.doctor_fragment, container, false);
        context=this.getActivity();
        loadDoctor();

        ListAdapter listAdapter=new StockListAdapter(context,doctor_List);

        listView1=(ListView) rootView.findViewById(R.id.doctorList);

        listView1.setAdapter(listAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent consultaion=new Intent(context,Consulation.class);
                 consultaion.putExtra("contact", doctor_List.get(position));
                startActivity(consultaion);
           }
           });



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_List.clear();
                loadDoctor();
                Snackbar.make(view, "Loading available doctors...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return  rootView;
    }



    Doctor docList;
    public void getDoctors(){

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        String url ="http://50.23.0.202:31090/api/Doctor";
        final JSONObject json;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        try {
                            JSONArray doctorList = new JSONArray(response);

                            for (int i=0;i<doctorList.length();i++){
                                JSONObject jsonObject=(JSONObject)doctorList.get(i);
                                docList=new Doctor(jsonObject.getString("doctorName"),jsonObject.getString("contact"),jsonObject.getString("description"),jsonObject.getString("doctorID"));
                                doctor_List.add(docList);

                                listView1.invalidateViews();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void loadDoctor() {
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
                getDoctors();

                return null;
            }
        }.execute();
    }
}
