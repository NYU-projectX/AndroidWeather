package com.nyuprojectx.weather_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nyuprojectx.weather_test.data.Channel;
import com.nyuprojectx.weather_test.data.Item;
import com.nyuprojectx.weather_test.service.WeatherServiceCallback;
import com.nyuprojectx.weather_test.service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    static String[] items = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private EditText editText;
    private Button refreshWeather;
    private Button startNewActivity;
    private Button notificationButton;
    private Button clearButton;

    public final static String EXTRA_MESSAGE = "extra message";

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        refreshWeather = (Button)findViewById(R.id.refreshWeather);
        refreshWeather.setOnClickListener(new MyClickListener());
        startNewActivity = (Button)findViewById(R.id.newActivity);
        startNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, DisplayMessageActivity.class);
                EditText editText = (EditText) findViewById(R.id.inputLocation);
                String message;
                message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        String[] myItems = {"A", "B", "C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, myItems);
        ListView list = (ListView)findViewById(R.id.list1);
        list.setAdapter(adapter);

        notificationButton = (Button)findViewById(R.id.notification);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String[] newItems = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
                EditText inputLocation = (EditText)findViewById(R.id.inputLocation);
                String temp = inputLocation.getText().toString();
                populateTest(v, temp);
                ListView list = (ListView) findViewById(R.id.list1);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), R.layout.da_item, items);
                list.setAdapter(adapter);
            }
        });
        clearButton = (Button)findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, Login.class);
                startActivity(intent);
            }
        });
        service = new YahooWeatherService(this, getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("New York, NY");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawble = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawble);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(channel.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            editText = (EditText) findViewById(R.id.inputLocation);
            String newLocation = editText.getText().toString();

            TextView textview2disp = (TextView) findViewById(R.id.textViewToDisplay);
            textview2disp.setText(newLocation);
            service.refreshWeather(newLocation);
        }
    }

    private void populateTest(View v, String lastInput) {
        for (int i = items.length - 1; i > 0; i--) {
            items[i] = items[i - 1];
        }
        items[0] = lastInput;
        ListView list = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), R.layout.da_item, items);
        list.setAdapter(adapter);
    }
}