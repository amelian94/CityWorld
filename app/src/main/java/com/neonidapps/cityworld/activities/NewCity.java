package com.neonidapps.cityworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.neonidapps.cityworld.R;
import com.neonidapps.cityworld.models.Place;
import com.neonidapps.cityworld.utils.UtilsCode;
import com.squareup.picasso.Picasso;

/**
 * Created by Neonidas on 27/06/2017.
 */

public class NewCity extends AppCompatActivity implements View.OnClickListener {

    private Place place;

    private ImageView imageViewPreview;
    private ImageView imageViewLoadPreviewPicture;
    private EditText editTextAddName;
    private EditText editTextAddImageLink;
    private EditText editTextAddDesc;
    private RatingBar ratingBarCity;

    private FloatingActionButton fabSaveCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_city);

        place= new Place();

        imageViewLoadPreviewPicture = (ImageView) findViewById(R.id.imageViewAddLoadPreviewPicture);
        imageViewPreview= (ImageView) findViewById(R.id.imageViewAddPreview);

        editTextAddName= (EditText) findViewById(R.id.editTextAddName);
        editTextAddImageLink= (EditText) findViewById(R.id.editTextAddImageLink);
        editTextAddDesc= (EditText) findViewById(R.id.editTextAddDesc);

        ratingBarCity = (RatingBar) findViewById(R.id.ratingBarAddCity);

        fabSaveCity= (FloatingActionButton) findViewById(R.id.fabSaveCity);

        fabSaveCity.setOnClickListener(this);
        imageViewLoadPreviewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreviewPicture(editTextAddImageLink.getText().toString());
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        place= UtilsCode.getPlaceFromBundle(getIntent().getExtras());
        editTextAddName.setText(place.getName());
        editTextAddDesc.setText(place.getDesc());
        editTextAddImageLink.setText(place.getPicture());
        loadPreviewPicture(place.getPicture());
        ratingBarCity.setRating((float) place.getRate());

    }

    @Override
    public void onClick(View v) {

        if (isValidDataFormatNewCity()){

            Intent intent= new Intent();

            intent.putExtra("NAME",editTextAddName.getText().toString());
            intent.putExtra("IMAGE_LINK",editTextAddImageLink.getText().toString());
            intent.putExtra("DESC",editTextAddDesc.getText().toString());
            intent.putExtra("RATE", (double) ratingBarCity.getRating());

            //startActivity(intent);
            setResult(RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(this,"Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadPreviewPicture(String url){
        if (!url.isEmpty()){
            Picasso.with(NewCity.this).load(url).fit().into(imageViewPreview);
        }
    }

    private boolean isValidDataFormatNewCity(){
        boolean result=false;
        if (editTextAddName.getText().length() > 0 &&
                editTextAddDesc.getText().length() > 0 &&
                editTextAddImageLink.getText().length() > 0){
            result= true;
        }
        return result;
    }

}
