package com.example.musictheory;

import java.io.Serializable;
import java.util.ArrayList;

public class LessonView implements Serializable {
    private int mImageId;  // Holds the resource ID for the image

    // TextView 1: Holds the number in digits (e.g., "1")
    private String mChapterName;

    // TextView 2: Holds the number in text (e.g., "One")
    private String mstatus;

    private ArrayList<Question> mQuestions;

    private ArrayList<String> mFlashcards;


    // Constructor to set the values for all the parameters of a single view
    public LessonView(int numbersImageId, String chapterName, String Status, ArrayList<Question> Question, ArrayList<String> Flashcards) {
        mImageId = numbersImageId;
        mChapterName = chapterName;
        mstatus = Status;
        mQuestions = Question;
        mFlashcards = Flashcards;
    }

    // Getter method for returning the image resource ID
    public int getImageId() {

        return mImageId;
    }

    // Getter method for returning the number in digits (TextView 1)
    public String getChapterName() {

        return mChapterName;
    }

    // Getter method for returning the number in text (TextView 2)
    public String getStatus() {

        return mstatus;
    }

    public void setStatus(String Value){
        mstatus = Value;
    }

    public ArrayList<Question> getmQuestion() {

        return mQuestions;
    }

    public ArrayList<String> getFlashcards() {
        return mFlashcards;
    }

}
