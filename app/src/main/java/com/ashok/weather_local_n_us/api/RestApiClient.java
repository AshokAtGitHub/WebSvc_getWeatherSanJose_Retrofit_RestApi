package com.ashok.weather_local_n_us.api;
import android.util.Log;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
/**
 * Created by Ashok on 5/22/2016.
 */
public class RestApiClient {
    private static RestApi_Interface m_RestApi_Interface;
    //private static final String BaseURL_ofJsonDataToFetch = "http://codeentries.com";
    private static final String BaseURL_forYahooWebSvcAPI = "https://query.yahooapis.com";
    private static final String TAG = "ak";

    static {//Initialization-block called only-once when class loaded;
        Log.i(TAG, "ApiClient:* This 'initialization-Block' gets called 'only once' when Class loaded");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL_forYahooWebSvcAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        m_RestApi_Interface = retrofit.create(RestApi_Interface.class);
    }//Initialization-Block
    //-----------------------------------------------------------
    public static RestApi_Interface getDataUsingRetrofitAndRestApiInterface() {
        //Log.i(TAG, "ApiClient:restApi_Interface_get();");
        return m_RestApi_Interface;
    }
    //-------------------------------------------------------------
}