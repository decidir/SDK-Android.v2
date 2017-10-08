package com.android.decidir.sdk.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by biandra on 06/07/16.
 */
public class DecidirConfiguration {

    static private final String version = "0.1.4";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String MAX_AGE_0 = "max-age=0";
    public static final String USER_AGENT = "User-Agent";
    public static final String DECIDIR_JAVA_SDK_V = "Decidir Java SDK v ";
    public static final String APIKEY = "apikey";


    public static <T> T initRetrofit(final String secretAccessToken, final String apiUrl, final Integer timeOut, final Class<T> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS);

        httpClient.networkInterceptors().add(new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .header(CACHE_CONTROL, MAX_AGE_0)
                        .header(APIKEY, secretAccessToken)
                        //.header("X-Consumer-Username", secretAccessToken + "_public")
                        .header(USER_AGENT, getUserAgent())
                        .build();

                return chain.proceed(request);
            }
        });

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }

    static private String getUserAgent() {
        return DECIDIR_JAVA_SDK_V + version;
    }

}
