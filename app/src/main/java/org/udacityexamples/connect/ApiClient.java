package org.udacityexamples.connect;

import android.app.Application;

import com.laimiux.rxnetwork.RxNetwork;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shawn Li on 2/1/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = buildClient(application);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient buildClient(final Application application) {

        Cache cache = new Cache(application.getCacheDir(), 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        try {
                                        if (!RxNetwork.getConnectivityStatus(application.getApplicationContext()))
                                            getCachedResponse(chain);
                                            Request request = chain.request();
                                            return chain.proceed(request);
                                        } catch (Exception exception) {
                                            return getCachedResponse(chain);
                                        }

                                    }
                                }
                );
        /*try {
            cache.delete();
        }
        catch (IOException io) {
            io.printStackTrace();
        }*/
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return builder.build();
    }

    // http://stackoverflow.com/questions/35312917/okhttp3-max-stale-cache
    private static Response getCachedResponse(Interceptor.Chain chain) throws IOException {

        CacheControl cacheControl = new CacheControl
                .Builder()
                .onlyIfCached()
                .maxStale(5, TimeUnit.MINUTES).build();

        Request request = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build();

        Response forceCacheResponse = chain.proceed(request);
        return forceCacheResponse.newBuilder()
                .build();
    }
}


