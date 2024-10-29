package com.example.musictheory;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static JSONObject readJsonFromAssets(Context context, String fileName) {
        String json = null;
        InputStream inputStream = null;
        try {
            // Load the JSON file from the assets folder
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(fileName);

            // Read the file into a string
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            // Convert the byte array to a String using UTF-8 encoding
            json = new String(buffer, StandardCharsets.UTF_8);

            // Convert the string to a JSON object
            return new JSONObject(json);

        } catch (IOException | JSONException e) {
            // Print the error stack trace if something goes wrong
            e.printStackTrace();
            return null;

        } finally {
            // Close the InputStream safely
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
