package com.nyuprojectx.weather_test.data;

import org.json.JSONObject;

/**
 * Created by liyuanda on 2015/9/13.
 */
public interface JSONPopulator {
    void populate(JSONObject data);
    JSONObject toJSON();
}