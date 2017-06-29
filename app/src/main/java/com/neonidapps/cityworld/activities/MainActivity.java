package com.neonidapps.cityworld.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.neonidapps.cityworld.R;
import com.neonidapps.cityworld.adapters.RecyclerViewAdapter;
import com.neonidapps.cityworld.models.Place;
import com.neonidapps.cityworld.utils.UtilsCode;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int ADD_CITY_REQUEST = 1;  // The request code

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton fabAdd;

    private Realm realm;
    private RealmResults<Place> placesRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        realm = Realm.getDefaultInstance();
        placesRealm = getAllPlaces();

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.where(Place.class).findAll().deleteAllFromRealm();
//            }
//        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        setHideShowFAB();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(placesRealm, this, new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Place place, int position) {
                Intent intent = new Intent(MainActivity.this, NewCity.class);
                intent = UtilsCode.putIntentExtrasFronPlaceObject(intent, place);
                startActivityForResult(intent, ADD_CITY_REQUEST);
            }

            @Override
            public void onDeleteClick(final Place place, final int position) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        showAlertDialogDeleteCity("Delete city", "Are you sure you want to delete " + place.getName(), position);
                    }
                });
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        fabAdd = (FloatingActionButton) findViewById(R.id.fabAddCity);
        fabAdd.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITY_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                addPlace(UtilsCode.getPlaceFromBundle(data.getExtras()));
            }
        }
    }

    public RealmResults<Place> getAllPlaces() {

        return realm.where(Place.class).findAll();

    }


    public void addPlace(final Place place) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(place);
                placesRealm = getAllPlaces();
                mAdapter.notifyDataSetChanged();
            }

        });
    }


    @Override
    public void onClick(View v) {
        int id;
        if (!placesRealm.isEmpty()) {
            id = realm.where(Place.class).max("Id").intValue();
        } else {
            id = 0;
        }
        Intent intent = new Intent(this, NewCity.class);
        intent = UtilsCode.putIntentExtrasFronPlaceObject(intent, new Place("","","",0.0,id+1));
        startActivityForResult(intent, ADD_CITY_REQUEST);

    }

    private void setHideShowFAB(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy >0)
                    fabAdd.hide();
                else if (dy<0)
                    fabAdd.show();
            }
        });
    }

    private void showAlertDialogDeleteCity(String title, String message, final int position){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("REMOVE", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton){
                        deleteCity(position);
                        Toast.makeText(MainActivity.this, "City Removed Succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", null).show();
    }

    private void deleteCity(int position){
        realm.beginTransaction();
        placesRealm.get(position).deleteFromRealm();
        realm.commitTransaction();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }


}
