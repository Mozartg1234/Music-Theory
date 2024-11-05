package com.example.musictheory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashcardsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private ArrayList<String> flashcards;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param flashcards List of flashcards.
     * @return A new instance of fragment FlashcardsFragment.
     */
    public static FlashcardsFragment newInstance(ArrayList<String> flashcards) {
        FlashcardsFragment fragment = new FlashcardsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, flashcards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flashcards = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcards, container, false);

        // Find the TextView elements in the layout

        TextView F2 = view.findViewById(R.id.textView3);


        // Set text for each TextView if available in the flashcards list
        if (flashcards != null) {

            if (flashcards.size() > 0) {
                F2.setText(flashcards.get(0));
            }

        }

        return view;
    }
}
