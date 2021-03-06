package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv) ImageView mImageView;
    @BindView(R.id.also_known_tv) TextView mKnownAsText;
    @BindView(R.id.origin_tv) TextView mOriginText;
    @BindView(R.id.description_tv) TextView mDescriptionText;
    @BindView(R.id.ingredients_tv) TextView mIngredientsText;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
                .into(mImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        mKnownAsText.setText(replaceEmptyValue(toCommaSeparatedString(sandwich.getAlsoKnownAs())));
        mOriginText.setText(replaceEmptyValue(sandwich.getPlaceOfOrigin()));
        mDescriptionText.setText(replaceEmptyValue(sandwich.getDescription()));
        mIngredientsText.setText(replaceEmptyValue(toCommaSeparatedString(sandwich.getIngredients())));
    }

    private String toCommaSeparatedString(List<String> stringList) {
        String separator = ", ";
        StringBuilder sb = new StringBuilder();
        for (String s : stringList) {
            if(sb.length()>0)
                sb.append(separator);
            sb.append(s);
        }

        return sb.toString();
    }

    private String replaceEmptyValue(String value)
    {
        if(value==null || value.trim().equals(""))
            return getResources().getString(R.string.blank_sandwitch_data);

        return value;
    }

}
