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
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class CollegeDataActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_data);

        Intent intent = getIntent();
        String school = intent.getStringExtra(("collegeName"));

        // General
        final TextView school_name = findViewById(R.id.name);
        final TextView location = findViewById(R.id.location);
        final TextView degrees_awarded_highest = findViewById(R.id.degrees_awarded_highest);
        final TextView locale = findViewById(R.id.locale);
        final TextView number_of_students = findViewById(R.id.number_of_students);
        final TextView admission_rate_overall = findViewById(R.id.admission_rate_overall);
        final TextView avg_net_price = findViewById(R.id.avg_net_price);

        // Admissions
        final TextView admission_rate_overall2 = findViewById(R.id.admission_rate_overall2);
        final TextView sat_overall = findViewById(R.id.sat_overall);
        final TextView sat_math = findViewById(R.id.sat_math);
        final TextView sat_reading = findViewById(R.id.sat_reading);
        final TextView sat_writing = findViewById(R.id.sat_writing);
        final TextView act_composite = findViewById(R.id.act_composite);

        // Finances
        final TextView median_earnings = findViewById(R.id.median_earnings);
        final TextView in_tuition = findViewById(R.id.in_tuition);
        final TextView out_tuition = findViewById(R.id.out_tuition);
        final TextView debt = findViewById(R.id.debt);
        final TextView fin_aid_percent = findViewById(R.id.fin_aid_percent);

        // Diversity
        final TextView percent_male = findViewById(R.id.percent_male);
        final TextView percent_female = findViewById(R.id.percent_female);
        final TextView percent_white = findViewById(R.id.percent_white);
        final TextView percent_asian = findViewById(R.id.percent_asian);
        final TextView percent_black = findViewById(R.id.percent_black);
        final TextView percent_other = findViewById(R.id.percent_other);
        final TextView percent_first_gen = findViewById(R.id.percent_first_gen);
        final TextView percent_undergrad = findViewById(R.id.percent_undergrad);
        final TextView percent_grad = findViewById(R.id.percent_grad);

        // Fun Facts
        TextView net_revenue = findViewById(R.id.net_revenue);
        TextView average_salary = findViewById(R.id.average_salary);


        /** Building API Call */
        StringBuilder APICall = new StringBuilder(200);
        String name = school;
        school_name.setText(name);
        String baseURL = "https://api.data.gov/ed/collegescorecard/v1/schools.json?api_key=";
        String apiKey = "XtwjKaILRJ3uITtYwTOHnlUeTHl3GEsRMfa73uFB";
        APICall.append(baseURL)
                .append(apiKey)
                .append("&school.name=")
                .append(name.replace("", "%20"));


        // Instantiate a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, APICall.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /** Getting latest data for college */
                            JSONObject collegeData = response;
                            String temp = collegeData.get("results").toString();
                            temp = temp.substring(1, temp.length() - 1);
                            collegeData = new JSONObject(temp);

                            /** JSONObjects for specific categories */
                            JSONObject latestInfo = collegeData.getJSONObject("latest");
                            JSONObject schoolInfo = collegeData.getJSONObject("school");
                            JSONObject studentInfo = latestInfo.getJSONObject("student");
                            JSONObject earningsInfo = latestInfo.getJSONObject("earnings");
                            JSONObject admissionsInfo = latestInfo.getJSONObject("admissions");
                            JSONObject SATInfo = admissionsInfo.getJSONObject("sat_scores");
                            JSONObject ACTInfo = admissionsInfo.getJSONObject("act_scores");

                             /** Location */
                             String locationText = schoolInfo.get("city").toString() + ", "
                             + schoolInfo.get("state").toString();
                             location.setText(locationText);

                             /** Highest Degree Awarded */
                             String degrees_awarded_highestText = schoolInfo.getJSONObject("degrees_awarded")
                             .get("highest").toString();
                             switch (degrees_awarded_highestText) {
                             case "0": degrees_awarded_highestText = "No degree"; break;
                             case "1": degrees_awarded_highestText = "Certificate"; break;
                             case "2": degrees_awarded_highestText = "Associate's"; break;
                             case "3": degrees_awarded_highestText = "Bachelor's"; break;
                             case "4": degrees_awarded_highestText = "Graduate"; break;
                             }
                            degrees_awarded_highest.setText(degrees_awarded_highestText);

                             /** Locale */
                             String localeText = schoolInfo.get("locale").toString();
                             switch (localeText) {
                             case "11": localeText = "Large City"; break;
                             case "12": localeText = "Midsize City"; break;
                             case "13": localeText = "Small City"; break;
                             case "21": localeText = "Large Suburbs"; break;
                             case "22": localeText = "Midsize Suburbs"; break;
                             case "23": localeText = "Small Suburbs"; break;
                             case "31": localeText = "Fringe Town"; break;
                             case "32": localeText = "Distant Town"; break;
                             case "33": localeText = "Remote Town"; break;
                             case "41": localeText = "Rural Fringe"; break;
                             case "42": localeText = "Rural Distant"; break;
                             case "43": localeText = "Rural Remote"; break;
                             }
                             locale.setText(localeText);

                             /** Number of students */
                             String number_of_studentsText  = String.valueOf((Integer) studentInfo.get("grad_students")
                             + (Integer) studentInfo.get("undergrads_with_pell_grant_or_federal_student_loan")
                             + (Integer) studentInfo.get("undergrads_non_degree_seeking"));
                             number_of_students.setText(number_of_studentsText);

                             /** Average net price */
                             Object avg_net_priceText = latestInfo.getJSONObject("cost")
                             .getJSONObject("avg_net_price")
                             .get("public");

                             if (avg_net_priceText == null) {
                             avg_net_priceText = latestInfo.getJSONObject("cost")
                             .getJSONObject("avg_net_price")
                             .get("private");
                             }

                             avg_net_priceText = (Integer) avg_net_priceText;
                             avg_net_price.setText(avg_net_priceText.toString());

                             /** Admission rate */
                             Double admission_rate_overallInt = (Double) admissionsInfo
                             .getJSONObject("admission_rate")
                             .get("overall") * 100;
                             String admission_rate_overallText = admission_rate_overallInt.toString() + "%";
                             admission_rate_overall.setText(admission_rate_overallText);
                             admission_rate_overall2.setText(admission_rate_overallText);

                             /** SAT Math */
                             Integer sat_math25 = (Integer) SATInfo
                             .getJSONObject("25th_percentile")
                             .get("math");

                             Integer sat_math75 = (Integer) SATInfo
                             .getJSONObject("75th_percentile")
                             .get("math");

                             String sat_mathText = sat_math25.toString() + " - " + sat_math75.toString();
                             sat_math.setText(sat_mathText);

                             /** SAT Reading */
                             Integer sat_reading25 = (Integer) SATInfo
                             .getJSONObject("25th_percentile")
                             .get("critical_reading");

                             Integer sat_reading75 = (Integer) SATInfo
                             .getJSONObject("75th_percentile")
                             .get("critical_reading");

                             String sat_readingText = sat_reading25.toString() + " - " + sat_reading75.toString();
                             sat_reading.setText(sat_readingText);

                             /** SAT writing */
                             Integer sat_writing25 = (Integer) SATInfo
                             .getJSONObject("25th_percentile")
                             .get("writing");

                             Integer sat_writing75 = (Integer) SATInfo
                             .getJSONObject("75th_percentile")
                             .get("writing");

                             String sat_writingText = sat_writing25.toString() + " - " + sat_writing75.toString();
                             sat_writing.setText(sat_writingText);

                             /** SAT Overall */
                             Integer sat_overall25 = sat_math25 + sat_reading25 + sat_writing25;
                             Integer sat_overall75 = sat_math75 + sat_reading75 + sat_writing75;
                             String sat_overallText = sat_overall25.toString() + " - " + sat_overall75.toString();
                             sat_overall.setText(sat_overallText);

                             /** ACT Overall */
                             Integer act_overall25 = (Integer) ACTInfo.getJSONObject("25th_percentile").get("cumulative");
                             Integer act_overall75 = (Integer) ACTInfo.getJSONObject("75th_percentile").get("cumulative");
                             String act_overallText = act_overall25.toString() + " - " + act_overall75.toString();
                             act_composite.setText(act_overallText);

                            /** Median Earnings */
                            String median_earningsText = earningsInfo.getJSONObject("6_yrs_after_entry")
                                    .get("median").toString();
                            median_earnings.setText(median_earningsText);

                            /** In-state tuition */
                            String in_tuitionText = latestInfo.getJSONObject("cost")
                                    .getJSONObject("tuition")
                                    .get("in_state").toString();
                            in_tuition.setText(in_tuitionText);

                            /** Out-of-state tuition */
                            String out_state_tuitionText = latestInfo.getJSONObject("cost")
                                    .getJSONObject("tuition")
                                    .get("out_of_state").toString();
                            out_tuition.setText(out_state_tuitionText);


                            /**  */
                            /**  */
                            /**  */
                            /**  */
                            /**  */
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("it didn't work");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
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
