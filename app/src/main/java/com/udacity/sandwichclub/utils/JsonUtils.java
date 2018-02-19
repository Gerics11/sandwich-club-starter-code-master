package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject baseJson = new JSONObject(json);
            JSONObject name = baseJson.getJSONObject("name");
            String mainName = name.optString("mainName");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownList.add(alsoKnownAs.optString(i));
            }

            String placeOfOrigin = baseJson.optString("placeOfOrigin");
            String description = baseJson.optString("description");
            String image = baseJson.optString("image");

            JSONArray ingredients = baseJson.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.optString(i));
            }

            return new Sandwich(mainName, alsoKnownList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
