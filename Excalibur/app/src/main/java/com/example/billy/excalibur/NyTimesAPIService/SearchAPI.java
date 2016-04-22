package com.example.billy.excalibur.NyTimesAPIService;

import com.example.billy.excalibur.keys.Keys;

import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.ArticleSearch;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for api methods used by our app from NY Times
 * Created by michaelmuccio on 4/18/16.
 */

public interface SearchAPI {

    @GET("{source}/{section}/1.json?&api-key=" + Keys.newsWireKey)
    Call<NewsWireResults> listNewsWireResults(@Path("source") String source,
                                              @Path("section") String section,
                                              @Query("limit") int limit);

    @GET("articlesearch.json?&sort=newest&api-key=" + Keys.nyTimesFullSearchQueryKey)
    Call<ArticleSearch> listArticleSearchDocs(@Query("q") String q);

}
