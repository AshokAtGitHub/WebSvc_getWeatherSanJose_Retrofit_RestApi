/* This app fetches  weather-data (temperature, cloudy-status) from Yahoo-Weather web svc.
       https://developer.yahoo.com/weather/
  That's it.
  To fetch above JSON data, this app uses Retrofit-2 Library and thus this App
  do NOT have to use (i) Http calls (ii) Async Task
  Refs for Retrofit exampels to get JSON data from remote websvc websites
    (1) http://codeentries.com/libraries/how-to-use-retrofit-2-in-android-the-example.html
  Ref for converting JSON data to Pojo file Pavel Dudka used AndroidWarrier.com web site info
  (ii)http://www.androidwarriors.com/2015/12/retrofit-20-android-example-web.html
 */
package com.ashok.weather_local_n_us;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ashok.weather_local_n_us.api.RestApiClient;
import com.ashok.weather_local_n_us.api.RestApi_Interface;
import com.ashok.weather_local_n_us.pojos.Channel;
import com.ashok.weather_local_n_us.pojos.Model;
import com.ashok.weather_local_n_us.pojos.NameAndId_pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//-----------------------------------------------------------------
public class MainActivity extends AppCompatActivity {
    String BaseURL_forYahooWebSvcAPI = "https://query.yahooapis.com";
    TextView mTxt_mainTitle_w_cityName, mTxt_temperatureLocal, mTxt_statusLocal, txt_humidity,
            txt_sunrise, txt_sunset;
    //Forcast variables
    TextView mTxt_titleFiveDayForcast_w_cityName;

    private ArrayList<ForecastItems>mArrayListFcastItems;//Array to store forcast items for 5 days
    private AdapterListView_Fcast mAdapter; //for binding each row of forcasst-data in Array to View
    Context mContextThisApp;
    String mCityName; //to find weather info
    private static final String TAG = "ak";
    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContextThisApp = getApplicationContext();

        //Get refs. for current weather condition to display on top
        mTxt_mainTitle_w_cityName = (TextView)findViewById(R.id.txt_titleLine_w_cityName);
        mTxt_temperatureLocal = (TextView)findViewById(R.id.txt_tempLocal);
        mTxt_statusLocal = (TextView)findViewById(R.id.txt_statusLocal);
        //txt_humidity = (TextView)findViewById(R.id.txt_humidity);
        //txt_sunrise = (TextView)findViewById(R.id.txt_sunrise);
        //txt_sunset = (TextView)findViewById(R.id.txt_sunset);

        //Forecast 5 days title
        mTxt_titleFiveDayForcast_w_cityName=(TextView)
                                    findViewById(R.id.txt_titleFivedaysForcast_w_cityName);
        //Array to store forcast for 5 days
        mArrayListFcastItems = new ArrayList<ForecastItems>();
        //set ListView Adapter for each row for our array for 5 days of Froecast data
        mAdapter = new AdapterListView_Fcast(mContextThisApp, mArrayListFcastItems);

