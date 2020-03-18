package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;

import java.util.List;

import static com.udacity.sandwichclub.utils.JsonUtils.parseSandwichJson;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String TAG = "Test";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;
    ImageView ingredientsIv;
    TextView mOriginTv,mAlsoknownTv,mIngredientsTv,mDescriptionTv,mTv2,mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);

        ingredientsIv = findViewById (R.id.image_iv);
        mOriginTv = findViewById (R.id.origin_tv);
        mAlsoknownTv = findViewById (R.id.also_known_tv);
        mIngredientsTv = findViewById (R.id.ingredients_tv);
        mDescriptionTv = findViewById (R.id.description_tv);
        mTv1=findViewById (R.id.tv1);
        mTv2=findViewById (R.id.tv2);


        Intent intent = getIntent ();
        if (intent == null) {
            closeOnError ();
        }

        int position = intent.getIntExtra (EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError ();
            return;
        }

        String[] sandwiches = getResources ().getStringArray (R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = parseSandwichJson (json);
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError ();
            return;
        }

        populateUI ();
        Picasso.with (this)
                .load (sandwich.getImage ())
                .into (ingredientsIv);

        setTitle (sandwich.getMainName ());
    }

    private void closeOnError() {
        finish ();
        Toast.makeText (this, R.string.detail_error_message, Toast.LENGTH_SHORT).show ();
    }

    private void populateUI() {
        if(!"".equals (sandwich.getPlaceOfOrigin ())){
            mAlsoknownTv.setText (sandwich.getPlaceOfOrigin ());
        }
        else {
            mTv1.setVisibility (View.GONE);
            mOriginTv.setVisibility (View.GONE);
        }
        mOriginTv.setText (sandwich.getPlaceOfOrigin ());
        String alsoKnownAs=populateString (sandwich.getAlsoKnownAs ());
        System.out.println ("AlsoKnownAs  : "+alsoKnownAs);
        if(!"".equals (alsoKnownAs)){
            mAlsoknownTv.setText (alsoKnownAs);
        }
        else {
            mTv2.setVisibility (View.GONE);
            mAlsoknownTv.setVisibility (View.GONE);
        }
        mDescriptionTv.setText (sandwich.getDescription ());
        mIngredientsTv.setText (populateString (sandwich.getIngredients ()));

    }

    private String populateString(List<String> list) {
        String flatList = "";
        for (String name : list) {
            flatList = flatList.concat (name + "\n");
        }
        return flatList.trim ();
    }
}
