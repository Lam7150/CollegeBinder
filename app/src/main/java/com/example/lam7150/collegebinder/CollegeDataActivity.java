package com.example.lam7150.collegebinder;

import android.content.Intent;
import android.os.Bundle;

public class CollegeDataActivity extends SearchActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_data);
        Intent intent = getIntent();
    }
}
