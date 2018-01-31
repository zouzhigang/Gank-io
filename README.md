### 干货集中营Android版APP

### App所有api来自gank.io,非常感谢！

#### 技术点：

* 使用RecyclerView列表。可参考：http://www.jianshu.com/p/a6f158d1a9c9

* 网络请求使用Retrofit。

* 图片放大使用第三方库photoview。

* 图片加载使用glide。

* 使用Rxjava，Rxandroid进行异步操作。

#### build.gradle

```
    compile 'com.squareup.retrofit2:retrofit:2.0.1'
    compile 'com.squareup.retrofit2:converter-gson:2.0.1'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.1'
    compile 'io.reactivex:rxjava:1.1.3'
    compile 'io.reactivex:rxandroid:1.1.0'
```



#### 创建 Api.class

```
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


```

#### 使用Retrofit进行网络请求

```
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                
Api api = retrofit.create(Api.class);
Call<BaseModel<ArrayList<Benefit>>> call = api.defaultBenefits(20, page++);
 call.enqueue(new Callback<BaseModel<ArrayList<Benefit>>>() {
                         @Override
                         public void onResponse(Call<BaseModel<ArrayList<Benefit>>> call, Response<BaseModel<ArrayList<Benefit>>> response) {
                         //请求接口返回数据操作~
             
              }
        );

```
