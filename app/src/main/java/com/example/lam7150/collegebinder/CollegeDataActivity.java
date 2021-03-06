package com.example.lam7150.collegebinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.text.DecimalFormat;
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

        Intent in = getIntent();
        String school = in.getStringExtra(("collegeName"));

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

        // Fun Facts
        final TextView net_revenue = findViewById(R.id.net_revenue);
        final TextView average_salary = findViewById(R.id.average_salary);

        /** Building API Call */
        StringBuilder APICall = new StringBuilder(200);
        String name = school;
        school_name.setText(name);
        String baseURL = "https://api.data.gov/ed/collegescorecard/v1/schools.json?api_key=";
        String apiKey = "XtwjKaILRJ3uITtYwTOHnlUeTHl3GEsRMfa73uFB";
        APICall.append(baseURL)
                .append(apiKey)
                .append("&school.name=")
                .append(name.replace(" ", "%20"));

        /** Decimal Format */
        final DecimalFormat df = new DecimalFormat("#.##");

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
                            try {
                                String locationText = schoolInfo.get("city").toString() + ", "
                                        + schoolInfo.get("state").toString();
                                if (locationText.equals("null")) {
                                    locationText = "no data";
                                }
                                location.setText(locationText);
                            } catch (Exception error) {
                                location.setText("no data");
                                error.printStackTrace();
                            }

                             /** Highest Degree Awarded */
                             try {
                                 String degrees_awarded_highestText = schoolInfo.getJSONObject("degrees_awarded")
                                         .get("highest").toString();
                                 switch (degrees_awarded_highestText) {
                                     case "0": degrees_awarded_highestText = "No degree"; break;
                                     case "1": degrees_awarded_highestText = "Certificate"; break;
                                     case "2": degrees_awarded_highestText = "Associate's"; break;
                                     case "3": degrees_awarded_highestText = "Bachelor's"; break;
                                     case "4": degrees_awarded_highestText = "Graduate"; break;
                                 }
                                 if (degrees_awarded_highestText.equals("null")) {
                                     degrees_awarded_highestText = "no data";
                                 }
                                 degrees_awarded_highest.setText(degrees_awarded_highestText);
                             } catch (Exception error) {
                                 degrees_awarded_highest.setText("no data");
                                 error.printStackTrace();
                             }

                             /** Locale */
                             try {
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
                                 if (localeText.equals("null")) {
                                     localeText = "no data";
                                 }
                                 locale.setText(localeText);
                             } catch (Exception error) {
                                 locale.setText("no data");
                                 error.printStackTrace();
                             }

                             /** Number of students */
                             try {
                                 String number_of_studentsText = studentInfo.getJSONObject("enrollment")
                                         .get("all").toString();
                                 if (number_of_studentsText.equals("null")) {
                                     number_of_studentsText = "no data";
                                 }
                                 number_of_students.setText(number_of_studentsText);
                             } catch (Exception error) {
                                 number_of_students.setText("no data");
                                 error.printStackTrace();
                             }

                             /** Average net price */
                             try {
                                 Object avg_net_priceText = latestInfo.getJSONObject("cost")
                                         .getJSONObject("avg_net_price")
                                         .get("public");

                                 if (avg_net_priceText == null) {
                                     avg_net_priceText = latestInfo.getJSONObject("cost")
                                             .getJSONObject("avg_net_price")
                                             .get("private");
                                 }

                                 if (avg_net_priceText.toString().equals("null")) {
                                     avg_net_price.setText("no data");
                                 } else {
                                     avg_net_price.setText("$" + avg_net_priceText.toString());
                                 }
                             } catch (Exception error) {
                                 avg_net_price.setText("no data");
                                 error.printStackTrace();
                             }

                             /** Admission rate */
                             try {
                                 Double admission_rate_overallInt = (Double) admissionsInfo
                                         .getJSONObject("admission_rate")
                                         .get("overall") * 100;
                                 String admission_rate_overallText = admission_rate_overallInt.toString() + "%";
                                 if (admission_rate_overallText.equals("null")) {
                                     admission_rate_overallText = "no data";
                                 }
                                 admission_rate_overall.setText(admission_rate_overallText);
                                 admission_rate_overall2.setText(admission_rate_overallText);
                             } catch (Exception error) {
                                 admission_rate_overall.setText("no data");
                                 admission_rate_overall2.setText("no data");
                                 error.printStackTrace();
                             }


                             /** SAT Math */
                             try {
                                 Integer sat_math25 = (Integer) SATInfo
                                         .getJSONObject("25th_percentile")
                                         .get("math");

                                 Integer sat_math75 = (Integer) SATInfo
                                         .getJSONObject("75th_percentile")
                                         .get("math");

                                 String sat_mathText = sat_math25.toString() + " - " + sat_math75.toString();
                                 if (sat_mathText.equals("null")) {
                                     sat_mathText = "no data";
                                 }
                                 sat_math.setText(sat_mathText);
                             } catch (Exception error) {
                                 sat_math.setText("no data");
                                 error.printStackTrace();
                             }


                             /** SAT Reading */
                             try {
                                 Integer sat_reading25 = (Integer) SATInfo
                                         .getJSONObject("25th_percentile")
                                         .get("critical_reading");

                                 Integer sat_reading75 = (Integer) SATInfo
                                         .getJSONObject("75th_percentile")
                                         .get("critical_reading");

                                 String sat_readingText = sat_reading25.toString() + " - " + sat_reading75.toString();
                                 if (sat_readingText.equals("null")) {
                                     sat_readingText = "no data";
                                 }
                                 sat_reading.setText(sat_readingText);
                             } catch (Exception error) {
                                 sat_reading.setText("no data");
                                 error.printStackTrace();
                             }

                             /** SAT writing */
                            try {
                                Integer sat_writing25 = (Integer) SATInfo
                                        .getJSONObject("25th_percentile")
                                        .get("writing");

                                Integer sat_writing75 = (Integer) SATInfo
                                        .getJSONObject("75th_percentile")
                                        .get("writing");

                                String sat_writingText = sat_writing25.toString() + " - " + sat_writing75.toString();
                                if (sat_writingText.equals("null")) {
                                    sat_writingText = "no data";
                                }
                                sat_writing.setText(sat_writingText);
                            } catch (Exception error) {
                                sat_writing.setText("no data");
                                error.printStackTrace();
                            }

                             /** SAT Overall */
                            try {
                                Integer sat_math25 = (Integer) SATInfo
                                        .getJSONObject("25th_percentile")
                                        .get("math");

                                Integer sat_math75 = (Integer) SATInfo
                                        .getJSONObject("75th_percentile")
                                        .get("math");

                                Integer sat_reading25 = (Integer) SATInfo
                                        .getJSONObject("25th_percentile")
                                        .get("critical_reading");

                                Integer sat_reading75 = (Integer) SATInfo
                                        .getJSONObject("75th_percentile")
                                        .get("critical_reading");

                                Integer sat_writing25 = (Integer) SATInfo
                                        .getJSONObject("25th_percentile")
                                        .get("writing");

                                Integer sat_writing75 = (Integer) SATInfo
                                        .getJSONObject("75th_percentile")
                                        .get("writing");

                                Integer sat_overall25 = sat_math25 + sat_reading25 + sat_writing25;
                                Integer sat_overall75 = sat_math75 + sat_reading75 + sat_writing75;
                                String sat_overallText = sat_overall25.toString() + " - " + sat_overall75.toString();
                                if (sat_overallText.equals("null")) {
                                    sat_overallText = "no data";
                                }
                                sat_overall.setText(sat_overallText);
                            } catch (Exception error) {
                                sat_overall.setText("no data");
                                error.printStackTrace();
                            }

                            /** ACT Overall */
                            try {
                                Integer act_overall25 = (Integer) ACTInfo.getJSONObject("25th_percentile").get("cumulative");
                                Integer act_overall75 = (Integer) ACTInfo.getJSONObject("75th_percentile").get("cumulative");
                                String act_overallText = act_overall25.toString() + " - " + act_overall75.toString();
                                if (act_overallText.equals("null")) {
                                    act_overallText = "no data";
                                }
                                act_composite.setText(act_overallText);
                            } catch (Exception error) {
                                act_composite.setText("no data");
                                error.printStackTrace();
                            }

                            /** Median Earnings */
                            try {
                                String median_earningsText = earningsInfo.getJSONObject("6_yrs_after_entry")
                                        .get("median").toString();
                                if (median_earningsText.toString().equals("null")) {
                                    median_earnings.setText("no data");
                                } else {
                                    median_earnings.setText("$" + median_earningsText.toString());
                                }
                            } catch (Exception error) {
                                median_earnings.setText("no data");
                                error.printStackTrace();
                            }

                            /** In-state tuition */
                            try {
                                String in_tuitionText = latestInfo.getJSONObject("cost")
                                        .getJSONObject("tuition")
                                        .get("in_state").toString();
                                if (in_tuitionText.toString().equals("null")) {
                                    in_tuition.setText("no data");
                                } else {
                                    in_tuition.setText("$" + in_tuitionText.toString());
                                }
                            } catch (Exception error) {
                                in_tuition.setText("no data");
                                error.printStackTrace();
                            }

                            /** Out-of-state tuition */
                            try {
                                String out_state_tuitionText = latestInfo.getJSONObject("cost")
                                        .getJSONObject("tuition")
                                        .get("out_of_state").toString();
                                if (out_state_tuitionText.toString().equals("null")) {
                                    out_tuition.setText("no data");
                                } else {
                                    out_tuition.setText("$" + out_state_tuitionText.toString());
                                }
                            } catch (Exception error) {
                                out_tuition.setText("no data");
                                error.printStackTrace();
                            }

                            /** Median Debt */
                            try {
                                String median_debtText = latestInfo.getJSONObject("aid")
                                        .getJSONObject("median_debt")
                                        .getJSONObject("completers")
                                        .get("overall").toString();
                                if (median_debtText.toString().equals("null")) {
                                    debt.setText("no data");
                                } else {
                                    debt.setText("$" + median_debtText.toString());
                                }
                            } catch (Exception error) {
                                debt.setText("no data");
                                error.printStackTrace();
                            }


                            /** Financial Aid Rate */
                            try {
                                Double loanRate = (Double) latestInfo.getJSONObject("aid").get("federal_loan_rate") * 100;
                                String fin_aid_percentText = df.format(loanRate) + "%";
                                if (fin_aid_percentText.equals("null")) {
                                    fin_aid_percentText = "no data";
                                }
                                fin_aid_percent.setText(fin_aid_percentText);
                            } catch (Exception error) {
                                fin_aid_percent.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent Female */
                            try {
                                Double female = (Double) studentInfo.getJSONObject("demographics")
                                        .get("female_share") * 100;
                                String percent_femaleText = df.format(female) + "%";
                                percent_femaleText.length();
                                percent_female.setText(percent_femaleText);
                            } catch (Exception error) {
                                percent_female.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent Male */
                            try {
                                Double female = (Double) studentInfo.getJSONObject("demographics")
                                        .get("female_share") * 100;
                                String percent_maleText = df.format(100 - female) + "%";
                                if (percent_maleText.equals("null")) {
                                    percent_maleText = "no data";
                                }
                                percent_male.setText(percent_maleText);
                            } catch (Exception error) {
                                percent_male.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent White */
                            try {
                                Double white = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("white") * 100;
                                String percent_whiteText = df.format(white) + "%";
                                if (percent_whiteText.equals("null")) {
                                    percent_whiteText = "no data";
                                }
                                percent_white.setText(percent_whiteText);
                            } catch (Exception error) {
                                percent_white.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent Black */
                            try {
                                Double black = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("black") * 100;
                                String percent_blackText = df.format(black) + "%";
                                if (percent_blackText.equals("null")) {
                                    percent_blackText = "no data";
                                }
                                percent_black.setText(percent_blackText);
                            } catch (Exception error) {
                                percent_black.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent Asian */
                            try {
                                Double asian = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("asian") * 100;
                                String percent_asianText = df.format(asian) + "%";
                                if (percent_asianText.equals("null")) {
                                    percent_asianText = "no data";
                                }
                                percent_asian.setText(percent_asianText);
                            } catch (Exception error) {
                                percent_asian.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent Other */
                            try {
                                Double white = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("white") * 100;

                                Double black = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("black") * 100;

                                Double asian = (Double) studentInfo
                                        .getJSONObject("demographics")
                                        .getJSONObject("race_ethnicity")
                                        .get("asian") * 100;

                                String percent_OtherText = df.format(100 - white - asian - black) + "%";
                                if (percent_OtherText.equals("null")) {
                                    percent_OtherText = "no data";
                                }
                                percent_other.setText(percent_OtherText);
                            } catch (Exception error) {
                                percent_other.setText("no data");
                                error.printStackTrace();
                            }

                            /** Percent first-gen */
                            try {
                                Double first_gen = (Double) studentInfo
                                        .get("share_firstgeneration") * 100;
                                String percent_first_genText = df.format(first_gen) + "%";
                                if (percent_first_genText.equals("null")) {
                                    percent_first_genText = "no data";
                                }
                                percent_first_gen.setText(percent_first_genText);
                            } catch (Exception error) {
                                percent_first_gen.setText("no data");
                                error.printStackTrace();
                            }

                            /** Net revenue per student */
                            try {
                                String net_revenueText = "$" + schoolInfo.get("tuition_revenue_per_fte").toString();
                                if (net_revenueText.equals("$null")) {
                                    net_revenueText = "no data";
                                }
                                net_revenue.setText(net_revenueText);
                            } catch (Exception error) {
                                net_revenue.setText("no data");
                                error.printStackTrace();
                            }

                            /** Average salary faculty */
                            try {
                                String average_salaryText = "$" + schoolInfo.get("faculty_salary").toString();
                                if (average_salaryText.equals("$null")) {
                                    average_salaryText = "no data";
                                }
                                average_salary.setText(average_salaryText);
                            } catch (Exception error) {
                                average_salary.setText("no data");
                                error.printStackTrace();
                            }

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
