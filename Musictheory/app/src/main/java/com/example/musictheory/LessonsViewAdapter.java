package com.example.musictheory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LessonsViewAdapter extends ArrayAdapter<LessonsView> {
    // Constructor for LessonsViewAdapter
    public LessonsViewAdapter(@NonNull Context context, ArrayList<LessonsView> arrayList) {
        // Pass the context and arrayList to the super constructor of ArrayAdapter
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Recyclable view
        View currentItemView = convertView;

        // If the recyclable view is null, inflate the custom layout
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.customlistview, parent, false);
            Log.d("message:", "null " + position);
        } else {
            Log.d("message:", "not null " + position);
        }

        // Get the current LessonsView object at this position
        LessonsView currentNumberPosition = getItem(position);

        // Ensure the currentNumberPosition is not null
        assert currentNumberPosition != null;

        // Set the image resource
        ImageView numbersImage = currentItemView.findViewById(R.id.img_png);
        numbersImage.setImageResource(currentNumberPosition.getImageId());

        // Set the first text view (digit)
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getChapterName());

        return currentItemView;
    }
}
