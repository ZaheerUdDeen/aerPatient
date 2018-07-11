package infiniun.aerpatient;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 * Created by Zephyr on 5/30/2018.
 */

public interface Rest_Api {
    @FormUrlEncoded
    @POST("/loginSocial")
    public void loginSocial(


    );
}
