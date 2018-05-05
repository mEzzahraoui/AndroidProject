package com.example.meriame.authenticationtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class InfoPlace extends AppCompatActivity {
    String TAG= "Message";
    TextView text;
    String TaString;
    TextView userNameEditText, userStautsEditText;
    ImageView userImageProfileView;

    //FIREBASE AUTHENTICATION FIELDS
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //FIREBASE DATABASE FIELDS
    DatabaseReference mUserDatabse;
    StorageReference mStorageRef;

    //IMAGE HOLD URI
    Uri imageHoldUri = null;

    //PROGRESS DIALOG
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_place);

        Bundle extras = getIntent().getExtras();
        TaString = extras.getString("PlaceName");
        text=(TextView)findViewById(R.id.textView);
        text.setText(TaString);

        //ASSIGN INSTANCE TO FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //LOGIC CHECK USER
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    finish();
                    Intent moveToHome = new Intent(InfoPlace.this, MainActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }

            }
        };

        //PROGRESS DIALOG
        mProgress = new ProgressDialog(this);
        //FIREBASE DATABASE INSTANCE
        mUserDatabse = FirebaseDatabase.getInstance().getReference().child("Places");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("Places").child(TaString);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Place value = dataSnapshot.getValue(Place.class);
                userNameEditText=(TextView)findViewById(R.id.placeName);
                userStautsEditText=(TextView)findViewById(R.id.placeStatus);
                userImageProfileView = (ImageView) findViewById(R.id.placeImageView);
                userNameEditText.setText(value.getName());
                userStautsEditText.setText(value.getStatus());
                StorageReference storageReference = mStorageRef.child("Pic_Places").child(value.getUri());
                //Glide.with(InfoPlace.this ).using(new FirebaseImageLoader()).load(storageReference).into(userImageProfileView);
                Picasso.get().load(value.getUri()).resize(200, 200).centerCrop().into(userImageProfileView);
                Log.d(TAG, "Value is: " );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

}
