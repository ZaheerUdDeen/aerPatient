package infiniun.aerpatient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import data.model.Doctor;

/**
 * Created by Zephyr on 3/17/2018.
 */

public class StockListAdapter extends ArrayAdapter<Doctor> {


    Context context;
    public StockListAdapter(@NonNull Context context, ArrayList<Doctor> resource) {
        super(context, R.layout.doctor_list,resource);
        this.context=context;

    }

    TextView docName;
    TextView contactNo,description;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("after load anachaihye");

        System.out.println("hereStockList");
        LayoutInflater changeListView=LayoutInflater.from(getContext());
        View customView=changeListView.inflate(R.layout.doctor_list,parent,false);

        docName =(TextView)customView.findViewById(R.id.consultatioID);
        contactNo =(TextView)customView.findViewById(R.id.contactNo);
        description =(TextView)customView.findViewById(R.id.description);


        docName.setText(getItem(position).getDocName());
        contactNo.setText(getItem(position).getContactNumber());
        description.setText(getItem(position).getDescription());


        return  customView;
    }



}
