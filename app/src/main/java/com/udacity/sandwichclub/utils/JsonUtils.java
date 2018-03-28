package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        int i;
        if(json != null && !json.isEmpty()) {
            sandwich = new Sandwich();
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONObject jsonObjName = jsonObj.getJSONObject("name");
                sandwich.setMainName(jsonObjName.getString("mainName"));
                JSONArray jsonArrayKa = jsonObjName.getJSONArray("alsoKnownAs");
                List<String> knownAsList = new ArrayList<>();
                for(i=0; i<jsonArrayKa.length(); i++) {
                    knownAsList.add(jsonArrayKa.getString(i));
                }
                sandwich.setAlsoKnownAs(knownAsList);
                sandwich.setPlaceOfOrigin(jsonObj.getString("placeOfOrigin"));
                sandwich.setDescription(jsonObj.getString("description"));
                sandwich.setImage(jsonObj.getString("image"));
                JSONArray jsonArrayIngr = jsonObj.getJSONArray("ingredients");
                List<String> ingrList = new ArrayList<>();
                for(i=0; i<jsonArrayIngr.length(); i++) {
                    ingrList.add(jsonArrayIngr.getString(i));
                }
                sandwich.setIngredients(ingrList);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return sandwich;
    }
}
