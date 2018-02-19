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
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    private TextView tvAlsoKnown;
    private TextView tvDescription;
    private TextView tvIngredients;
    private TextView tvOrigin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvAlsoKnown = findViewById(R.id.also_known_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvOrigin = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Display views according to the Sandwich object
     */
    private void populateUI() {
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        StringBuilder builder = new StringBuilder();
        if (alsoKnownAs.size() != 0) {
            for (String name : alsoKnownAs) {
                builder.append(name);
                builder.append("\n");
            }
            tvAlsoKnown.setText(builder.toString().trim());
        } else {
            TextView tvAlsoKnownLabel = findViewById(R.id.also_known_label_tv);
            tvAlsoKnownLabel.setVisibility(View.INVISIBLE);
            tvAlsoKnown.setVisibility(View.INVISIBLE);
        }

        tvDescription.setText(sandwich.getDescription());

        List<String> ingredients = sandwich.getIngredients();
        builder = new StringBuilder();
        for (String ingredient : ingredients) {
            builder.append(ingredient);
            builder.append("\n");
        }
        tvIngredients.setText(builder.toString().trim());

        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            TextView tvOriginLabel = findViewById(R.id.detail_origin_tv);
            tvOriginLabel.setVisibility(View.INVISIBLE);
        } else {
            tvOrigin.setText(origin);
        }
    }
}
