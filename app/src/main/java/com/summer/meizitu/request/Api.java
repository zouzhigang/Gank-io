package com.summer.meizitu.request;


import com.summer.meizitu.model.BaseModel;
import com.summer.meizitu.model.Benefit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface Api {
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> defaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/拓展资源/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> getExpanding(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/Android/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> AndroiddefaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/iOS/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> iOSdefaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/App/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> getAppdefaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/休息视频/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> getRestVideo(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );
}
