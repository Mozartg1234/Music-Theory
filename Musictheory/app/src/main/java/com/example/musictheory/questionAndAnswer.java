package com.example.musictheory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class questionAndAnswer extends AppCompatActivity {
    private LessonsView selectedLesson; // Declare it at class level
    private int currentQuestionIndex = 0; // Track the current question index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_and_answer);

        Intent intent = getIntent();
        selectedLesson = (LessonsView) intent.getSerializableExtra("selectedLesson");

        if (selectedLesson != null) {
            showQuestion(currentQuestionIndex); // Display the first question

            // Set up the button click listener
            Button mybutton = findViewById(R.id.button);
            mybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next();
                }
            });
        } else {
            Log.d("message", "No lesson data received.");
        }
    }

    private void showQuestion(int index) {
        ArrayList<Question> questions = selectedLesson.getmQuestion();
        if (index < questions.size()) {
            Question displayQuestion = questions.get(index);
            String questionText = displayQuestion.getText();
            ArrayList<String> options = displayQuestion.getOptions();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LessonsFragment Lesson = LessonsFragment.newInstance(questionText,options);
            fragmentTransaction.replace(R.id.fragmentContainerView2, Lesson);

            fragmentTransaction.commit();
        } else {
            Log.d("message", "No more questions.");
            // Optionally, you can disable the button or show a message when there are no more questions.
        }
    }

    public void next() {
        currentQuestionIndex++; // Increment the question index
        showQuestion(currentQuestionIndex); // Show the next question
    }


    @Override
    public void onBackPressed() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--; // Decrement the question index
            showQuestion(currentQuestionIndex); // Show the previous question
        } else {
            super.onBackPressed(); // Exit activity if on the first question
        }
    }

}

