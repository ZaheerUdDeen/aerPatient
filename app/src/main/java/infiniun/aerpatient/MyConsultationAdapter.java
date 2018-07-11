package infiniun.aerpatient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import data.model.MyConsultation;

/**
 * Created by Zephyr on 3/17/2018.
 */

public class MyConsultationAdapter extends ArrayAdapter<MyConsultation> {


    Context context;
    public MyConsultationAdapter(@NonNull Context context, ArrayList<MyConsultation> resource) {
        super(context, R.layout.my_consultation_list,resource);
        this.context=context;

    }

    TextView consultationID, message,consultationCompleted,procedure,illnessDescription;

    ImageView cStatus;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater changeListView=LayoutInflater.from(getContext());
        View customView=changeListView.inflate(R.layout.my_consultation_list,parent,false);

        cStatus=(ImageView) customView.findViewById(R.id.cStatus);
        consultationID =(TextView)customView.findViewById(R.id.consultationIDMC);
        message =(TextView)customView.findViewById(R.id.messageInC);
        illnessDescription =(TextView)customView.findViewById(R.id.IllnesDecription);
        procedure =(TextView)customView.findViewById(R.id.procedure);


        procedure.setText( getItem(position).getProcedure());
        consultationID.setText(getItem(position).getConsultationID());
        message.setText(getItem(position).getMessage());

        if(getItem(position).getConsultationCompleted().equals("true")){
            cStatus.setVisibility(View.VISIBLE);
        }
        illnessDescription.setText(getItem(position).getIllnessDescription());

        return  customView;
    }



}
