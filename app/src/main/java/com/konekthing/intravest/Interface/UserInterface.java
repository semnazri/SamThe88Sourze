package com.konekthing.intravest.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/29/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public interface UserInterface {

    @GET("auth")
    Call<ResponseBody> getAuthorize();

}
