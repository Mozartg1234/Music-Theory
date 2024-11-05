package com.example.musictheory;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

public class questionfragment extends Fragment {

    private static final String ARG_QUESTION_TEXT = "questionText";
    private static final String ARG_OPTIONS = "options";
    private static final String ARG_CURRENT_INDEX = "currentIndex";
    private static final String ARG_CORRECT_VALUE = "correctValue";

    private OnIndexPass indexPasser;

    public static questionfragment newInstance(String questionText, ArrayList<String> options, int currentIndex, String correctAnswer) {
        questionfragment fragment = new questionfragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_TEXT, questionText);
        args.putStringArrayList(ARG_OPTIONS, options);
        args.putInt(ARG_CURRENT_INDEX, currentIndex);
        args.putString(ARG_CORRECT_VALUE, correctAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnIndexPass {
        void onIndexPass(int currentIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            indexPasser = (OnIndexPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnIndexPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionfragment, container, false);

        if (getArguments() != null) {
            String questionText = getArguments().getString(ARG_QUESTION_TEXT);
            ArrayList<String> options = getArguments().getStringArrayList(ARG_OPTIONS);
            int currentIndex = getArguments().getInt(ARG_CURRENT_INDEX);
            String correctAnswer = getArguments().getString(ARG_CORRECT_VALUE);

            TextView questionTextView = view.findViewById(R.id.textView2);
            questionTextView.setText(questionText);

            Button button1 = view.findViewById(R.id.button6);
            Button button2 = view.findViewById(R.id.button7);
            Button button3 = view.findViewById(R.id.button8);
            Button button4 = view.findViewById(R.id.button9);

            if (options != null && options.size() >= 4) {
                button1.setText(options.get(0));
                button2.setText(options.get(1));
                button3.setText(options.get(2));
                button4.setText(options.get(3));
            }

            button1.setOnClickListener(v -> checkAndPassIndex(button1, correctAnswer, currentIndex));
            button2.setOnClickListener(v -> checkAndPassIndex(button2, correctAnswer, currentIndex));
            button3.setOnClickListener(v -> checkAndPassIndex(button3, correctAnswer, currentIndex));
            button4.setOnClickListener(v -> checkAndPassIndex(button4, correctAnswer, currentIndex));
        }
        return view;
    }

    private void checkAndPassIndex(Button button, String correctValue, int currentIndex) {
        if (button.getText().toString().equals(correctValue)) {
            Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
            currentIndex++; // Increment currentIndex if the answer is correct
            passIndex(currentIndex); // Send the updated currentIndex to the activity only if the answer is correct
        } else {
            Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
            // Optionally provide feedback to the user about the incorrect answer
        }
    }

    private void passIndex(int currentIndex) {
        if (indexPasser != null) {
            indexPasser.onIndexPass(currentIndex);
        }
    }
}
