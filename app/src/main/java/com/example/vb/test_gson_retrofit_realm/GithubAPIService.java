package com.example.vb.test_gson_retrofit_realm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by VB on 10.08.2017.
 */

public interface GithubAPIService {

    @GET("/users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);
}
