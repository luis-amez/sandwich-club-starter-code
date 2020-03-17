package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        ImageView imageImageView = findViewById(R.id.image_iv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        LinearLayoutCompat alsoKnownSection = findViewById(R.id.also_known_section);
        LinearLayoutCompat originSection = findViewById(R.id.origin_section);

        setTitle(mSandwich.getMainName());
        if (!mSandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownTextView.setText(TextUtils.join(", ", mSandwich.getAlsoKnownAs()));
        } else {
            alsoKnownSection.setVisibility(LinearLayoutCompat.GONE);
        }
        if (!mSandwich.getPlaceOfOrigin().equals("")) {
            originTextView.setText(mSandwich.getPlaceOfOrigin());
        } else {
            originSection.setVisibility(LinearLayoutCompat.GONE);
        }
        descriptionTextView.setText(mSandwich.getDescription());
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(imageImageView);
        ingredientsTextView.setText(TextUtils.join(", ", mSandwich.getIngredients()));
    }
}
