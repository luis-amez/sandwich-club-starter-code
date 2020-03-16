package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;

        try {
            JSONObject sandwichJsonObject = new JSONObject(json);

            JSONObject nameJsonObject = sandwichJsonObject.getJSONObject(NAME_KEY);

            String mainName = nameJsonObject.getString(MAIN_NAME_KEY);

            JSONArray alsoKnownAsJsonArray = nameJsonObject.getJSONArray(ALSO_KNOWN_AS_KEY);
            List<String> alsoKnownAs = new ArrayList<>(alsoKnownAsJsonArray.length());
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            String placeOfOrigin = sandwichJsonObject.getString(PLACE_OF_ORIGIN_KEY);

            String description = sandwichJsonObject.getString(DESCRIPTION_KEY);

            String image = sandwichJsonObject.getString(IMAGE_KEY);

            JSONArray ingredientsJsonArray = sandwichJsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>(ingredientsJsonArray.length());
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sandwich;
    }
}
