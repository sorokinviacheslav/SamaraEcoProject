package com.magenta.samara.eco.core;

import com.magenta.samara.eco.entity.Address;
import com.magenta.samara.eco.entity.Building;
import com.magenta.samara.eco.entity.Point;
import com.magenta.samara.eco.entity.Street;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Component(GeoJsonTools.NAME)
public class GeoJsonTools {
    public static final String NAME = "eco_GeoJsonTools";

    public JSONObject getGeoJson(String params) throws IOException, JSONException {
        params = URLEncoder.encode(params,"utf-8");
        try (InputStream is = new URL("http://search.maps.sputnik.ru/search/addr?q="+params).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public boolean checkJsonFullMatch(JSONObject json) {
        JSONArray array = (JSONArray)json.getJSONObject("result").get("address");
        json = (JSONObject)array.get(0);
        json = (JSONObject) ((JSONObject)json.getJSONArray("features").get(0)).get("properties");
        if(!json.getBoolean("full_match")) return false;
        return true;
    }

    public JSONArray getCoordinatesAsArray(JSONObject json) {
        JSONArray array = (JSONArray)json.getJSONObject("result").get("address");
        json = (JSONObject)array.get(0);
        json = (JSONObject) ((JSONObject)json.getJSONArray("features").get(0)).get("geometry");
        return ((JSONObject)json.getJSONArray("geometries").get(0)).getJSONArray("coordinates");
    }


    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}