package com.example.musictheory;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question implements Serializable {
    private String text;
    private ArrayList <String> options;
    private int correctAnswer;

    public Question(String text, ArrayList<String> options,int correctAnswer){

        this.text= text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {

        return text;
    }

    public ArrayList<String> getOptions(){

        return options;
    }

    public int getCorrectAnswer(){
        return correctAnswer;
    }
}

