package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String TAG = "JSON UTILITY CLASS";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichDetails;
        sandwichDetails = new JSONObject(json);
        List<String> alsoKnownAs=populateList (sandwichDetails.getJSONObject("name").getJSONArray ("alsoKnownAs"));
        List<String> ingredients=populateList (sandwichDetails.getJSONArray ("ingredients"));
        Sandwich sandwich=new Sandwich();
        sandwich.setMainName(sandwichDetails.getJSONObject("name").getString ("mainName"));
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setPlaceOfOrigin (sandwichDetails.getString ("placeOfOrigin"));
        sandwich.setDescription (sandwichDetails.getString ("description"));
        sandwich.setImage (sandwichDetails.getString ("image"));
        sandwich.setIngredients (ingredients);
        return sandwich;
    }
    private static List<String> populateList(JSONArray jsonArray) throws JSONException {
        List<String> list=new ArrayList<> ();
        for(int i=0;i<jsonArray.length ();i++) {
            list.add (jsonArray.getString (i).trim ());
        }
        return list;
    }
}
