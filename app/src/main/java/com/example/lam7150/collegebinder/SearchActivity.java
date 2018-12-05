package com.example.lam7150.collegebinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(message);

    }
    public void viewData(View view) {
        startActivity(new Intent(SearchActivity.this, CollegeDataActivity.class));
    }
}
