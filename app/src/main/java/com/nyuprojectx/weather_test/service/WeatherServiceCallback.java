package com.nyuprojectx.weather_test.service;

import com.nyuprojectx.weather_test.data.Channel;

/**
 * Created by liyuanda on 2015/9/13.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}