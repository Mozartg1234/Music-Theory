package com.example.musictheory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;

public class optionsQuestionAnswerFragment extends Fragment {

    private static final String ARG_OPTIONS = "options";

    public optionsQuestionAnswerFragment() {
        // Required empty public constructor
    }

    public static optionsQuestionAnswerFragment newInstance(ArrayList<String> options) {
        optionsQuestionAnswerFragment fragment = new optionsQuestionAnswerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options_question_answer, container, false);

        if (getArguments() != null) {
            ArrayList<String> options = getArguments().getStringArrayList(ARG_OPTIONS);

            Button button1 = view.findViewById(R.id.button2);
            Button button2 = view.findViewById(R.id.button3);
            Button button3 = view.findViewById(R.id.button4);
            Button button4 = view.findViewById(R.id.button5);

            // Set options text, ensuring there are enough options
            if (options != null && options.size() >= 4) {
                button1.setText(options.get(0));
                button2.setText(options.get(1));
                button3.setText(options.get(2));
                button4.setText(options.get(3));
            }
        }

        return view;
    }
}
