package com.example.lam7150.collegebinder;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // intent to start activity from main activity
        Intent intent = getIntent();

        //setup search view and search bar
        ListView lv = (ListView) findViewById(R.id.listView);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Search Colleges/Institutions");

        //test strings for search implementation
        List<String> colleges = new ArrayList<>();
        colleges.add("University of Illinois Urbana-Champaign");
        colleges.add("Ohio State University");
        colleges.add("Purdue University");
        colleges.add("University of Illinois at Chicago");

        //Adapter
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_search, colleges);
        lv.setAdapter(adapter);

        //search bar text change listener
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //search filter
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //list view item clicked
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchActivity.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewData(View view) {
        startActivity(new Intent(SearchActivity.this, CollegeDataActivity.class));
    }
}
