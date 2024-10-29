package com.example.musictheory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link textViewQuestionAnswer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class textViewQuestionAnswer extends Fragment {

    private static final String ARG_QUESTION_TEXT = "question_text";

    public textViewQuestionAnswer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionText The text of the question to display.
     * @return A new instance of fragment textViewQuestionAnswer.
     */
    public static textViewQuestionAnswer newInstance(String questionText) {
        textViewQuestionAnswer fragment = new textViewQuestionAnswer();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_TEXT, questionText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_view_question_answer, container, false);

        // Retrieve and display the question text
        if (getArguments() != null) {
            String questionText = getArguments().getString(ARG_QUESTION_TEXT);
            TextView questionTextView = view.findViewById(R.id.textView);
            questionTextView.setText(questionText);
        }

        return view;
    }
}
