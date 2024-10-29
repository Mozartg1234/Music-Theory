package com.example.musictheory;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LessonsView implements Serializable {
    private int mNumbersImageId;  // Holds the resource ID for the image

    // TextView 1: Holds the number in digits (e.g., "1")
    private String mNumberInDigit;

    // TextView 2: Holds the number in text (e.g., "One")
    private String mNumberInText;

    private ArrayList<Question> mQuestions;

    private ArrayList<String> mFlashcards;


    // Constructor to set the values for all the parameters of a single view
    public LessonsView(int numbersImageId, String numberInDigit, String numberInText, ArrayList<Question> Question, ArrayList<String> Flashcards) {
        mNumbersImageId = numbersImageId;
        mNumberInDigit = numberInDigit;
        mNumberInText = numberInText;
        mQuestions = Question;
        mFlashcards = Flashcards;
    }

    // Getter method for returning the image resource ID
    public int getNumbersImageId() {

        return mNumbersImageId;
    }

    // Getter method for returning the number in digits (TextView 1)
    public String getNumberInDigit() {

        return mNumberInDigit;
    }

    // Getter method for returning the number in text (TextView 2)
    public String getNumberInText() {

        return mNumberInText;
    }

    public ArrayList<Question> getmQuestion() {

        return mQuestions;
    }

    public ArrayList<String> getFlashcards() {
        return mFlashcards;
    }

}
