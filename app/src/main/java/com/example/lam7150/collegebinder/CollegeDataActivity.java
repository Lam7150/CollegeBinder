package com.example.lam7150.collegebinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.List;

public class CollegeDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_data);
        Intent intent = getIntent();
    }
}
