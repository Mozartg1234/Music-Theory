package com.example.musictheory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity implements questionfragment.OnIndexPass {
    private LessonView selectedLesson;
    private int currentIndex = 0;
    private boolean showingFlashcards = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson);

        Intent intent = getIntent();
        selectedLesson = (LessonView) intent.getSerializableExtra("selectedLesson");

        if (selectedLesson != null) {
            showFlashcard(currentIndex);

            Button myButton = findViewById(R.id.button);
            myButton.setOnClickListener(v -> next());
        } else {
            Log.d("Lesson", "No lesson data received.");
        }
    }

    private void showFlashcard(int index) {
        ArrayList<String> flashcards = selectedLesson != null ? selectedLesson.getFlashcards() : null;
        if (flashcards == null || flashcards.isEmpty()) return;

        int endIndex = Math.min(index + 1, flashcards.size());
        ArrayList<String> flashcardSubset = new ArrayList<>(flashcards.subList(index, endIndex));

        FlashcardsFragment cardFragment = FlashcardsFragment.newInstance(flashcardSubset);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView2, cardFragment)
                .commit();
    }

    public void toc() {
        Intent intent = new Intent(LessonActivity.this, LessonsListActivity.class);
        startActivity(intent);
    }

    private void showQuestion(int index) {
        Button next1 = findViewById(R.id.button);
        next1.setVisibility(View.GONE);
        ArrayList<Question> questions = selectedLesson != null ? selectedLesson.getmQuestion() : null;
        if (questions == null || index >= questions.size()) {
            Log.d("Lesson", "No more questions.");
            toc();
            return;
        }

        Question displayQuestion = questions.get(index);
        String questionText = displayQuestion.getText();
        ArrayList<String> options = displayQuestion.getOptions();
        int answerIndex = displayQuestion.getCorrectAnswer();
        String correctValue = options.get(answerIndex);

        questionfragment questionFragment = questionfragment.newInstance(questionText, options, currentIndex, correctValue);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, questionFragment)
                .commit();
    }

    // Receive currentIndex from questionfragment
    @Override
    public void onIndexPass(int index) {
        if (index > currentIndex) { // Check if the index passed is greater than the currentIndex
            this.currentIndex = index; // Update currentIndex only if it is greater (indicating a correct answer)
            Log.d("Lesson", "Updated currentIndex received from fragment: " + currentIndex);

            // Enable the button after receiving the index
            Button next1 = findViewById(R.id.button);
            next1.setEnabled(true);

            // Advance to the next question
            showQuestion(currentIndex);
        } else {
            // Handle incorrect answers if needed (e.g., show a message)
            Log.d("Lesson", "Answer was incorrect, not advancing to next question.");
        }
    }

    public void next() {
        ArrayList<String> flashcards = selectedLesson != null ? selectedLesson.getFlashcards() : null;

        if (showingFlashcards) {
            if (flashcards != null && currentIndex + 1 < flashcards.size()) {
                currentIndex++;
                showFlashcard(currentIndex);
            } else {
                showingFlashcards = false;
                currentIndex = 0;
                showQuestion(currentIndex);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (currentIndex > 0) {
            if (showingFlashcards) {
                currentIndex = Math.max(0, currentIndex - 1);
                showFlashcard(currentIndex);
            } else {
                currentIndex--;
                showQuestion(currentIndex);
            }
        } else {
            super.onBackPressed();
        }
    }
}
