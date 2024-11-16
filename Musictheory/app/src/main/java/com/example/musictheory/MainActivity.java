package com.example.musictheory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<LessonView> arrayList = new ArrayList<>(); // Moved to a member variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load JSON data from the assets and populate the ArrayList
        try {
            JSONObject jsonObject = JsonReader.readJsonFromAssets(this, "Lessons.json");
            if (jsonObject.has("lessons")) {
                JSONArray lessonsArray = jsonObject.getJSONArray("lessons");

                // Retrieve the completed chapters from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_key_value), Context.MODE_PRIVATE);
                Set<String> completedChapters = sharedPreferences.getStringSet("completedChapters", new HashSet<>());

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

                        LessonView lessonsObject = new LessonView(R.drawable.img, chapter, "Incomplete", questions, flashcards);
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
    }

    public void tableofContents(View view) {
        Intent intent = new Intent(this, LessonsListActivity.class);
        intent.putExtra("arrayList", arrayList); // Ensure LessonView implements Serializable
        startActivity(intent);
    }

    public void startResumeButtonClick(View view) {
        // Get hash set from shared preferences
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_key_value), Context.MODE_PRIVATE);
        Set<String> completedChapters = sharedPref.getStringSet("completedChapters", new HashSet<>());

        // Validate and handle empty completedChapters
        if (completedChapters == null || completedChapters.isEmpty()) {
            // If no chapters are completed, navigate to LessonsListActivity
            Intent intent = new Intent(this, LessonsListActivity.class);
            intent.putExtra("arrayList", arrayList); // Ensure LessonView implements Serializable
            startActivity(intent);
            return;
        } else {
            Log.d("Completed Chapters", completedChapters.toString());

            // Iterate through the arrayList to find the first incomplete chapter
            for (int x = 0; x < arrayList.size(); x++) {
                LessonView currentLesson = arrayList.get(x);
                String chapterName = currentLesson.getChapterName(); // Access the exact chapter identifier

                // Check if the chapter is not in the completedChapters set
                if (!completedChapters.contains(chapterName)) {
                    // Start LessonActivity with the first incomplete lesson
                    Intent lessonIntent = new Intent(MainActivity.this, LessonActivity.class);
                    Log.d("Starting Chapter", chapterName);
                    lessonIntent.putExtra("selectedLesson", currentLesson);
                    startActivity(lessonIntent);
                    return; // Exit after navigating to the first incomplete chapter
                }
            }
        }

        // If no incomplete chapters are found, show a congratulatory message
        Toast.makeText(getApplicationContext(), "Congratulations, you have finished the course", Toast.LENGTH_LONG).show();
        completedChapters.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve SharedPreferences
        Button button = findViewById(R.id.start_resume_button);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_key_value), Context.MODE_PRIVATE);

        // Retrieve the stored HashSet, or an empty HashSet if none is found
        Set<String> completedChapters = sharedPref.getStringSet("completedChapters", new HashSet<>());

        if (completedChapters == null || completedChapters.isEmpty()) {
            // No chapters completed, set the button to "Start"
            Log.d("SharedPreferences", "No completed chapters found.");
            button.setText("Start");
        } else if (completedChapters.size() == arrayList.size()) {
            // All chapters completed, set the button to "Start" to restart the course
            Log.d("SharedPreferences", "All chapters completed. Resetting to Start.");
            button.setText("Start");
        } else {
            // Some chapters completed, set the button to "Resume"
            Log.d("SharedPreferences", "Some chapters completed: " + completedChapters);
            button.setText("Resume");
        }

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

    }


}
