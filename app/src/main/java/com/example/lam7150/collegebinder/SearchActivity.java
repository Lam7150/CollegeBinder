package com.example.lam7150.collegebinder;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /** intent to start activity from main activity */
        Intent intent = getIntent();

        /**setup search view and search bar */
        ListView lv = (ListView) findViewById(R.id.listView);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Search Colleges/Institutions");


        /**Implementation of CSVFile.java to get array list of our colleges. */
        InputStream inputStream = getResources().openRawResource(R.raw.colleges);
        CSVFile csvFile = new CSVFile(inputStream);
        final List collegeList = csvFile.read();

        /** Adapter, allows us to carry information (in this case our collegeList)
         * and let it be useful and accessible to a layout (our activity_search.xml and content_main.xml)
         */
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collegeList);
        lv.setAdapter(adapter);




        /** search bar text change listener, narrows down our collegeList based on the search that is
         * being made in real time, since there are around 700 total string to consider. */
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
        /**
        lv.setSelected(true);
        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selected = (String) parent.getSelectedItem();
                Intent intent = new Intent(getApplicationContext(), CollegeDataActivity.class);
                intent.putExtra("collegeName", selected);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        }); */

        /** Allow any list_view item to be clicked, triggers the CollegeDataActivity when clicked */
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //TextView text = Toast.makeText(SearchActivity.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(SearchActivity.this, CollegeDataActivity.class));
                //String selected = ((TextView) view.findViewById(R.id.)).getText().toString();
                String selected = (String) adapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), CollegeDataActivity.class);
                intent.putExtra("collegeName", selected);
                startActivity(intent);
            }
        });
    }

}
