package com.example.musictheory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LessonsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lessons_list);
        Intent intent = getIntent();

        // Adjust window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final ArrayList<LessonsView> arrayList = new ArrayList<>();

        // Load JSON data from the assets and populate the ArrayList
        try {
            JSONObject jsonObject = JsonReader.readJsonFromAssets(this, "Lessons.json");
            if (jsonObject.has("lessons")) {
                JSONArray lessonsArray = jsonObject.getJSONArray("lessons");

                for (int i = 0; i < lessonsArray.length(); i++) {
                    JSONObject lessonObject = lessonsArray.getJSONObject(i);

                    // Extract topic
                    if (lessonObject.has("topic")) {
                        String chapter = lessonObject.getString("topic");

                        // Extract flashcards
                        ArrayList<String> flashcards = new ArrayList<>();
                        if (lessonObject.has("flashcards")) {
                            JSONArray flashcardsArray = lessonObject.getJSONArray("flashcards");
                            for (int b = 0; b < flashcardsArray.length(); b++) {
                                flashcards.add(flashcardsArray.getString(b));
                            }
                        } else {
                            Log.d("message", "No flashcards array found");
                        }

                        // Extract questions
                        ArrayList<Question> questions = new ArrayList<>();
                        if (lessonObject.has("questionObject")) {
                            JSONArray questionsArray = lessonObject.getJSONArray("questionObject");

                            for (int x = 0; x < questionsArray.length(); x++) {
                                JSONObject questionObject = questionsArray.getJSONObject(x);
                                String questionText = questionObject.getString("Text");

                                // Extract options
                                JSONArray optionsArray = questionObject.getJSONArray("Options");
                                ArrayList<String> options = new ArrayList<>();
                                for (int f = 0; f < optionsArray.length(); f++) {
                                    options.add(optionsArray.getString(f));
                                }

                                int correctAnswer = questionObject.getInt("CorrectOption");
                                questions.add(new Question(questionText, options, correctAnswer));
                            }
                        } else {
                            Log.d("message", "No questions array found");
                        }

                        // Create LessonsView object and add it to the array list
                        LessonsView lessonsObject = new LessonsView(R.drawable.img, chapter, "4.5", questions, flashcards);
                        arrayList.add(lessonsObject);
                    } else {
                        Log.d("message", "No topic found for lesson " + i);
                    }
                }
            } else {
                Log.d("message", "No lessons found in JSON file ");
            }
        } catch (JSONException e) {
            Log.e("message", "JSON exception", e);
        } catch (Exception e) {
            Log.e("message", "Exception occurred", e);
        }

        // Set up ListView and adapter
        ListView lessonsListView = findViewById(R.id.list);
        if (lessonsListView != null) {
            LessonsViewAdapter lessonsArrayAdapter = new LessonsViewAdapter(this, arrayList);
            lessonsListView.setAdapter(lessonsArrayAdapter);

            // Set item click listener for ListView
            lessonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LessonsListActivity.this, Lesson.class);
                    LessonsView selectedLesson = arrayList.get(position);
                    intent.putExtra("selectedLesson", selectedLesson);
                    Toast.makeText(view.getContext(), "Clicked position " + position, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });
        } else {
            Log.d("message", "ListView not found in the layout.");
        }
    }
}
