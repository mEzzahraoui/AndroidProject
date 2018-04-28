package com.example.meriame.authenticationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PickplaceActivity extends AppCompatActivity {
    private TextView get_place;
    int PLACE_PIKER_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_pickplace);
        get_place=(TextView)findViewById(R.id.textview);
        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(PickplaceActivity.this);
                    startActivityForResult(intent,PLACE_PIKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode==PLACE_PIKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data,this);
                String address=String.format("Place:%s",place.getAddress());
                get_place.setText(address);

            }
        }*/
    }
}
