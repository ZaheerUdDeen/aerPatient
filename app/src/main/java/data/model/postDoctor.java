package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Zephyr on 5/30/2018.
 */

public class postDoctor {
    @SerializedName("$class")
    @Expose
    private String $class;
    @SerializedName("doctorID")
    @Expose
    private String doctorID;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("description")
    @Expose
    private String description;

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "postDoctor{" +
                "$class='" + $class + '\'' +
                ", doctorID='" + doctorID + '\'' +
                ", contact='" + contact + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