        //get ref to listView for 5-days forecastdata; It is in main-XML file
        ListView listViewForForcastDataFor5Days = (ListView) findViewById(R.id.listVIew_inMain);
        //set AdapterForArrayList for out List View in main-XML file
        listViewForForcastDataFor5Days.setAdapter(mAdapter);
        mCityName = "San Jose, CA";//"San Jose, CA 95136";//("New York, NY");//("Miami, FL");
        getWeatherDataFromYahooWebService(mCityName);
    }
    //-----------------------------------------------------------------------
    void getWeatherDataFromYahooWebService(String cityName) {
        String queryStringForYahooWebSvc = yahooQueryLangFormat(cityName);
        //Call<Model> callRetrofit = restApiViaRetrofit.getWheatherReport(queryStrForYahooWebSvc);
        Call<Model> callRetrofit = RestApiClient.getDataUsingRetrofitAndRestApiInterface().
                                        queryIn_RestApi_Interface(queryStringForYahooWebSvc);
        //********
        callRetrofit.enqueue(new Callback<Model>() {
            //---------------------------------------------------------------------------------
            @Override public void onResponse(Call<Model> callNotUsed, Response<Model> response){
                try {
                    //get reponse-data from Yahoo-weather-Websvc. It has JSON format
                    Channel channel = response.body().getQuery().getResults().getChannel();

                    String cityLocal = channel.getLocation().getCity();
                    String temperatureLocal = channel.getItem().getCondition().getTemp();
                    String statusLocal = channel.getItem().getCondition().getText();
                    String forecastStatus_0 = channel.getItem().getForecast().get(0).getText();
                    statusLocal = forecastStatus_0;
                    //String humidity = channel.getAtmosphere().getHumidity();
                    //String sunriseTime = channel.getAstronomy().getSunrise();
                    //String sunsetTime = channel.getAstronomy().getSunset();
                    /****************************************************************
                     * get forecast from JSON-Array from Yahoo-Web-svc for ALL 5 days
                     ***************************************************************/
                    for (int i = 0; i < 5; i++){
                        String forecastDay = channel.getItem().getForecast().get(i).getDay();
                        forecastDay = centerString(forecastDay, 5);
                        if (i == 0)
                            forecastDay = "Today";
                        //Log.i(TAG, "Response():Day1="+forecastDay + " Len="+forecastDay_1.length());
                        String forecastHi = channel.getItem().getForecast().get(i).getHigh();
                        String forecastLo = channel.getItem().getForecast().get(i).getLow();
                        String forecastStatus = channel.getItem().getForecast().get(i).getText();
                        //forecastStatus_1 = makeIt_13_charLong(forecastStatus_2);
                        forecastStatus = centerString(forecastStatus, 13);
                        String forecastDate = channel.getItem().getForecast().get(i).getDate();
                        forecastDate = getMonth_n_day(forecastDate);
                        //Log.i(TAG, "Status_Len="+forecastStatus.length());
                        //Log.i(TAG, "Date="+forecastDate);
                        ForecastItems forecastItems = new ForecastItems(forecastDay,
                                forecastHi+ "\u00B0",
                                forecastLo+ "\u00B0",
                                forecastStatus,
                                forecastDate);

                        //add this forecastItems to ArralyList
                        mArrayListFcastItems.add(forecastItems);
                    }//for
                    mAdapter.notifyDataSetChanged();
                    //----------------------------------------------------------
                    //Display CURRENT weather in top
                    if (null != cityLocal)
                        mTxt_mainTitle_w_cityName.setText("Weather for " + cityLocal);
                    if (null != temperatureLocal)
                        mTxt_temperatureLocal.setText(temperatureLocal + "\u00B0");//for degree F "\u2109");
                    else
                        mTxt_temperatureLocal.setText("*Temp*");//for degree F "\u2109");
                    if (null != statusLocal)
                        mTxt_statusLocal.setText(forecastStatus_0);
                    else
                        mTxt_statusLocal.setText("*Status*");

                    //txt_humidity.setText("humidity  : " + humidity);

                    //txt_sunrise.setText("Sunrise time: " + sunriseTime);
                    //txt_sunset.setText("Sunset time: " + sunsetTime);

                    //Forecast-5-days-title-line
                    mTxt_titleFiveDayForcast_w_cityName.setText("Five days forecast for "+cityLocal);

                    //txt_subtitleHiLoStatusForecast.setText(stringToDisplay);

                } catch (Exception e) {
                    Log.i(TAG, "Error:Exception - onResponce()");
                    mTxt_mainTitle_w_cityName.setText("Sorry. Can not find weather data for " + mCityName);
                    e.printStackTrace();
                }
            }//onResponse()
            //---------------------------------------------------------
            @Override public void onFailure(Call<Model> call, Throwable t) {
                Log.e("test", "failure: ");
                Log.i(TAG, "**ERROR*:Response from Yahoo-Weather-Web-Service FAILED");
            }
        });
    }//getWeatherDataFromYahooWebSvc
    //--------------------------------------------------------------
    //Get only month and day (MAY 12) from full-date JAN 15, 2016
    String getMonth_n_day(String forecastDateString){
        StringBuilder month_n_day_SB = new StringBuilder(6);
        if (forecastDateString.length() < 6)
            return(forecastDateString) ;

        month_n_day_SB.append(forecastDateString.substring(3,6));
        month_n_day_SB.append(' ');
        month_n_day_SB.append(forecastDateString.substring(0,2));
        return month_n_day_SB.toString();
    }
    //-----------------------------------------------
    private static String centerString(String strIn, int totalLen){
        String strOut = String.format("%"+totalLen+"s%s%"+totalLen+"s", "",strIn,"");
        float mid = strOut.length() / 2;
        float start = mid - (totalLen/2);
        float end = start + totalLen;
        return strOut.substring((int)start, (int)end);
    }
    //------------------------------------------------
    String makeIt_13_charLong(String forecastStatus){
        if (forecastStatus.length() < 13){
            StringBuilder sb = new StringBuilder();
            sb.append(forecastStatus);
            int addNumberOfBlanks = 13 - sb.length();
            for (int i = 0; i <= addNumberOfBlanks; i++)
                sb.append(" ");
            return sb.toString();
        }
        if (forecastStatus.length() > 13){
            StringBuilder sb = new StringBuilder();
            sb.append(forecastStatus);
            int truncateEndChars = sb.length() - 13;
            sb.delete(13,sb.length());
            return sb.toString();
        }
        return (forecastStatus);
    }
    //-------------------------------------------------------------------
    public String yahooQueryLangFormat(String cityNameLocation) {
        /**************************************************
         This Query format taken from https://developer.yahoo.com/weather/
         select * from weather.forecast where woeid in (select woeid from geo.places(1) where text="nome, ak")
         **************************************************/
        return "select * from weather.forecast " +
                "where woeid in " +
                "(select woeid from geo.places(1) " +
                "where text= \" " + cityNameLocation + " \" )";
    }
    //--------------------------------------------------------------------
}