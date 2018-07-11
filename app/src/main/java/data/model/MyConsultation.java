package data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zephyr on 3/6/2018.
 */

public class MyConsultation implements Parcelable {






    private String illnessDescription;
    private String consultationID;


    private String procedure;
    private String message;
    private String consultationCompleted;



    private String medMg;
    private String medDosag;



    private String medName;


    private String doctor;

    public MyConsultation(String consultationID, String consultationCompleted, String message, String illnessDescription, String procedure,String medName,String medMg,String medDosag){
        this.consultationID =consultationID;
        this.message = message;
        this.illnessDescription=illnessDescription;
        this.consultationCompleted = consultationCompleted;
        this.procedure = procedure;
        this.medName = medName;
        this.medMg = medMg;
        this.medDosag = medDosag;

    }
    public String getMedName() {
        return medName;
    }
    public String getMedMg() {
        return medMg;
    }
    public String getMedDosag() {
        return medDosag;
    }
    public String getMessage() {
        return message;
    }
    public String getProcedure() {
        return procedure;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getConsultationCompleted() {
        return this.consultationCompleted;
    }
    public String getDoctor() {return this.doctor;}

    public String getIllnessDescription() {
        return illnessDescription;
    }

    public void setIllnessDescription(String illnessDescription) {this.illnessDescription = illnessDescription;}


    public String getConsultationID(){
        return this.consultationID;
    }


    public String toString(){
        return consultationID;
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(consultationID);
        out.writeString(message);
        out.writeString(consultationCompleted);
        out.writeString(doctor);
        out.writeString(illnessDescription);
        out.writeString(procedure);
        out.writeString(medName);
        out.writeString(medMg);
        out.writeString(medMg);


    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<MyConsultation> CREATOR = new Creator<MyConsultation>() {
        public MyConsultation createFromParcel(Parcel in) {

            return new MyConsultation(in);
        }

        public MyConsultation[] newArray(int size) {
            return new MyConsultation[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    public MyConsultation(Parcel in) {
        consultationID = in.readString();
        message = in.readString();
        consultationCompleted =in.readString();
        doctor =in.readString();
        illnessDescription=in.readString();

        procedure=in.readString();

        medMg=in.readString();
        medDosag=in.readString();
        medName=in.readString();

    }
}
