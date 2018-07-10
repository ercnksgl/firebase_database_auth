package com.agriculture.ek.agriculture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kwabenaberko.openweathermaplib.Lang;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    JSONObject data = null;
private static final String OPEN_WEATHER_MAP_API_KEY="dcc2a377d08e502ede67753400bec71b";
private static String TAG="HAVADURUMU";
private int day_which=0;//first day
    TextView desc;
    EditText day_edt;
    EditText city_edt;
    Button btn_show_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_show_weather=findViewById(R.id.activity_weather_show_btn);
        desc=findViewById(R.id.activity_weather_desc_txt);
        day_edt=findViewById(R.id.activity_weather_take_day_count_edt);
        city_edt=findViewById(R.id.activity_weather_city_edt);
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
        btn_show_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON(city_edt.getText().toString().trim());
                if(Integer.parseInt(day_edt.getText().toString().trim())<5) {
                    day_which = Integer.parseInt(day_edt.getText().toString().trim());
                }else{
                    day_which=0;
                }

            }
        });


    }
        @SuppressLint("StaticFieldLeak")
        public void getJSON(final String city) {

            OpenWeatherMapHelper helper = new OpenWeatherMapHelper();
            helper.setApiKey(OPEN_WEATHER_MAP_API_KEY);
            helper.setUnits(Units.METRIC);//C'
            helper.setLang(Lang.TURKISH);

            helper.getThreeHourForecastByCityName(city, new OpenWeatherMapHelper.ThreeHourForecastCallback() {
                @Override
                public void onSuccess(ThreeHourForecast threeHourForecast) {
                   desc.setText(
                            "City/Country: "+ threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry() +"\n"
                                    +"Forecast Array Count: " + threeHourForecast.getCnt() +"\n"
                                    //For this example, we are logging details of only the first forecast object in the forecasts array
                                    +"First Forecast Date Timestamp: " + threeHourForecast.getThreeHourWeatherArray().get(day_which).getDt() +"\n"
                                    +"Hava Durumu: " + threeHourForecast.getThreeHourWeatherArray().get(0).getWeatherArray().get(0).getDescription()+ "\n"
                                    +"Sıcaklık: " + threeHourForecast.getThreeHourWeatherArray().get(day_which).getMain().getTempMax()+"\n"
                                    +"Rüzgar Hızı: " + threeHourForecast.getThreeHourWeatherArray().get(day_which).getWind().getSpeed() + "\n"
                    );
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.v(TAG, throwable.getMessage());
                }
            });

        }
}
