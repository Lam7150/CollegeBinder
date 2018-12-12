package com.example.lam7150.collegebinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.List;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;
import java.util.*;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class CollegeDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_data);
        Intent intent = getIntent();

        //TextView college = (TextView) findViewById(R.id.);
        //college.setText("LAMBO");

        /** Creating API Calls */
        String APICall = urlBuilder("school", new String[]{"name"}, 0);
        JSONObject collegeData = new JSONObject();


        try {
            for (int i = 0; i < 72; i++) {
                APICall = urlBuilder("school", new String[]{"name"}, i);
                Thread.sleep(100);
                /*String temp = collegeScorecard(APICall).get("results").toString();
                temp = temp.substring(1, temp.length() - 1);*/
                collegeData = collegeScorecard(APICall);
                JSONArray jsonArray = new JSONArray(collegeData.get("results").toString());
                Map[] schools = new HashMap[jsonArray.length()];
                for(int n = 0, objectCount = jsonArray.length(); n < objectCount; n++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(n);
                    schools[i] = toMap(jsonObject);
                }
            }

            //JSONToFile("colleges.json", collegeNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // testing output
        //System.out.println(collegeData.toString());

        /** Parsing JSON files
         JSONArray jsonArray = new JSONArray(collegeData.get("results").toString());
         Map[] schools = new HashMap[jsonArray.length()];

         /** Iterating through jsonArray */
        /*for(int i = 0, objectCount = jsonArray.length(); i < objectCount; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            schools[i] = toMap(jsonObject);
            System.out.println(schools[i].get("school.name"));
        }*/
    }

    public static JSONObject collegeScorecard(String url) throws Exception {
        /** Calling API */
        // Setting up connection
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Setting up HTML specifications
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Calling API and storing information stream
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();

        return new JSONObject(response.toString());
    }

    /**
     * Writes API Call to College Scorecard with inputted parameters
     *
     * @param category name of dataset category
     * @param fields names of fields
     * @param pageNumber page of API
     * @return URL to make API Call and retrieve information
     */
    public static String urlBuilder(String category, String fields[], int pageNumber) {
        /** Declaring URL components to be combined into API call */
        StringBuilder URL = new StringBuilder(120);
        String baseURL = "https://api.data.gov/ed/collegescorecard/v1/schools.json?api_key=";
        String apiKey = "XtwjKaILRJ3uITtYwTOHnlUeTHl3GEsRMfa73uFB";
        String pagination = "&_per_page=100";
        String page = "&_page=" + String.valueOf(pageNumber);

        /** Building base URL before fields */
        URL.append(baseURL)
                .append(apiKey)
                .append(pagination)
                .append(page);

        /** Adding fields to API Call */
        if (fields.length != 0) {
            URL.append("&_fields=");
            for (String field : fields) {
                URL.append(category)
                        .append(".")
                        .append(field)
                        .append(",");
            }

            // Erasing trailing comma
            URL.deleteCharAt(URL.length() - 1);
        }

        return URL.toString();
    }

    /**
     * Writes a given JSON Object to a file
     *
     * @param fileName name of file to be written to
     * @param object JSON Object to be transcribed
     * @throws Exception exceptions that may occur
     */
    public static void JSONToFile(String fileName, JSONObject object) throws Exception {
        /** Initializing file */
        File file = new File(fileName);
        file.createNewFile();

        /** Writing to file */
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(object.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * In conjunction with toList, converts a JSONObject to a Map recursively
     *
     * @param object JSONObject to be converted
     * @return Map of JSON objects
     * @throws JSONException exceptions that may arise
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * helper function for toMap
     *
     * @param array array of key values
     * @return list of key values
     * @throws JSONException exceptions that may arise
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
