package com.neonidapps.cityworld.utils;

import android.content.Intent;
import android.os.Bundle;

import com.neonidapps.cityworld.models.Place;

/**
 * Created by Neonidas on 29/06/2017.
 */

public class UtilsCode {

    static public Intent putIntentExtrasFronPlaceObject(Intent intent, Place place){
        intent.putExtra("NAME",place.getName());
        intent.putExtra("IMAGE_LINK",place.getPicture());
        intent.putExtra("DESC",place.getDesc());
        intent.putExtra("RATE", place.getRate());
        intent.putExtra("ID", place.getId());

        return intent;
    }

    static public Place getPlaceFromBundle(Bundle extras) {

        Place place= new Place();

        if (extras != null) {
            String name = extras.getString("NAME");
            String desc = extras.getString("DESC");
            String imageLink = extras.getString("IMAGE_LINK");
            double rate = extras.getDouble("RATE");
            int id= extras.getInt("ID");

            place = new Place(imageLink, name, desc, rate,id);
        }
        return place;
    }

}
