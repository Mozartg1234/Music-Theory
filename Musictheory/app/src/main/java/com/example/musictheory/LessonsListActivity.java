package com.example.musictheory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LessonsListActivity extends AppCompatActivity {

    private ArrayList<LessonView> arrayList; // Define arrayList as a member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lessons_list);

        // Retrieve arrayList from the Intent
        Intent intent = getIntent();
        arrayList = (ArrayList<LessonView>) intent.getSerializableExtra("arrayList");

        // Check if arrayList is null
        if (arrayList == null) {
            Log.e("message", "arrayList is null.");
            return; // Exit early if no data was passed
        }

        // Adjust window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up ListView and adapter
        ListView lessonsListView = findViewById(R.id.list);
        if (lessonsListView != null) {
            LessonsViewAdapter lessonsArrayAdapter = new LessonsViewAdapter(this, arrayList);
            lessonsListView.setAdapter(lessonsArrayAdapter);

            // Set item click listener for ListView
            lessonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent lessonIntent = new Intent(LessonsListActivity.this, LessonActivity.class);
                    LessonView selectedLesson = arrayList.get(position);

                    lessonIntent.putExtra("selectedLesson", selectedLesson);
                    startActivity(lessonIntent);
                }
            });
        } else {
            Log.d("message", "ListView not found in the layout.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_key_value), Context.MODE_PRIVATE);
        Set<String> completedChapters = sharedPref.getStringSet("completedChapters", new HashSet<>());
        for (int x = 0; x < arrayList.size(); x++) {
            LessonView currentLesson = arrayList.get(x);
            String chapterName = currentLesson.getChapterName();
            if (completedChapters.contains(chapterName)) {
                currentLesson.setStatus("Completed");
            }
            else{
                currentLesson.setStatus("Incomplete");
            }
        }
        ListView lessonsListView = findViewById(R.id.list);
        lessonsListView.invalidateViews();
    }
}
