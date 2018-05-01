package com.example.meriame.authenticationtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InfoPlace extends AppCompatActivity {
   /* //DECLARE THE FIELDS
    EditText userNameEditText, userStautsEditText;
    ImageView userImageProfileView;
    LinearLayout saveProfileBtn;
    String namePlace;

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
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoplace);
     /*  ImpotInfoPlace();


        Intent myIntent = getIntent(); // gets the previously created intent
        String placeTitle = myIntent.getStringExtra("PlaceName");

        Toast.makeText(this, placeTitle, Toast.LENGTH_SHORT).show();
        //ASSIGN ID'S
        userNameEditText = (EditText) findViewById(R.id.placeName);
        userStautsEditText = (EditText) findViewById(R.id.placeStatus);

        userImageProfileView = (ImageView) findViewById(R.id.placeImageView);

        saveProfileBtn = (LinearLayout) findViewById(R.id.savePlace);

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

        //ONCLICK SAVE PROFILE BTN
        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });*/

    }

    /*
    private void ImpotInfoPlace() {

        final String username, userStatus;

        username = userNameEditText.getText().toString().trim();
        userStatus = userStautsEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userStatus)) {

            if (imageHoldUri != null) {

                mProgress.setTitle("Saveing Place");
                mProgress.setMessage("Please wait....");
                mProgress.show();

                StorageReference mChildStorage = mStorageRef.child("Pic_Places").child(imageHoldUri.getLastPathSegment());
                String profilePicUrl = imageHoldUri.getLastPathSegment();

                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        final Uri imageUrl = taskSnapshot.getDownloadUrl();
                        /*mUserDatabse.child("username").setValue(username);
                        mUserDatabse.child("status").setValue(userStatus);
                        mUserDatabse.child("userid").setValue(mAuth.getCurrentUser().getUid());
                        mUserDatabse.child("imageurl").setValue(imageUrl.toString());*/
    /*                    Place place = new Place();
                        place.setName(username);
                        place.setStatus(userStatus);
                        place.setNote(5);
                        place.setAddedBy(mAuth.getCurrentUser().getUid());
                        place.setUri(imageUrl.toString());
                        place.setLatitude(FMaps.latitude);
                        place.setLongitude(FMaps.longitude);
                        sendPlaceData(place);
                        mProgress.dismiss();

                        finish();
                        Intent moveToHome = new Intent(InfoPlace.this, MainActivity.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);


                    }
                });
            } else {
                Toast.makeText(InfoPlace.this, "Please select the pic", Toast.LENGTH_LONG).show();
            }

        } else {

            Toast.makeText(InfoPlace.this, "Please enter name and status", Toast.LENGTH_LONG).show();

        }

    }


    private void sendPlaceData(Place place2) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Places").child(place2.getName());
        Place place = place2;
        myRef.setValue(place);
    }

*/
}

