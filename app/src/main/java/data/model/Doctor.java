package data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zephyr on 3/6/2018.
 */

public class Doctor implements Parcelable {

    private String docName;


    private String docID;
    private String contactNumber;

    private String description;

    public Doctor(String brandName, String contactNumber,String description,String docID){
        this.docName =brandName;
        this.docID=docID;

        this.contactNumber = contactNumber;
        this.description = description;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
    public String getContactNumber() {
        return this.contactNumber;
    }
    public String getDescription() {
        return this.description;
    }



    public String getDocName(){
        return this.docName;
    }


    public String toString(){
        return docName;
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(docName);
        out.writeString(docID);
        out.writeString(contactNumber);
        out.writeString(description);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        public Doctor createFromParcel(Parcel in) {

            return new Doctor(in);
        }

        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    public Doctor(Parcel in) {
        docName = in.readString();
        docID = in.readString();
        contactNumber =in.readString();
        description=in.readString();
    }
}
