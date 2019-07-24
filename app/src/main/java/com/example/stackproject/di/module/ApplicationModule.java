package com.example.stackproject.di.module;

import com.example.stackproject.BaseApplication;
import com.example.stackproject.data.rest.RepoService;


import java.io.IOException;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private static final String BASE_URL = "http://api.stackexchange.com/2.2/";

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(BaseApplication.getInstance().getCacheDir(), 10 * 1024 * 1024))
                .addInterceptor(provideOfflineCacheInterceptor())
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    static RepoService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(RepoService.class);
    }

    //Create offline Interceptor to fetch the data in Cache in offline mode.
    // The request is to show the error message hence, throwing network error message .
    //Can use CacheControl builder to fetch the stored data in Cache and show in App in offline mode.
    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();
                if (!BaseApplication.hasNetwork() )
                {
                    throw (new NoConnectivityException());
                }

                return chain.proceed( request );
            }
        };
    }
}
