package com.example.musictheory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LessonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_OPTIONS = "options";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LessonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionText Parameter 1.
     * @param options Parameter 2.
     * @return A new instance of fragment LessonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonsFragment newInstance(String questionText, ArrayList<String> options) {
        LessonsFragment fragment = new LessonsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, questionText);
        args.putStringArrayList(ARG_OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_OPTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lessons, container, false);
        // Retrieve and display the question text
        if (getArguments() != null) {
            String questionText = getArguments().getString(ARG_PARAM1);
            TextView questionTextView = view.findViewById(R.id.textView2);
            questionTextView.setText(questionText);
        }
        if (getArguments() != null) {
            ArrayList<String> options = getArguments().getStringArrayList(ARG_OPTIONS);


            Button button1 = view.findViewById(R.id.button6);
            Button button2 = view.findViewById(R.id.button7);
            Button button3 = view.findViewById(R.id.button8);
            Button button4 = view.findViewById(R.id.button9);


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