package com.rezwan502.app_wordman.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DefinitionInterface {

    @GET("entries/en/{word}")
    Call<List<Example>> getDefinitions(@Path("word") String word);
}
